package net.endrealm.realmdrive.utils.properties;

import lombok.Data;

import java.util.List;

@Data
public class FieldProperties {
    private final boolean write;
    private final boolean read;
    private final String name;
    private final List<String> aliases;
    private final boolean optional;

}
