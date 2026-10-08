
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCP_NIO_01 {
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
        return new String(b.array());
    }

    static void sendFrame(SocketChannel sc, String str) throws Exception {
        byte[] data = str.getBytes();
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

        sendFrame(sc, "B23DCCN336;Wt1K1ODZ");

        String http = readFrame(sc) + readFrame(sc) + readFrame(sc);
        String[] lines = http.split("\r\n");
        String[] first = lines[0].split(" ");
        String host = "";

        for (String s : lines) {
            if (s.toLowerCase().startsWith("host:")) {
                host = s.substring(5).trim();
                break;
            }
        }

        sendFrame(sc, first[0] + ";" + first[1] + ";" + host);

        sc.close();
    }
}