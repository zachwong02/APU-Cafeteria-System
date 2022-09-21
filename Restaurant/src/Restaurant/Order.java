package Restaurant;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private String name;
    private String orderID;
    private ArrayList<Cart> orderList = new ArrayList<>();
    private double total;
    private String orderStatus;
    private String pendingOrder;
    private LocalDateTime dateOrdered;
    public static ArrayList<Order> orders = new ArrayList<Order>();


    public Order(String orderID, String orderStatus, String pendingOrder, String name, ArrayList<Cart> orderList, double total, LocalDateTime dateOrdered) {
        this.name = name;
        this.orderID = orderID;
        this.orderList = orderList;
        this.total = total;
        this.orderStatus = orderStatus;
        this.pendingOrder = pendingOrder;
        this.dateOrdered = dateOrdered;
    }

    public Order(String name, ArrayList<Cart> orderList, double total, LocalDateTime dateOrdered) {
        this.orderID = "ORD-" + Order.orders.size();
        this.total = total;
        this.dateOrdered = dateOrdered;
        this.orderStatus = "Not Ready";
        this.pendingOrder = "Pending";
        this.orderList = orderList;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderID() {
        return orderID;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPendingOrder() {
        return pendingOrder;
    }

    public void setPendingOrder(String pendingOrder) {
        this.pendingOrder = pendingOrder;
    }

    public LocalDateTime getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(LocalDateTime dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public ArrayList<Cart> getOrderList() {return orderList;}

    public void setOrderList(ArrayList<Cart> orderList) {this.orderList = orderList;}
}
