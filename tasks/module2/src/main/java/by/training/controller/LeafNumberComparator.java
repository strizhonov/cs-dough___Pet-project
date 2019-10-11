package by.training.controller;

import by.training.model.TextComposite;

import java.util.Comparator;

public class LeafNumberComparator implements Comparator<TextComposite> {

    private boolean asc;

    public LeafNumberComparator(boolean asc) {
        this.asc = asc;
    }

    @Override
    public int compare(TextComposite o1, TextComposite o2) {
        if (asc) {
            return Integer.compare(o1.getAll().size(), o2.getAll().size());
        } else {
            return Integer.compare(o2.getAll().size(), o1.getAll().size());
        }

    }
}
