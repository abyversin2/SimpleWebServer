import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("WeakerAccess")
public class WebServer {

    private final int port;

    public static WebServer create(int port){
        return new WebServer(port);
    }

    private WebServer(int port){
        this.port = port;
    }

    public void start() throws IOException {

        final ServerSocket serverSocket = new ServerSocket(this.port);

        while (true) {
            final Socket clientSocket = serverSocket.accept();
            InputStream clientSocketInputStream = clientSocket.getInputStream();

            new Thread(() -> {
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(clientSocketInputStream);
                    BufferedReader bufferReader = new BufferedReader(inputStreamReader);


                    String request = null;
                    String line = null;
                    while((line = bufferReader.readLine()) != null && !line.isEmpty()){
                        if(line.contains("GET")) {
                            String[] data = line.split(" ");
                            request = data[1];
                        }
                    }
                    if(request != null) {
                        if (request.equals("/")) {
                            HttpImageSender.create(clientSocket.getOutputStream()).sendMethodIMGSRC();
                        } else {
                            HttpImageSender.create(clientSocket.getOutputStream()).sendMehodStream();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();

        }

    }
}
