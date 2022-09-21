package Restaurant;

import java.awt.geom.Arc2D;
import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.io.*;

public class DataIO {

    private Formatter x;       //For writing files
    private Scanner y;         //For reading files

    public void closeFormatter() {
        x.close();
    }

    public void closeScanner() {
        y.close();
    }


    public void openFile(String file) {
        try {
            x = new Formatter(file + ".txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openExistingFile(String file) {
        try {
            y = new Scanner(new File(file + ".txt"));
        } catch (FileNotFoundException e) {
            openFile(file);
        }
    }

    public void write(String file) {
        openFile(file);

        switch (file){


//      Write Manager
//----------------------------------------------------------------------------------------------------------------------
            case "manager":
                for (int i = 0; i < Manager.managers.size(); i++) {
                    Manager m = Manager.managers.get(i);
                    x.format("%s %s %s %s %n", m.getName(), m.getPassword(), m.getEmail(), m.getNumber());
                }

                closeFormatter();
                break;


//      Write Customer
//----------------------------------------------------------------------------------------------------------------------
            case "customer":
                for (int i = 0; i < Customer.customers.size(); i++) {
                    Customer c = Customer.customers.get(i);
                    x.format("%s %s %s %s %.2f%n", c.getName(), c.getPassword(), c.getEmail(), c.getNumber(), c.getBalance());
                }
                closeFormatter();
                openFile("cart");
                for (int i = 0; i < Customer.customers.size(); i++) {
                    Customer c = Customer.customers.get(i);

                    if (c.getCart().size() > 0) {
                        for (int j = 0; j < c.getCart().size(); j++) {
                            Cart current = c.getCart().get(j);

                            String item = current.getItem();
                            double price = current.getPrice();
                            int amt =  current.getAmt();
                            x.format("%s %.2f %d %n", item, price, amt);
                        }
                        x.format("%s %n", c.getName());
                    }
                }
                closeFormatter();
                break;



//      Write Orders
//----------------------------------------------------------------------------------------------------------------------
            case "orders":
                for (int i = 0; i < Customer.customers.size(); i++) {
                    Customer c = Customer.customers.get(i);
                    if (c.getOrders().size() > 0){
                        for (int j = 0; j < c.getOrders().size(); j++) {
                            Order currentOrder = c.getOrders().get(j);

                            String orderID = currentOrder.getOrderID();
                            String name = c.getName();
                            double total = currentOrder.getTotal();
                            LocalDateTime dateordered = currentOrder.getDateOrdered();
                            String orderStatus = currentOrder.getOrderStatus();
                            String pendingOrder = currentOrder.getPendingOrder();

                            x.format("%s %n", orderID);
                            x.format("%.2f %n", total);
                            x.format("%s %n", dateordered);
                            x.format("%s %n", orderStatus);
                            x.format("%s %n", pendingOrder);

                            for (int k = 0; k < currentOrder.getOrderList().size(); k++) {
                                Cart currentItem = currentOrder.getOrderList().get(k);
                                String currentItemName = currentItem.getItem();
                                int currentItemAmt = currentItem.getAmt();
                                double currentItemPrice = currentItem.getPrice();

                                x.format("%s %.2f %d %n", currentItemName, currentItemPrice, currentItemAmt);
                            }

                            x.format("%s%n", name);

                        }
                    }

                }
                closeFormatter();
                break;


//      Write Food
//----------------------------------------------------------------------------------------------------------------------
            case "food":

                for (int i = 0; i < Food.food.size(); i++) {
                    Food f = Food.food.get(i);
                    x.format("%s %.2f%n",f.getName(),f.getPrice());
                }
                closeFormatter();
                break;


//      Write Drinks
//----------------------------------------------------------------------------------------------------------------------
            case "drink":
                for (int i = 0; i < Drink.drink.size(); i++) {
                    Drink d = Drink.drink.get(i);
                    x.format("%s %.2f%n",d.getName(),d.getPrice());
                }
                closeFormatter();
                break;

            case "feedback":
                for (int i = 0; i < Feedback.feedbacks.size(); i++) {
                    x.format("%s\n",Feedback.feedbacks.get(i).getOrderID());
                    x.format("%s\n",Feedback.feedbacks.get(i).getFeedback());
                }
                closeFormatter();
                break;
        }
//----------------------------------------------------------------------------------------------------------------------
    }




    public void read(String file) {
        openExistingFile(file);
        switch (file) {
//      Read Customer Data
//----------------------------------------------------------------------------------------------------------------------
            case "customer":
                String cName = "";
                String cEmail = "";
                String cNumber = "";
                String cTmp = "";
                int cPassword = 0;
                double balance = 0;

                ArrayList<Customer> customers = new ArrayList<Customer>();

                while (y.hasNext()) {
                    cTmp = y.next();
                    try{
                        cPassword = Integer.parseInt(cTmp);

                        cEmail = y.next();
                        cNumber = y.next();
                        balance = Double.parseDouble(y.next());
                        customers.add(new Customer(cName.trim(), cPassword, cEmail, cNumber, balance, new ArrayList<Cart>(), new ArrayList<>()));

                        cName = "";

                    }catch (NumberFormatException e){
                        cName += cTmp + " ";
                    }

                }

                Customer.setCustomers(customers);

                closeScanner();
                break;

//      Read Manager Data
//----------------------------------------------------------------------------------------------------------------------
            case "manager":
                String mName = "";
                String mEmail = "";
                String mNumber = "";
                String mTmp = "";
                int mPassword;
                ArrayList<Manager> managers = new ArrayList<Manager>();

                while(y.hasNext()) {

                    mTmp = y.next();
                    try{
                        mPassword = Integer.parseInt(mTmp);

                        mEmail = y.next();
                        mNumber = y.next();

                        managers.add(new Manager(mName.trim(),mPassword,mEmail,mNumber));

                    }catch (NumberFormatException e){
                        mName += mTmp + " ";
                    }

                }
                Manager.setManagers(managers);

                closeScanner();
                break;


//      Read Customer Cart Data
//----------------------------------------------------------------------------------------------------------------------
            case "cart":
                ArrayList<Cart> carts = new ArrayList<>();
                ArrayList names = new ArrayList<>();
                String cartName;
                Scanner itemScanner;


                for (int i = 0; i < Customer.customers.size(); i++) {
                    names.add(Customer.customers.get(i).getName());
                }

                while(y.hasNextLine()){
                    cartName = y.nextLine();

                    if (names.contains(cartName.trim())){
                        for (int i = 0; i < Customer.customers.size(); i++) {

                            Customer current = Customer.customers.get(i);

                            if (current.getName().equals(cartName.trim())) {
                                current.setCart(carts);
                                carts = new ArrayList<>();
                            }
                        }
                    }

                    else {
                        itemScanner = new Scanner(cartName);
                        String cartItem = "";
                        String cartTmp = "";
                        double cartPrice = 0;
                        int cartAmt = 0;

                        while (itemScanner.hasNext()){
                            cartTmp = itemScanner.next();
                            try{
                                cartPrice = Double.parseDouble(cartTmp);
                                cartAmt = Integer.parseInt(itemScanner.next());
                                carts.add(new Cart(cartItem.trim(),cartPrice,cartAmt));
                                cartItem = "";
                            }catch (NumberFormatException e){
                                cartItem += cartTmp + " ";
                            }
                        }
                    }

                }

                closeScanner();
                break;

//      Read Food Data
//----------------------------------------------------------------------------------------------------------------------
            case "food":
                String foodName = "";
                double foodPrice = 0;
                String tmpFood = "";

                while(y.hasNext()){
                    tmpFood = y.next();
                    try{
                        foodPrice = Double.parseDouble(tmpFood);
                        Food.food.add(new Food(foodName.trim(),foodPrice));
                        foodName = "";
                    }catch (NumberFormatException e){
                        foodName += tmpFood + " ";
                    }

                }
                break;




//      Read Drink Data
//----------------------------------------------------------------------------------------------------------------------
            case "drink":
                String drinkName = "";
                double drinkPrice = 0;
                String tmpDrink = "";

                while(y.hasNext()){
                    tmpDrink = y.next();
                    try{
                        drinkPrice = Double.parseDouble(tmpDrink);
                        Drink.drink.add(new Drink(drinkName.trim(),drinkPrice));
                        drinkName = "";
                    }catch (NumberFormatException e){
                        drinkName += tmpDrink + " ";
                    }

                }
                break;

//      Read Orders
//----------------------------------------------------------------------------------------------------------------------

            case "orders":
                ArrayList<Cart> orderList = new ArrayList<>();
                ArrayList customerNames = new ArrayList<>();
                String checkName;
                Scanner orderItemScanner;


                for (int i = 0; i < Customer.customers.size(); i++) {
                    customerNames.add(Customer.customers.get(i).getName());
                }

                while(y.hasNextLine()){
                    String orderID = y.nextLine().trim();
                    double total = Double.parseDouble(y.nextLine().trim());
                    LocalDateTime dateOrdered = LocalDateTime.parse(y.nextLine().trim());
                    String orderStatus = y.nextLine().trim();
                    String pendingOrder = y.nextLine().trim();

                    checkName = y.nextLine();


                    while(customerNames.contains(checkName.trim()) == false){
                        orderItemScanner = new Scanner(checkName);
                        String cartItem = "";
                        String cartTmp = "";
                        double cartPrice = 0;
                        int cartAmt = 0;

                        while (orderItemScanner.hasNext()){
                            cartTmp = orderItemScanner.next();
                            try{
                                cartPrice = Double.parseDouble(cartTmp);
                                cartAmt = Integer.parseInt(orderItemScanner.next());
                                orderList.add(new Cart(cartItem.trim(),cartPrice,cartAmt));
                                checkName = y.nextLine();
                                cartItem = "";
                                break;

                            }catch (NumberFormatException e){
                                cartItem += cartTmp + " ";
                            }
                        }
                    }

                    if (customerNames.contains(checkName.trim())){
                        for (int i = 0; i < Customer.customers.size(); i++) {

                            Customer current = Customer.customers.get(i);

                            if (current.getName().equals(checkName.trim())) {

                                current.getOrders().add(new Order(orderID.trim(),orderStatus.trim(),pendingOrder.trim(),checkName.trim(),orderList,total,dateOrdered));
                                Order.orders.add(new Order(orderID.trim(),orderStatus.trim(),pendingOrder.trim(),checkName.trim(),orderList,total,dateOrdered));
                                orderList = new ArrayList<>();
                            }
                        }
                    }

                }
            case "feedback":
                while(y.hasNextLine()){
                    String orderNo = y.nextLine().trim();
                    String feedback = y.nextLine();

                    Feedback.feedbacks.add(new Feedback(orderNo,feedback));
            }
                break;
        }
//----------------------------------------------------------------------------------------------------------------------
    }

}









