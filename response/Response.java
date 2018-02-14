package response;
import resource.*;
import java.io.OutputStream;
public abstract class Response{
  public int code;
  public String reasonPhrase;
  Resource resource;

  public Response(Resource resource){
    this.resource = resource;
  }

  public void send(OutputStream output){
      byte[] statusLine = generateStatusLine();
      output.write(statusLine);
  }

  private byte[] generateStatusLine(){
     String statusLine = "HTTP/1.1 " + code + " " + reasonPhrase + "/r/n";
     return statusLine.getBytes();
  }
}
