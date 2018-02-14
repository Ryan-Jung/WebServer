package response;

public class Response204{
  Response204(Resource resource){
    super(resource);
    this.code = 204;
    this.reasonPhrase = "No Content";
  }

}
