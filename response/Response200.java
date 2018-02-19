package response;
import resource.Resource;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;
import java.util.TimeZone;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import filereaders.MimeTypes;

public class Response200 extends Response{

    Response200(Resource resource){
        super(resource);
        this.code = 200;
        this.reasonPhrase = "OK";
    }


    @Override
    public void send(OutputStream outputStream) throws IOException{
      super.send(outputStream);
      write(additionalHeaders,outputStream);
      write("\r\n".getBytes(),outputStream);
      write(body,outputStream);
    }



    public void addResource() throws IOException{
      this.body = fileResourceToBytes();
    }


    public void addContentHeaders(MimeTypes mimes) throws IOException{
      byte[] file = fileResourceToBytes();

      int indexOfExtension = resource.getAbsolutePath().indexOf('.') + 1;
      String extension = resource.getAbsolutePath().substring(indexOfExtension);

      String contentType = "text/text";
      if(!mimes.lookUp(extension).equals("")){
        contentType = mimes.lookUp(extension);
      };

      String contentLengthHeader = "Content-Length: " + file.length + "\r\n";
      String contentTypeHeader = "Content-Type: " + contentType + "\r\n";
      byte[] contentHeaders = (contentLengthHeader + contentTypeHeader).getBytes();

      addToHeaders(contentHeaders);
    }


    public Date addLastModifiedHeader() throws IOException{
      File resourceFile = new File(resource.getAbsolutePath());

      SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm:ss");
      dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));

      Date lastModifiedDate = new Date(resourceFile.lastModified());
      byte[] lastModifiedHeader = ("Last-Modifed: " + dateFormatter.format(lastModifiedDate) + " GMT\r\n").getBytes();

      addToHeaders(lastModifiedHeader);
      return lastModifiedDate;
    }


    private void addToHeaders(byte[] headerToAdd){
      int newLength = additionalHeaders.length + headerToAdd.length;
      byte [] newHeaderArray = new byte[newLength];

      System.arraycopy(additionalHeaders, 0, newHeaderArray, 0, additionalHeaders.length);
      System.arraycopy(headerToAdd, 0, newHeaderArray, additionalHeaders.length, headerToAdd.length);

      this.additionalHeaders = newHeaderArray;
    }


    private byte[] fileResourceToBytes() throws IOException {
      Path filePath = Paths.get(resource.getAbsolutePath());
      return Files.readAllBytes(filePath);
    }
}
