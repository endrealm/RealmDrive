package net.endrealm.realmdrive.utils.properties;

import lombok.Data;

import java.util.List;

@Data
public class ClassProperties {
    private final String tableName;
    private final List<String> ignoredFields;
    private final boolean saveAll;
}
