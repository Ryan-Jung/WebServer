package response;
import resource.Resource;
import java.io.IOException;
import java.io.OutputStream;

public class Response201 extends Response{
    Response201(Resource resource){
      super(resource);
      this.code = 201;
      this.reasonPhrase = "Created";
    }

    @Override
    public void send(OutputStream outputStream) throws IOException{
      super.send(outputStream);
      String contentLocation = "Content-Location: " + resource.getAbsolutePath();
      addToHeaders(contentLocation.getBytes());
      write(additionalHeaders, outputStream);
    }
}
