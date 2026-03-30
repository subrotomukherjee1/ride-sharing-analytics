package com.rideshare.analytics.controller;

import com.rideshare.analytics.dto.DriverEarningsDTO;
import com.rideshare.analytics.dto.TripResponseDTO;
import com.rideshare.analytics.model.Trip;
import com.rideshare.analytics.service.interfaces.TripAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trips")
public class TripController {
    @Autowired
    @Qualifier("tripAnalyticsServiceImpl")
    private TripAnalyticsService service;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") String filename) { // Improve the design
        File file = new File(filename);
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            List<Trip> trips = reader.lines()
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(tokens -> new Trip(
                            tokens[0].trim(),
                            tokens[1].trim(),
                            Double.parseDouble(tokens[2].trim()),
                            new BigDecimal(tokens[3].trim())
                    ))
                    .toList();
            service.addTrips(trips);
            return ResponseEntity.ok("File processed successfully");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "File processing failed");
        }
    }

    @GetMapping("/earnings")
    public List<DriverEarningsDTO> getEarnings() {
        final List<Trip> trips = service.getTrips(); // Can we convert this into Stream
        final Map<String, BigDecimal> earnings =
                service.calculateTotalEarnings(trips.stream());
        return earnings.entrySet()
                .stream()
                .map(e -> new DriverEarningsDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    @GetMapping("/top-drivers")
    public ResponseEntity<?> getTopDrivers() {
        return ResponseEntity.ok(
                service.findHighestEarningDrivers(
                        service.calculateTotalEarnings(
                                service.getTrips().stream()
                        )
                )
        );
    }

    @GetMapping("/above-average")
    public List<TripResponseDTO> getTripsAboveAverage() {
        double averageDistance =
                service.calculateAverageDistance(service.getTrips().stream());
        return service.findTripsAboveAverage(service.getTrips().stream(), averageDistance)
                .stream()
                .map(t -> new TripResponseDTO(
                        t.getTripId(),
                        t.getDriverId(),
                        t.getDistance()
                ))
                .toList();
    }
}