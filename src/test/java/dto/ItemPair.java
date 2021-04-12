package dto;

public class ItemPair {
    String name;
    int countVirtualItems;
    int countRealItems;

    public ItemPair(String name, int countVirtualItems, int countRealItems){
        this.name = name;
        this.countVirtualItems = countVirtualItems;
        this.countRealItems = countRealItems;
    }

    public int getCountVirtualItems() {
        return countVirtualItems;
    }

    public int getCountRealItems() {
        return countRealItems;
    }

    public String getName() {
        return name;
    }
}
