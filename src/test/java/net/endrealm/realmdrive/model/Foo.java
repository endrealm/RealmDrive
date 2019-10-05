package net.endrealm.realmdrive.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.endrealm.realmdrive.annotations.IgnoreVar;
import net.endrealm.realmdrive.annotations.SaveAll;
import net.endrealm.realmdrive.annotations.SaveTable;
import net.endrealm.realmdrive.annotations.SaveVar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SaveAll
@SaveTable(tableName = "fooTable")
public class Foo extends Choo {

    private int value2;
    private long value3;
    private float value4;
    private double value5;
    private List<Bar> bar;

    @IgnoreVar
    private final String aConstValue = "Great Right?";

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
