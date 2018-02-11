import java.util.HashMap;
import java.util.Map;
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

  Request(String request){
      this.request = request;
  }


  Request(InputStream inputStream){
    readInputStream(inputStream);
  }


  public void readInputStream(InputStream inputStream){
    try{
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      while(bufferedReader.ready()){
        String line = bufferedReader.readLine();
        request += line + "\r\n";
      }
      bufferedReader.close();
    }catch(IOException e){
      e.printStackTrace();
    }
  }


  public void parse(){
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
    try{
      String[] requestLineInfo = requestLine.split("\\s");
      verb = requestLineInfo[0];
      uri = requestLineInfo[1];
      httpVersion = requestLineInfo[2];
    }catch(Exception e){

    }
  }


  private void readHeaders(String headerLine){
    int indexOfColon = headerLine.indexOf(':');
    String header = headerLine.substring(0,indexOfColon);
    String value = headerLine.substring(indexOfColon+2);
    headers.put(header,value);
  }


  public void test(){
    System.out.println(verb + "\n"+ uri + "\n"+  httpVersion);
    for(Map.Entry<String,String> entry : headers.entrySet()){
      String key = entry.getKey();
      String value = entry.getValue();
      System.out.println("Key: " + key + "\n" + "Value: " + value + "\n");
    }
    if(body.length > 0){

      System.out.println(new String(body));
    }
    System.out.println("-----------------------------------------------");
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
