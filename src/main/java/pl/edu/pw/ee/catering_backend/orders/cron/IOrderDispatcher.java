package pl.edu.pw.ee.catering_backend.orders.cron;

import pl.edu.pw.ee.catering_backend.orders.domain.Order;

import java.util.List;
import java.util.Map;

public interface IOrderDispatcher {
    Map<Boolean, Order> sendBatch(List<Order> batch);
}
