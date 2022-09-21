package Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomerOrderUI extends UI{
    private JLabel currentOrderLabel, previousOrderLabel;
    private LocalDateTime dateTime;
    private JSeparator sep1, sep2;
    private JPanel mainPanel,currentOrderPanel,previousOrderPanel,currentOrderHeader,previousOrderHeader;
    private JPanel currentDataPanel,previousDataPanel;
    private DefaultTableModel currentTableModel, previousTableModel;
    private JTable currentData, previousData;
    private String[] col = {"Order ID", "Order Status", "Order Date"};
    private JScrollPane currentScroll,previousScroll;
    private JFrame frame;
    private Customer c;

    public CustomerOrderUI(Customer c) {

        this.c = c;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
//=================================================================================================================================
        currentOrderLabel = new JLabel("Current Orders");
        currentOrderLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        currentOrderPanel = new JPanel();
        currentOrderHeader = new JPanel();
        currentOrderHeader.setLayout(new BoxLayout(currentOrderHeader,BoxLayout.Y_AXIS));
        sep1 = new JSeparator();

        currentOrderPanel.setLayout(new BorderLayout());
        currentOrderHeader.add(currentOrderLabel);
        currentOrderHeader.add(sep1);

        currentOrderPanel.add(currentOrderHeader,BorderLayout.NORTH);

        currentDataPanel = new JPanel();
        currentDataPanel.setLayout(new BoxLayout(currentDataPanel,BoxLayout.X_AXIS));

        currentTableModel = new DefaultTableModel(col, 0);
        currentData = new JTable(currentTableModel);



        currentScroll = new JScrollPane();



//=================================================================================================================================

        previousOrderLabel = new JLabel("Previous Orders");
        previousOrderLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        previousOrderPanel = new JPanel();
        previousOrderHeader = new JPanel();
        previousOrderHeader.setLayout(new BoxLayout(previousOrderHeader,BoxLayout.Y_AXIS));
        sep2 = new JSeparator();

        previousOrderPanel.setLayout(new BorderLayout());
        previousOrderHeader.add(previousOrderLabel);
        previousOrderHeader.add(sep2);

        previousOrderPanel.add(previousOrderHeader,BorderLayout.NORTH);

        previousDataPanel = new JPanel();
        previousDataPanel.setLayout(new BoxLayout(previousDataPanel,BoxLayout.X_AXIS));

        previousTableModel = new DefaultTableModel(col, 0);
        previousData = new JTable(previousTableModel);

        previousScroll = new JScrollPane();
//=================================================================================================================================
        currentData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int row = currentData.getSelectedRow();
                    Object itemName = currentData.getModel().getValueAt(row, 0);
                    OrderReceipt receipt = new OrderReceipt(itemName.toString(),true);
                }
            }
        });

        previousData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int row = previousData.getSelectedRow();
                    Object itemName = previousData.getModel().getValueAt(row, 0);
                    OrderReceipt receipt = new OrderReceipt(itemName.toString(),true);
                }
            }
        });



        draw();
    }

    public void draw(){

        currentData.clearSelection();
        previousData.clearSelection();
        currentTableModel.getDataVector().removeAllElements();
        previousTableModel.getDataVector().removeAllElements();

        for (int i = 0; i < c.getOrders().size(); i++) {

            Order current = c.getOrders().get(i);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDate = current.getDateOrdered().format(formatter);

            Object[] data = {current.getOrderID(),current.getOrderStatus(),formattedDate};

            if (current.getPendingOrder().equals("Rejected") && current.getOrderStatus().equals("Not Ready")){
                previousTableModel.addRow(data);
            }else if(current.getPendingOrder().equals("Accepted") && current.getOrderStatus().equals("Not Ready")) {
                currentTableModel.addRow(data);
            }else if (current.getPendingOrder().equals("Accepted") && current.getOrderStatus().equals("Ready")) {
                previousTableModel.addRow(data);
            } else if (current.getPendingOrder().equals("Pending") && current.getOrderStatus().equals("Not Ready")) {
                currentTableModel.addRow(data);
            }else if (current.getPendingOrder().equals("Cancelled") && current.getOrderStatus().equals("Not Ready")) {
                previousTableModel.addRow(data);
            }
        }


        currentData.setDefaultEditor(Object.class,null);
        currentScroll.setViewportView(currentData);

        currentDataPanel.add(currentScroll);
        currentOrderPanel.add(currentDataPanel,BorderLayout.CENTER);

        previousData.setDefaultEditor(Object.class,null);
        previousScroll.setViewportView(previousData);

        previousDataPanel.add(previousScroll);
        previousOrderPanel.add(previousDataPanel,BorderLayout.CENTER);

        mainPanel.add(currentOrderPanel);
        mainPanel.add(previousOrderPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
