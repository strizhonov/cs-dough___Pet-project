package by.training.repository;

import by.training.entity.Vegetable;

import java.util.ArrayList;
import java.util.List;

public class VegetableRepository implements Repository<Vegetable> {

    private List<Vegetable> salad;

    public VegetableRepository() {
        this.salad = new ArrayList<>();
    }

    public List<Vegetable> getData() {
        return new ArrayList<>(salad);
    }

    @Override
    public void add(Vegetable vegetable) {
        salad.add(vegetable);
    }

    @Override
    public void remove(Vegetable vegetable) {
        salad.remove(vegetable);
    }

    @Override
    public List<Vegetable> findBy(Specification<Vegetable> spec) {
        List<Vegetable> found = new ArrayList<>();

        for (Vegetable vegetable : salad) {
            if (spec.isSatisfiedBy(vegetable)) {
                found.add(vegetable);
            }
        }

        return found;
    }
}
