package response;
import resource.Resource;

public class Response200 extends Response{
    Response200(Resource resource){
        super(resource);
        this.code = 200;
        this.reasonPhrase = "OK";
    }
}
