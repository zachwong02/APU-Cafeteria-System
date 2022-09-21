package Restaurant;

import javax.swing.*;
import java.awt.*;

public abstract class Validation {

    public static boolean isPhone(String phone){
        if(phone.startsWith("01") && phone.matches("[0-9]+")){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isEmail(String email){
        if(email.contains("@")){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isPassword(String password){
        try{
            Integer.parseInt(password);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isPrice(String price){
        try{
            Double.parseDouble(price);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isAllFilled(String ...textFields){
        boolean isFilled = true;
        for (int i = 0; i < textFields.length; i++) {
            if (textFields[i].isBlank()){
                isFilled = false;
                break;
            }
        }
        return isFilled;
    }

    public static boolean nameExists(String nameFields){
        boolean exists = false;

        for (int i = 0; i < Customer.customers.size(); i++) {
            String currentName = Customer.customers.get(i).getName();
            if (currentName.equals(nameFields)){
                exists = true;
                break;
            }
        }
        return exists;
    }


    public static boolean managerExists(String nameFields){
        boolean exists = false;

        for (int i = 0; i < Manager.managers.size(); i++) {
            String currentName = Manager.managers.get(i).getName();
            if (currentName.equals(nameFields)){
                exists = true;
                break;
            }
        }
        return exists;
    }
}
