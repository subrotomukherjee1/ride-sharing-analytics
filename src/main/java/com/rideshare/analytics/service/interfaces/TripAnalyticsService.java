package com.rideshare.analytics.service.interfaces;

import com.rideshare.analytics.model.Trip;

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

public interface TripAnalyticsService {
    Map<String, BigDecimal> calculateTotalEarnings(final Stream<Trip> tripStream);

    Set<String> findHighestEarningDrivers(final Map<String, BigDecimal> earningsMap);

    double calculateAverageDistance(final Stream<Trip> tripStream);

    List<Trip> findTripsAboveAverage(Stream<Trip> tripStream, double averageDistance);

    void addTrips(final List<Trip> trips);

    List<Trip> getTrips();
}
