package kr.gagaotalk.server;

public class ErrorInProcessingException extends Exception {

    public final int errorCode;
    public final String errorMessage;
    public ErrorInProcessingException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
