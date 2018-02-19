package response;
import resource.*;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.DataOutputStream;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;

public abstract class Response{
  public int code;
  public String reasonPhrase;
  Resource resource;
  byte[] additionalHeaders = {};
  byte[] body = {};

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
    SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, dd MMMM YYYY HH:mm:ss");
    dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    String date = "Date: " + dateFormatter.format(Calendar.getInstance().getTime()) + " GMT\r\n";
    String server = "Server: ryan-alvin-web-server\r\n";
    return (date + server).getBytes();
  }

  private byte[] getStatusLine(){

     String statusLine = "HTTP/1.1 " + code + " " + reasonPhrase +"\r\n";
     return statusLine.getBytes();
  }
}
