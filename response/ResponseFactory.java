package response;
import request.*;
import resource.*;
import filereaders.*;

public class ResponseFactory{
    private Resource requestResource;
    private HttpdConfig config; 

    public ResponseFactory(HttpdConfig configFile) {
      config = configFile;
    }

    public Response getResponse(Request request, Resource requestResource){
      this.requestResource = requestResource;
      if(request.isValidRequest()){
        return completeRequest(request, requestResource);
      }else{
        return new Response400(requestResource);
      }
    }

    private Response completeRequest(Request request, Resource resource){
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
          case "GET": response = completeGetRequest(request);
                      break;
          case "PUT": completePutRequest(request);
                      break;
          case "HEAD": completeHeadRequest(request);
                      break;
          case "POST": completePostRequest(request);
                      break;
          case "DELETE": completeDeleteRequest(request);
                      break;
        }
        return response;
    }



    private Response completeGetRequest(Request request){
      System.out.println(requestResource.isProtected());
      return new Response200(requestResource);
    }


    private Response completePutRequest(Request request){

      return null;
    }


    private Response completeHeadRequest(Request request){

      return null;
    }


    private Response completePostRequest(Request request){

      return null;
    }


    private Response completeDeleteRequest(Request request){

      return null;
    }



    private boolean hasAuthHeaders(Request request) {
      if( request.containsHeader("WWW-Authenticate") 
      &&  request.containsHeader("Authorization")  ) {
        return true;
      }
      return false;
    }

    private boolean checkAuthorization(Request request){
         
      Htaccess htaccess = new Htaccess(config.getConfigValue("AccessFileName"));
      String authInfo = request.getHeaderValue("Authorization");
      String username = authInfo.substring( 0, authInfo.indexOf(":"));
      String password = authInfo.substring( authInfo.indexOf("}") + 2 );
     
      return htaccess.isAuthorized(username,password);
    } 

}
