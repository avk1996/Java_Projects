package com.app.unit.controller;

import com.app.unit.service.UnitConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/unit/convert")
public class UnitConverterController {

    @Autowired
    UnitConverterService unitConverterService;

    @GetMapping("/{value1}/{value2}")
    public ResponseEntity<BigDecimal> convert(@PathVariable BigDecimal value1,
                                              @PathVariable BigDecimal value2){
        try{
            ResponseEntity.ok(unitConverterService.convert(value1,value2));
        }catch (Exception e){
            ResponseEntity.badRequest().body("unable to convert!");
        }
    }
}
