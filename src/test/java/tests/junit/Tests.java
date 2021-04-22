package tests.junit;

import dto.ItemPair;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import parser.JsonParser;
import parser.NoSuchFileException;
import shop.Cart;
import shop.RealItem;
import shop.VirtualItem;
import utils.Constants;
import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @MethodSource("fileNameProvider")
    @DisplayName("File exist")
    public void filesExist(List<String> collect1) {
        List<String> collect2 = FILE_NAME_LIST.stream().map(s -> s = s + ".json").collect(Collectors.toList());
        Assertions.assertTrue(collect1.containsAll(collect2));
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

    @ParameterizedTest
    @ValueSource(ints = {0})
    @DisplayName("Saving of Cart with RealItems and VirtualItems")
    public void checkCartItemsSaving(int argument) {
        Cart cart = parser.readFromFile(new File("src/main/resources/" + FILE_NAME_LIST.get(argument) + ".json"));
        Assertions.assertAll("Cart name and total price are correct",
                () -> assertEquals(cart.getCartName(), carts.get(argument).getCartName(), "Cart name"),
                () -> assertEquals(cart.getTotalPrice(), carts.get(argument).getTotalPrice(), "Total price")
        );
    }

    @Test
    @DisplayName("Throwing NoSuchFileException")
    public void notSuchException() {
        Assertions.assertThrows(NoSuchFileException.class,
                () -> new JsonParser().readFromFile(new File("NoSuchFile.txt")));
    }

    @ParameterizedTest
    @MethodSource("fileProvider")
    @DisplayName("Parsing wrong file")
    public void parsingWrongFile(String fileName) {
        Assertions.assertThrows(NoSuchFileException.class,
                () -> new JsonParser().readFromFile(new File(fileName)));
    }

    @ParameterizedTest
    @MethodSource("fileProvider")
    @DisplayName("Parsing broken file")
    public void parsingBrokenFile(String fileName) {
        try {
            Files.write(Paths.get("src/main/resources/" + fileName), "}".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            throw new NoSuchFileException("Error to write to the file", ex);
        }
        Assertions.assertThrows(NoSuchFileException.class,
                () -> new JsonParser().readFromFile(new File(fileName)));
    }

    @Test
    @DisplayName("Check RealItem")
    public void checkRealItem() {
        RealItem realItem = new RealItem();
        Utils.parametrizeRealItem(realItem, "Audi", 25698.00, 1900.03);
        Assertions.assertEquals(realItem.toString(),
                String.format("Class: %s; Name: %s; Price: %s; Weight: %s",
                    realItem.getClass(),
                    realItem.getName(),
                    realItem.getPrice(),
                    realItem.getWeight()
        ));
    }

    @Test
    @DisplayName("Check VirtualItem")
    public void checkVirtualItem() {
        VirtualItem virtualItem = new VirtualItem();
        Utils.parametrizeVirtualItem(virtualItem, "Home Design", 98.00, 190.00);
        Assertions.assertEquals(virtualItem.toString(),
                String.format("Class: %s; Name: %s; Price: %s; Size on disk: %s",
                    virtualItem.getClass(),
                    virtualItem.getName(),
                    virtualItem.getPrice(),
                    virtualItem.getSizeOnDisk()
        ));
    }

    @ParameterizedTest
    @ValueSource(ints = {5})
    @DisplayName("Check addVirtualItem method")
    public void checkAddVirtualItem(int argument) {
        double originalPrice = carts.get(argument).getTotalPrice();
        VirtualItem virtualItem = new VirtualItem();
        Utils.parametrizeVirtualItem(virtualItem, "Home Design", 98.00, 190.00);
        Cart cart = carts.get(argument);
        cart.addVirtualItem(virtualItem);

        Assertions.assertEquals(cart.getTotalPrice(), originalPrice + virtualItem.getPrice() * 1.2);
    }

    @ParameterizedTest
    @ValueSource(ints = {5})
    @DisplayName("Check deleteVirtualItem method")
    public void checkDeleteVirtualItem(int argument) {
        double totalPrice = 0.d;
        Cart cart = carts.get(argument);
        VirtualItem virtualItem = new VirtualItem();
        Utils.parametrizeVirtualItem(virtualItem, "Home Design", 98.00, 190.00);
        cart.addVirtualItem(virtualItem);
        totalPrice = cart.getTotalPrice();
        cart.deleteVirtualItem(virtualItem);
        Assertions.assertTrue(totalPrice > cart.getTotalPrice());
    }

    @ParameterizedTest
    @ValueSource(ints = {5})
    @DisplayName("Check addRealItem method")
    public void checkAddRealItem(int argument) {
        double originalPrice = carts.get(argument).getTotalPrice();
        RealItem realItem = new RealItem();
        Utils.parametrizeRealItem(realItem, "Audi", 25698.00, 1900.03);
        Cart cart = carts.get(argument);
        cart.addRealItem(realItem);

        Assertions.assertEquals(cart.getTotalPrice(), originalPrice + realItem.getPrice() * 1.2);
    }

    @ParameterizedTest
    @ValueSource(ints = {5})
    @DisplayName("Check deleteRealItem method")
    public void checkDeleteRealItem(int argument) {
        double totalPrice = 0.d;
        Cart cart = carts.get(argument);
        RealItem realItem = new RealItem();
        Utils.parametrizeRealItem(realItem, "Audi", 25698.00, 1900.03);
        cart.addRealItem(realItem);
        totalPrice = cart.getTotalPrice();
        cart.deleteRealItem(realItem);
        Assertions.assertTrue(totalPrice > cart.getTotalPrice());
    }

    @AfterAll
    public static void cleanUp() throws IOException {
        if (Files.list(Paths.get("src/main/resources/")).findAny().isPresent()) {
            Files.walk(Paths.get("src/main/resources/"))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            boolean isMyTestFile = path.getFileName().toString().contains(Constants.FILE_NAME);
                            if (isMyTestFile) {
                                Files.delete(path);
                            }
                        } catch (IOException ex) {
                            System.out.println("Can't delete file path: " + path);
                        }
                    });
        }

    }

    static Stream<List<String>> fileNameProvider() {
        List<String> collect1 = null;
        try {
            collect1 = Files.list(Paths.get("src/main/resources"))
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new NoSuchFileException("Error to read the file", ex);
        }
        return Stream.of(collect1);
    }

    static Stream<String> fileProvider() {
        RealItem realItem = new RealItem();
        Utils.parametrizeRealItem(realItem, "Audi", 25698.00, 1900.03);
        String fileName = Constants.FILE_NAME + ".json";
        Utils.writeToFile(realItem);
        return Stream.of(fileName);
    }
}
