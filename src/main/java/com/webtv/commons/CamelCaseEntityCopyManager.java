package com.webtv.commons;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Subclass of @see EntityCopyManager. It serve as copying a specified attribute and convert a maj to an underscore.
 * 
 * @author abned.fandrena
 *         Apr 27, 2020
 */
public class CamelCaseEntityCopyManager extends EntityCopyManager {

    /**
     * The updatable field
     */
    private String[] fieldNames;

    public CamelCaseEntityCopyManager(String... fields) {
        this.fieldNames = fields;
    }

    public CamelCaseEntityCopyManager() {

    }

    @Override
    protected Field[] getFieldsToCopy(Class<?> contentClass) {
        List<Field> fields = new ArrayList<>();
        Field[] declaredFields = contentClass.getDeclaredFields();
        List<String> lstFields = Arrays.asList(this.fieldNames);
        for (Field field : declaredFields) {
            if (lstFields.contains(field.getName())) {
                fields.add(field);
            }
        }
        Field[] fieldToUpdates = new Field[fields.size()];
        fields.toArray(fieldToUpdates);
        return fieldToUpdates;
    }

    @Override
    protected String getEquivalentGetMethodAsString(Field field) {
        String fieldName = field.getName();
        String[] wordFieldNames = fieldName.split("[A-Z]");
        if (wordFieldNames.length == 2) {
            Matcher matcher = Pattern.compile(".*(?<capital>[A-Z]).*").matcher(fieldName);
            if (matcher.matches()) {
                fieldName = String.format("%s_%s%s", wordFieldNames[0], matcher.group("capital").toLowerCase(),
                        wordFieldNames[1]);
            }
        }
        return String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(),
                fieldName.substring(1, fieldName.length()));
    }
}