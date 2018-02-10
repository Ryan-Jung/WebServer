import java.util.HashMap;
import java.util.ArrayList;
public class Request{

  private String uri;
  private String body;
  private String verb;
  private String httpVersion;
  private HashMap<String,String> headers;
  private String request;
  private static final ArrayList<String> VERBS = ["GET","HEAD","PUT","POST","DELETE"];
  Request(String request){
      this.request = request;
  }

  public void parse(){
      readRequestLine(request);
  }

  private void readRequestLine(String requestLine){
    String[] requestLineInfo = requestLine.split("\\s");
    verb = requestLineInfo[0];
    uri = requestLineInfo[1];
    httpVersion = requestLineInfo[2];
  }
  private void test(){
    System.out.println(verb + "\n"+ uri + "\n"+  httpVersion);
  }
  public static void main(String[] args){
      Request request = new Request("GET /path/ HTTP/1.1");
      request.parse();
      request.test();
  }

}
