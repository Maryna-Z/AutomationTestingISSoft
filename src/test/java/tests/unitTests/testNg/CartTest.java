package tests.unitTests.testNg;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;
import tests.unitTests.testNg.retry.MyRetry;
import utils.Utils;

import static tests.unitTests.testNg.JsonParserTest.carts;


public class CartTest {

    @Parameters({"cart-number"})
    @Test(groups = {"goodTests"}, testName = "Check addVirtualItem method")
    public void checkAddVirtualItem(int argument){
        double originalPrice = carts.get(argument).getTotalPrice();
        VirtualItem virtualItem = new VirtualItem();
        Utils.parametrizeVirtualItem(virtualItem, "Home Design", 98.00, 190.00);
        Cart cart = carts.get(argument);
        cart.addVirtualItem(virtualItem);

        Assert.assertTrue(cart.getTotalPrice() == originalPrice + virtualItem.getPrice()*1.2);
    }

    @Parameters({"cart-number"})
    @Test(groups = {"goodTests"}, testName = "Check deleteVirtualItem method", retryAnalyzer = MyRetry.class)
    public void checkDeleteVirtualItem(int argument){
        double totalPrice = 0.d;
        Cart cart = carts.get(argument);
        VirtualItem virtualItem = new VirtualItem();
        Utils.parametrizeVirtualItem(virtualItem, "Home Design", 98.00, 190.00);
        cart.addVirtualItem(virtualItem);
        totalPrice=cart.getTotalPrice();
        cart.deleteVirtualItem(virtualItem);
        Assert.assertTrue(totalPrice > cart.getTotalPrice());
    }

    @Parameters({"cart-number"})
    @Test(groups = {"goodTests"}, testName = "Check addRealItem method")
    public void checkAddRealItem(int argument){
        double originalPrice = carts.get(argument).getTotalPrice();
        RealItem realItem = new RealItem();
        Utils.parametrizeRealItem(realItem, "Audi", 25698.00, 1900.03);
        Cart cart = carts.get(argument);
        cart.addRealItem(realItem);
        Assert.assertEquals(cart.getTotalPrice(),originalPrice + realItem.getPrice()*1.2);
    }

    @Parameters({"cart-number"})
    @Test(groups = {"goodTests"}, testName = "Check deleteRealItem method")
    public void checkDeleteRealItem(int argument){
        double totalPrice = 0.d;
        Cart cart = carts.get(argument);
        RealItem realItem = new RealItem();
        Utils.parametrizeRealItem(realItem, "Audi", 25698.00, 1900.03);
        cart.addRealItem(realItem);
        totalPrice=cart.getTotalPrice();
        cart.deleteRealItem(realItem);
        Assert.assertTrue(totalPrice > cart.getTotalPrice());
    }
}
