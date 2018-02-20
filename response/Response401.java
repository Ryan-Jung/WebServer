package response;
import resource.Resource;
import java.io.OutputStream;
import java.io.IOException;

public class Response401 extends Response {
  Response401(Resource resource){
    super(resource);
    this.code = 401;
    this.reasonPhrase = "Unauthorized";
  }

  public void send(OutputStream outputStream) throws IOException{
    super.send(outputStream);
    write("WWW-Authenticate: Basic realm=\"stuff\",charest=\"UTF-8\"".getBytes(),outputStream);
  }
}
