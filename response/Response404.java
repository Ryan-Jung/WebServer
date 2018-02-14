package response;

public class Response404{
  Response404(Resource resource){
    super(resource);
    this.code = 404;
    this.reasonPhrase = "Not Found";
  }

}
