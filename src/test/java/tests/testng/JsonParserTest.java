package tests.testNg;

import dto.ItemPair;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.*;
import parser.JsonParser;
import parser.NoSuchFileException;
import shop.Cart;
import shop.RealItem;
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

    @Parameters({"items-size"})
    @Test(groups = {"goodTests"}, testName = "File exist")
    public void filesExist(String items_size) {
        for (int i = 0; i < Integer.valueOf(items_size); i++) {
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

    @Test(groups = "brokenTests", testName = "Quantity of files")
    public void filesQuantity() {
        try {
            Assertions.assertTrue(Files.list(Paths.get("src/main/resources")).count() >= itemPairs.size());
        } catch (IOException ex) {
            throw new NoSuchFileException("Error to read the file", ex);
        }
    }

    @Parameters({"cart0"})
    @Test(groups = "goodTests", testName = "Saving of Cart with RealItems and VirtualItems")
    public void checkCartItemsSaving(String cart0){
        Cart cart = parser.readFromFile(new File("src/main/resources/" + FILE_NAME_LIST.get(Integer.valueOf(cart0)) + ".json"));
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
            throw new NoSuchFileException("Error to read the file", ex);
        }
    }

    @Test(groups = {"goodTests"}, testName = "Throwing NoSuchFileException")
    public void notSuchException(){
        Assertions.assertThrows(NoSuchFileException.class,
                () -> new JsonParser().readFromFile(new File("NoSuchFile.txt")));
    }

    @AfterSuite(alwaysRun = true)
    public static void cleanUp() throws IOException {
        for (int i = 0; i < carts.size(); i++) {
            Path fileToDeletePath = Paths.get("src/main/resources/" + FILE_NAME_LIST.get(i) + ".json");
            Files.delete(fileToDeletePath);
        }
    }
}
