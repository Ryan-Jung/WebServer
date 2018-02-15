package response;
import request.*;
import resource.*;

public class ResponseFactory{
    private Request request;
    private Resource requestResource;


    public ResponseFactory(){
    }

    public Response getResponse(Request request, Resource requestResource){
      this.request = request;
      this.requestResource = requestResource;
      return null;
    }





}
