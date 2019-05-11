package net.endrealm.realmdrive.utils;

import java.util.ArrayList;

/**
 * @author johannesjumpertz
 *
 * A utlity class to help dealing with MySQL
 */
public class MySQLUtils {

    /**
     * Returns the name a class would have if it was a table in mysql.
     *
     * @param clazz the class to convert
     * @return name class would have in mysql
     */
    public static String getTableName(Class clazz) {
        String name = clazz.getSimpleName();
        return getConventionName(name);
    }

    /**
     * Returns the string converted to following conventions:
     * <ul>
     *     <li>lowercase</li>
     *     <li>separated by underscore</li>
     * </ul>
     *
     * @param name name to convert
     * @return name matching conventions
     */
    public static String getConventionName(String name) {
        ArrayList<String> parts = new ArrayList<>();

        StringBuilder builder = new StringBuilder();

        for(char character : name.toCharArray()) {
            if(Character.isUpperCase(character)) {
                if(builder.length() != 0) {
                    parts.add(builder.toString());
                    builder = new StringBuilder();
                }
            }
            builder.append(character);
        }
        if(builder.length() != 0) {
            parts.add(builder.toString());
        }

        return String.join("_", parts);
    }
}
