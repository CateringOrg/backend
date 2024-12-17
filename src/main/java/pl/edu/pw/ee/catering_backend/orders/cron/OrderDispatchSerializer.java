package pl.edu.pw.ee.catering_backend.orders.cron;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public  class OrderDispatchSerializer implements IOrderDispatchSerializer {
    @Override
    public String serialize(OrderDispatchPayload payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(payload);
    }
}