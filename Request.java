import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;

public class Request{

  private String uri;
  private byte[] body = {};
  private String verb;
  private String httpVersion;
  private HashMap<String,String> headers = new HashMap<String,String>();
  private String request = "";
  private HashMap<String,String> VERBS = new HashMap<String,String>();

  Request(String request){
      this.request = request;
      setVerbs();
  }
  Request(InputStream inputStream){
    setVerbs();
    try{
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      while(bufferedReader.ready()){
        String line = bufferedReader.readLine();
        //System.out.println(bufferedReader.readLine());
        if(line != null)
          request += line + "\r\n";
      }
      bufferedReader.close();
    }catch(IOException e){
      e.printStackTrace();
    }

  }
  private void setVerbs(){
    VERBS.put("GET","T");
    VERBS.put("HEAD","T");
    VERBS.put("POST","T");
    VERBS.put("PUT","T");
    VERBS.put("DELETE","T");
  }
  public void parse(){
      if(request == null){
        return;
      }
      String [] splitRequest = request.split("\\r?\\n");
      readRequestLine(splitRequest[0]);
      int counter = 1;
      while(counter < splitRequest.length && !splitRequest[counter].trim().isEmpty()){
        readHeaders(splitRequest[counter]);
        counter++;
      }
      if(counter++ < splitRequest.length){
        readBody(splitRequest[counter]);
      }
  }

  private void readBody(String bodyLine){
    byte[] temp = bodyLine.getBytes();
    byte[] newBody = new byte[temp.length + body.length];

    System.arraycopy(body,0,newBody,0,body.length);
    System.arraycopy(temp,0,newBody,body.length,temp.length);
    body = newBody;
  }

  private void readRequestLine(String requestLine){
    System.out.println("request:" +requestLine);
    String[] requestLineInfo = requestLine.split("\\s");
    if(VERBS.containsKey(requestLineInfo[0])){
          verb = requestLineInfo[0];
    }else{

    }
    uri = requestLineInfo[1];
    httpVersion = requestLineInfo[2];
  }

  private void readHeaders(String headerLine){
    int indexOfColon = headerLine.indexOf(':');
    String header = headerLine.substring(0,indexOfColon);
    String value = headerLine.substring(indexOfColon+2);
    headers.put(header,value);
  }
  public void test(){
    //System.out.println(request);
    // System.out.println(verb + "\n"+ uri + "\n"+  httpVersion);
    // for(Map.Entry<String,String> entry : headers.entrySet()){
    //   String key = entry.getKey();
    //   String value = entry.getValue();
    //   System.out.println("Key: " + key + "\n" + "Value: " + value + "\n");
    // }
    // if(body.length > 0){
    //
    //   System.out.println(new String(body));
    // }
    // System.out.println("-----------------------------------------------");
  }
  // public static void main(String[] args){
  //     Request request = new Request("GET / HTTP/1.1\r\n"
  //                                   +"test: 12312321-5532\r\n"
  //                                   +"Content-Length: 9\r\n\r\n"+
  //                                   "123456789"
  //                                   );
  //     request.parse();
  //     request.test();
  //
  // }

}
