package mathpar.web.learning.school.utils.exceptions;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException() {
    }

    public NotAuthorizedException(String message) {
        super(message);
    }
}
