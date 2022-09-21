package Restaurant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerMenu extends UI implements ActionListener{
    private JFrame frame;
    private Button foodButton, drinkButton, addButton, removeButton, editButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private DataIO data = new DataIO();
    private String view = "Food";
    private JScrollPane scrollPanel;
    private JPanel itemContainer,btnContainer, mainPanel;

    public ManagerMenu(){

        String[] col = {"Item Name", "Price"};

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

        foodButton = new Button("Food");
        drinkButton = new Button("Drink");
        addButton = new Button("Add");
        removeButton = new Button("Remove");
        editButton = new Button("Edit");

        foodButton.addActionListener(this);
        drinkButton.addActionListener(this);
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        removeButton.addActionListener(this);


        draw(view);
    }

    private void draw(String view){

        itemContainer.add(foodButton);
        itemContainer.add(drinkButton);
        btnContainer.add(addButton);
        btnContainer.add(removeButton);
        btnContainer.add(editButton);

        table.clearSelection();
        tableModel.getDataVector().removeAllElements();

        if (view.equals("Food")){
            for (int i = 0; i < Food.food.size(); i++) {

                Food current = Food.food.get(i);

                Object[] data = {current.getName(), current.getPrice()};

                tableModel.addRow(data);
            }
        } else if (view.equals("Drink")) {
            for (int i = 0; i < Drink.drink.size(); i++) {

                Drink current = Drink.drink.get(i);

                Object[] data = {current.getName(), current.getPrice()};

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

    private void drawItemDetails(){
        JPanel panel = new JPanel();
        JPanel nameContainer = new JPanel();
        JPanel priceContainer = new JPanel();

        JLabel nameLabel = new JLabel("Item Name:");
        JLabel priceLabel = new JLabel("Item Price:");

        TextField nameText = new TextField(25);
        TextField priceText = new TextField(25);


        nameContainer.add(nameLabel);
        nameContainer.add(nameText);

        priceContainer.add(priceLabel);
        priceContainer.add(priceText);

        panel.setLayout(new GridLayout(2, 1));

        panel.add(nameContainer);
        panel.add(priceContainer);

        int addItem = JOptionPane.showConfirmDialog(frame,panel, "", JOptionPane.OK_CANCEL_OPTION);
        if (addItem == JOptionPane.OK_OPTION){
            if(Validation.isAllFilled(nameText.getText(),priceText.getText())){
                if (Validation.isPrice(priceText.getText())){
                    if(this.view.equals("Food")){
                        for (int i = 0; i < Food.food.size(); i++) {
                            if(Food.food.get(i).getName().equals(nameText.getText())){message(frame,"Name is not valid");}
                            else {
                                Food.food.add(new Food(nameText.getText(),Double.parseDouble(priceText.getText())));
                                data.write("food");
                                draw(this.view);
                                message(frame,"Item added!");
                                break;
                            }
                        }
                    }

                    else if(this.view.equals("Drink")){
                        for (int i = 0; i < Drink.drink.size(); i++) {
                            if(Drink.drink.get(i).getName().equals(nameText.getText())){message(frame,"Name is not valid");}
                            else {
                                Drink.drink.add(new Drink(nameText.getText(),Double.parseDouble(priceText.getText())));
                                data.write("drink");
                                draw(this.view);
                                message(frame,"Item added!");
                                break;
                            }
                        }
                    }

                }else{message(frame,"Please enter a valid price");}
            }else{message(frame,"Please fill up the form");}

        }
    }


    private void drawItemDetails(Item item){
        JPanel panel = new JPanel();
        JPanel nameContainer = new JPanel();
        JPanel priceContainer = new JPanel();

        JLabel nameLabel = new JLabel("Item Name:");
        JLabel priceLabel = new JLabel("Item Price:");

        TextField nameText = new TextField(25);
        nameText.setText(item.getName());
        TextField priceText = new TextField(25);
        priceText.setText(String.valueOf(item.getPrice()));

        nameContainer.add(nameLabel);
        nameContainer.add(nameText);

        priceContainer.add(priceLabel);
        priceContainer.add(priceText);

        panel.setLayout(new GridLayout(2, 1));

        panel.add(nameContainer);
        panel.add(priceContainer);

        int editItem = JOptionPane.showConfirmDialog(frame,panel, "", JOptionPane.OK_CANCEL_OPTION);
        if (editItem == JOptionPane.OK_OPTION){
            if(Validation.isAllFilled(nameText.getText(),priceText.getText())){
                if (Validation.isPrice(priceText.getText())){
                    if(item instanceof Food){
                        for (int i = 0; i < Food.food.size(); i++) {
                            if(Food.food.get(i).getName().equals(nameText.getText())){message(frame,"Name is not valid");}
                            else {
                                if (Food.food.get(i) == item){
                                    Food.food.get(i).setName(nameText.getText());
                                    Food.food.get(i).setPrice(Double.parseDouble(priceText.getText()));
                                    data.write("food");
                                    draw(this.view);
                                    message(frame,"Item edited!");
                                    break;
                                }
                            }
                        }
                    }

                    else if(item instanceof  Drink){
                        for (int i = 0; i < Drink.drink.size(); i++) {
                            if(Drink.drink.get(i).getName().equals(nameText.getText())){message(frame,"Name is not valid");}
                            else {
                                if (Drink.drink.get(i) == item) {
                                    Drink.drink.get(i).setName(nameText.getText());
                                    Drink.drink.get(i).setPrice(Double.parseDouble(priceText.getText()));
                                    data.write("drink");
                                    draw(this.view);
                                    message(frame,"Item edited!");
                                    break;
                                }
                            }
                        }
                    }

                }else{message(frame,"Please enter a valid price");}
            }else{message(frame,"Please fill up the form");}

        }
    }


    private void removeItem(Item item){
        if (item instanceof Food){
            for (int i = 0; i < Food.food.size(); i++) {
                if (Food.food.get(i) == item){
                    Food.food.remove(item);
                    data.write("food");
                    draw(this.view);
                    break;
                }
            }
        }
        else if (item instanceof Drink) {
            for (int i = 0; i < Drink.drink.size(); i++) {
                if (Drink.drink.get(i) == item){
                    Drink.drink.remove(item);
                    data.write("drink");
                    draw(this.view);
                    break;
                }
            }
        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == foodButton){
            this.view = "Food";
            draw(this.view);
        }else if(e.getSource() == drinkButton){
            this.view = "Drink";
            draw(this.view);
        }else if(e.getSource() == addButton){
            drawItemDetails();
        } else if (e.getSource() == editButton) {
            if (this.view.equals("Food")){
                try{
                    int row = table.getSelectedRow();
                    Object foodItem = table.getModel().getValueAt(row, 0);
                    for (int i = 0; i < Food.food.size(); i++) {
                        if(Food.food.get(i).getName().equals(foodItem.toString())){
                            Food foodObj = Food.food.get(i);
                            drawItemDetails(foodObj);
                            break;
                        }
                    }
                }catch(NullPointerException ex){}


            } else if (this.view.equals("Drink")) {
                try{
                    int row = table.getSelectedRow();
                    Object foodItem = table.getModel().getValueAt(row, 0);
                    for (int i = 0; i < Drink.drink.size(); i++) {
                        if(Drink.drink.get(i).getName().equals(foodItem.toString())){
                            Drink drinkObj = Drink.drink.get(i);
                            drawItemDetails(drinkObj);
                            break;
                        }
                    }
                }catch(NullPointerException ex){}
            }
        } else if (e.getSource() == removeButton) {
            if(table.getSelectionModel().isSelectionEmpty() == false){

                int wantRemove = JOptionPane.showConfirmDialog(frame,"Are you sure want to remove this item?",null,JOptionPane.OK_CANCEL_OPTION);
                if(wantRemove == JOptionPane.OK_OPTION){
                    try{
                        int row = table.getSelectedRow();
                        Object item = table.getModel().getValueAt(row, 0);
                        if (this.view.equals("Food")){
                            for (int i = 0; i < Food.food.size(); i++) {
                                if (Food.food.get(i).getName().equals(item.toString())){
                                    removeItem(Food.food.get(i));
                                    break;
                                }
                            }

                        } else if (this.view.equals("Drink")) {
                            for (int i = 0; i < Drink.drink.size(); i++) {
                                if (Drink.drink.get(i).equals(item.toString())) {
                                    removeItem(Drink.drink.get(i));
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
