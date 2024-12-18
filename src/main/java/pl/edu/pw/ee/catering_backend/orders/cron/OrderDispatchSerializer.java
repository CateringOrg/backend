package pl.edu.pw.ee.catering_backend.orders.cron;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.orders.comms.serializers.LocalDateTimeDeserializer;
import pl.edu.pw.ee.catering_backend.orders.comms.serializers.LocalDateTimeSerializer;
import pl.edu.pw.ee.catering_backend.orders.domain.Order;

import java.time.LocalDateTime;

@Service
public  class OrderDispatchSerializer implements IOrderDispatchSerializer {

    final SimpleModule localDateTimeSerialization = new SimpleModule();
    final ObjectMapper objectMapper = new ObjectMapper();

    public OrderDispatchSerializer() {
        localDateTimeSerialization.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        localDateTimeSerialization.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());

        objectMapper.registerModule(localDateTimeSerialization);
    }

    @Override
    public String serialize(Order order) throws JsonProcessingException {
        return objectMapper.writeValueAsString(order);
    }
}