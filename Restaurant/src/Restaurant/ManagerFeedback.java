package Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManagerFeedback extends UI {
    private DefaultTableModel tableModel;
    private JTable table;
    private String[] col = {"Order ID", "Feedback"};
    private JScrollPane scrollPane;
    private JFrame frame;
    private JPanel mainPanel;

    public ManagerFeedback() {

        frame = new JFrame();

        tableModel = new DefaultTableModel(col, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int row = table.getSelectedRow();
                    Object itemName = table.getModel().getValueAt(row, 0);
                    OrderReceipt receipt = new OrderReceipt(itemName.toString(),false);
                }
            }
        });

        draw();
    }

    public void draw(){

        table.clearSelection();
        tableModel.getDataVector().removeAllElements();

        for (int i = 0; i < Feedback.feedbacks.size(); i++) {

            Feedback feedback = Feedback.feedbacks.get(i);
            Object[] data = {feedback.getOrderID(),feedback.getFeedback()};
            tableModel.addRow(data);
        }


        table.setDefaultEditor(Object.class,null);
        scrollPane.setViewportView(table);


        mainPanel = new JPanel();
        mainPanel.add(scrollPane);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        frame.add(mainPanel);
        frame.pack();
        centerWindow(frame);
        frame.setVisible(true);
    }

}
