package org.razu.utils;

import java.util.LinkedHashMap;
import org.springframework.validation.BindingResult;

public class ErrorUtils implements ResponseTagName {

    public static LinkedHashMap userError(BindingResult result) {
        LinkedHashMap serviceResponse = new LinkedHashMap<String, Object>();
        serviceResponse.put(STATUS, Boolean.TRUE);
        serviceResponse.put(STATUS_CODE, CODE_201);
        serviceResponse.put(MESSAGE, result.getAllErrors().get(0).getDefaultMessage());
        return serviceResponse;
    }
}
