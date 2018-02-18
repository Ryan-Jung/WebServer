package response;
import resource.*;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.DataOutputStream;
import java.time.LocalDateTime;

public abstract class Response{
  public int code;
  public String reasonPhrase;
  Resource resource;

  public Response(Resource resource){
    this.resource = resource;
  }


  public void send(OutputStream outputStream) throws IOException{
      write(getStatusLine(),outputStream);
      write(getDateAndServer(),outputStream);
  }


  void write(byte[] bytesToWrite,OutputStream outputStream) throws IOException{
        for(int i = 0; i < bytesToWrite.length; i++){
          outputStream.write(bytesToWrite[i]);
        }
  }

  private byte[] getDateAndServer(){
    String date = "Date: " +LocalDateTime.now().toString() + "\r\n";
    String server = "Server: ryan-alvin-web-server\r\n";
    return (date + server).getBytes();
  }

  private byte[] getStatusLine(){

     String statusLine = "HTTP/1.1 " + code + " " + reasonPhrase +"\r\n";
     return statusLine.getBytes();
  }
}
