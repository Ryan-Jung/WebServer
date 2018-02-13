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
      Resource requestResource = new Resource(config, request.getUri());
      responseFactory.getResponse(request,requestResource);
      if(htaccessExists()){
        Htaccess htaccess = new Htaccess(config.getConfigValue("AccessFileName"));
        Htpassword htpaswd = htaccess.getAuthUserFile();
        String authHeader = config.getConfigValue("Authorization");
        htpaswd.isAuthorized(authHeader);
      }
    }


    private boolean htaccessExists(){
      File htaccessFile = new File(config.getConfigValue("AccessFileName"));
      return htaccessFile.exists();
    }

    private boolean hasAuthorization(){
      return false;
    }
}
