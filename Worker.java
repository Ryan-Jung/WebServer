import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    public void run(){
      try{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        if(bufferedReader.ready()){
          System.out.println(bufferedReader.readLine());
        }else{
          bufferedReader.close();
        }
      }catch(IOException e){
        e.printStackTrace();
      }
    }
}
