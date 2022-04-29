package ch.saunah.saunahbackend.controller;

import ch.saunah.saunahbackend.dto.BookingBody;
import ch.saunah.saunahbackend.dto.BookingResponse;
import ch.saunah.saunahbackend.repository.BookingRepository;
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
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @Operation(description = "Creates a new booking.")
    @PostMapping(path = "booking/add")
    @ResponseBody
    ResponseEntity<String> createBooking(@RequestBody BookingBody bookingBody) {
        bookingService.addBooking(bookingBody);
        return  ResponseEntity.ok("success");
    }

    @Operation(description = "Returns a list of bookings.")
    @GetMapping(path="bookings")
    public @ResponseBody
    List<BookingResponse> getAllBooking() {
        return bookingService.getAllBookings().stream().map(x -> new BookingResponse(x)).collect(Collectors.toList());
    }

}
