package com.app.unit.utility;

import java.math.BigDecimal;

public enum Length {
    // Define each unit with its value relative to 1 Meter
    MILLIMETER(new BigDecimal("0.001")),
    CENTIMETER(new BigDecimal("0.01")),
    METER(new BigDecimal("1.0")),
    KILOMETER(new BigDecimal("1000.0")),
    INCH(new BigDecimal("0.0254")),
    FOOT(new BigDecimal("0.3048")),
    YARD(new BigDecimal("0.9144")),
    MILE(new BigDecimal("1609.344"));

    private final BigDecimal baseFactor;

    Length(BigDecimal baseFactor) {
        this.baseFactor = baseFactor;
    }

    public BigDecimal getBaseFactor() {
        return this.baseFactor;
    }
}
