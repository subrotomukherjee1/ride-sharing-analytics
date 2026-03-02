package com.rideshare.analytics.service;

import com.rideshare.analytics.model.Trip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TripAnalyticsServiceImplTest {

    private TripAnalyticsServiceImpl service;

    private Trip t1 = new Trip("T1", "D1", 10, new BigDecimal("100"));
    private Trip t2 = new Trip("T2", "D1", 20, new BigDecimal("200"));
    private Trip t3 = new Trip("T3", "D2", 30, new BigDecimal("300"));

    @BeforeEach
    void setup() {
        service = new TripAnalyticsServiceImpl();
    }

    @Test
    void shouldCalculateTotalEarningsPerDriver() {

        Map<String, BigDecimal> earnings =
                service.calculateTotalEarnings(Stream.of(t1, t2, t3));

        assertEquals(new BigDecimal("300"), earnings.get("D1"));
        assertEquals(new BigDecimal("300"), earnings.get("D2"));
    }

    @Test
    void shouldFindHighestEarningDriversWithTie() {

        Map<String, BigDecimal> earnings =
                service.calculateTotalEarnings(Stream.of(t1, t2, t3));

        Set<String> topDrivers =
                service.findHighestEarningDrivers(earnings);

        assertEquals(2, topDrivers.size());
        assertTrue(topDrivers.contains("D1"));
        assertTrue(topDrivers.contains("D2"));
    }

    @Test
    void shouldCalculateAverageDistance() {

        double average =
                service.calculateAverageDistance(Stream.of(t1, t2, t3));

        assertEquals(20.0, average);
    }

    @Test
    void shouldFindTripsAboveAverageDistance() {

        double average =
                service.calculateAverageDistance(Stream.of(t1, t2, t3));

        List<Trip> aboveAverage =
                service.findTripsAboveAverage(Stream.of(t1, t2, t3), average);

        assertEquals(1, aboveAverage.size());
        assertEquals("T3", aboveAverage.get(0).getTripId());
    }

    @Test
    void shouldHandleEmptyTripStream() {

        final Map<String, BigDecimal> earnings =
                service.calculateTotalEarnings(Stream.empty());

        assertTrue(earnings.isEmpty());

        double average =
                service.calculateAverageDistance(Stream.empty());

        assertEquals(0, average);
    }
}