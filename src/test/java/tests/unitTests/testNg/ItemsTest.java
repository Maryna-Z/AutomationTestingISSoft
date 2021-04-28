package tests.unitTests.testNg;

import org.testng.Assert;
import org.testng.annotations.Test;
import shop.RealItem;
import shop.VirtualItem;
import utils.Utils;

public class ItemsTest {

    @Test(groups = {"goodTests"}, testName = "Check RealItem")
    public void checkRealItem(){
        RealItem realItem = new RealItem();
        Utils.parametrizeRealItem(realItem, "Audi", 25698.00, 1900.03);
        Assert.assertEquals(realItem.toString(),
                String.format("Class: %s; Name: %s; Price: %s; Weight: %s",
                        realItem.getClass(),
                        realItem.getName(),
                        realItem.getPrice(),
                        realItem.getWeight()));
    }

    @Test(groups = {"goodTests"}, testName = "Check VirtualItem")
    public void checkVirtualItem(){
        VirtualItem virtualItem = new VirtualItem();
        Utils.parametrizeVirtualItem(virtualItem, "Home Design", 98.00, 190.00);
        Assert.assertEquals(virtualItem.toString(),
                String.format("Class: %s; Name: %s; Price: %s; Size on disk: %s",
                        virtualItem.getClass(),
                        virtualItem.getName(),
                        virtualItem.getPrice(),
                        virtualItem.getSizeOnDisk()));
    }
}
