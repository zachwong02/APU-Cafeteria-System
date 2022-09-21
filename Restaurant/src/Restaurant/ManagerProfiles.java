package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerProfiles extends UI implements ActionListener {
    private JFrame frame;
    private JPanel panel1, panel2, buttons, titleContainer, balanceContainer;
    private JLabel nameLabel, emailLabel, numberLabel,title;
    private TextField name, email, number;
    private JButton edit;
    private DataIO data = new DataIO();
    private User u;
    private ManagerCustomer ui;

    public ManagerProfiles(User user, ManagerCustomer ui) {

        this.u = user;
        this.ui = ui;
        frame = new JFrame();
        panel1 = new JPanel();
        panel2 = new JPanel();
        buttons = new JPanel();
        balanceContainer = new JPanel();
        titleContainer = new JPanel();

        title = new JLabel(u.getName() + "'s Profile", JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        title.setForeground(Color.decode("#3171e0"));
        title.setFont(new Font("Nunito", Font.PLAIN, 20));
        titleContainer.add(title);
        title.setForeground(Color.decode("#3171e0"));
        titleContainer.setBackground(Color.decode("#2a2a2a"));

        nameLabel = new JLabel("Name", JLabel.CENTER);
        emailLabel = new JLabel("Email", JLabel.CENTER);
        numberLabel = new JLabel("Number", JLabel.CENTER);

        name = new TextField(u.getName());
        email = new TextField(u.getEmail());
        number = new TextField(u.getNumber());

        name.setEditable(false);
        email.setEditable(false);
        number.setEditable(false);


        edit = new JButton("Edit");

        buttons.setLayout(new FlowLayout());
        buttons.add(edit);

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

                    if (u instanceof Customer){
                        if (nameExists(name.getText()) == true) {
                            if (u.getName().equals(name.getText())){}
                            else{
                                message(frame, "Username exists!");
                                name.setText("");
                            }
                        }
                    }

                    if (u instanceof Manager){
                        if (managerExists(name.getText()) == true) {
                            if (u.getName().equals(name.getText())){}
                            else{
                                message(frame, "Username exists!");
                                name.setText("");
                            }
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
                        u.setName(name.getText().trim());
                        u.setEmail(email.getText().trim());
                        u.setNumber(number.getText().trim());
                        data.write("manager");
                        data.write("customer");
                        data.write("orders");
                        this.ui.draw();
                        frame.dispose();
                    }
                }else {message(frame,"Please fill in all the fields!");}

            }
            name.setEditable(true);
            email.setEditable(true);
            number.setEditable(true);
            balanceContainer.setVisible(false);
            edit.setText("Save");

        }

    }


}
