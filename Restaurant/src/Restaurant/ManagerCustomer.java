package Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerCustomer extends UI implements ActionListener {
    private JFrame frame;
    private Button customerButton, managerButton, addButton, removeButton, editButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private DataIO data = new DataIO();
    private String view = "Customer";
    private JScrollPane scrollPanel;
    private JPanel itemContainer,btnContainer, mainPanel;

    public ManagerCustomer(){

        String[] col = {"Name", "Email", "Phone Number"};

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

        customerButton = new Button("Customer");
        managerButton = new Button("Manager");
        addButton = new Button("Add");
        removeButton = new Button("Remove");
        editButton = new Button("Edit");

        customerButton.addActionListener(this);
        managerButton.addActionListener(this);
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        removeButton.addActionListener(this);


        draw();
    }

    public void draw(){

        itemContainer.add(customerButton);
        itemContainer.add(managerButton);
        btnContainer.add(addButton);
        btnContainer.add(removeButton);
        btnContainer.add(editButton);

        table.clearSelection();
        tableModel.getDataVector().removeAllElements();

        if (this.view.equals("Customer")){
            for (int i = 0; i < Customer.customers.size(); i++) {

                Customer current = Customer.customers.get(i);

                Object[] data = {current.getName(), current.getEmail(), current.getNumber()};

                tableModel.addRow(data);
            }
        } else if (this.view.equals("Manager")) {
            for (int i = 0; i < Manager.managers.size(); i++) {

                Manager current = Manager.managers.get(i);

                Object[] data = {current.getName(), current.getEmail(), current.getNumber()};

                tableModel.addRow(data);
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




    private void removeItem(User user){
        if (user instanceof Customer){
            for (int i = 0; i < Customer.customers.size(); i++) {
                if (Customer.customers.get(i) == user){
                    Customer.customers.remove(user);
                    data.write("customer");
                    draw();
                    break;
                }
            }
        }
        else if (user instanceof Manager) {
            for (int i = 0; i < Manager.managers.size(); i++) {
                if (Manager.managers.get(i) == user){
                    Manager.managers.remove(user);
                    data.write("manager");
                    draw();
                    break;
                }
            }
        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == customerButton){
            this.view = "Customer";
            draw();
        }else if(e.getSource() == managerButton){
            this.view = "Manager";
            draw();
        }else if(e.getSource() == addButton){
            if (this.view.equals("Customer")){
                Register r = new Register("customer",this);
            } else if (this.view.equals("Manager")) {
                Register r = new Register("manager",this);
            }
        } else if (e.getSource() == editButton) {
            if(table.getSelectionModel().isSelectionEmpty() == false){

                if (this.view.equals("Customer")){
                    try{
                        int row = table.getSelectedRow();
                        Object customer = table.getModel().getValueAt(row, 0);
                        Customer c = Customer.getCustomer(customer.toString());
                        ManagerProfiles p = new ManagerProfiles(c,this);

                    }catch(NullPointerException ex){}
                } else if (this.view.equals("Manager")) {
                    try{
                        int row = table.getSelectedRow();
                        Object manager = table.getModel().getValueAt(row, 0);
                        Manager c = Manager.getManager(manager.toString());
                        ManagerProfiles p = new ManagerProfiles(c,this);

                    }catch(NullPointerException ex){}
                }
            }
        } else if (e.getSource() == removeButton) {
            if(table.getSelectionModel().isSelectionEmpty() == false){

                int wantRemove = JOptionPane.showConfirmDialog(frame,"Are you sure want to remove this item?",null,JOptionPane.OK_CANCEL_OPTION);
                if(wantRemove == JOptionPane.OK_OPTION){
                    try{
                        int row = table.getSelectedRow();
                        Object item = table.getModel().getValueAt(row, 0);
                        if (this.view.equals("Customer")){
                            for (int i = 0; i < Customer.customers.size(); i++) {
                                if (Customer.customers.get(i).getName().equals(item.toString())){
                                    removeItem(Customer.customers.get(i));
                                    break;
                                }
                            }

                        } else if (this.view.equals("Manager")) {
                            for (int i = 0; i < Manager.managers.size(); i++) {
                                if (Manager.managers.get(i).getName().equals(item.toString())) {
                                    removeItem(Manager.managers.get(i));
                                    break;
                                }
                            }
                        }
                    }catch (NullPointerException ex){}
                }
            }


        }

    }

}
