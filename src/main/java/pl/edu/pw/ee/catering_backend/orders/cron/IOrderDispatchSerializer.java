package pl.edu.pw.ee.catering_backend.orders.cron;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IOrderDispatchSerializer {
    String serialize(OrderDispatchPayload payload) throws JsonProcessingException;
}
