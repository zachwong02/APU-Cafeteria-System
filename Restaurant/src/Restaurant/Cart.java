package Restaurant;

import java.util.ArrayList;

public class Cart {
    private String item;
    private int amt;
    private double price;

    public Cart(String item, double price, int amt) {
        this.item = item;
        this.amt = amt;
        this.price = price;
    }


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
