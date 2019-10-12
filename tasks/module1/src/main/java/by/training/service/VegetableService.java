package by.training.service;

import by.training.entity.Vegetable;
import by.training.repository.Repository;
import by.training.repository.Specification;

import java.util.Comparator;
import java.util.List;

public class VegetableService {

    private Repository<Vegetable> repo;

    public VegetableService(Repository<Vegetable> repo) {
        this.repo = repo;
    }

    public void addToRepo(Vegetable vegetable) {
        repo.add(vegetable);
    }

    public List<Vegetable> sort(Comparator<Vegetable> comparator) {
        List<Vegetable> sortedVegetables = repo.getData();
        sortedVegetables.sort(comparator);
        return sortedVegetables;
    }

    public List<Vegetable> findBy(Specification<Vegetable> spec) {
        return repo.find(spec);
    }

    public double getSaladCalories() {
        double calories = 0;

        for (Vegetable vegetable : repo.getData()) {
            calories += vegetable.getCalories();
        }

        return calories;
    }

    public List<Vegetable> getAll() {
        return repo.getData();
    }

    public Repository<Vegetable> getRepo() {
        return repo;
    }
}