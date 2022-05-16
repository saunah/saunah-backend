package ch.saunah.saunahbackend.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import ch.saunah.saunahbackend.dto.BookingPriceResponse;
import ch.saunah.saunahbackend.dto.BookingSaunaResponse;
import ch.saunah.saunahbackend.model.*;
import ch.saunah.saunahbackend.repository.UserRepository;
import ch.saunah.saunahbackend.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.dto.BookingResponse;
import ch.saunah.saunahbackend.service.BookingService;
import ch.saunah.saunahbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;

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
    public @ResponseBody
    ResponseEntity<BookingResponse> createBooking(@RequestBody BookingBody bookingBody, BookingPrice bookingPrice, BookingSauna bookingSauna, Principal principal) throws Exception {
        User currentUser = userService.getUserByMail(principal.getName());
        Booking booking = bookingService.addBooking(bookingBody, currentUser.getId());
        mailService.sendAdminOpenedBookingMail(userRepository.findByRole(UserRole.ADMIN), booking);
        mailService.sendUserOpenedBookingMail(userService.getUser(booking.getUserId()).getEmail(), booking, bookingPrice, bookingSauna);
        return ResponseEntity.ok(new BookingResponse(booking));
    }

    @Operation(description = "Returns the bookingPrice with the ID specified.")
    @GetMapping(path = "bookings/{id}/price")
    public @ResponseBody
    ResponseEntity<BookingPriceResponse> getBookingPrice(@PathVariable(value = "id", required = true) Integer id, Principal principal) throws AuthenticationException {
        Booking booking = bookingService.getBooking(id);
        BookingPrice bookingPrice = bookingService.getBookingPrice(id);
        User user = userService.getUserByMail(principal.getName());
        if (booking.getUserId() == user.getId() || UserRole.ADMIN.equals(user.getRole())) {
            return ResponseEntity.ok(new BookingPriceResponse(bookingPrice));
        }
        throw new AuthenticationException("user is not authenticated to view this booking");
    }

    @Operation(description = "Returns the bookingPrice with the ID specified.")
    @GetMapping(path = "bookings/{id}/sauna")
    public @ResponseBody
    ResponseEntity<BookingSaunaResponse> getBookingSauna(@PathVariable(value = "id", required = true) Integer id, Principal principal) throws AuthenticationException {
        Booking booking = bookingService.getBooking(id);
        BookingSauna bookingSauna = bookingService.getBookingSauna(id);
        User user = userService.getUserByMail(principal.getName());
        if (booking.getUserId() == user.getId() || UserRole.ADMIN.equals(user.getRole())) {
            return ResponseEntity.ok(new BookingSaunaResponse(bookingSauna));
        }
        throw new AuthenticationException("user is not authenticated to view this booking");
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

    @Operation(description = "Allows editing an existing Booking structure.")
    @PutMapping(path = "bookings/{id}")
    public @ResponseBody
    ResponseEntity<BookingResponse> editBooking(@PathVariable(value = "id", required = true) Integer id, int userId, @RequestBody BookingBody bookingBody) throws IOException {
        Booking booking = bookingService.editBooking(id, bookingBody, userId);
        return ResponseEntity.ok(new BookingResponse(booking));
    }

    @Operation(description = "Approves a existing booking structure with the ID specified.")
    @PutMapping(path = "bookings/{id}/approve")
    public @ResponseBody
    ResponseEntity<String> approveBooking(@PathVariable(value = "id", required = true) Integer id, BookingPrice bookingPrice, BookingSauna bookingSauna) throws AuthenticationException, IOException {
        bookingService.approveBooking(id);
        Booking booking = bookingService.getBooking(id);
        mailService.sendUserApprovedBookingMail(userService.getUser(booking.getUserId()).getEmail(), booking, bookingPrice, bookingSauna);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Cancels a existing booking structure with the ID specified.")
    @PutMapping(path = "bookings/{id}/cancel")
    public @ResponseBody
    ResponseEntity<String> cancelBooking(@PathVariable(value = "id", required = true) Integer id, BookingPrice bookingPrice, BookingSauna bookingSauna) throws IOException {
        bookingService.cancelBooking(id);
        Booking booking = bookingService.getBooking(id);
        mailService.sendUserCanceledBookingMail(userService.getUser(booking.getUserId()).getEmail(), booking, bookingPrice, bookingSauna);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Returns the list of all bookings.")
    @GetMapping(path = "bookings/all")
    public @ResponseBody
    List<BookingResponse> getAllBooking() {
        return bookingService.getAllBooking().stream().map(x -> new BookingResponse(x)).collect(Collectors.toList());
    }
}
