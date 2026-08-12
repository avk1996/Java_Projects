package com.app.unit.utility;

import java.math.BigDecimal;
import java.math.MathContext;

public enum Weight {
    MILIGRAM(new BigDecimal("0.001")),
    GRAM(new BigDecimal("1.0")),
    KILOGRAM(new BigDecimal("1000.0")),
    OUNCE(new BigDecimal("28.349523125")),
    POUND(new BigDecimal("453.59237"));

    private final BigDecimal baseFactor;

    Weight(BigDecimal baseFactor) {
        this.baseFactor = baseFactor;
    }

    public BigDecimal getBaseFactor() {
        return this.baseFactor;
    }
}
