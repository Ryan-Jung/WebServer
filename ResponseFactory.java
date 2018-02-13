public class ResponseFactory{
    private Request request;
    private Resource requestResource;


    public Response getResponse(Request request, Resource requestResource){
      this.request = request;
      this.requestResource = requestResource;
      return null;
    }


}
