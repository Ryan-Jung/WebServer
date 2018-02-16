package response;
import resource.*;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.DataOutputStream;

public abstract class Response{
  public int code;
  public String reasonPhrase;
  Resource resource;

  public Response(Resource resource){
    this.resource = resource;
  }


  public void send(OutputStream outputStream){

      String statusLine = generateStatusLine();
      write(statusLine.getBytes(),outputStream);
      String space = "\r\n";
      byte[] body =  "<html><p style = 'color:green'>Hello world</p></html>".getBytes();
      String headers = "Content-Length" + body.length + "\r\n";

      write(headers.getBytes(),outputStream);
      write(space.getBytes(),outputStream);
      write(body,outputStream);
  }


  void write(byte[] bytesToWrite,OutputStream outputStream) throws IOException{
        for(int i = 0; i < bytesToWrite.length; i++){
          outputStream.write(bytesToWrite[i]);
        }
  }


  private String generateStatusLine(){

     String statusLine = "HTTP/1.1 " + code + " " + reasonPhrase +"\r\n";
     return statusLine;
  }
}
