package response;
import request.*;
import resource.*;

public class ResponseFactory{
    private Resource requestResource;

    public Response getResponse(Request request, Resource requestResource){
      this.requestResource = requestResource;
      if(request.isValidRequest()){
        return completeRequest(request);
      }else{
        return new Response400(requestResource);
      }
    }


    private Response completeRequest(Request request){
        Response response = null;
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


}
