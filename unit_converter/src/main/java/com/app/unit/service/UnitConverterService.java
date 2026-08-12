package com.app.unit.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class UnitConverterService {
    public BigDecimal convert(BigDecimal value1, BigDecimal value2){
        return new BigDecimal("");
    }
}
