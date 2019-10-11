package by.training.service;

import by.training.entity.*;
import by.training.entity.Tomato;
import by.training.entity.Leek;
import by.training.entity.Carrot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import by.training.controller.VegetableCaloriesComparator;
import by.training.controller.VegetableWeightComparator;
import by.training.repository.VegetableRepository;
import by.training.repository.Specification;
import by.training.repository.VegetableCaloriesHigherThanSpecification;
import by.training.repository.VegetableCaloriesLowerThanSpecification;

import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class VegetableServiceTest {

    @Mock
    private VegetableService vegetableService;

    @Before
    public void init() {
        vegetableService = new VegetableService(new VegetableRepository());
        vegetableService.addToRepo(new Carrot(0.2, 30, false, false));
        vegetableService.addToRepo(new Leek(0.1, 30, 0.2, 0.2));
        vegetableService.addToRepo(new Tomato(0.14, 44, false, false));
    }

    @Test
    public void addVegetableToSalad() {
        int count = vegetableService.getRepo().getData().size();
        assertEquals(3, count);

        vegetableService.addToRepo(new Carrot(0.2, 20, false, false));
        count = vegetableService.getRepo().getData().size();
        assertEquals(4, count);
    }

    @Test
    public void sortByCalories() {
        Comparator<Vegetable> caloriesComparator = new VegetableCaloriesComparator(true);

        List<Vegetable> sortedVegetables = vegetableService.sort(caloriesComparator);

        assertTrue(sortedVegetables.get(0).getCalories() <= sortedVegetables.get(1).getCalories() &&
                sortedVegetables.get(1).getCalories() <= sortedVegetables.get(2).getCalories());
    }

    @Test
    public void sortByCaloriesAndWeight() {
        Comparator<Vegetable> caloriesComparator = new VegetableCaloriesComparator(true);
        Comparator<Vegetable> weightComparator = new VegetableWeightComparator(true);
        Comparator<Vegetable> caloriesAndWeightComparator = caloriesComparator.thenComparing(weightComparator);

        List<Vegetable> sortedVegetables = vegetableService.sort(caloriesAndWeightComparator);

        assertEquals(30, sortedVegetables.get(0).getCalories(), 0.01);
        assertEquals(0.1, sortedVegetables.get(0).getWeight(), 0.01);
        assertEquals(30, sortedVegetables.get(1).getCalories(), 0.01);
        assertEquals(0.2, sortedVegetables.get(1).getWeight(), 0.01);
        assertEquals(44, sortedVegetables.get(2).getCalories(), 0.01);
    }

    @Test
    public void getSaladCalories() {
        double calories = vegetableService.getSaladCalories();

        assertEquals(104, calories, 0.01);
    }

    @Test
    public void getVegetablesByCaloriesRange() {
        Specification<Vegetable> spec1 = new VegetableCaloriesHigherThanSpecification(5);
        Specification<Vegetable> spec2 = new VegetableCaloriesLowerThanSpecification(30);
        List<Vegetable> vegetables = vegetableService.findBy(spec1.and(spec2));
        assertEquals(2, vegetables.size());
    }

}
