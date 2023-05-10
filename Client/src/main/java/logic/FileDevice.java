package logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.TreeSet;

public class FileDevice extends IODevice {
    private final File file;

    public FileDevice(String fileName) throws FileNotFoundException {
        super(new Scanner(new FileInputStream(fileName)));
        this.file = new File(fileName);
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
            Class<?> type = method.getParameterTypes()[0];
            try {
                if (type != String.class) {
                    method.invoke(base, readElement(type));
                } else {
                    String field = input.nextLine();
                    method.invoke(base, field);
                }
            } catch (InvocationTargetException e) {
                System.out.printf("В файле %s: %s%n", file.getName(), e.getCause().getMessage());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return base;
    }

    public boolean hasNextLine() {
        return input.hasNextLine();
    }
}
