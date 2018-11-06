import java.io.*;
import java.util.*;
import java.util.logging.Logger;

class PhoneBook {
    private HashMap<String, ArrayList<String>> phoneList = new HashMap<>();
    private static Logger logger = Logger.getLogger(PhoneBook.class.getName());

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

    synchronized void readFile() {
        try {
            String path = "/home/ikigai/IdeaProjects/FirstServletJava/phonelist.txt";
            File list = new File(path);
            Scanner sc = new Scanner(list);

            while (sc.hasNextLine()) {
                String[] tmp = sc.nextLine().replaceAll("[+,]", "").split(" ");
                StringBuilder name = new StringBuilder();
                int i = 0;
                for (; i < tmp.length; i++) {
                    if (tmp[i].contains(":")) {
                        name.append(tmp[i].replaceAll(":", ""));
                        i++;
                        break;
                    }
                    name.append(" ");
                    name.append(tmp[i]);
                }
                for (; i < tmp.length; i++) {
                    this.addNamePhone(name.toString(), tmp[i]);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            logger.info(e.toString());
        }
    }

    synchronized void writeFile() {
        try {
            String path = "/home/ikigai/IdeaProjects/FirstServletJava/phonelist.txt";
            FileWriter fstream = new FileWriter(path);
            BufferedWriter out = new BufferedWriter(fstream);

            for (Map.Entry<String, ArrayList<String>> pairs : phoneList.entrySet()) {

                out.write(pairs.getKey() + ": ");
                ArrayList<String> value = pairs.getValue();
                out.write(value.get(0));
                for (int i = 1; i < value.size(); i++) {
                    out.write(", " + value.get(i));
                }
                out.write("\n");

            }
            out.close();
        } catch (IOException e) {
            logger.info(e.toString());
        }
    }

}
