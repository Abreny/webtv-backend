package com.webtv.commons;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Copy the content of an object to an other object using refrection.
 * 
 * @author abned.fandresena
 */
public class EntityCopyManager {
    /**
     * Copy the declared field in o2 to o1.
     * 
     * @param o1 The target object
     * @param o2 the object provider
     * @throws Exception
     */
    public void copy(Object o1, Object o2) throws Exception {
        copyValue(o1, o2, new ArrayList<>());
    }

    /**
     * Copy the declared field in o2 to o1, and escape the gin field in escape
     * params.
     * 
     * @param o1     The target object
     * @param o2     the object provider
     * @param escape The field name to escape
     * @throws Exception
     */
    public void copy(Object o1, Object o2, List<String> escape) throws Exception {
        copyValue(o1, o2, escape);
    }

    /**
     * Copy the content of an other object to the object.
     * 
     * @param anObject       Object The target object
     * @param anOtherObject  Object The kinds of object to copy
     * @param fieldsToEscape The field of the object to ignore
     */
    private void copyValue(Object anObject, Object anOtherObject, List<String> fieldsToEscape) throws Exception {
        Class<?> c1 = anObject.getClass();
        Class<?> c2 = anOtherObject.getClass();
        Field[] fields1 = getFieldsToCopy(c1);
        for (Field field : fields1) {
            if (!"id".equalsIgnoreCase(field.getName()) && !Modifier.isStatic(field.getModifiers())
                    && !fieldsToEscape.contains(field.getName())) {
                String getMethodAsString = getMethodAsString(field);
                Method getMethod = c1.getMethod(getMethodAsString);
                String setMethodAsString = setMethodAsString(field);
                Method setMethod = c1.getMethod(setMethodAsString, field.getType());
                Method getMethodOther = null;
                try {
                    getMethodOther = c2.getMethod(getEquivalentGetMethodAsString(field));
                    if (getMethod.getReturnType().getName()
                            .equalsIgnoreCase(getMethodOther.getReturnType().getName())) {
                        Object oM = getMethodOther.invoke(anOtherObject);
                        if (oM != null) {
                            setMethod.invoke(anObject, oM);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Get the fields to copy
     */
    protected Field[] getFieldsToCopy(Class<?> contentClass) {
        return contentClass.getDeclaredFields();
    }

    protected String getMethodAsString(Field field) {
        return String.format("get%s%s", field.getName().substring(0, 1).toUpperCase(),
                field.getName().substring(1, field.getName().length()));
    }

    protected String getEquivalentGetMethodAsString(Field field) {
        return String.format("get%s%s", field.getName().substring(0, 1).toUpperCase(),
                field.getName().substring(1, field.getName().length()));
    }

    protected String setMethodAsString(Field field) {
        return String.format("set%s%s", field.getName().substring(0, 1).toUpperCase(),
                        field.getName().substring(1, field.getName().length()));
    }
}
