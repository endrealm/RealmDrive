package net.endrealm.realmdrive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.endrealm.realmdrive.annotations.SaveVar;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choo {
    @SaveVar
    private char value0;
    @SaveVar
    private String value1;
}
