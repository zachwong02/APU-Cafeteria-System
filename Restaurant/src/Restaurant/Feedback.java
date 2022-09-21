package Restaurant;

import java.util.ArrayList;

public class Feedback {

    private String orderID;
    private String feedback;
    public static ArrayList<Feedback> feedbacks = new ArrayList<>();

    public Feedback(String orderID, String feedback) {
        this.orderID = orderID;
        this.feedback = feedback;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getFeedback() {
        return feedback;
    }
}
