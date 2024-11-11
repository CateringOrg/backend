package pl.edu.pw.ee.catering_backend.catering_company.comms.dtos;

import lombok.Value;

@Value
public class AddCateringCompanyDTO {

  String address;
  String name;
  String nip;
}
