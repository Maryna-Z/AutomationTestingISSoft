package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemPair {
    String name;
    int countVirtualItems;
    int countRealItems;
}
