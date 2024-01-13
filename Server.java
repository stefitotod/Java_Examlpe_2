import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    
    static List<Regex> file = new ArrayList<>();
    public static void main(String[] args) throws IOException{
       
        file.add(new Regex("sdfdsf", "jdfskjdsk"));
        file.add(new Regex("SDKJKJSDKFJSDK", "jkskdfjsdk")); 
        
        try(
            ServerSocket server = new ServerSocket(1212);       
        ) {
            while(true) {
                Socket socket = server.accept();
                Thread thread = new RegexTester(socket);
                thread.start();
            }
        }
    }
    
}
