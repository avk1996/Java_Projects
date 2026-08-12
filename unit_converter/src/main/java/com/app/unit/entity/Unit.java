package com.app.unit.entity;

import com.app.unit.utility.Length;
import com.app.unit.utility.Temperature;
import com.app.unit.utility.Weight;
import lombok.Data;

@Data
public class Unit {
    private Length length;
    private Weight weight;
    private Temperature temperature;
}
