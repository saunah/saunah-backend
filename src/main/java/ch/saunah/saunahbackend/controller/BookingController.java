package ch.saunah.saunahbackend.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.dto.BookingResponse;
import ch.saunah.saunahbackend.dto.SaunahApiResponse;
import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.BookingState;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.UserRepository;
import ch.saunah.saunahbackend.service.BookingService;
import ch.saunah.saunahbackend.service.MailService;
import ch.saunah.saunahbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controls the different operations that can be done with booking.
 */
@RestController
@RequestMapping
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Operation(description = "Creates a new booking.")
    @PostMapping(path = "bookings")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Booking created", content = @Content(schema = @Schema(implementation = BookingResponse.class))),
        @ApiResponse(responseCode = "400", description = "Bad request, set fields do not match with the conditions", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
        @ApiResponse(responseCode = "500", description = "Unable to send Mail", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class)))
    })
    public @ResponseBody
    ResponseEntity<BookingResponse> createBooking(@RequestBody BookingBody bookingBody, Principal principal) throws IllegalArgumentException, IOException {
        User currentUser = userService.getUserByMail(principal.getName());
        Booking booking = bookingService.addBooking(bookingBody, currentUser.getId());
        for (User admin : userRepository.findByRole(UserRole.ADMIN)) {
            mailService.sendAdminOpenedBookingMail(admin, booking);
        }
        mailService.sendUserOpenedBookingMail(currentUser.getEmail(), booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BookingResponse(booking));
    }

    @Operation(description = "Returns the list of the bookings of the current user.")
    @GetMapping(path = "bookings")
    public @ResponseBody
    List<BookingResponse> getUserBookings(Principal principal) {
        User user = userService.getUserByMail(principal.getName());
        return bookingService.getAllBooking().stream().filter(x -> x.getUserId() == user.getId()).map(BookingResponse::new).collect(Collectors.toList());
    }

    @Operation(description = "Returns the booking with the ID specified.")
    @GetMapping(path = "bookings/{id}")
    public @ResponseBody
    ResponseEntity<BookingResponse> getBooking(@PathVariable(value = "id") Integer id, Principal principal) throws NotFoundException, AuthenticationException {
        Booking booking = bookingService.getBooking(id);
        User user = userService.getUserByMail(principal.getName());
        if (booking.getUserId() == user.getId() || UserRole.ADMIN.equals(user.getRole())) {
            return ResponseEntity.ok(new BookingResponse(booking));
        }
        throw new AuthenticationException("user is not authenticated to view this booking");
    }

    @Operation(description = "Allows editing an existing Booking structure.")
    @PutMapping(path = "bookings/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Booking edit"),
        @ApiResponse(responseCode = "400", description = "Booking does not exist", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    public @ResponseBody
    ResponseEntity<BookingResponse> editBooking(@PathVariable(value = "id") Integer id, @RequestBody BookingBody bookingBody) throws IOException {
        Booking booking = bookingService.editBooking(id, bookingBody);
        if (booking.getState() == BookingState.APPROVED) {
            mailService.sendUserEditedBookingMail(userService.getUser(booking.getUserId()).getEmail(), booking);
        }
        return ResponseEntity.ok(new BookingResponse(booking));
    }

    @Operation(description = "Approves a existing booking structure with the ID specified.")
    @PutMapping(path = "bookings/{id}/approve")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Booking approved"),
        @ApiResponse(responseCode = "400", description = "Booking does not exist", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    public @ResponseBody
    ResponseEntity<String> approveBooking(@PathVariable(value = "id") Integer id) throws NotFoundException, IOException {
        bookingService.approveBooking(id);
        Booking booking = bookingService.getBooking(id);
        mailService.sendUserApprovedBookingMail(userService.getUser(booking.getUserId()).getEmail(), booking);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Cancels a existing booking structure with the ID specified.")
    @PutMapping(path = "bookings/{id}/cancel")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Booking canceled"),
        @ApiResponse(responseCode = "400", description = "Booking does not exist", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
        @ApiResponse(responseCode = "401", description = "Not authorized", content = @Content(schema = @Schema(implementation = SaunahApiResponse.class))),
    })
    public @ResponseBody
    ResponseEntity<String> cancelBooking(@PathVariable(value = "id") Integer id) throws IOException {
        bookingService.cancelBooking(id);
        Booking booking = bookingService.getBooking(id);
        mailService.sendUserCanceledBookingMail(userService.getUser(booking.getUserId()).getEmail(), booking);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Returns the list of all bookings.")
    @GetMapping(path = "bookings/all")
    public @ResponseBody
    List<BookingResponse> getAllBooking() {
        return bookingService.getAllBooking().stream().map(BookingResponse::new).collect(Collectors.toList());
    }
}
