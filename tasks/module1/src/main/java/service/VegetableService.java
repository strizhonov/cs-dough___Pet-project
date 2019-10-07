package service;

import entity.Vegetable;
import repository.Repository;
import repository.Specification;

import java.util.Comparator;
import java.util.List;

public class VegetableService implements EntityService<Vegetable> {

    private Repository<Vegetable> repo;

    public VegetableService(Repository<Vegetable> repo) {
        this.repo = repo;
    }

    @Override
    public void addToRepo(Vegetable vegetable) {
        repo.add(vegetable);
    }

    @Override
    public List<Vegetable> sort(Comparator<Vegetable> comparator) {
        List<Vegetable> sortedVegetables = repo.getData();
        sortedVegetables.sort(comparator);
        return sortedVegetables;
    }

    @Override
    public List<Vegetable> findBy(Specification<Vegetable> spec) {
        return repo.findBy(spec);
    }

    public double getSaladCalories() {
        double calories = 0;

        for (Vegetable vegetable : repo.getData()) {
            calories += vegetable.getCalories();
        }

        return calories;
    }

    @Override
    public List<Vegetable> getAll() {
        return repo.getData();
    }

    public Repository<Vegetable> getRepo() {
        return repo;
    }
}