package net.endrealm.realmdrive.utils.properties;

import net.endrealm.realmdrive.annotations.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Reads drive properties from class and fields
 */
public class PropertyReader {


    public static ClassProperties readProperties(Class<?> clazz) {

        SaveAll saveAll = clazz.getAnnotation(SaveAll.class);
        SaveTable saveTable = clazz.getAnnotation(SaveTable.class);

        String tableName = null;
        List<String> ignoredFields = new ArrayList<>();
        boolean saveAllVariables = false;

        if(saveAll != null) {
            saveAllVariables = true;
            ignoredFields.addAll(Arrays.asList(saveAll.ignored()));
        }

        if(saveTable != null) {
            tableName = saveTable.tableName().equals("") ? null : saveTable.tableName();
        }

        return new ClassProperties(tableName, ignoredFields, saveAllVariables);
    }

    public static FieldProperties readProperties(Field field, ClassProperties classProperties) {
        IgnoreVar ignoreVar = field.getAnnotation(IgnoreVar.class);
        SaveVar saveVar = field.getAnnotation(SaveVar.class);
        ReadOnly readOnly = field.getAnnotation(ReadOnly.class);
        WriteOnly writeOnly = field.getAnnotation(WriteOnly.class);



        boolean write = false;
        boolean read = false;
        String name = field.getName();
        List<String> aliases = new ArrayList<>();
        boolean optional = true;

        if(saveVar != null) {
            name = saveVar.name().equals("") ? field.getName() : saveVar.name();
            aliases.addAll(Arrays.asList(saveVar.aliases()));
            optional = saveVar.optional();

            read = true;
            write = true;
        }

        if(classProperties.isSaveAll()) {
            read = true;
            write = true;
        }

        if(readOnly != null) {
            read = true;
            write = false;
        }
        if(writeOnly != null) {
            write = true;
            read = false;
        }

        // Field is blacklisted so ignore it
        if(ignoreVar != null || classProperties.getIgnoredFields().contains(field.getName())) {
            read = false;
            write = false;
        }

        return new FieldProperties(write, read, name, aliases, optional);
    }
}
