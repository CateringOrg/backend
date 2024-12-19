package pl.edu.pw.ee.catering_backend.orders.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.orders.domain.Order;

import java.util.*;

@Service
public class SystemOutOrderSender implements ICateringCompanySystem {
    private final IOrderDispatchSerializer orderDispatchSerializer;
    private final Logger logger = LoggerFactory.getLogger(SystemOutOrderSender.class);

    public SystemOutOrderSender(IOrderDispatchSerializer orderDispatchSerializer) {
        this.orderDispatchSerializer = orderDispatchSerializer;
    }

    @Override
    public Map<Order, Boolean> sendOrders(List<Order> batch) {
        HashMap<Order, Boolean> serializationResult = new HashMap<>();
        List<String> payloadsToSend = new ArrayList<>();
        batch.forEach(payload -> {
            try {
                String payloadSerialization = orderDispatchSerializer.serialize(payload);
                logger.info("Successfully serialized {}", payloadSerialization);
                serializationResult.put(payload, true);
                payloadsToSend.add(payloadSerialization);
            } catch (Exception e) {
                serializationResult.put(payload, false);
                logger.error("Failed to send order dispatch payload", e);
            }
        });

        payloadsToSend.forEach(serializedPayload ->
                System.out.println("Sent payload to catering company" + serializedPayload)
        );

        return serializationResult;
    }
}
