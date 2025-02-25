package app.brunosantos.orderreceiving.exception;

public class ResellerNotFoundException extends RuntimeException {
    public ResellerNotFoundException(String cnpj) {
        super("Reseller not found with CNPJ: " + cnpj);
    }
}