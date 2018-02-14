package response;

public class Response304{
    Response304(Resource resource){
      super(resource);
      this.code = 304;
      this.reasonPhrase = "Not Modified";
    }


}
