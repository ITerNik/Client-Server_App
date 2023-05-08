package annotations;

import java.lang.annotation.*;

/**
 * Аннотация для считывания экземпляров коллекции.
 * Помечает методы-сеттеры класса, которые будут использоваться для заполнения полей нового экземпляра.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Builder {
    String field() default "";
    String[] variants() default {};
    int order();
}

