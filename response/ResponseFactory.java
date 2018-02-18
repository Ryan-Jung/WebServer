package response;
import request.*;
import resource.*;
import filereaders.*;
import java.io.File;
import java.io.IOException;

public class ResponseFactory{
    private Resource requestResource;
    private HttpdConfig config;

    public ResponseFactory(HttpdConfig configFile) {
      config = configFile;
    }

    public Response getResponse(Request request, Resource requestResource) throws IOException{
      this.requestResource = requestResource;
      if(request.isValidRequest()){
        return completeRequest(request, requestResource);
      }else{
        return new Response400(requestResource);
      }
    }


    private Response completeRequest(Request request, Resource resource) throws IOException{
        Response response = null;
        if( resource.isProtected() ) {
          if( !hasAuthHeaders( request) ) {
            return new Response401(resource);
          } else {
            if( !checkAuthorization(request) ) {
              return new Response403(resource);
            }
          }
        }
        switch (request.getVerb()){
          case "GET": response = completeGetRequest();
                      break;
          case "PUT": response = completePutRequest();
                      break;
          case "HEAD": response = completeHeadRequest();
                      break;
          case "POST": response = completePostRequest();
                      break;
          case "DELETE": response = completeDeleteRequest();
                      break;
        }
        return response;
    }

    private boolean fileExists(){
      File fileRequested = new File(requestResource.getAbsolutePath());
      return fileRequested.exists();
    }



    private Response completeGetRequest(){
      if(fileExists()){
        Response200 response200 = new Response200(requestResource);
        return response200;
      }else{
        return new Response400(requestResource);
      }
    }


    private Response completePutRequest(){

      return null;
    }


    private Response completeHeadRequest(){

      return null;
    }


    private Response completePostRequest(){

      return null;
    }


    private Response completeDeleteRequest(){

      return null;
    }



    private boolean hasAuthHeaders(Request request) {
      if( request.containsHeader("WWW-Authenticate")
      &&  request.containsHeader("Authorization")  ) {
        return true;
      }
      return false;
    }

    private boolean checkAuthorization(Request request) throws IOException{
      String accessFileName = ".htaccess";
      if(config.getConfigValue("AccessFileName") != null){
        accessFileName = config.getConfigValue("AccessFileName");
      }
      Htaccess htaccess = new Htaccess(requestResource.getDirectory() + accessFileName);
      Htpassword htpass =  htaccess.createPasswordFile();
      String authInfo = request.getHeaderValue("Authorization");

      return htpass.isAuthorized(authInfo);
    }

}
