package app.brunosantos.orderreceiving.model;

public enum OrderStatus {
    PENDING,
    RETRY_PENDING,
    PROCESSED,
    FAILED;

    public String getStatus() {
        return this.name();
    }
}
