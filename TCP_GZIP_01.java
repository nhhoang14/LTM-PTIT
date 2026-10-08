import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class TCP_GZIP_01 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2210);

        GZIPOutputStream gos = new GZIPOutputStream(socket.getOutputStream());
        gos.write("B23DCCN336;7Ea560PZ\n".getBytes());
        gos.finish();

        GZIPInputStream gis = new GZIPInputStream(socket.getInputStream());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b;
        while ((b = gis.read()) != -1 && b != '\n'){
            baos.write(b);
        }
        String str = baos.toString();

        String reversed = new StringBuilder(str).reverse().toString();
        String base64 = Base64.getEncoder().encodeToString(reversed.getBytes());

        gos = new GZIPOutputStream(socket.getOutputStream());
        gos.write((reversed+"|"+base64+"\n").getBytes());
        gos.finish();

        socket.close();
    }
}