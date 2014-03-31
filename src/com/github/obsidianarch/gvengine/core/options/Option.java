package com.github.obsidianarch.gvengine.core.options;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes an option for the OptionManager to account for.
 * 
 * @author Austin
 * 
 * @since 14.03.30
 * @version 14.03.30
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
@Documented
public @interface Option {
    
    /**
     * @return The description of the option that appears to the user.
     */
    String value();
    
}
