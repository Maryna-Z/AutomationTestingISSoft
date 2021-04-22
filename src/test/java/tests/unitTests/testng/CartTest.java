package tests.testng;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;
import tests.testng.retry.MyRetry;
import utils.Utils;

import static tests.testng.JsonParserTest.carts;

public class CartTest {

    @Parameters({"cart-number"})
    @Test(groups = {"goodTests"}, testName = "Check addVirtualItem method")
    public void checkAddVirtualItem(int argument){
        double originalPrice = carts.get(argument).getTotalPrice();
        VirtualItem virtualItem = new VirtualItem();
        Utils.parametrizeVirtualItem(virtualItem, "Home Design", 98.00, 190.00);
        Cart cart = carts.get(argument);
        cart.addVirtualItem(virtualItem);

        assert(cart.getTotalPrice() == originalPrice + virtualItem.getPrice()*1.2);
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
        assert(totalPrice > cart.getTotalPrice());
    }

    @Parameters({"cart-number"})
    @Test(groups = {"goodTests"}, testName = "Check addRealItem method")
    public void checkAddRealItem(int argument){
        double originalPrice = carts.get(argument).getTotalPrice();
        RealItem realItem = new RealItem();
        Utils.parametrizeRealItem(realItem, "Audi", 25698.00, 1900.03);
        Cart cart = carts.get(argument);
        cart.addRealItem(realItem);

        assert(cart.getTotalPrice() == originalPrice + realItem.getPrice()*1.2);
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
        assert(totalPrice > cart.getTotalPrice());
    }
}
