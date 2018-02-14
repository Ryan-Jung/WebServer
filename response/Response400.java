package response;

public class Response400{
    Response400(Resource resource){
      super(resource);
      this.code = 400;
      this.reasonPhrase = "Bad Request";
    }

}
