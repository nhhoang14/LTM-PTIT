import java.io.*;
import java.util.*;
import java.net.*;

public class TCP_DATA_02 {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242", 2208);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        socket.setSoTimeout(5000);
        //a
        String msv = "B23DCCN336;2kTOTgfx";
        out.writeUTF(msv);
        out.writeUTF("\n");
        out.flush();
        //b
        String a = in.readUTF();
        int s = in.readInt();
        //c
        StringBuilder res = new StringBuilder();
        for(char c : a.toCharArray()){
            if (c >= 'A' && c <= 'Z') {
                c = (char) ('A' + (c - 'A' - s + 26) % 26);
            }
            else if (c >= 'a' && c <= 'z') {
                c = (char) ('a' + (c - 'a' - s + 26) % 26);
            }
            res.append(c);
        }
        out.writeUTF(res.toString());
        out.writeUTF("\n");
        out.flush();
        //d
        socket.close();
    }
}
