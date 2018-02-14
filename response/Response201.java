package response;

public class Response201{
    Response201(Resource resource){
      super(resource);
      this.code = 201;
      this.reasonPhrase = "Created";
    }


}
