import java.io.*;
import java.util.*;
import java.net.*;

public class TCP_DATA_01 {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242", 2208);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        socket.setSoTimeout(5000);
        //a
        String msv = "B23DCCN336;BTPuzCJ6";
        out.write(msv.getBytes());
        out.write("\n".getBytes());
        out.flush();
        //b
        int a = in.readInt();
        int b = in.readInt();
        //c
        int sum = a + b;
        int product = a * b;
        out.writeInt(sum);
        out.writeInt(product);
        out.flush();
        //d
        socket.close();
    }
}
