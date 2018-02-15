package response;
import resource.Resource;

public class Response201 extends Response{
    Response201(Resource resource){
      super(resource);
      this.code = 201;
      this.reasonPhrase = "Created";
    }


}
