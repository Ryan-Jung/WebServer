package response;
import request.*;
import resource.*;
import java.util.HashSet;
public class ResponseFactory{
    private Request request;
    private Resource requestResource;
    private HashSet<String> validVerbs = new HashSet<String>();

    public ResponseFactory(){
      loadValidVerbs();
    }

    public Response getResponse(Request request, Resource requestResource){
      this.request = request;
      this.requestResource = requestResource;
      if(isValidVerb(request.getVerb())){

      }else{
        return null;
      }
      return null;
    }

    private void loadValidVerbs(){
      validVerbs.add("GET");
      validVerbs.add("HEAD");
      validVerbs.add("POST");
      validVerbs.add("PUT");
      validVerbs.add("DELETE");
    }

    private boolean isValidVerb(String verb){
      return validVerbs.contains(verb);
    }

}
