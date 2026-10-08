import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCP_GZIP_02 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2210);
        GZIPOutputStream out = new GZIPOutputStream(socket.getOutputStream());
        out.write("B23DCCN336;9yEU24QF\n".getBytes());
        out.finish();

        GZIPInputStream in = new GZIPInputStream(socket.getInputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = in.read()) != -1 && b != '\n'){
            baos.write(b);
        }
        String s = baos.toString();
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String res = new String(chars);

        out = new GZIPOutputStream(socket.getOutputStream());
        out.write((res+"\n").getBytes());
        out.finish();

        socket.close();
    }
}
