package com.dasvoximon.railwaysystem.entities.models;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface ValidPhoneNumber {

    String message() default "Invalid phone number - " +
            "Phone Number must start with 0 and be 11 digits long.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
