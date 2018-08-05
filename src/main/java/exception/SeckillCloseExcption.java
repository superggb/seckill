package exception;

/**
 * 秒杀关闭异常
 */
public class SeckillCloseExcption extends SeckillException {
    public SeckillCloseExcption(String message) {
        super(message);
    }

    public SeckillCloseExcption(String message, Throwable cause) {
        super(message, cause);
    }
}
