package response;
import request.*;
import resource.*;
import filereaders.*;
import java.io.File;
import java.io.IOException;

public class ResponseFactory{
    private Resource requestResource;
    private HttpdConfig configFile;
    private MimeTypes mimeTypes;

    public ResponseFactory(HttpdConfig configFile,MimeTypes mimeTypes) {
      this.configFile = configFile;
      this.mimeTypes = mimeTypes;
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



    private Response completeGetRequest() throws IOException{
      if(fileExists()){
        Response200 response200 = new Response200(requestResource);
        response200.addResource();
        response200.addContentHeaders(mimeTypes);
        return response200;
      }else{
        return new Response404(requestResource);
      }
    }


    private Response completePutRequest() throws IOException{

      return null;
    }


    private Response completeHeadRequest() throws IOException{

      if(fileExists()){
        Response200 response200 = new Response200(requestResource);
        response200.addContentHeaders(mimeTypes);
        response200.addLastModifiedHeader();
        return response200;
      }
      return null;
    }


    private Response completePostRequest() throws IOException{

      return null;
    }


    private Response completeDeleteRequest() throws IOException{

      return null;
    }



    private boolean hasAuthHeaders(Request request) throws IOException{
      if( request.containsHeader("Authorization")  ) {
        return true;
      }
      return false;
    }

    private boolean checkAuthorization(Request request) throws IOException{
      String accessFileName = ".htaccess";
      if(configFile.getConfigValue("AccessFileName") != null){
        accessFileName = configFile.getConfigValue("AccessFileName");
      }
      Htaccess htaccess = new Htaccess(requestResource.getDirectory() + accessFileName);
      Htpassword htpass =  htaccess.createPasswordFile();
      String authInfo = request.getHeaderValue("Authorization");
      String [] authStrings = authInfo.split("\\s+");

      return htpass.isAuthorized(authStrings[1]);
    }

}
