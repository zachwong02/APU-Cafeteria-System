package Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends UI implements ActionListener {
    private static JFrame x;
    private JPanel main,username,password,buttons;
    private Label labeluser,labelpass;
    private TextField textuser;
    private JPasswordField textpass;
    private Button login,register;
    private DataIO data = new DataIO();


    public static CustomerUI cui;
    public static ManagerUI mui;



    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == login) {

            String user = textuser.getText().trim();
            String password = String.valueOf(textpass.getPassword());

            if (user.isEmpty()){
                message(x,"Please enter your username");
            } else if (password.isEmpty()) {
                message(x,"Please enter your password");
            }else{
                Customer c = Customer.getCustomer(user);
                Manager m = Manager.getManager(user);

                if (c != null && m != null) {
                    if(c.getName().equals(m.getName())){
                        sameName(c,m,user,password);
                    }
                }

                else if (c != null) {
                    checkPassword(c,user,password);
                }
                else if (m != null) {
                    checkPassword(m,user,password);
                }else{
                    message(x,"User Not Found");
                    textuser.setText("");
                    textpass.setText("");
                }
            }

        }else if(e.getSource() == register){
            Register r = new Register("customer",this);
        }
    }

    public Login(){

        x = new JFrame("APU Cafeteria System");

        main = new JPanel();
        username = new JPanel();
        password = new JPanel();
        labeluser = new Label("Username");
        textuser = new TextField(20);
        labelpass = new Label("Password");
        textpass = new JPasswordField(14);
        username.add(labeluser);
        username.add(textuser);
        password.add(labelpass);
        password.add(textpass);

        login = new Button("Login");
        register = new Button("Signup");

        buttons = new JPanel();
        buttons.add(login);
        buttons.add(register);

        login.addActionListener(this);
        register.addActionListener(this);

        username.setLayout(new FlowLayout(FlowLayout.LEFT));
        password.setLayout(new FlowLayout(FlowLayout.LEFT));

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        main.add(username);
        main.add(password);
        main.add(buttons);

        main.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));

        x.add(main);
        x.pack();
        x.setResizable(false);
        centerWindow(x);
        x.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        x.setVisible(true);
    }



    private void sameName(Customer c , Manager m, String user, String pass){
        String[] role = {"Manager","Student"};
        try{
            String s = (String)JOptionPane.showInputDialog(
                    x,
                    "Select User Type",
                    null,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    role,
                    "Student");

            if (s.equals("Student")){
                checkPassword(c,user,pass);
            }
            else if(s.equals("Manager")){
                checkPassword(m,user,pass);
            }
        }catch (NullPointerException e){}

    }

    private void checkPassword(User u,String user, String password){

        if (u.getName().equals(user)) {
            try{
                if (u.getPassword() == Integer.parseInt(password)) {
                    if (u instanceof Customer) {
                        textuser.setText("");
                        textpass.setText("");
                        x.setVisible(false);
                        Customer u1 = (Customer) u;
                        cui = new CustomerUI(u1);

                    } else if (u instanceof Manager) {
                        textuser.setText("");
                        textpass.setText("");
                        x.setVisible(false);
                        Manager u1 = (Manager) u;
                        mui = new ManagerUI();
                    }
                } else {
                    message(x,"Incorrect Password!");
                    textpass.setText("");
                }
            }catch (NumberFormatException e){
                message(x,"Incorrect Password!");
                textpass.setText("");
            }
        }
    }

    public static JFrame getLogin() {
        return x;
    }
}
