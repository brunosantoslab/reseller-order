package app.brunosantos.orderreceiving.exception;

import feign.FeignException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("VALIDATION_ERROR", "Invalid request data", errors));
    }

    @ExceptionHandler(ResellerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResellerNotFound(ResellerNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("RESELLER_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.ServiceUnavailable.class)
    public ResponseEntity<ErrorResponse> handleFeignServiceUnavailable(FeignException.ServiceUnavailable ex) {
        ErrorResponse error = new ErrorResponse("SERVICE_UNAVAILABLE", "Reseller service is unavailable");
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(SupplierServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleSupplierUnavailable(SupplierServiceUnavailableException ex) {
        ErrorResponse error = new ErrorResponse("SUPPLIER_UNAVAILABLE", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse("INVALID_ORDER", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}