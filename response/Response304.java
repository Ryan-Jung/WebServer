package response;
import resource.Resource;

public class Response304 extends Response200{
    Response304(Resource resource){
      super(resource);
      this.code = 304;
      this.reasonPhrase = "Not Modified";
    }


}
