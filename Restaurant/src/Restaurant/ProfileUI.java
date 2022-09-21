package Restaurant;

import org.w3c.dom.Text;

import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProfileUI extends UI implements ActionListener {
    private JFrame frame;
    private JPanel panel1, panel2, buttons, titleContainer, balanceContainer;
    private JLabel nameLabel, emailLabel, numberLabel, balanceLabel, balance, title;
    private TextField name, email, number;
    private JButton changePassword, edit, reload;
    private DataIO data = new DataIO();
    private Customer c;

    public ProfileUI(Customer c) {

        this.c = c;

        frame = new JFrame();
        panel1 = new JPanel();
        panel2 = new JPanel();
        buttons = new JPanel();
        balanceContainer = new JPanel();
        titleContainer = new JPanel();

        title = new JLabel(c.getName() + "'s Profile", JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        title.setForeground(Color.decode("#3171e0"));
        title.setFont(new Font("Nunito", Font.PLAIN, 20));
        titleContainer.add(title);
        title.setForeground(Color.decode("#3171e0"));
        titleContainer.setBackground(Color.decode("#2a2a2a"));

        nameLabel = new JLabel("Name", JLabel.CENTER);
        emailLabel = new JLabel("Email", JLabel.CENTER);
        numberLabel = new JLabel("Number", JLabel.CENTER);
        balanceLabel = new JLabel("Balance", JLabel.CENTER);

        name = new TextField(c.getName());
        email = new TextField(c.getEmail());
        number = new TextField(c.getNumber());
        balance = new JLabel(String.valueOf(c.getBalance()));

        name.setEditable(false);
        email.setEditable(false);
        number.setEditable(false);

        reload = new JButton("Reload");

        changePassword = new JButton("Change Password");
        edit = new JButton("Edit");

        buttons.setLayout(new FlowLayout());
        buttons.add(changePassword);
        buttons.add(edit);

        reload.addActionListener(this);
        changePassword.addActionListener(this);
        edit.addActionListener(this);

        panel2.setLayout(new GridLayout(4, 2));

        panel2.add(nameLabel);
        panel2.add(name);

        panel2.add(emailLabel);
        panel2.add(email);

        panel2.add(numberLabel);
        panel2.add(number);

        panel2.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        balanceContainer.setLayout(new FlowLayout());
        balanceContainer.add(balance);
        balanceContainer.add(reload);
        panel2.add(balanceLabel);
        panel2.add(balanceContainer);


        panel1.setLayout(new BorderLayout());
        panel1.add(titleContainer, BorderLayout.PAGE_START);
        panel1.add(panel2, BorderLayout.CENTER);
        panel1.add(buttons, BorderLayout.PAGE_END);

        frame.add(panel1);
        frame.setSize(600, 400);
        frame.setResizable(false);
        centerWindow(frame);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == edit){
            if (edit.getText().equals("Save")){

                if (isAllFilled(name.getText(),email.getText(),number.getText())){
                    String message = "Please enter a valid";
                    if (nameExists(name.getText()) == true) {
                        if (c.getName().equals(name.getText())){}
                        else{
                            message(frame, "Username exists!");
                            name.setText("");
                        }
                    }
                    if (isEmail(email.getText()) == false) {
                        message += " [Email] ";
                        email.setText("");
                    }
                    if (isPhone(number.getText()) == false) {
                        message += " [Number] ";
                        number.setText("");
                    }

                    if (message.equals("Please enter a valid") == false) message(frame,message);
                    else{
                        c.setName(name.getText().trim());
                        c.setEmail(email.getText().trim());
                        c.setNumber(number.getText().trim());
                        data.write("customer");
                        data.write("orders");

                        JOptionPane.showMessageDialog(frame,"Your details have been changed! Logging out...");

                        Login.getLogin().setVisible(true);
                        Login.cui.getCui().dispose();
                        frame.dispose();
                    }
                }else {message(frame,"Please fill in all the fields!");}

            }
            name.setEditable(true);
            email.setEditable(true);
            number.setEditable(true);
            balanceLabel.setVisible(false);
            balanceContainer.setVisible(false);
            changePassword.setText("Cancel");
            edit.setText("Save");

        }
        else if (e.getSource() == changePassword) {
            if (changePassword.getText().equals("Cancel")) {
                name.setEditable(false);
                email.setEditable(false);
                number.setEditable(false);
                balanceLabel.setVisible(true);
                balanceContainer.setVisible(true);
                changePassword.setText("Change Password");
                edit.setText("Edit");
            }else {
                String cpass = JOptionPane.showInputDialog("Please Enter Your Current Password");
                if (cpass != null) {
                    if (cpass.equals(String.valueOf(c.getPassword()))){

                        JPanel panel = new JPanel();
                        JPanel pass1Container = new JPanel();
                        JPanel pass2Container = new JPanel();

                        JLabel pass1Label = new JLabel("New Password");
                        JLabel pass2Label = new JLabel("Re-enter Password");

                        TextField pass1 = new TextField(25);
                        TextField pass2 = new TextField(25);

                        pass1Container.add(pass1Label);
                        pass1Container.add(pass1);

                        pass2Container.add(pass2Label);
                        pass2Container.add(pass2);

                        panel.setLayout(new GridLayout(2, 1));

                        panel.add(pass1Container);
                        panel.add(pass2Container);

                        int confirmPass = JOptionPane.showConfirmDialog(frame, panel, "", JOptionPane.OK_CANCEL_OPTION);

                        if (pass1.getText().equals(pass2.getText())) {
                            if(isPassword(pass1.getText())){
                                c.setPassword(Integer.parseInt(pass1.getText()));
                                data.write("customer");

                                JOptionPane.showMessageDialog(frame,"Your details have been changed! Logging out...");

                                frame.dispose();
                                Login.cui.getCui().dispose();
                                Login.getLogin().setVisible(true);

                            }else {
                                message(frame,"Please enter a valid password.");
                            }

                        }else {
                            message(frame,"Passwords do not match!");
                        }


                    }else{
                        message(frame, "Incorrect Password!");
                    }

                }


            }
        } else if(e.getSource() == reload){
            CustomerUI x = new CustomerUI();
                try {
                    Login.cui.reload(this.c,this.balance,this.frame);
                    Login.cui.getBalanceAmt().setText(this.balance.getText());
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(frame,"Please enter a valid reload amount");
                }
        }
    }



}
