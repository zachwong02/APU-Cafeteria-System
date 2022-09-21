package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerUI extends UI implements ActionListener {

    private static JFrame cui;
    private JPanel buttons, menu, balance, balanceContainer, titleContainer;
    private GridLayout gridSideContainer, gridMenuContainer;
    private Button profile, orders, cart, logout,food, beverages, reload;
    private JLabel title, balanceAmt;
    private Customer c;
    private DataIO data = new DataIO();
    private JPanel foodMenu;
    private JPanel drinkMenu;
    private CartUI cartPanel;
    private static CustomerOrderUI ordersPanel;

    public CustomerUI(){}

    public CustomerUI(Customer c) {
        this.c = c;

        cui = new JFrame();
        buttons = new JPanel();
        menu = new JPanel();
        balance = new JPanel();
        balanceContainer = new JPanel();
        titleContainer = new JPanel();
        title = new JLabel("APU CAFETERIA SYSTEM",JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        title.setForeground(Color.decode("#3171e0"));
        title.setFont(new Font("Nunito",Font.PLAIN,20));
        titleContainer.add(title);
        titleContainer.setBackground(Color.decode("#2a2a2a"));

        cui.setLayout(new BorderLayout());

        gridSideContainer = new GridLayout();
        gridMenuContainer = new GridLayout();

        gridSideContainer.setVgap(30);
        gridSideContainer.setColumns(1);
        gridSideContainer.setRows(4);

        gridMenuContainer.setRows(2);
        gridMenuContainer.setColumns(1);
        gridMenuContainer.setVgap(30);


        profile = new Button("Profile");
        orders = new Button("Orders");
        cart = new Button("Cart");
        logout = new Button("Logout");


        food = new Button("Food");
        beverages = new Button("Beverages");

        reload = new Button("Reload");
        balanceAmt = new JLabel("RM " + c.getBalance());
        balance.setLayout(new BoxLayout(balance,BoxLayout.Y_AXIS));
        balance.add(balanceAmt);
        balance.add(reload);
        balanceAmt.setAlignmentX(Component.CENTER_ALIGNMENT);
        balanceContainer.add(balance);

        menu.add(food);
        menu.add(beverages);
        menu.setLayout(gridMenuContainer);
        menu.setBorder(BorderFactory.createEmptyBorder(80,80,80,80));

        buttons.add(profile);
        buttons.add(orders);
        buttons.add(cart);
        buttons.add(logout);


        buttons.setLayout(gridSideContainer);
        buttons.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        buttons.setPreferredSize(new Dimension(150,768));

        profile.addActionListener(this);
        orders.addActionListener(this);
        cart.addActionListener(this);
        logout.addActionListener(this);
        reload.addActionListener(this);
        food.addActionListener(this);
        beverages.addActionListener(this);

        this.foodMenu = new CustomerFoodMenu(c).getFoodPanel();
        this.drinkMenu = new CustomerDrinkMenu(c).getdrinkPanel();
        this.cartPanel = new CartUI(c);
        this.ordersPanel = new CustomerOrderUI(c);

        cui.add(titleContainer,BorderLayout.PAGE_START);
        cui.add(balanceContainer,BorderLayout.LINE_END);
        cui.add(buttons,BorderLayout.LINE_START);
        cui.add(menu,BorderLayout.CENTER);
        cui.setSize(1024,768);
        centerWindow(cui);
        cui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cui.setVisible(true);

    }

    public JFrame getCui() {return cui;}

    public CustomerOrderUI getOrdersPanel(){return ordersPanel;}

    public JLabel getBalanceAmt() {return balanceAmt;}

    public void reload(Customer customer, JLabel label, JFrame parent) throws Exception {
        String amt = JOptionPane.showInputDialog("Reload Amount");

        if (amt != null) {
            if(amt.isEmpty()){
                throw new Exception();
            }

            try{
                Double.parseDouble(amt);
            }catch (Exception ex){
                throw new Exception();
            }

            int i = JOptionPane.showConfirmDialog(parent,"Are you sure?\n" + "Reload Amount: RM " + amt );

            if(i == JOptionPane.YES_OPTION) {
                customer.increaseBalance(Double.parseDouble(amt));
                label.setText("RM " + customer.getBalance());
                data.write("customer");
                amt = null;
            }
            else if(i == JOptionPane.NO_OPTION){reload(this.c,this.balanceAmt,this.cui);}
            else if (i == JOptionPane.CANCEL_OPTION) {} //Ignores Error When Clicking Cancel
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == logout) {
            cui.dispose();
            Login.getLogin().setVisible(true);
        }

        else if (e.getSource() == reload){
            try{reload(this.c,this.balanceAmt,this.cui);}
            catch(Exception ex){
                JOptionPane.showMessageDialog(cui,"Please enter a valid reload amount");
            }
        }

        else if (e.getSource() == profile) {

            if(profile.getLabel().equals("Back")){

                if (foodMenu.getParent() != null){cui.remove(foodMenu);}
                else if (drinkMenu.getParent() != null){cui.remove(drinkMenu);}
                else if (cartPanel.getCartPanel().getParent() != null) {cui.remove(cartPanel.getCartPanel());}
                else if (ordersPanel.getMainPanel().getParent() != null){cui.remove(ordersPanel.getMainPanel());}

                cui.add(menu,BorderLayout.CENTER);
                orders.setVisible(true);
                cart.setVisible(true);
                logout.setVisible(true);
                profile.setLabel("Profile");
                cui.revalidate();
                cui.repaint();

            }else{
                ProfileUI p = new ProfileUI(c);
            }

        }

        else if (e.getSource() == food) {

            cui.remove(menu);
            orders.setVisible(false);
            cart.setVisible(false);
            logout.setVisible(false);
            profile.setLabel("Back");
            cui.add(foodMenu,BorderLayout.CENTER);
            cui.revalidate();
            cui.repaint();
        }

        else if (e.getSource() == beverages) {
            cui.remove(menu);
            orders.setVisible(false);
            cart.setVisible(false);
            logout.setVisible(false);
            profile.setLabel("Back");
            cui.add(drinkMenu,BorderLayout.CENTER);
            cui.revalidate();
            cui.repaint();

        }

        else if (e.getSource() == cart) {
            cui.remove(menu);
            orders.setVisible(false);
            cart.setVisible(false);
            logout.setVisible(false);
            profile.setLabel("Back");
            cartPanel.draw();
            cui.add(cartPanel.getCartPanel(),BorderLayout.CENTER);
            cui.revalidate();
            cui.repaint();
        }

        else if (e.getSource() == orders) {
            cui.remove(menu);
            orders.setVisible(false);
            cart.setVisible(false);
            logout.setVisible(false);
            profile.setLabel("Back");
            ordersPanel.draw();
            cui.add(ordersPanel.getMainPanel(),BorderLayout.CENTER);
            cui.revalidate();
            cui.repaint();
        }

    }
}
