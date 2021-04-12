package tests;

import dto.ItemPair;
import exceptions.CommonTestException;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.*;
import parser.JsonParser;
import parser.NoSuchFileException;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static constants.Constants.FILE_NAME_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void filesExist() {
        for (int i = 0; i < 6; i++) {
            try {
                Assertions.assertTrue(Files.list(Paths.get("src/main/resources"))
                                .filter(Files::isRegularFile)
                                .map(path -> path.getFileName().toString())
                                .collect(Collectors.toList())
                                .contains(FILE_NAME_LIST.get(i) + ".json"),
                        String.format("File %s is exist", FILE_NAME_LIST.get(i) + ".json"));
            } catch (IOException e) {
                throw new CommonTestException("Error to read the file");
            }
        }
    }

    @Disabled("Excessive check")
    @Test
    public void filesQuantity() {
        try {
            Assertions.assertTrue(Files.list(Paths.get("src/main/resources")).count() >= itemPairs.size());
        } catch (IOException ex) {
            throw new CommonTestException("Error to read the file", ex);
        }
    }

    @Test
    public void checkCartItemsSaving(){
        Cart cart = parser.readFromFile(new File("src/main/resources/" + FILE_NAME_LIST.get(0) + ".json"));
        Field realItems = null;
        Field virtualItems = null;
        try {
            realItems = Cart.class.getDeclaredField("realItems");
            virtualItems = Cart.class.getDeclaredField("virtualItems");
            realItems.setAccessible(true);
            virtualItems.setAccessible(true);
            List<RealItem> realItemList = (List<RealItem>) realItems.get(cart);
            List<RealItem> vitalItemList = (List<RealItem>) virtualItems.get(cart);

            Assertions.assertTrue(
                    CollectionUtils.isNotEmpty(realItemList) && CollectionUtils.isNotEmpty(vitalItemList)
                            && realItemList.size() == 1 && vitalItemList.size() == 1
            );
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new CommonTestException("Error to read the file", ex);
        }
    }

    @Test
    public void notSuchException(){
        Assertions.assertThrows(NoSuchFileException.class,
                () -> new JsonParser().readFromFile(new File("NoSuchFile.txt")));
    }

    @Test
    public void checkRealItem(){
        Cart cart = parser.readFromFile(new File("src/main/resources/" + FILE_NAME_LIST.get(1) + ".json"));
        Field realItems = null;
        try {
            realItems = Cart.class.getDeclaredField("realItems");
            realItems.setAccessible(true);
            List<RealItem> realItemList = (List<RealItem>) realItems.get(cart);

            Cart cartFromMemory = carts.get(1);
            List<RealItem> realItemListFromMemory = (List<RealItem>) realItems.get(cartFromMemory);

            Assertions.assertAll(
                    "The value of fields of real item from memory is equal the value of field of real item from file",
                    () -> assertEquals(
                            realItemList.get(0).getWeight(),
                            realItemListFromMemory.get(0).getWeight(),
                            "Weight"),
                    () -> assertEquals(
                            realItemList.get(0).getPrice(),
                            realItemListFromMemory.get(0).getPrice(),
                            "Price")
            );
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new CommonTestException("Error to read the file", ex);
        }
    }

    @Test
    public void checkVirtualItem(){
        Cart cart = parser.readFromFile(new File("src/main/resources/" + FILE_NAME_LIST.get(3) + ".json"));
        Field virtualItems = null;
        try {
            virtualItems = Cart.class.getDeclaredField("virtualItems");
            virtualItems.setAccessible(true);
            List<VirtualItem> virtualItemList = (List<VirtualItem>) virtualItems.get(cart);

            Cart cartFromMemory = carts.get(3);
            List<VirtualItem> virtualItemListFromMemory = (List<VirtualItem>) virtualItems.get(cartFromMemory);

            Assertions.assertAll(
                    "The value of fields of virtual item from memory is equal the value of field of virtual item from file",
                    () -> assertEquals(
                            virtualItemList.get(0).getSizeOnDisk(),
                            virtualItemListFromMemory.get(0).getSizeOnDisk(),
                            "Size on disk"),
                    () -> assertEquals(
                            virtualItemList.get(0).getPrice(),
                            virtualItemListFromMemory.get(0).getPrice(),
                            "Price")
            );
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new CommonTestException("Error to read the file", ex);
        }
    }

    @Test
    public void checkCart(){
        Cart cart = parser.readFromFile(new File("src/main/resources/" + FILE_NAME_LIST.get(0) + ".json"));
        Field virtualItems = null;
        Field realItems = null;
        try {
            realItems = Cart.class.getDeclaredField("realItems");
            realItems.setAccessible(true);
            List<RealItem> realItemList = (List<RealItem>) realItems.get(cart);

            virtualItems = Cart.class.getDeclaredField("virtualItems");
            virtualItems.setAccessible(true);
            List<VirtualItem> virtualItemList = (List<VirtualItem>) virtualItems.get(cart);

            Cart cartFromMemory = carts.get(0);
            List<VirtualItem> virtualItemListFromMemory = (List<VirtualItem>) virtualItems.get(cartFromMemory);
            List<RealItem> realItemListFromMemory = (List<RealItem>) realItems.get(cartFromMemory);

            Assertions.assertAll(
                    "The cart from memory is equal the cart from file",
                    () -> assertEquals(cart.getCartName(),
                            cartFromMemory.getCartName(),
                            "Cart name"),
                    () -> assertEquals(cart.getTotalPrice(),
                            cartFromMemory.getTotalPrice(),
                            "Total price"),
                    () -> assertEquals(
                            realItemList.get(0).getWeight(),
                            realItemListFromMemory.get(0).getWeight(),
                            "Weight"),
                    () -> assertEquals(
                            realItemList.get(0).getPrice(),
                            realItemListFromMemory.get(0).getPrice(),
                            "Price"),
                    () -> assertEquals(
                            virtualItemList.get(0).getSizeOnDisk(),
                            virtualItemListFromMemory.get(0).getSizeOnDisk(),
                            "Size on disk"),
                    () -> assertEquals(
                            virtualItemList.get(0).getPrice(),
                            virtualItemListFromMemory.get(0).getPrice(),
                            "Price")
            );
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new CommonTestException("Error to read the file", ex);
        }
    }

    @AfterAll
    public static void cleanUp() throws IOException {
        for (int i = 0; i < 6; i++) {
            Path fileToDeletePath = Paths.get("src/main/resources/" + FILE_NAME_LIST.get(i) + ".json");
            Files.delete(fileToDeletePath);
        }
    }
}
