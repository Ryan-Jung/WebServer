import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class WebServer{
  public WebServer(){
    //load files
  }

  public static void main(String args[]){
    try{
      start();
    }catch(IOException e){
      e.printStackTrace();
      System.out.println("closing server");
    }
  }
  public static void start() throws IOException{
      try(
        ServerSocket  serverSocket = new ServerSocket(3002);
        Socket socket = serverSocket.accept();
      ){
        while(true){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if(bufferedReader.ready()){
              System.out.println(bufferedReader.readLine());
            }
          }
     }


  }

}
