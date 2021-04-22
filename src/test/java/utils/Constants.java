package utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Constants {
    public static final List<String> FILE_NAME_LIST = Stream.of(
            "marina0-cart", // 1 real, 1 virtual
            "marina1-cart", // 2 real
            "marina2-cart", //3 real
            "marina3-cart", // 4 virtual
            "marina4-cart", // 5 virtual
            "marina5-cart") // 0 real, 0 virtual
            .collect(Collectors.toList());

    public static final List<String> REAL_ITEM_NAME = Stream.of(
            "Audi",
            "Mercedes",
            "Maserati",
            "Lamborghini",
            "Porsche",
            "Jaguar")
            .collect(Collectors.toList());

    public static final List<String> VIRTUAL_ITEM_NAME = Stream.of(
            "Windows",
            "Adobe Photoshop",
            "ZOOM meetings",
            "WinRAR",
            "Home Design",
            "Affinity Photo")
            .collect(Collectors.toList());

    public static final String FILE_NAME = "marina";
}
