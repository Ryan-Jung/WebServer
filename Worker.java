import filereaders.*;
import response.*;
import resource.*;
import request.*;
import java.net.Socket;
import java.io.IOException;
import java.io.File;

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
        createRequestAndSendResponse();
      }catch(IOException e){
        try{
          Response500 internalError  = new Response500(null);
          internalError.send(client.getOutputStream());
          client.close();
        }catch(IOException ioe){}
      }
    }


    private void createRequestAndSendResponse() throws IOException{
      Request request = new Request(client.getInputStream());
      //request.test();
      Resource requestResource = new Resource(config, request.getUri());
      Response response = responseFactory.getResponse(request,requestResource);
      response.send(client.getOutputStream());
      client.close();
    }
}
