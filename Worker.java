import java.net.Socket;

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

      try{
        Request request = new Request(client.getInputStream());
        request.parse();
        request.test();
      }catch(IOException e){
        e.printStackTrace();
      }

    }
}
