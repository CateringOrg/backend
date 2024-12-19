package pl.edu.pw.ee.catering_backend.payment.domain;

import pl.edu.pw.ee.catering_backend.payment.comm.CreatePaymentDTO;

public interface IPaymentService {
    boolean payForOrder(CreatePaymentDTO createPaymentDTO);
}