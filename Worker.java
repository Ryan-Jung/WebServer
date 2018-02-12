import java.net.Socket;
import java.io.IOException;
public class Worker{

    private Socket client;
    private MimeTypes mimes;
    private HttpdConfig config;

    Worker(Socket client, MimeTypes mimes, HttpdConfig config){
      this.client = client;
      this.mimes = mimes;
      this.config = config;
    }

    private void printLineBreak() {
      System.out.println("----------------------------------------");
    }

    public void run(){
      ResponseFactory response = new ResponseFactory();
      try{
        Request request = new Request(client.getInputStream());
        Resource requestResource = new Resource(config, request.getUri());
        //request.test();
      }catch(IOException e){
        e.printStackTrace();
      }

    }
}
