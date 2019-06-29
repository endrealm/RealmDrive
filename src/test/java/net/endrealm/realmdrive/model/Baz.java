package net.endrealm.realmdrive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.endrealm.realmdrive.annotations.SaveVar;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Baz {
    @SaveVar
    private String value0;
    @SaveVar
    private UUID value1;
}
