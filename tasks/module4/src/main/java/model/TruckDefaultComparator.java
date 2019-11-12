package model;

import java.util.Comparator;

public class TruckDefaultComparator implements Comparator<Truck> {

    @Override
    public int compare(Truck o1, Truck o2) {
        if (o1.isPerishable() && o1.getCargoWeight() > 0 && !o2.isPerishable()) {
            return -1;
        } else if (o2.isPerishable() && o2.getCargoWeight() > 0 && !o1.isPerishable()) {
            return 1;
        } else {
            return 0;
        }
    }

}
