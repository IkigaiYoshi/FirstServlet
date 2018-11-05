import java.util.*;

class PhoneBook {
    private HashMap<String, ArrayList<String>> phoneList = new HashMap<>();

    PhoneBook() {

    }

    synchronized void addNamePhone(String name, String phone) {
        if (phoneList.containsKey(name)) {
            ArrayList<String> tmp = phoneList.get(name);
            if (!phone.equals(""))
                tmp.add(phone);
            phoneList.put(name, tmp);
        } else {
            ArrayList<String> tmp = new ArrayList<>();
            if (!phone.equals(""))
                tmp.add(phone);

            phoneList.put(name, tmp);
        }
    }

    HashMap<String, ArrayList<String>> getPhoneBook() {

        return phoneList;
    }

    boolean containName(String name) {
        return phoneList.containsKey(name);
    }

    boolean containPhone(String name, String phone) {
        if (containName(name))
            return phoneList.get(name).contains(phone);
        else
            return containName(name);
    }

}
