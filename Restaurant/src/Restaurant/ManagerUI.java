package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerUI extends UI implements ActionListener {
    private JFrame frame;
    private Button menuButton, accountsButton, orderButton, reportButton, feedbackButton, logoutButton;
    private JPanel titleContainer,mainPanel, logoutContainer, panel;
    private JLabel title;
    private GridLayout grid;

    public ManagerUI() {
        frame = new JFrame();

        titleContainer = new JPanel();
        title = new JLabel("APU CAFETERIA SYSTEM",JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        title.setForeground(Color.decode("#3171e0"));
        title.setFont(new Font("Nunito",Font.PLAIN,20));
        titleContainer.add(title);
        titleContainer.setBackground(Color.decode("#2a2a2a"));

        grid = new GridLayout(2,3);
        grid.setVgap(10);
        grid.setHgap(10);

        panel = new JPanel();
        panel.setLayout(grid);

        mainPanel = new JPanel();
        mainPanel.setLayout(grid);


        menuButton = new Button("Menu");
        orderButton = new Button("Order");
        accountsButton = new Button("Accounts");
        reportButton = new Button("Reports");
        feedbackButton = new Button("Feedback");
        logoutButton = new Button("Logout");

        menuButton.addActionListener(this);
        orderButton.addActionListener(this);
        accountsButton.addActionListener(this);
        reportButton.addActionListener(this);
        feedbackButton.addActionListener(this);
        logoutButton.addActionListener(this);


        mainPanel.add(menuButton);
        mainPanel.add(orderButton);
        mainPanel.add(accountsButton);
        mainPanel.add(reportButton);
        mainPanel.add(feedbackButton);
        mainPanel.add(logoutButton);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        frame.setLayout(new BorderLayout());
        frame.add(titleContainer,BorderLayout.NORTH);
        frame.add(mainPanel,BorderLayout.CENTER);
        frame.setSize(1024,768);
        centerWindow(frame);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton){
            ManagerMenu menu = new ManagerMenu();
        } else if (e.getSource() == orderButton) {
            ManagerOrder order = new ManagerOrder();
        } else if (e.getSource() == accountsButton) {
            ManagerCustomer mc = new ManagerCustomer();
        } else if (e.getSource() == reportButton) {
            Report r = new Report();
        } else if (e.getSource() == feedbackButton) {
            ManagerFeedback f = new ManagerFeedback();
        } else if (e.getSource() == logoutButton) {
            frame.dispose();
            Login.getLogin().setVisible(true);
        }
    }
}