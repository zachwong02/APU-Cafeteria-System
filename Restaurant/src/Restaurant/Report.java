package Restaurant;

import javax.swing.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Report extends UI{
    private ArrayList<Order> realPurchases = new ArrayList<>();
    private double revenue;
    private double tax;
    private ArrayList<String> item = new ArrayList<>();
    private Map<String, Long> counterMap;
    private JFrame frame;
    private String data = "";
    private JTextArea textArea;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private HashMap<String, Integer> hashMap = new HashMap<String, Integer>();



    public Report() {
        filterOrders();
        getRevenue();
        getTax();
        genFamous();


        frame = new JFrame();
        data += "Report Generated for " + LocalDateTime.now().format(formatter) + "\n";
        data += "*******************************************************************************\n";
        data += "\n";
        data += "Revenue for the day:\t\t\t" + revenue + "\n";
        data += "\n";
        data +=  "Tax:\t\t\t\t" + df.format(tax) + "\n";
        data += "*******************************************************************************\n";
        data += "\n";
        data += "Number of Customers\n";
        data += "*******************************************************************************\n";
        data += "\n";
        data += realPurchases.size() + "\n";
        data += "\n";
        data += "*******************************************************************************\n";
        data += "\n";
        data += "\n";
        data += "Number of items sold\n";
        data += "*******************************************************************************\n";
        data += "\n";
        hashMap.forEach((key, value) -> {
            data += key + "\t" + value + "\n";
        });
        data += "\n";
        data += "*******************************************************************************\n";


        textArea = new JTextArea();
        textArea.setText(data);
        textArea.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        textArea.setEditable(false);
        frame.setTitle("Report Generated for " + LocalDateTime.now().format(formatter));
        frame.add(textArea);
        frame.pack();
        centerWindow(frame);
        frame.setVisible(true);

    }



    private void filterOrders(){
        for (int i = 0; i < Order.orders.size(); i++) {
            Order currentOrder = Order.orders.get(i);

            if (currentOrder.getOrderStatus().equals("Ready")){
                realPurchases.add(currentOrder);
            }
        }
    }


    private void getRevenue(){
        for (int i = 0; i < realPurchases.size(); i++) {
            revenue += realPurchases.get(i).getTotal();
        }
    }

    private void getTax(){
        tax = revenue * CartUI.tax;
    }



    private void genFamous(){
        for (int i = 0; i < realPurchases.size(); i++) {
            Order currentOrder = realPurchases.get(i);

            for (int j = 0; j < currentOrder.getOrderList().size(); j++) {
                Cart currentCart = currentOrder.getOrderList().get(j);
                hashMap.put(currentCart.getItem(),currentCart.getAmt());
            }

        }

    }
}
