package response;
import resource.Resource;

public class Response204 extends Response{
  Response204(Resource resource){
    super(resource);
    this.code = 204;
    this.reasonPhrase = "No Content";
  }

}
