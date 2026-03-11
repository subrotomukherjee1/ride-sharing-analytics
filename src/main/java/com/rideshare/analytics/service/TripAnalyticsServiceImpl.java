package com.rideshare.analytics.service;

import com.rideshare.analytics.model.Trip;
import com.rideshare.analytics.service.interfaces.TripAnalyticsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class TripAnalyticsServiceImpl implements TripAnalyticsService {
    private final List<Trip> trips = new ArrayList<>();
    public Map<String, BigDecimal> calculateTotalEarnings(final Stream<Trip> tripStream) {
        final Map<String, BigDecimal> earnings = new ConcurrentHashMap<>();
        tripStream.forEach(trip ->
                earnings.merge(
                        trip.getDriverId(),
                        trip.getFare(),
                        BigDecimal::add
                )
        );
        return earnings;
    }
    public Set<String> findHighestEarningDrivers(final Map<String, BigDecimal> earningsMap) {
        if (earningsMap.isEmpty()) {
            return Collections.emptySet();
        }
        final BigDecimal max = earningsMap.values()
                .stream()
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        final Set<String> topDrivers = new HashSet<>();
        earningsMap.forEach((driverId, total) -> {
            if (total.compareTo(max) == 0) {
                topDrivers.add(driverId);
            }
        });
        return topDrivers;
    }

    public double calculateAverageDistance(final Stream<Trip> tripStream) {
        double totalDistance = 0;
        long count = 0;
        final Iterator<Trip> iterator = tripStream.iterator();
        while (iterator.hasNext()) {
            Trip trip = iterator.next();
            totalDistance += trip.getDistance();
            count++;
        }
        return count == 0 ? 0 : totalDistance / count;
    }

    public List<Trip> findTripsAboveAverage(Stream<Trip> tripStream, double averageDistance) {
        final List<Trip> result = new ArrayList<>();
        tripStream.forEach(trip -> {
            if (trip.getDistance() > averageDistance) {
                result.add(trip);
            }
        });
        return result;
    }

    @Override
    public void addTrips(final List<Trip> trips) {
        this.trips.addAll(trips);
    }

    public List<Trip> getTrips() {
        return this.trips;
    }
}
