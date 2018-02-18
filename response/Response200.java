package response;
import resource.Resource;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Response200 extends Response{

    private byte[] additionalHeaders = {};
    private byte[] body = {};


    Response200(Resource resource){
        super(resource);
        this.code = 200;
        this.reasonPhrase = "OK";
    }

    @Override
    public void send(OutputStream outputStream) throws IOException{
      super.send(outputStream);
      addFile();
      write(additionalHeaders,outputStream);
      write("\r\n".getBytes(),outputStream);
      write(body,outputStream);
    }



    public void addFile() throws IOException{
      Path filePath = Paths.get(resource.getAbsolutePath());
      byte[] file = Files.readAllBytes(filePath);
      //String contentLength = "Content-Length: " + file.length + "\r\n";
      //String contentType = "Content-Type: text/html\r\n";
      //additionalHeaders = (contentLength + contentType).getBytes();
      //body = file;
    }



}
