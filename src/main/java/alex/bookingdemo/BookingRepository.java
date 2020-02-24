package alex.bookingdemo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<HotelBooking, Long> {
    List<HotelBooking>  findByPricePerNightLessThan(double price); // find on jpa.repositories on spring.io
    // automatically implements the method for us
}
