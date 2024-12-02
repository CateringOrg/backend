package pl.edu.pw.ee.catering_backend.offers.domain;

import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.MealRepository;
import pl.edu.pw.ee.catering_backend.offers.comms.MealMapper;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientOffersService implements IClientOffersService {

    private final MealRepository mealRepository;
    private final MealMapper mealMapper;

    public ClientOffersService(MealRepository mealRepository, MealMapper mealMapper) {
        this.mealRepository = mealRepository;
        this.mealMapper = mealMapper;
    }

    @Override
    public List<GetMealDTO> getMeals() {
        return mealRepository.findAll().stream()
                .map(mealMapper::mapToGetMealDTO)
                .collect(Collectors.toList());
    }
}