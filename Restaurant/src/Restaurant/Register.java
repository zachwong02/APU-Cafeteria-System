package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Register extends UI implements ActionListener {
    private GridLayout grid;
    private JFrame frame;
    private TextField nameText, emailText, numberText;
    private JPasswordField passText,passTextConfirm;
    private Label nameLabel, emailLabel, numberLabel, passLabel, passLabelConfirm, title;
    private JPanel panel,mainPanel;
    private JCheckBox terms;
    private Button submit;
    private DataIO data = new DataIO();
    private String type;
    private ManagerCustomer ui;

    public Register(String type, Object ui) {

        if (ui instanceof ManagerCustomer){
            this.ui = (ManagerCustomer) ui;
        }

        this.type = type;

        frame = new JFrame();
        panel = new JPanel();
        mainPanel = new JPanel();

        grid = new GridLayout(13,1);
        grid.setVgap(10);
        panel.setLayout(grid);

        title = new Label("Registration",Label.CENTER);
        title.setFont(new Font("Nunito",Font.PLAIN,20));
        panel.add(title);

        nameLabel = new Label("Name",Label.LEFT);
        nameText = new TextField(20);
        panel.add(nameLabel);
        panel.add(nameText);

        emailLabel = new Label("Email");
        emailText = new TextField(20);
        panel.add(emailLabel);
        panel.add(emailText);

        numberLabel = new Label("Phone Number");
        numberText = new TextField(20);
        panel.add(numberLabel);
        panel.add(numberText);

        passLabel = new Label("Password");
        passText = new JPasswordField(20);
        panel.add(passLabel);
        panel.add(passText);


        passLabelConfirm = new Label("Confirm Password");
        passTextConfirm = new JPasswordField(20);
        panel.add(passLabelConfirm);
        panel.add(passTextConfirm);
        panel.setPreferredSize(new Dimension(300,500));

        terms = new JCheckBox("I confirm the details above are correct");
        panel.add(terms);

        submit = new Button("Submit");
        submit.addActionListener(this);
        panel.add(submit);

        mainPanel.add(panel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));



        frame.add(mainPanel);
        frame.pack();
        centerWindow(frame);
        frame.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit){

            String name = nameText.getText().trim();
            String email = emailText.getText();
            String number = numberText.getText();
            String password = String.valueOf(passText.getPassword());
            String confirmPassword = String.valueOf(passTextConfirm.getPassword());
            if (terms.isSelected()){
                if (isAllFilled(name,email,number,password,confirmPassword)){
                    String message = "Please enter a valid";

                    if(this.type.equals("customer")){
                        if (nameExists(name) == true) {
                            message(frame, "Username exists!");
                            nameText.setText("");
                            return;
                        }
                    }

                    if(this.type.equals("manager")){
                        if (managerExists(name) == true) {
                            message(frame, "Username exists!");
                            nameText.setText("");
                            return;
                        }
                    }

                    if (isEmail(email) == false) {
                        message += " [Email] ";
                        emailText.setText("");
                    }
                    if (isPhone(number) == false) {
                        message += " [Number] ";
                        numberText.setText("");
                    }
                    if (isPassword(password) == false || isPassword(confirmPassword) == false) {
                        message += " [Password] ";
                        passText.setText("");
                        passTextConfirm.setText("");
                    }
                    if (message.equals("Please enter a valid") == false) message(frame,message);
                    else{
                        if(password.equals(confirmPassword) == false) message(frame,"Passwords do not match!");
                        else{
                            if (this.type.equals("customer")){
                                Customer.customers.add(new Customer(name,Integer.parseInt(password),email,number,0,new ArrayList<>(),new ArrayList<>()));
                                data.write("customer");
                                message(frame,"You are registered as a customer! 😃");
                                ui.draw();
                                frame.dispose();
                            }
                            if (this.type.equals("manager")){
                                Manager.managers.add(new Manager(name,Integer.parseInt(password),email,number));
                                data.write("manager");
                                ui.draw();
                                message(frame,"You are registered a manager! 😃");
                                frame.dispose();

                            }
                        }
                    }

                }else {
                    message(frame,"Please fill in all the fields!");
                }
            }else {
                message(frame,"Please accept the Terms & Conditions");
            }
        }
    }
}
