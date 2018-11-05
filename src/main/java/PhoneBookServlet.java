
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;


public class PhoneBookServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(HelloServlet.class.getName());
    private static PhoneBook phoneBook = new PhoneBook();

    @Override
    public void init(ServletConfig config) {
        try {
            File list = new File("/home/ikigai/IdeaProjects/FirstServlet/list.txt");
            String in;
            Scanner sc = new Scanner(list);
            while (sc.hasNextLine()) {
                in = sc.nextLine();
                String[] tmp = in.split(" ");
                StringBuilder name = new StringBuilder(tmp[0]);
                for (int i = 1; i < tmp.length - 1; i++) {
                    name.append(" ");
                    name.append(tmp[i]);
                }
                for (int i = 0; i < Integer.parseInt(tmp[tmp.length - 1]); i++) {
                    phoneBook.addNamePhone(name.toString(), sc.nextLine());
                }
            }
            sc.close();
        } catch (IOException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<html>\n<body>\n");
            if (request.getParameterMap().size() != 0) {
                String parameter2 = "phone";
                if (isDigitsOnly(request.getParameter(parameter2))) {
                    String parameter1 = "name";
                    if (!phoneBook.containName(request.getParameter(parameter1))) {
                        if (!(request.getParameter(parameter1).equals("")))
                            phoneBook.addNamePhone(request.getParameter(parameter1), request.getParameter(parameter2));
                    } else {
                        if (!(phoneBook.containPhone(request.getParameter(parameter1), request.getParameter(parameter2)))) {
                            if (request.getParameter(parameter2).equals(""))
                                out.println("<h2>This user was already add</h2>\n");
                            else
                                phoneBook.addNamePhone(request.getParameter(parameter1), request.getParameter(parameter2));
                        } else
                            out.println("<h2>This phone was already add in the list</h2>\n");
                    }
                } else
                    out.println("<h2>The phone must contain only numbers</h2>\n");
            }
            out.println(getMainPage());
            out.println("</body>\n</html>");
        } catch (IOException e) {
            logger.info(e.toString());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<html>\n<body>\n");
            out.println(getMainPage());
            out.println("</body>\n</html>");
        } catch (IOException e) {
            logger.info(e.toString());
        }
    }

    private String getMainPage() {
        StringBuilder sb = new StringBuilder();

        sb.append("<form method=\"POST\" action=\"/phonebook\">\n")
                .append("Name: <input type=\"text\" name=\"name\"><br><br>\n")
                .append("Phone: <input type=\"text\" name=\"phone\"><br><br>\n")
                .append("<input type=\"submit\" value=\"Add\">\n")
                .append("</form>");

        HashMap<String, ArrayList<String>> tmp = phoneBook.getPhoneBook();
        for (HashMap.Entry<String, ArrayList<String>> hashMap : tmp.entrySet()) {
            sb.append("<p>").append(hashMap.getKey()).append(": ");
            String prefix = "";
            for (String p : hashMap.getValue()) {
                sb.append(prefix).append("+").append(p);
                prefix = ", ";
            }
            sb.append(".").append("</p>");
        }

        return sb.toString();
    }

    private boolean isDigitsOnly(String str) {
        for (int i = 0; i < str.length(); i++) {
            if ((str.charAt(i) < '0') || (str.charAt(i) > '9')) {
                return false;
            }
        }
        return true;
    }
}
