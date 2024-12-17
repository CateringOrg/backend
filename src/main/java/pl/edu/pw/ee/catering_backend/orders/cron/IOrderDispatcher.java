package pl.edu.pw.ee.catering_backend.orders.cron;

import java.util.List;
import java.util.Map;

public interface IOrderDispatcher {
    Map<Boolean, OrderDispatchPayload> sendBatch(List<OrderDispatchPayload> batchedPayload);
}
