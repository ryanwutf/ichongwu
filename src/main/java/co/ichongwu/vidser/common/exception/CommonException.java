package co.ichongwu.vidser.common.exception;

/**
 * Created by whf on 3/20/16.
 */
public abstract class CommonException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CommonException(String msg) {
        super(msg);
    }

    protected CommonException() {

    }

    /**
     * 重写此方法提高10倍性能
     * @return
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
