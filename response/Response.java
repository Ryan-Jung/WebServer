package response;
import resource.*;
import java.io.OutputStream;
import java.io.IOException;

public abstract class Response{
  public int code;
  public String reasonPhrase;
  Resource resource;

  public Response(Resource resource){

    this.resource = resource;
  }

  public void send(OutputStream outputStream){

      byte[] statusLine = generateStatusLine();
      write(statusLine,outputStream);
      flushOutput(outputStream);
  }


  void write(byte[] bytesToWrite,OutputStream outputStream){

    try{
      outputStream.write(bytesToWrite);
    }catch(IOException e){}
  }

  void flushOutput(OutputStream outputStream){

    try{
      outputStream.flush();
    }catch(IOException e){}
  }


  private byte[] generateStatusLine(){

     String statusLine = "HTTP/1.1 " + code + " " + reasonPhrase + "/r/n";
     return statusLine.getBytes();
  }
}
