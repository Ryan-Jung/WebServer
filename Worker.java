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
      ResponseFactory responseFactory = new ResponseFactory();
      Request request = new Request(client.getInputStream());
      request.test();
      Resource requestResource = new Resource(config, request.getUri());
      //responseFactory.getResponse(request,requestResource);
      //if(htaccessExists() && hasAuthorization()){
      //}
    }


    private boolean htaccessExists(){
      File htaccessFile = new File(config.getConfigValue("AccessFileName"));
      return htaccessFile.exists();
    }

    private boolean hasAuthorization(){
      Htaccess htaccess = new Htaccess(config.getConfigValue("AccessFileName"));
      Htpassword htpaswd = htaccess.getAuthUserFile();
      String authInfo = config.getConfigValue("Authorization");

      String[] authTypeAndCredentials = authInfo.split("//s");
      String authType = authTypeAndCredentials[0];
      String authCredentials = authTypeAndCredentials[1];

      if(authType != htaccess.getAuthType()){
          return false;
      }
      return htpaswd.isAuthorized(authCredentials);
    }
}
