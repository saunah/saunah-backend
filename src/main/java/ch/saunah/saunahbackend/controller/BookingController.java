package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.dto.BookingResponse;
import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.repository.BookingRepository;
import ch.saunah.saunahbackend.repository.UserRepository;
import ch.saunah.saunahbackend.service.BookingService;
import ch.saunah.saunahbackend.service.MailService;
import ch.saunah.saunahbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controls the different operations that can be done with booking.
 */
@RestController
@RequestMapping
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Operation(description = "Creates a new booking.")
    @PostMapping(path = "bookings")
    public @ResponseBody
    ResponseEntity<BookingResponse> createBooking(@RequestBody BookingBody bookingBody) throws Exception {
        BookingResponse bookingResponse = new BookingResponse(bookingService.addBooking(bookingBody));
        mailService.sendAdminOpenedBookingMail(userRepository.findByUserRole(UserRole.ADMIN), bookingRepository.findById(bookingResponse.getId()).get(), bookingRepository.findById(bookingResponse.getId()).get().getId());
        mailService.sendUserOpenedBookingMail(userService.getUser(bookingBody.getUserId()).getEmail(), bookingRepository.findById(bookingResponse.getId()).get());
        return ResponseEntity.ok(bookingResponse);
    }

    @Operation(description = "Returns the list of the bookings of the current user.")
    @GetMapping(path = "bookings")
    public @ResponseBody
    List<BookingResponse> getUserBookings(Principal principal) {
        User user = userService.getUserByMail(principal.getName());
        return bookingService.getAllBooking().stream().filter(x -> x.getUserId() == user.getId()).map(x -> new BookingResponse(x)).collect(Collectors.toList());
    }

    @Operation(description = "Returns the booking with the ID specified.")
    @GetMapping(path = "bookings/{id}")
    public @ResponseBody
    ResponseEntity<BookingResponse> getBooking(@PathVariable(value = "id", required = true) Integer id, Principal principal) throws AuthenticationException {
        Booking booking = bookingService.getBooking(id);
        User user = userService.getUserByMail(principal.getName());
        if (booking.getUserId() == user.getId() || UserRole.ADMIN.equals(user.getRole())) {
            return ResponseEntity.ok(new BookingResponse(booking));
        }
        throw new AuthenticationException("user is not authenticated to view this booking");
    }

    @Operation(description = "Approves a existing booking structure with the ID specified.")
    @PostMapping(path = "bookings/{id}/approve")
    public @ResponseBody
    ResponseEntity<String> approveBooking(@PathVariable(value = "id", required = true) Integer id) {
        bookingService.approveBooking(id);
        mailService.sendUserApprovedBookingMail(userRepository.findById(id).get().getEmail() , bookingRepository.findById(id).get());
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Cancels a existing booking structure with the ID specified.")
    @PostMapping(path = "bookings/{id}/cancel")
    public @ResponseBody
    ResponseEntity<String> cancelBooking(@PathVariable(value = "id", required = true) Integer id, Principal principal) throws AuthenticationException {
        User user = userService.getUserByMail(principal.getName());
        if (UserRole.ADMIN.equals(user.getRole())) {
            bookingService.cancelBooking(id);
            mailService.sendUserCanceledBookingMail(userRepository.findById(id).get().getEmail() , bookingRepository.findById(id).get());
            return ResponseEntity.ok("success");
        }
        throw new AuthenticationException("user is not authorized to cancel this booking");
    }

    @Operation(description = "Returns the list of all bookings.")
    @GetMapping(path = "allBookings")
    public @ResponseBody
    List<BookingResponse> getAllBooking() {
        return bookingService.getAllBooking().stream().map(x -> new BookingResponse(x)).collect(Collectors.toList());
    }
}
