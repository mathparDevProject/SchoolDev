package mathpar.web.learning.school.utils;

import mathpar.web.learning.school.utils.exceptions.MalformedDataException;

public class ValidationUtilities {
    public static void hasText(String variable, String errorMessage){
        if(variable.isBlank()) throw new MalformedDataException(errorMessage);
    }
}
