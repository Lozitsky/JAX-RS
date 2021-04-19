package com.kirilo.ws;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.stream.JsonGenerator;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

// https://youtu.be/v5fpDknuj_o
@Stateless
public class JSonStreamer {

    public <T> void getStream(OutputStream stream, Stream<T> objects, int ms) {

        try (JsonGenerator generator = Json.createGenerator(stream)) {
            generator.writeStartArray();

            objects.forEach(object -> {
                generator.writeStartObject();
                convertFieldsToJSON(object, generator);

                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });

            generator.writeEnd();
        }
    }

//    https://www.eclipse.org/community/eclipse_newsletter/2018/november/jsonjakartaee.php
    public <T> void getJsonbStream(OutputStream stream, Stream<T> objects, int ms) {
//        List<Book> books = new ArrayList<>();

        try (JsonGenerator generator = Json.createGenerator(stream); Jsonb jsonb = JsonbBuilder.create()) {
            generator.writeStartArray();
            generator.flush();

            AtomicBoolean first = new AtomicBoolean(true);

            objects.forEach(object -> {
                if (!first.get()) {
                    try {
                        stream.write(',');
                        stream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    first.set(false);
                }

                jsonb.toJson(object, stream);

                try {
                    Thread.sleep(ms);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            generator.writeEnd();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    https://www.geeksforgeeks.org/method-class-getname-method-in-java/
//    https://stackoverflow.com/q/160970/9586230
    private <T> void convertFieldsToJSON(T obj, JsonGenerator generator) {
        Class<?> aClass = obj.getClass();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            if (isPublic(method.getModifiers())) {
                String methodName = method.getName();
                if (methodName.contains("get") && !methodName.equals("getClass")) {
                    String value = "";
                    try {
                        value = String.valueOf(method.invoke(obj));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    generator.write(methodName.substring(3).toLowerCase(), value);
                }
            }
        }
        generator.writeEnd();
        generator.flush();
    }

    private boolean isPublic(int fieldModifiers) {
        return isModifierSet(fieldModifiers, Modifier.PUBLIC);
    }

    private boolean isModifierSet(int allModifiers, int specificModifier) {
        return (allModifiers & specificModifier) > 0;
    }
}
