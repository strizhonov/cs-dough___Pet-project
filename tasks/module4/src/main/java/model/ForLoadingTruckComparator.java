package model;

import java.util.Comparator;

public class ForLoadingTruckComparator implements Comparator<Truck> {

    @Override
    public int compare(Truck o1, Truck o2) {
        if (o1.getCargoWeight() > 0 && o2.getCargoWeight() == 0) {
            return -1;
        } else if (o2.getCargoWeight() > 0 && o1.getCargoWeight() == 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
