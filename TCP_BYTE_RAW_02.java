import java.net.*;
import java.io.*;
import java.util.*;

public class TCP_BYTE_RAW_02 {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242", 2208);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        socket.setSoTimeout(5000);
        //a
        String msv = "B23DCCN336;3Q4lnfAx";
        out.write(msv.getBytes());
        out.write("\n".getBytes());
        out.flush();
        //b
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        String data = new String(buffer, 0, len).trim();
        //c
        StringBuilder res = new StringBuilder();
        if(!data.isEmpty()){
            int[] a = Arrays.stream(data.split(","))
                    .mapToInt(s -> Integer.parseInt(s.trim()))
                    .toArray();
            int max = Arrays.stream(a).max().getAsInt();
            int nd = a[0];
            int pos = 0;
            for(int i = 1; i < a.length; i++){
                if(a[i] > nd && a[i] < max){
                    nd = a[i];
                    pos = i;
                }
            }
            String tmp = nd + "," + pos;
            res.append(tmp);
        }
        out.write(res.toString().getBytes());
        out.write("\n".getBytes());
        out.flush();
        //d
        socket.close();
    }
}
