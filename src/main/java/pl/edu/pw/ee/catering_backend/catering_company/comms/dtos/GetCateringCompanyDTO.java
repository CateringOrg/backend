package pl.edu.pw.ee.catering_backend.catering_company.comms.dtos;

import lombok.Value;

import java.util.UUID;

@Value
public class GetCateringCompanyDTO {
    UUID id;
    String address;
    String name;
    String nip;
}
