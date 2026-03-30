package com.rideshare.analytics.service.interfaces;

import com.rideshare.analytics.model.Trip;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public interface TripAnalyticsService {
    Map<String, BigDecimal> calculateTotalEarnings(final Stream<Trip> tripStream);// Business Requirement
    Set<String> findHighestEarningDrivers(final Map<String, BigDecimal> earningsMap); // Business Requirement

    double calculateAverageDistance(final Stream<Trip> tripStream);

    List<Trip> findTripsAboveAverage(Stream<Trip> tripStream, double averageDistance);// Business Requirement

    void addTrips(final List<Trip> trips);

    List<Trip> getTrips();
}
