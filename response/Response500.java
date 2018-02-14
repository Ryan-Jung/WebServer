package response;

public class Response500{
    Response500(Resource resource){
      super(resource);
      this.code = 500;
      this.reasonPhrase = "Internal Server Error";
    }

}
