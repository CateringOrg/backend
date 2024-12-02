package pl.edu.pw.ee.catering_backend.offers.domain;

import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;

import java.util.List;

public interface IClientOffersService {

    List<GetMealDTO> getMeals();
}
