package cs4262.kuiper.protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.util.Random;

import static cs4262.kuiper.protocol.KuiperCommand.REG;

/**
 * Created by pubudu on 12/25/16.
 */
public class KuiperProtocolImpl implements KuiperProtocol {
    private static InetAddress bsIp;
    private static int bsPort;
    private InetAddress ip;
    private int port;

    public KuiperProtocolImpl(String ip, int port) {
        String t[] = ip.split(".");
        byte[] ipParts = new byte[4];

        for (int i = 0; i < t.length; i++)
            ipParts[i] = Byte.parseByte(t[i]);

        try {
            bsIp = InetAddress.getByAddress(ipParts);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        bsPort = port;
    }

    public String register(String username) throws IOException {
        StringBuffer command = new StringBuffer(REG);
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }

        Random rnd = new Random();
        port = rnd.nextInt(64535) + 1000;

        command.append(" ");
        command.append(ip.getHostAddress());
        command.append(" ");
        command.append(Integer.toString(port));
        command.append(" ");
        command.append(username);

        String request = command.toString();
        request = String.format("%1$04d ", request.length() + 5) + request;
        System.out.println(request + ": " + request.length());

        DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(port));

        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.clear();
        buf.put(request.getBytes());
        buf.flip();

        channel.send(buf, new InetSocketAddress(bsIp, bsPort));

        buf.clear();

        channel.receive(buf);

        byte b[] = new byte[buf.capacity() - buf.remaining()];
        buf.flip();
        buf.get(b);
        String response = new String(b, Charset.forName("UTF-8"));
        System.out.println(response.length());

        return response;
    }

    public String unregister() {
        return null;
    }

    public String join() {
        return null;
    }

    public String leave() {
        return null;
    }

    public String search(String filename, int port) {
        return null;
    }
}
