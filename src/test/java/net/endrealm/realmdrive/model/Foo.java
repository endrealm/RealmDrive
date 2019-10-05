package net.endrealm.realmdrive.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.endrealm.realmdrive.annotations.SaveVar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Foo extends Choo {

    @SaveVar
    private int value2;
    @SaveVar
    private long value3;
    @SaveVar
    private float value4;
    @SaveVar
    private double value5;
    @SaveVar
    private List<Bar> bar;

    public Foo() {
    }

    public Foo(char value0, String value1, int value2, long value3, float value4, double value5, Bar... bar) {
        super(value0, value1);
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
        this.bar = new ArrayList<>(Arrays.asList(bar));
    }
}
