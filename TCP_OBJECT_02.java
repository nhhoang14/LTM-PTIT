package TCP;

import java.io.*;
import java.net.*;

class Customer implements Serializable {
    private static final long serialVersionUID = 20170711L;

    private int id;
    private String code;
    private String name;
    private String dayOfBirth;
    private String userName;

    public Customer(int id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDayOfBirth() { return dayOfBirth; }
    public void setDayOfBirth(String dayOfBirth) { this.dayOfBirth = dayOfBirth; }
    public void setUserName(String userName) { this.userName = userName; }
}

public class TCP_OBJECT_02 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2209);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        socket.setSoTimeout(5000);

        // a
        String msv = "B23DCCN336;u8lpCLEe";
        out.writeObject(msv);
        out.flush();

        // b
        Customer customer = (Customer) in.readObject();

        // c
        String[] nameParts = customer.getName().trim().split("\\s+");
        StringBuilder res = new StringBuilder();
        String username = "";
        res.append(nameParts[nameParts.length - 1].toUpperCase()).append(", ");
        for(int i = 0; i < nameParts.length - 1; i++){
            String word = nameParts[i];
            username += nameParts[i].charAt(0);
            String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
            res.append(capitalizedWord).append(" ");
        }

        username += nameParts[nameParts.length - 1];
        customer.setUserName(username.toLowerCase());
        customer.setName(res.toString().trim());
        String[] parts = customer.getDayOfBirth().trim().split("-");
        String dob = parts[1] + "/" + parts[0] + "/" + parts[2];
        customer.setDayOfBirth(dob);

        out.writeObject(customer);
        out.flush();

        // d
        socket.close();
    }
}