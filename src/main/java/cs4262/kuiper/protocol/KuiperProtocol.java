package cs4262.kuiper.protocol;

import java.io.IOException;

/**
 * Created by pubudu on 12/25/16.
 */
public interface KuiperProtocol {
    String register(String username) throws IOException;
    String unregister();
    String join();
    String leave();
    String search(String filename, int port);
}
