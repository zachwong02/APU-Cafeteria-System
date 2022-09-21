package Restaurant;

import java.util.ArrayList;

public class Customer extends User{
    private ArrayList<Cart> cart;
    private ArrayList<Order> orders;
    private double balance;
    public static ArrayList<Customer> customers = new ArrayList<Customer>();

    public Customer(String name, int password, String email, String number, double balance, ArrayList<Cart> cart, ArrayList<Order> orders) {
        super(name, password, email, number);
        this.cart = cart;
        this.orders = orders;
        this.balance = balance;
    }

    public static void setCustomers(ArrayList<Customer> customers) {
        Customer.customers = customers;
    }

    public ArrayList<Cart> getCart() {return cart;}

    public void setCart(ArrayList<Cart> cart) {
        this.cart = cart;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {this.balance = balance;}

    public void increaseBalance(double balance) {
        this.balance = this.balance + balance;
    }

    public ArrayList<Order> getOrders() {return orders;}

    public void setOrders(ArrayList<Order> orders) {this.orders = orders;}

    public static Customer getCustomer(String name){

        for (int i = 0; i < customers.size(); i++) {
            if(customers.get(i).getName().matches(name)){
                return customers.get(i);
            }
        }
        return null;
    }


}
