package tests.testNg;

import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;
import shop.RealItem;
import shop.VirtualItem;

public class ItemsTest {
    @Test(groups = {"goodTests"}, testName = "Check RealItem")
    public void checkRealItem(){
        RealItem realItem = new RealItem();
        realItem.setName("Audi");
        realItem.setPrice(25698.00);
        realItem.setWeight(1900.03);
        Assertions.assertTrue(realItem.toString().equals(String.format(
                "Class: %s; Name: %s; Price: %s; Weight: %s",
                realItem.getClass(),
                realItem.getName(),
                realItem.getPrice(),
                realItem.getWeight())
        ));
    }

    @Test(groups = {"goodTests"}, testName = "Check VirtualItem")
    public void checkVirtualItem(){
        VirtualItem virtualItem = new VirtualItem();
        virtualItem.setName("windows");
        virtualItem.setPrice(125.00);
        virtualItem.setSizeOnDisk(2699.00);
        Assertions.assertTrue(virtualItem.toString().equals(String.format(
                "Class: %s; Name: %s; Price: %s; Size on disk: %s",
                virtualItem.getClass(),
                virtualItem.getName(),
                virtualItem.getPrice(),
                virtualItem.getSizeOnDisk()
        )));
    }
}
