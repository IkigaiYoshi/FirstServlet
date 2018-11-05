import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class HttpURLConnectionGet extends Thread {
    private String address;
    private boolean stopConnect = false;
    private static Logger logger = Logger.getLogger(HttpURLConnectionGet.class.getName());

    HttpURLConnectionGet(String address) {
        this.address = address;
    }

    void setStopConnect() {
        this.stopConnect = true;
    }

    @Override
    public void run() {
        try {
            while (!stopConnect) {
                URL url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                connection.setRequestProperty("User-Agent", "Mozilla/5.0");


                int responseCode = connection.getResponseCode();
                logger.info("\nSending 'GET' request to URL : " + url);
                logger.info("Response Code : " + responseCode);

                Thread.sleep(5000);
            }

        } catch (IOException | InterruptedException e) {
            logger.info(e.toString());
            Thread.currentThread().interrupt();
        }
    }
}
