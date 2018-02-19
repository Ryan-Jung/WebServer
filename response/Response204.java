package response;

import resource.Resource;
import java.io.IOException;
import java.io.OutputStream;

public class Response204 extends Response{
  Response204(Resource resource){
    super(resource);
    this.code = 204;
    this.reasonPhrase = "No Content";
  }

  @Override
  public void send(OutputStream outputStream) throws IOException{
    super.send(outputStream);
    String contentLocation = "Content-Location: " + resource.getAbsolutePath();
    addToHeaders(contentLocation.getBytes());
    write(additionalHeaders, outputStream);
  }

}
