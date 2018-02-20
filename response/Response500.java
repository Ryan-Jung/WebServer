package response;
import resource.Resource;

public class Response500 extends Response{

    public Response500(Resource resource){
      super(resource);
      this.code = 500;
      this.reasonPhrase = "Internal Server Error";
    }

}
