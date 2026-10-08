import java.io.*;
import java.util.*;
import java.net.*;

public class TCP_CHARACTER_02 {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242", 2208);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        socket.setSoTimeout(5000);
        //a
        String msv = "B23DCCN336;Wcw1y9Mq";
        out.write(msv);
        out.newLine();
        out.flush();
        //b
        String data = in.readLine();
        //c
        StringBuilder res = new StringBuilder();
        if(!data.isEmpty()){
            Map<Character, Integer> map = new LinkedHashMap<>();
            for(char c : data.toCharArray()){
                if(Character.isLetterOrDigit(c)){
                    map.put(c, map.getOrDefault(c, 0) + 1);
                }
            }
            for (Map.Entry<Character, Integer> entry : map.entrySet()) {
                if (entry.getValue() > 1) {
                    res.append(entry.getKey())
                            .append(":")
                            .append(entry.getValue())
                            .append(",");
                }
            }
        }
        out.write(res.toString());
        out.newLine();
        out.flush();
        //d
        socket.close();
    }
}
