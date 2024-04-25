package com.huuduc.snacksnap.exception;

import java.util.Map;

public class NullException extends ExceptionCustom{

    public NullException(Map<String,Object> errors) {
        super("NULL PARAM",errors);
    }
}
