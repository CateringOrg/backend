package pl.edu.pw.ee.catering_backend.orders.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SystemOutOrderSender implements IOrderDispatcher {
    private final IOrderDispatchSerializer orderDispatchSerializer;
    private final Logger logger = LoggerFactory.getLogger(SystemOutOrderSender.class);

    public SystemOutOrderSender(IOrderDispatchSerializer orderDispatchSerializer) {
        this.orderDispatchSerializer = orderDispatchSerializer;
    }

    @Override
    public Map<Boolean, OrderDispatchPayload> sendBatch(List<OrderDispatchPayload> batchedPayload) {
        HashMap<Boolean, OrderDispatchPayload> serializationResult = new HashMap<>();
        List<String> payloadsToSend = new ArrayList<>();
        batchedPayload.forEach(payload -> {
            try {
                String payloadSerialization = orderDispatchSerializer.serialize(payload);
                logger.info("Successfully serialized {}", payloadSerialization);
                serializationResult.put(true, payload);
                payloadsToSend.add(payloadSerialization);
            } catch (Exception e) {
                serializationResult.put(false, payload);
                logger.error("Failed to send order dispatch payload", e);
            }
        });

        payloadsToSend.forEach(serializedPayload ->
                System.out.println("Sent payload to catering company {}" + serializedPayload)
        );

        return serializationResult;
    }
}
