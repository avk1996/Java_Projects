package com.app.unit.controller;

import com.app.unit.entity.Unit;
import com.app.unit.service.UnitConverterService;
import com.app.unit.utility.Length;
import com.app.unit.utility.Temperature;
import com.app.unit.utility.Weight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/unit/convert")
public class UnitConverterController {

    @Autowired
    UnitConverterService unitConverterService;

    @GetMapping("/length/{ivalue}/{length1}/{length2}")
    public ResponseEntity<BigDecimal> convertLength(@PathVariable BigDecimal ivalue,  @PathVariable Length length1, @PathVariable Length length2){
        try{
            return ResponseEntity.ok(unitConverterService.convert(ivalue,length1, length2));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new BigDecimal(""));
        }
    }

    @GetMapping("/weight/{ivalue}/{weight1}/{weight2}")
    public ResponseEntity<BigDecimal> convertWeight(@PathVariable BigDecimal ivalue,  @PathVariable Weight weight1, @PathVariable Weight weight2) {
        try {
            return ResponseEntity.ok(unitConverterService.convert(ivalue, weight1, weight2));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BigDecimal(""));
        }
    }

    @GetMapping("/length/{ivalue}/{temperature1}/{temperature2}")
    public ResponseEntity<BigDecimal> convertTemperature(@PathVariable BigDecimal ivalue, @PathVariable Temperature temperature1, @PathVariable Temperature temperature2) {
        try {
            return ResponseEntity.ok(unitConverterService.convert(ivalue, temperature1, temperature2));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BigDecimal(""));
        }
    }
}
