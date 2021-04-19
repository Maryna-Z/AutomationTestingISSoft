package tests.unitTests.junit;

import dto.ItemPair;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import parser.JsonParser;
import parser.NoSuchFileException;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Constants.FILE_NAME_LIST;

public class Tests {


    private static JsonParser parser = new JsonParser();
    private static List<Cart> carts;
    private static final List<ItemPair> itemPairs = Arrays.asList(
            new ItemPair(FILE_NAME_LIST.get(0), 1, 1),
            new ItemPair(FILE_NAME_LIST.get(1), 0, 2),
            new ItemPair(FILE_NAME_LIST.get(2), 0, 3),
            new ItemPair(FILE_NAME_LIST.get(3), 4, 0),
            new ItemPair(FILE_NAME_LIST.get(4), 5, 0),
            new ItemPair(FILE_NAME_LIST.get(5), 0, 0)
    );

    @BeforeAll
    public static void setUp() {
        carts = Utils.generateCart(itemPairs);
        for (Cart cart : carts) {
            parser.writeToFile(cart);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {6})
    @DisplayName("File exist")
    public void filesExist(int argument) {
        for (int i = 0; i < argument; i++) {
            try {
                Assertions.assertTrue(Files.list(Paths.get("src/main/resources"))
                                .filter(Files::isRegularFile)
                                .map(path -> path.getFileName().toString())
                                .collect(Collectors.toList())
                                .contains(FILE_NAME_LIST.get(i) + ".json"),
                        String.format("File %s is exist", FILE_NAME_LIST.get(i) + ".json"));
            } catch (IOException ex) {
                throw new NoSuchFileException("Error to read the file", ex);
            }
        }
    }

    @Disabled("Excessive check")
    @Test
    @DisplayName("Quantity of files")
    public void filesQuantity() {
        try {
            Assertions.assertTrue(Files.list(Paths.get("src/main/resources")).count() >= itemPairs.size());
        } catch (IOException ex) {
            throw new NoSuchFileException("Error to read the file", ex);
        }
    }

    @Test
    @DisplayName("Saving of Cart with RealItems and VirtualItems")
    public void checkCartItemsSaving(){
        Cart cart = parser.readFromFile(new File("src/main/resources/" + FILE_NAME_LIST.get(0) + ".json"));
            Assertions.assertAll("Cart name and total price are correct",
                    () -> assertEquals(cart.getCartName(), carts.get(0).getCartName(), "Cart name"),
                    () -> assertEquals(cart.getTotalPrice(), carts.get(0).getTotalPrice(), "Total price")
            );
    }

    @Test
    @DisplayName("Throwing NoSuchFileException")
    public void notSuchException(){
        Assertions.assertThrows(NoSuchFileException.class,
                () -> new JsonParser().readFromFile(new File("NoSuchFile.txt")));
    }

    @Test
    @DisplayName("Check RealItem")
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

    @Test
    @DisplayName("Check VirtualItem")
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

    @ParameterizedTest
    @ValueSource(ints = {5})
    @DisplayName("Check addVirtualItem method")
    public void checkAddVirtualItem(int argument){
        double originalPrice = carts.get(argument).getTotalPrice();
        VirtualItem virtualItem = new VirtualItem();
        virtualItem.setName("Home Design");
        virtualItem.setPrice(98.00);
        virtualItem.setSizeOnDisk(190.00);
        Cart cart = carts.get(argument);
        cart.addVirtualItem(virtualItem);

        Assertions.assertTrue(cart.getTotalPrice() == originalPrice + virtualItem.getPrice()*1.2);
    }

    @ParameterizedTest
    @ValueSource(ints = {5})
    @DisplayName("Check deleteVirtualItem method")
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
        Assertions.assertTrue(totalPrice > cart.getTotalPrice());
    }

    @ParameterizedTest
    @ValueSource(ints = {5})
    @DisplayName("Check addRealItem method")
    public void checkAddRealItem(int argument){
        double originalPrice = carts.get(argument).getTotalPrice();
        RealItem realItem = new RealItem();
        realItem.setName("Audi");
        realItem.setPrice(25698.00);
        realItem.setWeight(1900.03);
        Cart cart = carts.get(argument);
        cart.addRealItem(realItem);

        Assertions.assertTrue(cart.getTotalPrice() == originalPrice + realItem.getPrice()*1.2);
    }

    @ParameterizedTest
    @ValueSource(ints = {5})
    @DisplayName("Check deleteRealItem method")
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
        Assertions.assertTrue(totalPrice > cart.getTotalPrice());
    }

    @AfterAll
    public static void cleanUp() throws IOException {
        for (int i = 0; i < 6; i++) {
            Path fileToDeletePath = Paths.get("src/main/resources/" + FILE_NAME_LIST.get(i) + ".json");
            Files.delete(fileToDeletePath);
        }
    }
}
