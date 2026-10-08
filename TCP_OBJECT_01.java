package TCP;

import java.io.*;
import java.net.*;

class Laptop implements Serializable {
    private static final long serialVersionUID = 20150711L;

    private int id;
    private String code;
    private String name;
    private int quantity;

    public Laptop(int id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

public class TCP_OBJECT_01 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2209);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        socket.setSoTimeout(5000);

        // a
        String msv = "B23DCCN336;tJ8u6Khz";
        out.writeObject(msv);
        out.flush();

        // b
        Laptop laptop = (Laptop) in.readObject();

        // c
        String[] nameParts = laptop.getName().trim().split("\\s+");
        if (nameParts.length > 1) {
            String temp = nameParts[0];
            nameParts[0] = nameParts[nameParts.length - 1];
            nameParts[nameParts.length - 1] = temp;
            laptop.setName(String.join(" ", nameParts));
        }

        String qtyStr = String.valueOf(laptop.getQuantity());
        String reversedQty = new StringBuilder(qtyStr).reverse().toString();
        laptop.setQuantity(Integer.parseInt(reversedQty));

        out.writeObject(laptop);
        out.flush();

        // d
        socket.close();
    }
}