package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class OrderReceipt extends UI implements ActionListener {
    private JFrame frame;
    private JTextArea receipt;
    private Button giveFeedback,cancel;
    private JPanel btnContainer,mainPanel;
    private JLabel rejected, cancelled;
    private Order order;
    private double subTotal = 0;
    private double tax;
    private double total = 0;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private DataIO data = new DataIO();

    public OrderReceipt(String orderID, boolean btns) {
        for (int i = 0; i < Order.orders.size(); i++) {
            Order currentOrder = Order.orders.get(i);
            if (currentOrder.getOrderID().equals(orderID)){
                this.order = currentOrder;
            }
        }

        frame = new JFrame();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        receipt = new JTextArea();

        String data = "";

        calculateTotal();
        calculateSubTotal();
        calculateTax();

        data += "Order " + this.order.getOrderID() + "\n";
        data += "\n";
        data += "Item\t\tAmount\tPrice(RM)\n";
        data += "******************************************************************************\n";

        for (int i = 0; i < this.order.getOrderList().size(); i++) {

            Cart currentItem = this.order.getOrderList().get(i);
            data += currentItem.getItem() + "\t\t" + currentItem.getAmt() + "\t" + (currentItem.getPrice() * currentItem.getAmt()) + "\n";

        }


        data += "******************************************************************************\n";
        data += "\t\t\tSubtotal:\t RM" + df.format(subTotal) +"\n";
        data += "\t\t\tTax:\t RM" + df.format(tax) +"\n";
        data += "\t\t\tTotal:\t RM" + df.format(total) +"\n";



        receipt.setText(data);
        receipt.setEditable(false);

        btnContainer = new JPanel();

        giveFeedback = new Button("Give Feedback");
        cancel = new Button("Cancel Order");

        giveFeedback.addActionListener(this);
        cancel.addActionListener(this);

        rejected = new JLabel("Order Rejected");
        cancelled = new JLabel("Order Cancelled");

        if (order.getPendingOrder().equals("Rejected") && order.getOrderStatus().equals("Not Ready")){btnContainer.add(rejected);}
        else if(order.getPendingOrder().equals("Accepted") && order.getOrderStatus().equals("Not Ready")) {btnContainer.add(cancel);}
        else if (order.getPendingOrder().equals("Accepted") && order.getOrderStatus().equals("Ready")) {btnContainer.add(giveFeedback);}
        else if (order.getPendingOrder().equals("Pending") && order.getOrderStatus().equals("Not Ready")) {btnContainer.add(cancel);}
        else if (order.getPendingOrder().equals("Cancelled") && order.getOrderStatus().equals("Not Ready")) {btnContainer.add(cancelled);}

        mainPanel.add(receipt);
        mainPanel.add(btnContainer);

        if (btns == false){mainPanel.remove(btnContainer);}

        mainPanel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        frame.add(mainPanel);
        frame.pack();
        frame.setResizable(false);
        centerWindow(frame);
        frame.setVisible(true);

    }

    private void calculateSubTotal(){
        for (int i = 0; i < this.order.getOrderList().size(); i++) {
            this.subTotal += this.order.getOrderList().get(i).getAmt() * order.getOrderList().get(i).getPrice();
        }
    }
    private void calculateTax(){
        this.tax = this.total - this.subTotal;
    }

    private void calculateTotal(){
        this.total = order.getTotal();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == cancel){
            int d = JOptionPane.showConfirmDialog(frame,"Are you sure you want to cancel this order?",null,JOptionPane.OK_CANCEL_OPTION);
            if (d == JOptionPane.OK_OPTION){
                Customer c = Customer.getCustomer(order.getName());
                c.increaseBalance(total);
                for (int i = 0; i < c.getOrders().size(); i++) {
                    if (c.getOrders().get(i).getOrderID().equals(order.getOrderID())){
                        c.getOrders().get(i).setPendingOrder("Cancelled");
                        break;
                    }
                }
                data.write("customer");
                data.write("orders");
                order.setPendingOrder("Cancelled");
                Login.cui.getOrdersPanel().draw();
                Login.cui.getBalanceAmt().setText("RM "+ df.format(c.getBalance()));
                frame.dispose();
            }

        }else if (e.getSource() == giveFeedback){
            try{
                String message = JOptionPane.showInputDialog("Please provide us your feedback for this orderr️");
                if (message.isBlank() == false){
                    Feedback.feedbacks.add(new Feedback(this.order.getOrderID(),message));
                    data.write("feedback");
                }
            }catch (NullPointerException ex){}

        }

    }
}
