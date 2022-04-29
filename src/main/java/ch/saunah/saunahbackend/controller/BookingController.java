package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.dto.BookingResponse;
import ch.saunah.saunahbackend.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(description = "Creates a new booking.")
    @PostMapping(path = "booking/add")
    @ResponseBody
    ResponseEntity<String> createBooking(@RequestBody BookingBody bookingBody) {
        bookingService.addBooking(bookingBody);
        return  ResponseEntity.ok("success");
    }

    @Operation(description = "Returns the list of all bookings.")
    @GetMapping(path="bookings")
    public @ResponseBody
    List<BookingResponse> getAllBooking() {
        return bookingService.getAllBookings().stream().map(x -> new BookingResponse(x)).collect(Collectors.toList());
    }

    @Operation(description = "Returns the booking with the specified ID.")
    @GetMapping(path="booking/{id}")
    public @ResponseBody ResponseEntity<BookingResponse> getBooking(@PathVariable(value = "id", required = true) Integer id) {
        return ResponseEntity.ok(new BookingResponse(bookingService.getBooking(id)));
    }
}
