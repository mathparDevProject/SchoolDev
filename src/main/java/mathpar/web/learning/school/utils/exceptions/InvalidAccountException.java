package mathpar.web.learning.school.utils.exceptions;

/**
 * This exception should be thrown whenever account sent by user is invalid or doesn't exists.
 * This could happen when invalid email was passed as a parameter to method or account doesn't have valid profile
 */
public class InvalidAccountException extends Exception{
    public InvalidAccountException(String message) {
        super(message);
    }
}
