package com.init.inventariovacunaBACKEDN.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CedulaClassValidacion.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CedulaValidacion {

    String message() default "erro cedula no valida";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
