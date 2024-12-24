package com.kirito.todoList.annotaion;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Limit {
    /**
     * Resource key unique
     * effect：different interfaces, different flow control
     */
    String key() default "";

    /**
     * Maximum number of access restriction
     */
    double permitsPerSecond();

    /**
     * Maximum wait time for obtaining a token
     */
    long timeout();

    /**
     * Maximum wait time for obtaining a token
     * Unit(for example: minute/second/millisecond) Default value: millisecond
     */
    TimeUnit timeunit() default TimeUnit.MILLISECONDS;

    /**
     * No token prompt
     */
    String msg() default "The system is busy, please try again later";
}
