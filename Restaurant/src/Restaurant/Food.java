package Restaurant;

import java.util.ArrayList;

public class Food extends Item{

    public static ArrayList<Food> food = new ArrayList<>();

    public Food(String name, double price) {
        super(name, price);
    }
}
