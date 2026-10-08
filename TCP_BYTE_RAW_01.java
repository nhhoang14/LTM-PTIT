import java.io.*;
import java.net.*;
import java.util.Arrays;

public class TCP_BYTE_RAW_01 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        socket.setSoTimeout(5000);
        // a
        String msv = "B23DCCN336;aSDA88t2";
        out.write(msv.getBytes());
        out.write("\n".getBytes());
        out.flush();
        // b
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        String data = new String(buffer, 0, len).trim();
        // c
        StringBuilder res = new StringBuilder();
        if (!data.isEmpty()) {
            int[] a = Arrays.stream(data.split(","))
                    .mapToInt(s -> Integer.parseInt(s.trim()))
                    .sorted()
                    .toArray();
            int min = Integer.MAX_VALUE;
            int n1 = 0;
            int n2 = 0;
            for (int i = 0; i < a.length - 1; i++) {
                if (a[i + 1] - a[i] <= min) {
                    min = a[i + 1] - a[i];
                    n1 = a[i];
                    n2 = a[i + 1];
                }
            }
            String tmp = min + "," + n1 + "," + n2;
            res.append(tmp);
        }
        out.write(res.toString().getBytes());
        out.write("\n".getBytes());
        out.flush();
        // d
        socket.close();
    }
}