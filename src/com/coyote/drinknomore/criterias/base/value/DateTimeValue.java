/**************************************************************************
 * DateTimeValue.java, drinknomore Android
 *
 * Copyright 2015
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 10, 2015
 *
 **************************************************************************/
package com.coyote.drinknomore.criterias.base.value;

import com.google.common.collect.ObjectArrays;

/**
 * Date time value.
 */
public class DateTimeValue extends MethodValue {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     *
     * @param type Can be date/datetime or time.
     * @param value The value of the date
     * @param options various options
     */
    public DateTimeValue(Type type, String value, String... options) {
        super(type.getValue(), ObjectArrays.concat(value, options));
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor.
     *
     * @param format custom format (ex. strtf)
     * @param value The value of the date
     * @param options various options
     */
    public DateTimeValue(String format, String value, String... options) {
        super(format, ObjectArrays.concat(value, options));
        // TODO Auto-generated constructor stub
    }

    /** Type of the date time value. */
    public enum Type {
        /** Date. */
        DATE("date"),
        /** DateTime. */
        DATETIME("datetime"),
        /** Time. */
        TIME("time");

        /** Value. */
        private String value;

        /** Constructor.
         * @param value the value
         */
        private Type(String value) {
            this.value = value;
        }

        /** Gets the value.
         * @return the value
         */
        public String getValue() {
            return this.value;
        }
    }
}

