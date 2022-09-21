package Restaurant;

import java.util.ArrayList;

public class Main {

    public static void main(String arr[]) {


        DataIO data = new DataIO();
        Login x = new Login();
        data.read("customer");
        data.read("manager");
        data.read("cart");
        data.read("food");
        data.read("drink");
        data.read("orders");
        data.read("feedback");

    }

}
