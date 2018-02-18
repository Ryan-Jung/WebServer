import filereaders.*;
import response.*;
import resource.*;
import request.*;
import java.net.Socket;
import java.io.IOException;
import java.io.File;

public class Worker{
    private Socket client;
    private MimeTypes mimes;
    private HttpdConfig config;

    Worker(Socket client, MimeTypes mimes, HttpdConfig config){
      this.client = client;
      this.mimes = mimes;
      this.config = config;
    }
    private void printLineBreak() {
      System.out.println("----------------------------------------");
    }

    public void run() throws IOException{
      ResponseFactory responseFactory = new ResponseFactory(config);
      Request request = new Request(client.getInputStream());
      //request.test();
      Resource requestResource = new Resource(config, request.getUri());
      Response response = responseFactory.getResponse(request,requestResource);
      response.send(client.getOutputStream());
      client.close();
    }
}
