package util;

import java.util.ArrayList;
import java.util.List;

public class LoanMigrationsUtils {

    public static String getExpectedGender(String actualGender) {

        switch (actualGender) {
            case "Male":
                return "MALE";
            case "MALE":
                return "MALE";
            case "Female":
                return "FEMALE";
            case "FEMALE":
                return "FEMALE";
            case "Other":
                return "THIRD GENDER";
            case "OTHER":
                return "THIRD GENDER";
            default:
                return null;
        }
    }

    public static List<String> returnFirstLastNames(String fullName) {
        List<String> name = new ArrayList<String>();
        String[] words = fullName.split("\\P{Alnum}+");
        switch (words.length) {
            case 0:
                name.add(null);
                name.add(null);
            case 1:
                name.add(words[0]);
                name.add(null);
            default:
                name.add(words[0]);
                name.add(words[words.length - 1]);
        }
        /*try {
            //name.add(fullName.split("\\P{Alnum}+")[0]);
            name.add(fullName.split("\\P{Alnum}+")[0]);
        }
        catch(ArrayIndexOutOfBoundsException Exception)
        {
            Exception.printStackTrace();
            System.out.println(Exception.getMessage());
            //name.add(null);
            name.add(null);

        }
        try {
            //name.add(fullName.split("\\P{Alnum}+")[1]);
            name.add(fullName.split("\\P{Alnum}+")[1]);

        }
        catch(ArrayIndexOutOfBoundsException Exception)
        {
            Exception.printStackTrace();
            System.out.println(Exception.getMessage());
            //name.add(null);
            name.add(null);

        }*/
        return name;
    }

    public static String determineSalutation(String gender, String maritalStatus) {
        String salutation = null;
        try {
            try {
                if (gender.equalsIgnoreCase("MALE")) {
                    salutation = "MR";
                }
            } catch (Exception e) {
                return null;
            }
            if (gender.equalsIgnoreCase("FEMALE") || maritalStatus.equalsIgnoreCase("MARRIED")) {
                salutation = "MRS";
            } else if (gender.equalsIgnoreCase("FEMALE") || maritalStatus.equalsIgnoreCase("UNMARRIED")) {
                salutation = "MS";
            } else if (gender.equalsIgnoreCase("THIRD GENDER") || maritalStatus.equalsIgnoreCase("MARRIED")) {
                salutation = "MRS";
            } else if (gender.equalsIgnoreCase("THIRD GENDER") || maritalStatus.equalsIgnoreCase("UNMARRIED")) {
                salutation = "MS";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "MS";
        }
        return salutation;
    }
}
