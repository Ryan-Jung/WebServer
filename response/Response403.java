package response;
import resource.Resource;

public class Response403 extends Response{
  Response403(Resource resource){
    super(resource);
    this.code = 403;
    this.reasonPhrase = "Forbidden";
  }

}
