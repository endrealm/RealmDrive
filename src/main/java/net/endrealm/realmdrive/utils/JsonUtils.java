package net.endrealm.realmdrive.utils;

/**
 * @author johannesjumpertz
 *
 * Contains helpful json methods
 */
public class JsonUtils {

    /**
     * Turns a primitive into a json compatible string
     *
     * @param value primitive value
     * @return json representation
     */
    public static String parsePrimitive(Object value) {

        if(value instanceof String || value instanceof Character)
            return "\""+value+"\"";
        return value.toString();
    }
}
