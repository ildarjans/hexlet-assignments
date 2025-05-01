package exercise.exception;

// BEGIN
public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException() {
        super();
    }
}
// END
