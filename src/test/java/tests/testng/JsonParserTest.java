package tests.testng;

import dto.ItemPair;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import parser.JsonParser;
import parser.NoSuchFileException;
import shop.Cart;
import shop.RealItem;
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

import static utils.Constants.FILE_NAME_LIST;

public class JsonParserTest {
    private static JsonParser parser = new JsonParser();
    static List<Cart> carts;
    private static final List<ItemPair> itemPairs = Arrays.asList(
            new ItemPair(FILE_NAME_LIST.get(0), 1, 1),
            new ItemPair(FILE_NAME_LIST.get(1), 0, 2),
            new ItemPair(FILE_NAME_LIST.get(2), 0, 3),
            new ItemPair(FILE_NAME_LIST.get(3), 4, 0),
            new ItemPair(FILE_NAME_LIST.get(4), 5, 0),
            new ItemPair(FILE_NAME_LIST.get(5), 0, 0)
    );

    @BeforeSuite(alwaysRun = true)
    public static void setUp() {
        carts = Utils.generateCart(itemPairs);
        for (Cart cart : carts) {
            parser.writeToFile(cart);
        }
    }

    @Test(groups = {"goodTests"}, testName = "File exist", dataProvider = "fileNameProvider")
    public void filesExist(List<String> collect1) {
        List<String> collect2 = FILE_NAME_LIST.stream().map(s -> s = s + ".json").collect(Collectors.toList());
        Assert.assertTrue(collect1.containsAll(collect2));
    }

    @Test(groups = "brokenTests", testName = "Quantity of files")
    public void filesQuantity() {
        try {
            Assert.assertTrue(Files.list(Paths.get("src/main/resources")).count() >= itemPairs.size());
        } catch (IOException ex) {
            throw new NoSuchFileException("Error to read the file", ex);
        }
    }

    @Parameters({"cart0"})
    @Test(groups = "goodTests", testName = "Saving of Cart with RealItems and VirtualItems")
    public void checkCartItemsSaving(int cart0){
        Cart cart = parser.readFromFile(new File("src/main/resources/" + FILE_NAME_LIST.get(cart0) + ".json"));
        SoftAssert softAssert = new SoftAssert();
        Assert.assertEquals(cart.getCartName(), carts.get(cart0).getCartName(), "Cart name");
        Assert.assertEquals(cart.getTotalPrice(), carts.get(cart0).getTotalPrice(), "Total price");
        softAssert.assertAll();
    }

    @Test(groups = {"goodTests"}, testName = "Throwing NoSuchFileException")
    public void notSuchException(){
        Assert.assertThrows(NoSuchFileException.class,
                () -> new JsonParser().readFromFile(new File("NoSuchFile.txt")));
    }

    @Test(groups = {"goodTests"}, testName = "Parsing wrong file", dataProvider = "fileProvider")
    public void parsingWrongFile(String fileName){
        Assert.assertThrows(NoSuchFileException.class,
                () -> new JsonParser().readFromFile(new File(fileName)));
    }

    @Test(groups = {"goodTests"}, testName = "Parsing broken file", dataProvider = "fileProvider")
    public void parsingBrokenFile(String fileName){
        try {
            Files.write(Paths.get("src/main/resources/" + fileName), "}".getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            throw new NoSuchFileException("Error to write to the file", ex);
        }
        Assert.assertThrows(NoSuchFileException.class,
                () -> new JsonParser().readFromFile(new File(fileName)));
    }

    @AfterSuite(alwaysRun = true)
    public static void cleanUp() throws IOException {
        if(Files.list(Paths.get("src/main/resources/")).findAny().isPresent()){
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

    @DataProvider(name = "fileNameProvider")
    static Object[] fileNameProvider(){
        List<String> collect1 = null;
        try {
            collect1 = Files.list(Paths.get("src/main/resources"))
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new NoSuchFileException("Error to read the file", ex);
        }
        return new Object[]{collect1};
    }

    @DataProvider(name = "fileProvider")
    static Object[] fileProvider() {
        RealItem realItem = new RealItem();
        Utils.parametrizeRealItem(realItem, "Audi", 25698.00, 1900.03);
        String fileName = Constants.FILE_NAME + ".json";
        Utils.writeToFile(realItem);
        return new Object[]{fileName};
    }
}
