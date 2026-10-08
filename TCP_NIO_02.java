import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TCP_NIO_02 {
    static void readFull(SocketChannel sc, ByteBuffer b) throws Exception {
        while (b.hasRemaining()) {
            sc.read(b);
        }
    }

    static String readFrame(SocketChannel sc) throws Exception {
        ByteBuffer b = ByteBuffer.allocate(4);
        readFull(sc, b);
        b.flip();

        int n = b.getInt();
        b = ByteBuffer.allocate(n);
        readFull(sc, b);
        return new String(b.array(), StandardCharsets.UTF_8);
    }

    static void sendFrame(SocketChannel sc, String str) throws Exception {
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        ByteBuffer b = ByteBuffer.allocate(4 + data.length);
        b.putInt(data.length);
        b.put(data);
        b.flip();

        while (b.hasRemaining()) {
            sc.write(b);
        }
    }

    public static void main(String[] args) throws Exception {
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("36.50.135.242", 2211));

        sendFrame(sc, "B23DCCN336;akt8Ga1f");

        String res = (readFrame(sc) + readFrame(sc)).replace("\r", "").replace("\n", "");

        String event = res.replaceAll(".*\"event\"\\s*:\\s*\"([^\"]+)\".*", "$1");
        String user = res.replaceAll(".*\"user\"\\s*:\\s*\"([^\"]+)\".*", "$1");
        String ok = res.replaceAll(".*\"ok\"\\s*:\\s*(true|false).*", "$1");

        String kq = "event=" + event + ";user=" + user + ";ok=" + (ok.equals("true") ? "1" : "0");

        sendFrame(sc, kq);

        sc.close();
    }
}