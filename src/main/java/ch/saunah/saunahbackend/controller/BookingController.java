package ch.saunah.saunahbackend.controller;

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
    ResponseEntity<BookingResponse> createBooking(@RequestBody BookingBody bookingBody) {
        return ResponseEntity.ok(new BookingResponse(bookingService.addBooking(bookingBody)));
    }

    @Operation(description = "Returns the list of all bookings.")
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
    @PostMapping(path = "bookings/approve")
    public @ResponseBody
    ResponseEntity<String> approveBooking(@RequestParam(value = "Id", required = true) int id) {
        bookingService.approveBooking(id);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Cancels a existing booking structure with the ID specified.")
    @PostMapping(path = "bookings/cancel")
    public @ResponseBody
    ResponseEntity<String> cancelBooking(@RequestParam(value = "Id", required = true) int id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok("success");
    }

    @Operation(description = "Returns the list of all bookings.")
    @GetMapping(path = "allBookings")
    public @ResponseBody
    List<BookingResponse> getAllBooking() {
        return bookingService.getAllBooking().stream().map(x -> new BookingResponse(x)).collect(Collectors.toList());
    }
}
