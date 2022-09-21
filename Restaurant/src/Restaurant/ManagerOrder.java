package Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ManagerOrder extends UI implements ActionListener {
    private JFrame frame;
    private Button acceptButton, declineButton, readyButton, pendingButton, ongoingButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private DataIO data = new DataIO();
    private String view = "Pending";
    private JScrollPane scrollPanel;
    private JPanel itemContainer,btnContainer, mainPanel;

    public ManagerOrder() {
        String[] col = {"Order ID", "Order Pending"};

        frame = new JFrame();
        tableModel = new DefaultTableModel(col,0);
        table = new JTable(tableModel);
        scrollPanel = new JScrollPane();

        itemContainer = new JPanel();
        itemContainer.setLayout(new FlowLayout());
        btnContainer = new JPanel();
        btnContainer.setLayout(new FlowLayout());
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        acceptButton = new Button("Accept");
        declineButton = new Button("Decline");
        readyButton = new Button("Ready");
        pendingButton = new Button("Pending");
        ongoingButton = new Button("On Going");

        acceptButton.addActionListener(this);
        declineButton.addActionListener(this);
        readyButton.addActionListener(this);
        pendingButton.addActionListener(this);
        ongoingButton.addActionListener(this);
        draw(this.view);


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int row = table.getSelectedRow();
                    Object id = table.getModel().getValueAt(row, 0);

                    JPanel panel = new JPanel();
                    JTextArea textArea = new JTextArea();
                    String text = "\n";

                    for (int i = 0; i < Order.orders.size(); i++) {
                        Order current = Order.orders.get(i);
                        if (current.getOrderID().equals(id)){
                            for (int j = 0; j < current.getOrderList().size(); j++) {
                                text += " " + current.getOrderList().get(j).getItem() + "\t\t" + current.getOrderList().get(j).getAmt() + " \n";
                            }
                        }


                    }

                    textArea.setText(text);
                    textArea.setEditable(false);

                    panel.add(textArea);

                    JOptionPane.showMessageDialog(frame,panel,id.toString(),JOptionPane.INFORMATION_MESSAGE,null);

                }
            }
        });

    }

    private void draw(String view){
        itemContainer.add(pendingButton);
        itemContainer.add(ongoingButton);

        if (this.view.equals("Pending")){
            btnContainer.add(acceptButton);
            btnContainer.add(declineButton);
        }

        else if (this.view.equals("On Going")) {
            btnContainer.add(readyButton);
        }


        table.clearSelection();
        tableModel.getDataVector().removeAllElements();

        for (int i = 0; i < Order.orders.size(); i++) {

            Order current = Order.orders.get(i);

            if (this.view.equals("Pending")) {
                if (current.getPendingOrder().equals("Pending") && current.getOrderStatus().equals("Not Ready")) {
                    Object[] data = {current.getOrderID(), current.getPendingOrder()};
                    tableModel.addRow(data);
                }
            } else if (this.view.equals("On Going")) {
                if (current.getPendingOrder().equals("Accepted") && current.getOrderStatus().equals("Not Ready")) {
                    Object[] data = {current.getOrderID(), current.getOrderStatus()};
                    tableModel.addRow(data);
                }


            }
        }

            table.setDefaultEditor(Object.class,null);
            scrollPanel.setViewportView(table);



            mainPanel.add(itemContainer);
            mainPanel.add(scrollPanel);
            mainPanel.add(btnContainer);
            mainPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

            frame.add(mainPanel);
            frame.pack();
            centerWindow(frame);
            frame.setResizable(false);
            frame.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == acceptButton){
            if (table.getSelectionModel().isSelectionEmpty() == false){
                int row = table.getSelectedRow();
                Object orderID = table.getModel().getValueAt(row, 0);

                for (int i = 0; i < Order.orders.size(); i++) {
                    if (Order.orders.get(i).getOrderID().equals(orderID.toString())){
                        Order.orders.get(i).setPendingOrder("Accepted");
                        Customer c = Customer.getCustomer(Order.orders.get(i).getName());
                        for (int j = 0; j < c.getOrders().size(); j++) {
                            if (c.getOrders().get(j).getOrderID().equals(orderID.toString())){
                                c.getOrders().get(j).setPendingOrder("Accepted");
                                break;
                            }
                        }
                        break;
                    }
                }
                data.write("customer");
                data.write("orders");
                draw(view);
            }

        }else if(e.getSource() == declineButton){
            if (table.getSelectionModel().isSelectionEmpty() == false){
                int row = table.getSelectedRow();
                Object orderID = table.getModel().getValueAt(row, 0);

                for (int i = 0; i < Order.orders.size(); i++) {
                    if (Order.orders.get(i).getOrderID().equals(orderID.toString())){
                        Order.orders.get(i).setPendingOrder("Rejected");
                        Customer c = Customer.getCustomer(Order.orders.get(i).getName());
                        for (int j = 0; j < c.getOrders().size(); j++) {
                            if (c.getOrders().get(j).getOrderID().equals(orderID.toString())){
                                c.getOrders().get(j).setPendingOrder("Rejected");
                                break;
                            }
                        }
                        break;
                    }
                }
                data.write("customer");
                data.write("orders");
                draw(view);
            }

        }else if(e.getSource() == readyButton){
            if (table.getSelectionModel().isSelectionEmpty() == false) {
                int row = table.getSelectedRow();
                Object orderID = table.getModel().getValueAt(row, 0);

                for (int i = 0; i < Order.orders.size(); i++) {
                    if (Order.orders.get(i).getOrderID().equals(orderID.toString())) {
                        Order.orders.get(i).setOrderStatus("Ready");
                        Customer c = Customer.getCustomer(Order.orders.get(i).getName());
                        for (int j = 0; j < c.getOrders().size(); j++) {
                            if (c.getOrders().get(j).getOrderID().equals(orderID.toString())) {
                                c.getOrders().get(j).setOrderStatus("Ready");
                                break;
                            }
                        }
                        break;
                    }
                }
                data.write("customer");
                data.write("orders");
                draw(view);

            }

        }else if(e.getSource() == pendingButton){
            this.view = "Pending";
            btnContainer.remove(readyButton);
            draw(this.view);
        }else if(e.getSource() == ongoingButton){
            this.view = "On Going";
            btnContainer.remove(acceptButton);
            btnContainer.remove(declineButton);
            draw(this.view);
        }
    }
}
