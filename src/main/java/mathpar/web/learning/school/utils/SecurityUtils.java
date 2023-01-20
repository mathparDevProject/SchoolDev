package mathpar.web.learning.school.utils;

import mathpar.web.learning.school._configs.security.UserAuthentication;
import mathpar.web.learning.school.utils.exceptions.MalformedDataException;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static long extractAccountId(String token){
        try {
            return Long.parseLong(token.substring(0, token.indexOf("_")));
        }catch (NumberFormatException e){
            throw new MalformedDataException("AuthenticationToken is invalid");
        }
    }

    public static UserAuthentication getUserAuthentication(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof UserAuthentication)) throw new RuntimeException("User isn't authenticated or Authentication is invalid");
        return (UserAuthentication) auth;
    }
}
