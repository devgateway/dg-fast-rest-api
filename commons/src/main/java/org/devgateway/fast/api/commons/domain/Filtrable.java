package org.devgateway.fast.api.commons.domain;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Filtrable {
    String param();

    String description()  default "[unassigned]"; ;
}
