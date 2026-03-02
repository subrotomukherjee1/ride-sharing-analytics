package com.rideshare.analytics.util;

import com.rideshare.analytics.model.Trip;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class TripFileReader {

    public Stream<Trip> readTrips(final Path filePath) throws IOException {
        return Files.lines(filePath)
                .skip(1) // skip header
                .map(this::parseLine)
                .filter(trip -> trip != null);
    }

    private Trip parseLine(String line) {
        try {
            String[] tokens = line.split(",");
            String tripId = tokens[0].trim();
            String driverId = tokens[1].trim();
            double distance = Double.parseDouble(tokens[2].trim());
            BigDecimal fare = new BigDecimal(tokens[3].trim());
            return new Trip(tripId, driverId, distance, fare);
        } catch (Exception e) {
            // Invalid record — discard
            return null;
        }
    }
}