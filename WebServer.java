import filereaders.*;
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
      try(
        ServerSocket  serverSocket = new ServerSocket(getPortNumber());
      ){
        while(true){
              client = serverSocket.accept();
              Worker worker = new Worker(client, mimes, configFile);
              worker.run();
          }
       }catch(IOException e){
         e.printStackTrace();
         System.out.println("Closing Server");
       }


  }
  private int getPortNumber(){
    int DEFAULT_PORT_NUMBER = 8080;
    if(configFile.getConfigValue("Listen") != null){
      String portNum = configFile.getConfigValue("Listen");
      return Integer.parseInt(portNum);
    }else{
      return DEFAULT_PORT_NUMBER;
    }
  }

}
