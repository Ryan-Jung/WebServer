import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class WebServer{

  MimeTypes mimes;
  HttpdConfig configFile;
  Socket client;

  public void start(){
      configFile = new HttpdConfig("conf/httpd.conf");
      mimes = new MimeTypes("conf/mime.types");
      int counter = 0;
      mimes.load();
      configFile.load();
      try(
        ServerSocket  serverSocket = new ServerSocket(8080);
      ){
        while(true){
              client = serverSocket.accept();
              System.out.println("We should see this once");
              Worker worker = new Worker(client, mimes, configFile);
              worker.run();
              counter++;
              System.out.println("COUNTER: " + counter);
              client.close();
          }
       }catch(IOException e){
         e.printStackTrace();
         System.out.println("Closing Server");
       }


  }

}
