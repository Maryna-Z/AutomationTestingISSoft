package tests.testNg;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;
import tests.testNg.retry.MyRetry;

import static tests.testNg.JsonParserTest.carts;

public class CartTest {
    @Parameters({"cart-number"})
    @Test(groups = {"goodTests"}, testName = "Check addVirtualItem method")
    public void checkAddVirtualItem(String cartNumber){
        double originalPrice = carts.get(Integer.valueOf(cartNumber)).getTotalPrice();
        VirtualItem virtualItem = new VirtualItem();
        virtualItem.setName("Home Design");
        virtualItem.setPrice(98.00);
        virtualItem.setSizeOnDisk(190.00);
        Cart cart = carts.get(Integer.valueOf(cartNumber));
        cart.addVirtualItem(virtualItem);

        assert(cart.getTotalPrice() == originalPrice + virtualItem.getPrice()*1.2);
    }

    @Parameters({"cart-number"})
    @Test(groups = {"goodTests"}, testName = "Check deleteVirtualItem method", retryAnalyzer = MyRetry.class)
    public void checkDeleteVirtualItem(int argument){
        double totalPrice = 0.d;
        Cart cart = carts.get(argument);
        VirtualItem virtualItem = new VirtualItem();
        virtualItem.setName("Home Design");
        virtualItem.setPrice(98.00);
        virtualItem.setSizeOnDisk(190.00);
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
        realItem.setName("Audi");
        realItem.setPrice(25698.00);
        realItem.setWeight(1900.03);
        Cart cart = carts.get(argument);
        cart.addRealItem(realItem);

        assert(cart.getTotalPrice() == originalPrice + realItem.getPrice()*1.2);
    }

    @Parameters({"cart-number"})
    @Test(groups = {"goodTests"}, testName = "Check deleteRealItem method", retryAnalyzer = MyRetry.class)
    public void checkDeleteRealItem(int argument){
        double totalPrice = 0.d;
        Cart cart = carts.get(argument);
        RealItem realItem = new RealItem();
        realItem.setName("Audi");
        realItem.setPrice(25698.00);
        realItem.setWeight(1900.03);
        cart.addRealItem(realItem);
        totalPrice=cart.getTotalPrice();
        cart.deleteRealItem(realItem);
        assert(totalPrice > cart.getTotalPrice());
    }
}
