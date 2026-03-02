package com.rideshare.analytics;

import com.rideshare.analytics.model.Trip;
import com.rideshare.analytics.service.TripAnalyticsServiceImpl;
import com.rideshare.analytics.util.TripFileReader;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class RideSharingApplication {

    public static void main(String[] args) throws Exception {

        Path filePath = Path.of("trips.txt");

        TripFileReader reader = new TripFileReader();
        TripAnalyticsServiceImpl service = new TripAnalyticsServiceImpl();

        // ---- First Pass ----
        try (Stream<Trip> tripStream = reader.readTrips(filePath)) {
            final Map<String, BigDecimal> driverEarnings =
                    service.calculateTotalEarnings(tripStream);
            System.out.println("Total Earnings Per Driver:");
            driverEarnings.forEach((driver, total) ->
                    System.out.println(driver + " : " + total)
            );
            final Set<String> topDrivers =
                    service.findHighestEarningDrivers(driverEarnings);
            System.out.println("\nHighest Earning Driver(s): " + topDrivers);
        }

        // ---- Second Pass (Average + Above Avg) ----
        double averageDistance;
        try (Stream<Trip> tripStream = reader.readTrips(filePath)) {
            averageDistance = service.calculateAverageDistance(tripStream);
        }

        System.out.println("\nAverage Distance: " + averageDistance);

        try (Stream<Trip> tripStream = reader.readTrips(filePath)) {

            final List<Trip> aboveAverageTrips =
                    service.findTripsAboveAverage(tripStream, averageDistance);

            System.out.println("\nTrips Above Average Distance:");
            aboveAverageTrips.forEach(trip ->
                    System.out.println(trip.getTripId())
            );
        }
    }
}