import java.util.*;
import java.io.*;
import java.net.*;

public class TCP_CharacterStream {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242", 2208);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        socket.setSoTimeout(5000);
        //a
        String msv = "B23DCCN336;SAM3uAw6";
        out.write(msv);
        out.newLine();
        out.flush();
        //b
        String data = in.readLine();
        //c
        StringBuilder result = new StringBuilder();
        if (data != null && !data.isEmpty()) {
            String[] dataArray = data.split(",");
            for (String s : dataArray) {
                s = s.trim();
                if (s.endsWith(".edu")) {
                    if (result.length() > 0) {
                        result.append(", ");
                    }
                    result.append(s);
                }
            }
        }
        out.write(result.toString());
        out.newLine();
        out.flush();
        //d
        socket.close();
    }
}