package response;
import resource.Resource;

public class Response404 extends Response{
  Response404(Resource resource){
    super(resource);
    this.code = 404;
    this.reasonPhrase = "Not Found";
  }

}
