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
public class TripAnalyticsServiceParallelImpl implements TripAnalyticsService {

    public Map<String, BigDecimal> calculateTotalEarnings(final Stream<Trip> tripStream) {
        final List<Trip> trips = tripStream.collect(Collectors.toList());
        return trips
                .parallelStream()
                .collect(Collectors.groupingByConcurrent(
                        Trip::getDriverId,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Trip::getFare,
                                BigDecimal::add
                        )
                ));
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
        final List<Trip> trips = tripStream.collect(Collectors.toList());
        return trips
                .parallelStream()
                .mapToDouble(Trip::getDistance)
                .average()
                .orElse(0);
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
    public void addTrips(List<Trip> trips) {

    }

    @Override
    public List<Trip> getTrips() {
        return null;
    }
}
