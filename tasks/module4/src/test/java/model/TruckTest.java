package model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TruckTest {

    @Test
    public void call() throws InterruptedException {
        Warehouse warehouse = Warehouse.getInstance();
        warehouse.init(100, 50, 4);
        Truck truck = new Truck(1, 20, 20, false, warehouse);
        truck.call();
        Assert.assertEquals(70, warehouse.getCargoWeight());
        Assert.assertEquals(0, truck.getCargoWeight());
    }

    @Test(expected = InterruptedException.class)
    public void callWithoutWarehouse() throws InterruptedException {
        Truck truck = new Truck(1, 20, 20, false, null);
        truck.call();
    }

}
