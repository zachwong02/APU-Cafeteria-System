package Restaurant;

import java.util.ArrayList;

public class Drink extends Item{

    public static ArrayList<Drink> drink = new ArrayList<>();

    public Drink(String name, double price) {
        super(name, price);
    }
}
