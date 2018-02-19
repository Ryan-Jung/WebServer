import filereaders.*;
import java.net.ServerSocket;
import java.net.Socket;


public class WebServer{

  MimeTypes mimes;
  HttpdConfig configFile;
  Socket client;

  public void start(){
      configFile = new HttpdConfig("conf/httpd.conf");
      mimes = new MimeTypes("conf/mime.types");
      int portNumber = getPortNumber();
      System.out.println("Starting server on port: " + portNumber);
      try(
        ServerSocket  serverSocket = new ServerSocket(portNumber);
      ){
        while(true){
              client = serverSocket.accept();
              Worker worker = new Worker(client, mimes, configFile);
              worker.run();
          }
       }catch(Exception e){
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
