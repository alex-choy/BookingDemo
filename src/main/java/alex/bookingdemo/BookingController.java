package alex.bookingdemo;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/bookings")
@Api(name = "Hotel Booking API", description = "Provieds a list of methods that manages hotel bookings",
        stage = ApiStage.RC)

public class BookingController {
    private BookingRepository bookingRepository;

    @Autowired
    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET) // optional, but method makes it more obvious what the method does
    @ApiMethod(description = "Get all hotel bookings from the database")
    public List<HotelBooking> getAll() {
        return bookingRepository.findAll();
    }


    // price is a 'path variable', found in the URL as a parameter
    /**
     * Returns hotel bookings less than or equal to the given 'price'
     * @param price the price per night of a hotel
     * @return bookings cheaper than or equal to the price
     */
    @RequestMapping(value = "/affordable/{price}", method = RequestMethod.GET)
    @ApiMethod(description = "Get all hotels with a cost less than the given price")
    public List<HotelBooking> getAffordable(@ApiPathParam(name = "price") @PathVariable double price) {
        return  bookingRepository.findByPricePerNightLessThan(price);
    }

    // RequestBody means we want to initialize a JSON post request
    // Tells Spring to make a HotelBooking object based on the JSON request
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ApiMethod(description = "Create a new Hotel Booking")
    public List<HotelBooking> create(@RequestBody HotelBooking hotelBooking) {
        bookingRepository.save(hotelBooking);
        return bookingRepository.findAll();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ApiMethod(description = "Delete a Hotel Booking with the given ID")
    public List<HotelBooking> remove(@ApiPathParam(name = "id") @PathVariable long id) {
        bookingRepository.deleteById(id);
        return bookingRepository.findAll();
    }
}
