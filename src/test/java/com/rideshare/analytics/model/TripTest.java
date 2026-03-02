package com.rideshare.analytics.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TripTest {
    @Test
    void shouldCreateValidTrip() {
        Trip trip = new Trip("T1", "D1", 10.5, new BigDecimal("25.00"));
        assertEquals("T1", trip.getTripId());
        assertEquals("D1", trip.getDriverId());
        assertEquals(10.5, trip.getDistance());
        assertEquals(new BigDecimal("25.00"), trip.getFare());
    }

    @Test
    void shouldThrowExceptionIfTripIdMissing() {
        assertThrows(IllegalArgumentException.class, () ->
                new Trip("", "D1", 10, new BigDecimal("20"))
        );
    }

    @Test
    void shouldThrowExceptionIfDriverIdMissing() {
        assertThrows(IllegalArgumentException.class, () ->
                new Trip("T1", "", 10, new BigDecimal("20"))
        );
    }

    @Test
    void shouldThrowExceptionForNegativeDistance() {
        assertThrows(IllegalArgumentException.class, () ->
                new Trip("T1", "D1", -5, new BigDecimal("20"))
        );
    }

    @Test
    void shouldThrowExceptionForNegativeFare() {
        assertThrows(IllegalArgumentException.class, () ->
                new Trip("T1", "D1", 10, new BigDecimal("-1"))
        );
    }
}