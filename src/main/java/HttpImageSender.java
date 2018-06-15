import java.io.*;
import java.util.Date;

public class HttpImageSender {

    final private OutputStream outputStream;

    public static HttpImageSender create(OutputStream outputStream){
        return new HttpImageSender(outputStream);
    }

    private HttpImageSender(OutputStream outputStream){
        this.outputStream = outputStream;
    }

    public void sendMehodStream() throws IOException {
        InputStream inFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("skull.png");
        OutputStream out = new BufferedOutputStream(this.outputStream);
        PrintStream pout = new PrintStream(out);
        pout.print("HTTP/1.0 200 OK\r\n" +
                "Content-Type: " + "image/png" + "\r\n" +
                "Date: " + new Date() + "\r\n" +
                "Content-Length: " + inFile.available() + "\r\n" +
                "Server: FileServer 1.0\r\n\r\n");

        byte[] buffer = new byte[inFile.available()];
        while (inFile.available() > 0) {
            out.write(buffer, 0, inFile.read(buffer));
        }

        pout.flush();
        out.flush();
    }

    public void sendMethodIMGSRC() throws IOException {
        /*PrintWriter out = new PrintWriter(outputStream, true);
        String s = "<IMG SRC=\"/image\">";

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/html");
        out.println("Content-Length: " + s.getBytes().length);
        out.println("\r\n");
        out.println(s);

        out.close();*/

        String s = "<IMG SRC=\"/image\">";

        OutputStream out = new BufferedOutputStream(this.outputStream);
        PrintStream pout = new PrintStream(out);
        pout.print("HTTP/1.0 200 OK\r\n" +
                "Content-Type: " + "text/html" + "\r\n" +
                "Date: " + new Date() + "\r\n" +
                "Content-Length: " + s.getBytes().length + "\r\n" +
                "Server: FileServer 1.0\r\n\r\n");
        pout.flush();

        out.write(s.getBytes());

        pout.flush();
        out.flush();
        out.flush();

    }
}
