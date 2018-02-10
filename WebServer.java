import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class WebServer{

  MimeTypes mimes;
  HttpdConfig configFile;

  public void start(){
      configFile = new HttpdConfig("conf/httpd.conf");
      mimes = new MimeTypes("conf/mime.types");
      mimes.load();
      configFile.load();
      try(
        ServerSocket  serverSocket = new ServerSocket(8080);
      ){
        while(true){
              Socket client = serverSocket.accept();
              Worker worker = new Worker(client, mimes, configFile);
              worker.run();
          }
       }catch(IOException e){
         e.printStackTrace();
         System.out.println("Closing Server");
       }


  }

}
