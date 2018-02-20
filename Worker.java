import filereaders.*;
import response.*;
import resource.*;
import request.*;
import java.net.Socket;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Calendar;

public class Worker implements Runnable{
    private Socket client;
    private MimeTypes mimes;
    private HttpdConfig config;
    ResponseFactory responseFactory;

    Worker(Socket client, MimeTypes mimes, HttpdConfig config){
      this.client = client;
      this.mimes = mimes;
      this.config = config;
      this.responseFactory = new ResponseFactory(config,mimes);
    }

    @Override
    public void run(){
      try{
        Request request = new Request(client.getInputStream());
        //request.test();
        Resource requestResource = new Resource(config, request.getUri());
        Response response;
        response = responseFactory.getResponse(request,requestResource);
        response.send(client.getOutputStream());
        String log = createLogAndPrint(request,requestResource,response);
        writeToLogFile(log);
        client.close();
      }catch(IOException e){
        try{
          Response500 internalError  = new Response500(null);
          internalError.send(client.getOutputStream());
          client.close();
        }catch(IOException ioe){}
      }
    }


    private synchronized void writeToLogFile(String log) throws IOException{
      String logFile = config.getConfigValue("LogFile");
      File file = new File (logFile);
      file.createNewFile();
      FileWriter fileWriter = new FileWriter(file,true);
      fileWriter.write(System.lineSeparator());
      fileWriter.write(log);
      fileWriter.close();
    }


    private String createLogAndPrint(Request request, Resource resource, Response response){
        String ipAddress = client.getRemoteSocketAddress().toString();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ssZ]");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        String timeOfRequest = dateFormatter.format(Calendar.getInstance().getTime());

        String requestLine = "\"" + request.getVerb() + " " + request.getUri() + " "
                            + request.getHTTPVersion() + "\"";

        String objectSize = request.getBody().length > 0 ? Integer.toString(request.getBody().length) : "-";

        String log = ipAddress + " - " + timeOfRequest + " " + requestLine + " " + response.code +
                    " " + objectSize;
        System.out.println(log);
        return log;
    }
}
