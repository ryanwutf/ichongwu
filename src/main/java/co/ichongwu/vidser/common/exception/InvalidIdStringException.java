package co.ichongwu.vidser.common.exception;

/**
 * Created by whf on 3/24/16.
 */
public class InvalidIdStringException extends ValidationException {
    public InvalidIdStringException(String argName, String msg) {
        super(argName, msg);
    }
}
