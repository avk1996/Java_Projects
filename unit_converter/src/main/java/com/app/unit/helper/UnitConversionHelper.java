package com.app.unit.helper;

import com.app.unit.utility.Length;
import com.app.unit.utility.Temperature;
import com.app.unit.utility.Weight;

import java.math.BigDecimal;
import java.math.MathContext;

public class UnitConversionHelper {
    public static BigDecimal convertLength(BigDecimal value, Length from, Length to) {
        if (from == to) return value;
        BigDecimal inMeters = value.multiply(from.getBaseFactor());
        return inMeters.divide(to.getBaseFactor(), MathContext.DECIMAL128);
    }

    public static BigDecimal convertWeight(BigDecimal value, Weight from, Weight to) {
        if (from == to) return value;
        BigDecimal inGrams = value.multiply(from.getBaseFactor());
        return inGrams.divide(to.getBaseFactor(), MathContext.DECIMAL128);
    }

    public static BigDecimal convertTemperature(BigDecimal value, Temperature from, Temperature to) {
        if (from == to) return value;
        // Uses the dynamic lambda setup inside the Temperature enum
        return Temperature.convert(value, from, to);
    }
}
