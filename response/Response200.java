package response;

public class Response200{
    Response200(Resource resource){
        super(resource);
        this.code = 200;
        this.reasonPhrase = "OK";
    }
}
