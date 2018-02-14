package response;

public class Response401{
  Response401(Resource resource){
    super(resource);
    this.code = 401;
    this.reasonPhrase = 'Unauthorized';
  }

}
