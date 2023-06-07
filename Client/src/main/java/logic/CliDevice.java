package logic;

import annotations.Builder;
import constants.Messages;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.TreeSet;

public class CliDevice extends IODevice {

    public CliDevice() {
        super(new Scanner(System.in));
    }

    @Override
    public <T> T readElement(Class<T> cl) {
        T base = null;
        try {
            base = cl.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        TreeSet<Method> methods = selectMethods(cl.getMethods());
        for (Method method : methods) {
            Builder annotation = method.getAnnotation(Builder.class);
            Class<?> type = method.getParameterTypes()[0];
            while (true) {
                try {
                    if (type != String.class) {
                        method.invoke(base, readElement(type));
                    } else {
                        System.out.printf(getQuery(annotation));
                        String field = input.nextLine();
                        method.invoke(base, field);
                    }
                    break;
                } catch (InvocationTargetException e) {
                    System.out.println(e.getCause().getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return base;
    }
    @Override
    public boolean hasNext() {
        try {
            return (System.in.available() > 0);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private String getQuery(Builder annotation) {
        StringBuilder query = new StringBuilder(Messages.getMessage("input.format.parameter", Messages.getMessage(annotation.field())));
        if (annotation.variants().length != 0) {
            query.append(" ").append(Messages.getMessage("input.from_list")).append("\n");
            for (int i = 0; i < annotation.variants().length; ++i) {
                query.append(String.format("%s. %s\n", i + 1, Messages.getMessage(annotation.variants()[i])));
            }
        } else {
            query.append(":\n");
        }
        return query.toString();
    }

}
