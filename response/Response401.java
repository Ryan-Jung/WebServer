package response;
import resource.Resource;

public class Response401 extends Response {
  Response401(Resource resource){
    super(resource);
    this.code = 401;
    this.reasonPhrase = 'Unauthorized';
  }

}
