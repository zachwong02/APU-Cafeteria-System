package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ItemUI extends UI implements ActionListener {
    private JFrame frame;
    private Button increase,decrease,addToCart,confirm;
    private JLabel itemPrice,itemAmt,itemName;
    private JPanel mainContainer,variableContainer,inCart,confirmContainer,titleContainer,itemPriceContainer;

    private Customer c;
    private ArrayList<Cart> customerCart;
    private DataIO data = new DataIO();

    public ItemUI(Customer c,String u) {
        this.c = c;
        this.customerCart = c.getCart();

        frame = new JFrame();
        mainContainer = new JPanel();
        titleContainer = new JPanel();
        itemName = new JLabel(u,JLabel.CENTER);
        itemName.setFont(new Font("Dialog",Font.BOLD,15));
        titleContainer.add(itemName);
        variableContainer = new JPanel();
        confirmContainer = new JPanel();
        confirm = new Button("Back to Menu");
        confirm.addActionListener(this);
        confirmContainer.add(confirm);

        itemPriceContainer = new JPanel();
        itemPrice = new JLabel();
        getPrice(itemName.getText());
        itemPriceContainer.add(itemPrice);

        inCart = new JPanel();
        inCart.setLayout(new FlowLayout());
        decrease = new Button("-");
        itemAmt = new JLabel();
        increase = new Button("+");
        decrease.addActionListener(this);
        increase.addActionListener(this);

        inCart.add(decrease);
        inCart.add(itemAmt);
        inCart.add(increase);

        addToCart = new Button("Add to Cart");
        addToCart.addActionListener(this);

        draw(u);

    }

    private void check(String u){
        for (int i = 0; i < customerCart.size(); i++) {
            Cart currentItem = customerCart.get(i);
            if (currentItem.getItem().equals(u)) {
                itemAmt.setText(String.valueOf(currentItem.getAmt()));
                confirm.setLabel("Confirm");
                variableContainer.add(inCart);
                return;
            }
        }
        variableContainer.add(addToCart);
        return;
    }

    private void draw(String u){
        check(u);
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.add(titleContainer);
        mainContainer.add(itemPriceContainer);
        mainContainer.add(variableContainer);
        mainContainer.add(confirmContainer);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        frame.add(mainContainer);
        frame.pack();
        frame.setResizable(false);
        centerWindow(frame);
        frame.setVisible(true);

    }

    private void getPrice(String item){

        for (int i = 0; i < Food.food.size(); i++) {
            if (item.equals(Food.food.get(i).getName())){
                itemPrice.setText("Price: RM" + Food.food.get(i).getPrice());
            }
        }

        for (int i = 0; i < Drink.drink.size(); i++) {
            if (item.equals(Drink.drink.get(i).getName())){
                itemPrice.setText("Price: RM" + Drink.drink.get(i).getPrice());
            }
        }


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm){

            frame.dispose();
        }
        else if (e.getSource() == addToCart) {
            if (inCart.getParent() == null){
                variableContainer.remove(addToCart);
                itemAmt.setText("1");
                variableContainer.add(inCart);
                variableContainer.revalidate();
                variableContainer.repaint();

                String cartName = itemName.getText();
                double cartPrice = Double.parseDouble(itemPrice.getText().replace("Price: RM",""));

                Cart cartItem = new Cart(cartName,cartPrice,1);

                customerCart.add(cartItem);

                data.write("customer");

            }
        }
        else if (e.getSource() == increase) {
            String amt = String.valueOf(Integer.parseInt(itemAmt.getText()) + 1);
            itemAmt.setText(amt);

            for (int i = 0; i < customerCart.size(); i++) {
                Cart currentItem = customerCart.get(i);
                if (itemName.getText().equals(currentItem.getItem())){
                    currentItem.setAmt(Integer.parseInt(amt));
                    data.write("customer");
                    return;
                }

            }

        }
        else if (e.getSource() == decrease) {
            String amt = String.valueOf(Integer.parseInt(itemAmt.getText()) - 1);
            if (Integer.parseInt(amt) < 1){
                int isRemove = JOptionPane.showConfirmDialog(frame,"Are you sure you want to remove this from the cart?",null,JOptionPane.OK_CANCEL_OPTION);

                if (isRemove == JOptionPane.OK_OPTION){
                    variableContainer.remove(inCart);
                    variableContainer.add(addToCart);
                    variableContainer.revalidate();
                    variableContainer.repaint();


                    for (int i = 0; i < customerCart.size(); i++) {
                        if (customerCart.get(i).getItem().equals(itemName.getText())){
                            customerCart.remove(customerCart.get(i));
                            data.write("customer");
                            return;
                        }
                    }
                }

            }else{
                itemAmt.setText(amt);

                for (int i = 0; i < customerCart.size(); i++) {
                    Cart currentItem = customerCart.get(i);
                    if (itemName.getText().equals(currentItem.getItem())){
                        currentItem.setAmt(Integer.parseInt(amt));
                        data.write("customer");
                        return;
                    }

                }
            }

        }
    }

}





