package com.app.unit.service;

import com.app.unit.entity.Unit;
import com.app.unit.helper.UnitConversionHelper;
import com.app.unit.utility.Length;
import com.app.unit.utility.Temperature;
import com.app.unit.utility.Weight;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class UnitConverterService {
    public <T extends Enum<T>> BigDecimal convert(BigDecimal ivalue, T unit1, T unit2){
        try{
            return switch (unit1.getClass().getSimpleName()) {
                case "Length" -> UnitConversionHelper.convertLength(ivalue, (Length) unit1, (Length) unit2);
                case "Weight" -> UnitConversionHelper.convertWeight(ivalue, (Weight) unit1, (Weight) unit2);
                case "Temperature" ->
                        UnitConversionHelper.convertTemperature(ivalue, (Temperature) unit1, (Temperature) unit2);
                default -> new BigDecimal(0);
            };
        } catch (Exception e) {
            System.out.println("Error received: "+e.getLocalizedMessage());
            return new BigDecimal(0);
        }
    }
}
