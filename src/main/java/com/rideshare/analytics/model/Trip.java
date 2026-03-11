package com.rideshare.analytics.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Trip {

    private final String tripId;
    private final String driverId;
    private final double distance;
    private final BigDecimal fare; // What are the advantages of using BigDecimal ? What are the use cases for BigDecimal Usage?

    public Trip(final String tripId, final String driverId,
                final double distance, final BigDecimal fare) {

        if (tripId == null || tripId.isBlank()) {
            throw new IllegalArgumentException("Trip ID is mandatory");
        }

        if (driverId == null || driverId.isBlank()) {
            throw new IllegalArgumentException("Driver ID is mandatory");
        }

        if (distance < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }

        if (fare == null || fare.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Fare cannot be negative");
        }

        this.tripId = tripId;
        this.driverId = driverId;
        this.distance = distance;
        this.fare = fare;
    }

    public String getTripId() {
        return tripId;
    }

    public String getDriverId() {
        return driverId;
    }

    public double getDistance() {
        return distance;
    }

    public BigDecimal getFare() {
        return fare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trip)) return false;
        Trip trip = (Trip) o;
        return tripId.equals(trip.tripId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId);
    }
}