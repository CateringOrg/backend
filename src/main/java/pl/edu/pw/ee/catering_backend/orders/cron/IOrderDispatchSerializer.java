package pl.edu.pw.ee.catering_backend.orders.cron;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.edu.pw.ee.catering_backend.orders.domain.Order;

public interface IOrderDispatchSerializer {
    String serialize(Order order) throws JsonProcessingException;
}
