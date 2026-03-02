package com.rideshare.analytics.dto;

import java.math.BigDecimal;

public record DriverEarningsDTO(
        String driverId,
        BigDecimal totalEarnings
) {}