package Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CartUI extends UI implements ActionListener {


    private JScrollPane scrollPanel;
    private JTable cartPanel;
    private Customer c;
    private ArrayList<Cart> customerCart;
    private DefaultTableModel tableModel;
    private JPanel mainPanel, buttonsContainer, subTotalContainer, taxContainer, totalContainer;
    private Button refresh,payNow;
    private JLabel totalAmtLabel, subtotalAmtLabel, taxLabel;
    private JSeparator sep1, sep2;
    public final static double tax = 0.1;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private DataIO data = new DataIO();

    public JPanel getCartPanel () {
        return mainPanel;
    }

    public CartUI(Customer c) {

        this.c = c;
        this.customerCart = c.getCart();


        String col[] = {"Item","Amount","Total Price"};
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));


        scrollPanel = new JScrollPane();
        scrollPanel.setPreferredSize(new Dimension(500,200));


        tableModel = new DefaultTableModel(col, 0);
        cartPanel = new JTable(tableModel);

        sep1 = new JSeparator();
        sep2 = new JSeparator();

        subTotalContainer = new JPanel();
        taxContainer = new JPanel();
        totalContainer = new JPanel();

        subTotalContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        taxContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));
        totalContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));

        subtotalAmtLabel = new JLabel();
        taxLabel = new JLabel();
        totalAmtLabel = new JLabel();


        buttonsContainer = new JPanel();
        buttonsContainer.setLayout(new FlowLayout(FlowLayout.RIGHT));

        refresh = new Button("Refresh Cart");
        refresh.addActionListener(this);

        payNow = new Button("Pay Now");
        payNow.addActionListener(this);


        buttonsContainer.add(refresh);
        buttonsContainer.add(payNow);

        cartPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int row = cartPanel.getSelectedRow();
                    Object itemName = cartPanel.getModel().getValueAt(row, 0);
                    ItemUI update = new ItemUI(c,itemName.toString());
                }
            }
        });

        draw();
    }


    public void draw(){

        subtotalAmtLabel.setText("");
        taxLabel.setText("");
        totalAmtLabel.setText("");

        tableModel.getDataVector().removeAllElements();
        cartPanel.clearSelection();

        for (int i = 0; i < customerCart.size(); i++) {

            Cart current = customerCart.get(i);

            Object[] data = {current.getItem(),current.getAmt(),current.getPrice()* current.getAmt()};

            tableModel.addRow(data);
        }


        cartPanel.setDefaultEditor(Object.class,null);
        scrollPanel.setViewportView(cartPanel);


        mainPanel.add(scrollPanel);

        mainPanel.add(sep1);


        subtotalAmtLabel.setText("Subtotal: RM" + df.format(calculateSubTotal()));
        taxLabel.setText("Sales Tax: RM" + df.format(calculateTax()));
        totalAmtLabel.setText("Total: RM" + df.format(calculateTotal()));

        subTotalContainer.add(subtotalAmtLabel);
        taxContainer.add(taxLabel);
        totalContainer.add(totalAmtLabel);


        mainPanel.add(subTotalContainer);
        mainPanel.add(taxContainer);
        mainPanel.add(totalContainer);

        mainPanel.add(sep2);
        
        mainPanel.add(buttonsContainer);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(100,50,20,50));
    }

    private double calculateSubTotal(){

        double subtotalAmt = 0;

        for (int i = 0; i < customerCart.size(); i++) {
            subtotalAmt += customerCart.get(i).getPrice() * customerCart.get(i).getAmt();
        }

        return subtotalAmt;
    }

    private double calculateTax(){
        return calculateSubTotal() * tax;
    }

    private  double calculateTotal(){
        return calculateSubTotal() + calculateTax();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refresh){
            draw();
        }
        else if(e.getSource() == payNow){

            if (customerCart.size() > 0){

                double total = calculateTotal();

                if (c.getBalance() < total){
                    message(Login.cui.getCui(),"The total is RM" + total + "\nYou do not have enough to pay");
                }

                else{
                    c.setBalance(c.getBalance() - total);

                    c.getOrders().add(new Order(c.getName(),new ArrayList<>(customerCart),total, LocalDateTime.now()));
                    Order.orders.add(new Order(c.getName(),new ArrayList<>(customerCart),total,LocalDateTime.now()));

                    data.write("orders");
                    customerCart.removeAll(customerCart);
                    data.write("customer");

                    Login.cui.getBalanceAmt().setText("RM " +  df.format(c.getBalance()));
                    draw();
                    message(Login.cui.getCui(),"Your order has been placed!");
                }
            }else{
                message(Login.cui.getCui(),"You don't have anything in your cart...");
            }

        }

    }
}

