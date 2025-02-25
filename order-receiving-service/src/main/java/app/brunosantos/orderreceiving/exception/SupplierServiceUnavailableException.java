package app.brunosantos.orderreceiving.exception;

public class SupplierServiceUnavailableException extends RuntimeException {
    public SupplierServiceUnavailableException(String message) {
        super(message);
    }
}