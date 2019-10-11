package by.training.controller;

import by.training.model.SentenceComposite;

import java.util.Comparator;

public class WordNumberComparator implements Comparator<SentenceComposite> {

    private boolean asc;

    public WordNumberComparator(boolean asc) {
        this.asc = asc;
    }

    @Override
    public int compare(SentenceComposite o1, SentenceComposite o2) {
        if (asc) {
            return Integer.compare(o1.getAll().size(), o2.getAll().size());
        } else {
            return Integer.compare(o2.getAll().size(), o1.getAll().size());
        }

    }
}
