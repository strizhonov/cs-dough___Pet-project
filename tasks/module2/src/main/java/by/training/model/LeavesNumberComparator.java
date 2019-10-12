package by.training.model;

import java.util.Comparator;

public class LeavesNumberComparator implements Comparator<TextComposite> {

    private boolean asc;

    public LeavesNumberComparator(boolean asc) {
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
