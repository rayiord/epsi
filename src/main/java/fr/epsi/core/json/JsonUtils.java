package fr.epsi.core.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class JsonUtils {
    private JsonUtils() {
    }

    public static String toJson(Object o) {
        Class<?> clazz = o.getClass();

        JsonVisibility visibility = JsonVisibility.ALL;
        JsonStrategy annotation = clazz.getDeclaredAnnotation(JsonStrategy.class);
        if (annotation != null) {
            visibility = annotation.visibility();
        }

        List<String> fields = new ArrayList<>();
        List<String> fieldsJson = new ArrayList<>();
        Field[] attributes = clazz.getDeclaredFields();

        Method[] getters = Arrays.stream(clazz.getMethods())
                .filter(method -> method.getName().startsWith("get"))
                .toArray(Method[]::new);

        if (visibility == JsonVisibility.ALL || visibility == JsonVisibility.GETTER) {
            Arrays.stream(getters).forEach(getter -> {
                if (!"getClass".equals(getter.getName())) {
                    if (getter.getAnnotation(JsonIgnore.class) == null) {
                        String originalKey = camelCase(getter.getName().substring(3));
                        String key;
                        JsonProperty property = getter.getAnnotation(JsonProperty.class);
                        if (property == null) {
                            key = originalKey;
                        } else {
                            key = property.value();
                        }

                        Object value = null;
                        try {
                            value = getter.invoke(o);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                        }

                        if (getter.getReturnType() == String.class) {
                            value = String.format("\"%s\"", value);
                        }

                        fields.add(originalKey);
                        fieldsJson.add(String.format("\"%s\":%s", key, value));
                    }
                }
            });
        }

        if (visibility == JsonVisibility.ALL || visibility == JsonVisibility.FIELDS) {
            Arrays.stream(attributes).forEach(field -> {
                if (field.getAnnotation(JsonIgnore.class) == null) {
                    String originalKey = field.getName();
                    String key;
                    JsonProperty property = field.getAnnotation(JsonProperty.class);
                    if (property == null) {
                        key = originalKey;
                    } else {
                        key = property.value();
                    }

                    if (!fields.contains(originalKey)) {
                        field.setAccessible(true);
                        Object value = null;
                        try {
                            String getterName = "get" + capitalize(key);
                            Method getter = clazz.getMethod(getterName);
                            value = getter.invoke(o);
                        } catch (NoSuchMethodException e) {
                            try {
                                value = field.get(o);
                            } catch (IllegalAccessException illegalAccessException) {
                            }
                        } catch (IllegalAccessException | InvocationTargetException e) {
                        }

                        if (field.getType() == String.class) {
                            value = String.format("\"%s\"", value);
                        }
                        field.setAccessible(false);

                        fieldsJson.add(String.format("\"%s\":%s", key, value));
                    }
                }
            });
        }

        return String.format("\"{%s}\"", fieldsJson);
    }

    private static String capitalize(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    private static String camelCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
