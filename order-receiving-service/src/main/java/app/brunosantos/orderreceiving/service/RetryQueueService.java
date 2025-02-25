package app.brunosantos.orderreceiving.service;

import java.util.UUID;

public interface RetryQueueService {
    void enqueue(UUID orderId);
    void processRetries();
}