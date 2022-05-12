package ch.saunah.saunahbackend.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.dto.BookingResponse;
import ch.saunah.saunahbackend.model.Booking;
import ch.saunah.saunahbackend.model.User;
import ch.saunah.saunahbackend.model.UserRole;
import ch.saunah.saunahbackend.service.BookingService;
import ch.saunah.saunahbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

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
    private UserService userService;

    @Operation(description = "Creates a new booking.")
    @PostMapping(path = "bookings")
    public @ResponseBody
    ResponseEntity<BookingResponse> createBooking(@RequestBody BookingBody bookingBody, Principal principal) throws Exception {
        User currentUser = userService.getUserByMail(principal.getName());
        Booking booking = bookingService.addBooking(bookingBody, currentUser.getId());
        return ResponseEntity.ok(new BookingResponse(booking));
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
    ResponseEntity<BookingResponse> getBooking(@PathVariable(value = "id", required = true) Integer id, Principal principal) throws NotFoundException, AuthenticationException {
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
    ResponseEntity<String> approveBooking(@PathVariable(value = "id", required = true) Integer id) throws NotFoundException,IOException {
        bookingService.approveBooking(id);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Cancels a existing booking structure with the ID specified.")
    @PostMapping(path = "bookings/{id}/cancel")
    public @ResponseBody
    ResponseEntity<String> cancelBooking(@PathVariable(value = "id", required = true) Integer id, Principal principal) throws AuthenticationException,IOException {
        Booking booking = bookingService.getBooking(id);
        User user = userService.getUserByMail(principal.getName());
        if (booking.getUserId() == user.getId() || UserRole.ADMIN.equals(user.getRole())) {
            bookingService.cancelBooking(id);
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
