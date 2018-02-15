package response;
import resource.Resource;

public class Response304 extends Response{
    Response304(Resource resource){
      super(resource);
      this.code = 304;
      this.reasonPhrase = "Not Modified";
    }


}
