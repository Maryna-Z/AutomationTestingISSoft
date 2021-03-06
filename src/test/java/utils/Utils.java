package utils;

import com.google.gson.Gson;
import dto.ItemPair;
import parser.NoSuchFileException;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static utils.Constants.REAL_ITEM_NAME;
import static utils.Constants.VIRTUAL_ITEM_NAME;

public class Utils {

    private static Properties properties = new Properties();
    private static final Gson gson = new Gson();

    public static int generateRandomValue(int min, int max){
        return (int)(min + Math.random() * max);
    }
    public static double generateRandomValue(double min, double max){
        return Math.round((double) (min + Math.random() * max)*100.0)/100.0;
    }

    public static List<Cart> generateCart(List<ItemPair> itemPairs){
        List<Cart> carts = new ArrayList<>();
        itemPairs.stream().forEach(itemPair -> {
            Cart cart = new Cart(itemPair.getName());
            addVirtualItems(cart, itemPair.getCountVirtualItems());
            addRealItems(cart, itemPair.getCountRealItems());
            carts.add(cart);
        });
        return carts;
    }

    private static void addVirtualItems(Cart cart, int count) {
        while (count > 0){
            VirtualItem virtualItem = new VirtualItem();
            virtualItem.setName(VIRTUAL_ITEM_NAME.get(Utils.generateRandomValue(0, 5)));
            virtualItem.setPrice(Utils.generateRandomValue(1000.d, 5000.d));
            virtualItem.setSizeOnDisk(Utils.generateRandomValue(100.d, 2000.d));

            cart.addVirtualItem(virtualItem);
            count--;
        }
    }

    private static void addRealItems(Cart cart, int count) {
        while (count > 0){
            RealItem realItem = new RealItem();
            realItem.setName(REAL_ITEM_NAME.get(Utils.generateRandomValue(0, 5)));
            realItem.setPrice(Utils.generateRandomValue(1000.d, 5000.d));
            realItem.setWeight(Utils.generateRandomValue(100.d, 2000.d));

            cart.addRealItem(realItem);
            count--;
        }
    }

    public static Properties getProperties(String path) {
        try (InputStream input = new FileInputStream(path)) {
            properties.load(input);
        } catch (IOException ex) {
            throw new NoSuchFileException("File not found", ex);
        }
        return properties;
    }

    public static void writeToFile(RealItem realItem) {
        try (FileWriter writer = new FileWriter("src/main/resources/marina.json")) {
            writer.write(gson.toJson(realItem));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RealItem parametrizeRealItem(RealItem realItem, String name, double price, double weight){
        realItem.setName(name);
        realItem.setPrice(price);
        realItem.setWeight(weight);
        return realItem;
    }

    public static VirtualItem parametrizeVirtualItem(VirtualItem virtualItem, String name, double price, double sizeOnDisk){
        virtualItem.setName(name);
        virtualItem.setPrice(price);
        virtualItem.setSizeOnDisk(sizeOnDisk);
        return virtualItem;
    }
}
