package Restaurant;


import java.util.ArrayList;

import static javax.swing.text.StyleConstants.setBackground;

public class Manager extends User{

    public static ArrayList<Manager>  managers = new ArrayList<>();

    public Manager(String name, int password, String email, String number) {
        super(name, password, email, number);
    }

    public static void setManagers(ArrayList<Manager> managers) {Manager.managers = managers;}


    public void search(Manager manager){}

    public void search(Customer customer){}

    public static Manager getManager(String name){

        for (int i = 0; i < managers.size(); i++) {
            if(managers.get(i).getName().matches(name)){
                return managers.get(i);
            }
        }
        return null;
    }


}
