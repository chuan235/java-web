package top.gmfcj.exception;


public class TaskException extends Exception {

    private static final long serialVersionUID = 1L;
    private TaskException.Code code;

    public TaskException(String msg, TaskException.Code code) {
        this(msg, code, (Exception) null);
    }

    public TaskException(String msg, TaskException.Code code, Exception nestedEx) {
        super(msg, nestedEx);
        this.code = code;
    }

    public TaskException.Code getCode() {
        return this.code;
    }

    public static enum Code {
        TASK_EXISTS,
        NO_TASK_EXISTS,
        TASK_ALREADY_STARTED,
        UNKNOWN,
        CONFIG_ERROR,
        TASK_NODE_NOT_AVAILABLE;

        private Code() {
        }
    }
}
