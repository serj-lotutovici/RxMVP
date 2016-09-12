package com.serjltt.devfest.weather.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/** Injection consuming scope. */
@Scope @Retention(RetentionPolicy.CLASS)
public @interface Consumer {
}
