package Response;

public class response403{
  Response403(Resource resource){
    super(resource);
    this.code = 403;
    this.reasonPhrase = "Forbidden";
  }

}
