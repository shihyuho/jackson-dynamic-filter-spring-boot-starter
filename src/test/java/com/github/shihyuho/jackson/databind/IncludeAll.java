package com.github.shihyuho.jackson.databind;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IncludeAll {}
