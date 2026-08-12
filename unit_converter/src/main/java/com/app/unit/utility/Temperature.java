package com.app.unit.utility;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.Function;

public enum Temperature {
    CELSIUS(
            val -> val, // To Celsius
            val -> val  // From Celsius
    ),
    KELVIN(
            val -> val.subtract(new BigDecimal("273.15")), // To Celsius
            val -> val.add(new BigDecimal("273.15"))      // From Celsius
    ),
    FAHRENHEIT(
            val -> val.subtract(new BigDecimal("32"))
                    .multiply(new BigDecimal("5"))
                    .divide(new BigDecimal("9"), MathContext.DECIMAL128), // To Celsius
            val -> val.multiply(new BigDecimal("1.8"))
                    .add(new BigDecimal("32"))                           // From Celsius
    );

    private final Function<BigDecimal, BigDecimal> toCelsius;
    private final Function<BigDecimal, BigDecimal> fromCelsius;

    Temperature(Function<BigDecimal, BigDecimal> toCelsius, Function<BigDecimal, BigDecimal> fromCelsius) {
        this.toCelsius = toCelsius;
        this.fromCelsius = fromCelsius;
    }

    // Handles all temperature permutations dynamically without switch/if-else
    public static BigDecimal convert(BigDecimal value, Temperature fromUnit, Temperature toUnit) {
        if (fromUnit == toUnit) return value;

        // Step 1: Normalize any incoming unit to Celsius
        BigDecimal celsiusValue = fromUnit.toCelsius.apply(value);

        // Step 2: Convert Celsius to target unit
        return toUnit.fromCelsius.apply(celsiusValue);
    }
}
