package pl.edu.pw.ee.catering_backend.offers.domain;

import org.springframework.stereotype.Service;
import pl.edu.pw.ee.catering_backend.infrastructure.db.repositories.MealRepository;
import pl.edu.pw.ee.catering_backend.offers.comms.MealMapper;
import pl.edu.pw.ee.catering_backend.offers.comms.dtos.GetMealDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientOffersService implements IClientOffersService {

    private final MealMapper mealMapper;

    private final IMealsPersistenceService mealsPersistenceService;

    public ClientOffersService(MealRepository mealRepository, MealMapper mealMapper, IMealsPersistenceService mealsPersistenceService) {
        this.mealMapper = mealMapper;
        this.mealsPersistenceService = mealsPersistenceService;
    }

    @Override
    public List<GetMealDTO> getMeals() {
        return mealsPersistenceService.getMeals().stream()
                .map(mealMapper::mapToGetMealDTO)
                .collect(Collectors.toList());    }
}