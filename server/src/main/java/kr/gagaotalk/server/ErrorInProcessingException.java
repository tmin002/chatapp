package kr.gagaotalk.server;

public class ErrorInProcessingException extends Exception {

    public int errorCode = -1;
    public int statusCode = 1;
    public String errorMessage = "";
    public ErrorInProcessingException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorInProcessingException(int statusCode) {
        super("Session ID invalid.");
        this.statusCode = statusCode;
    }
}
