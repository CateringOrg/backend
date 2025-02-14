package pl.edu.pw.ee.catering_backend.delivery.application;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delivery-companies")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DeliveryManagementController {

}
