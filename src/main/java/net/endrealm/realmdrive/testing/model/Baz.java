package net.endrealm.realmdrive.testing.model;

import net.endrealm.realmdrive.annotations.SaveVar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Baz {
    @SaveVar
    private String value0;
    @SaveVar
    private List<Bar> value1;
}
