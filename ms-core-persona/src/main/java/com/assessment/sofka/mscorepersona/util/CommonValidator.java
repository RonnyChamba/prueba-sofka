package com.assessment.sofka.mscorepersona.util;

import com.assessment.sofka.mscorepersona.exeption.GenericException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonValidator {

    public static <T> void validateFieldObjectFromTemplate(T object, List<String> requiredFieldList) throws GenericException {
        StringBuilder result = new StringBuilder("");
        if (object != null) {

            if (requiredFieldList != null && !requiredFieldList.isEmpty()) {
                Field[] var3 = object.getClass().getDeclaredFields();

                // retrieve the field super class and add it to the list
                Field[] var3Super = object.getClass().getSuperclass().getDeclaredFields();

                List<Field> combinedList = new ArrayList<>();
                Collections.addAll(combinedList, var3);
                Collections.addAll(combinedList, var3Super);

                for (Field field : combinedList) {
                    field.setAccessible(true);
                    Iterator var7 = requiredFieldList.iterator();

                    while (var7.hasNext()) {
                        String key = (String) var7.next();

                        try {
                            if (key.equalsIgnoreCase(field.getName()) && (field.get(object) == null || field.get(object).toString().isEmpty())) {
                                result.append("Attribute ").append(object.getClass().getSimpleName()).append(".").append(field.getName()).append(" should not be null").append("\n");
                            }
                        } catch (IllegalAccessException var10) {
                            result.append("Attribute ").append(object.getClass().getSimpleName()).append(".").append(field.getName()).append(" couldn't has been validated").append("\n");
                        }
                    }
                }


            } else {
                result.append("Required Field List to validate shouldn't be empty or null");
            }
        } else {
            result.append("Object to validate shouldn't be null");
        }

        if (!result.toString().trim().isEmpty()) {
            throw new GenericException(result.toString());
        }

    }

    public static <T> T objectMapping(Object object, Class<T> targetType) throws GenericException {
        try {
            ObjectMapper objMapper = ((JsonMapper.Builder) ((JsonMapper.Builder) ((JsonMapper.Builder) JsonMapper.builder().enable(new MapperFeature[]{MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES})).disable(new SerializationFeature[]{SerializationFeature.FAIL_ON_EMPTY_BEANS})).defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))).build();
            return objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).convertValue(object, targetType);
        } catch (Exception var3) {
            Exception e = var3;
            throw new GenericException("The conversion could not be performed, from " + object.getClass().getName() + " to " + targetType.getName());
        }
    }


}
