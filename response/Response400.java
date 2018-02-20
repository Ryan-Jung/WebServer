package response;
import resource.Resource;

public class Response400 extends Response{
    Response400(Resource resource){
      super(resource);
      this.code = 400;
      this.reasonPhrase = "Bad Request";
    }
}
