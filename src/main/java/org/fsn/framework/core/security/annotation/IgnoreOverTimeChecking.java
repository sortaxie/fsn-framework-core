package org.fsn.framework.core.security.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * 忽略过期时间检查
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface IgnoreOverTimeChecking {
}
