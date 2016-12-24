package cs4262.kuiper;

import cs4262.kuiper.protocol.KuiperProtocolImpl;

import java.io.IOException;

/**
 * Created by pubudu on 12/24/16.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        KuiperProtocolImpl kp = new KuiperProtocolImpl("172.17.0.2", 55555);
        String response = kp.register("pubudu91");

        System.out.println(response + "|");
    }
}
