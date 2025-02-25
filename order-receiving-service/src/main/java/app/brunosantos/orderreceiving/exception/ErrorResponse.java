package app.brunosantos.orderreceiving.exception;

import java.util.List;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    private List<String> details;

    public ErrorResponse(String code, String message) {
        this(code, message, null);
    }

    public ErrorResponse(String code, String message, List<String> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }
}