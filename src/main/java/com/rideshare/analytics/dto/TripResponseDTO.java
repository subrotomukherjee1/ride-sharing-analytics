package com.rideshare.analytics.dto;

public record TripResponseDTO(
        String tripId,
        String driverId,
        double distance
) {}