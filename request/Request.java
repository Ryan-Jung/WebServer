package request;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;

public class Request {

  private String uri;
  private String verb;
  private String httpVersion;
  private String request = "";
  private byte[] body = {};
  private HashMap<String, String> headers = new HashMap<String, String>();
  private HashSet<String> validVerbs = new HashSet<String>();


  private Request(String request) {
    this.request = request;
    parse();
  }


  public Request(InputStream inputStream) {
    loadValidVerbs();
    readInputStream(inputStream);
    parse();
  }


  private void loadValidVerbs() {
    validVerbs.add("GET");
    validVerbs.add("HEAD");
    validVerbs.add("POST");
    validVerbs.add("PUT");
    validVerbs.add("DELETE");
  }


  public void readInputStream(InputStream inputStream) {
    StringBuffer builder = new StringBuffer();
    String line;
    try {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      while ((line = bufferedReader.readLine()) != null && !line.equals("")) {
        builder.append(line + "\r\n");
      }
      if(bufferedReader.ready()){
        builder.append("\r\n");
        while(bufferedReader.ready()){
          builder.append((char) bufferedReader.read());
        }
      }
      request = builder.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private void parse() {
    String[] splitRequest = request.split("\\r?\\n");
    readRequestLine(splitRequest[0]);
    int counter = 1;
    while (counter < splitRequest.length && !splitRequest[counter].trim().isEmpty()) {
      readHeaders(splitRequest[counter]);
      counter++;
    }
    counter++;
    while (counter < splitRequest.length) {
      readBody(splitRequest[counter]);
      counter++;
    }
  }


  private void readBody(String bodyLine) {
    byte[] temp = (bodyLine).getBytes();
    byte[] newBody = new byte[temp.length + body.length];

    System.arraycopy(body, 0, newBody, 0, body.length);
    System.arraycopy(temp, 0, newBody, body.length, temp.length);

    body = newBody;
  }


  private void readRequestLine(String requestLine) {
    String[] requestLineInfo = requestLine.split("\\s");

    if (requestLineInfo.length == 3) {
      verb = requestLineInfo[0];
      uri = requestLineInfo[1];
      httpVersion = requestLineInfo[2];
    }
  }


  private void readHeaders(String headerLine) {
    int indexOfColon = headerLine.indexOf(':');
    String header = headerLine.substring(0, indexOfColon);
    String value = headerLine.substring(indexOfColon + 2);
    headers.put(header, value);
  }


  public String getUri() {
    return uri;
  }


  public String getVerb() {
    return verb;
  }


  public String getHTTPVersion() {
    return httpVersion;
  }


  public byte[] getBody() {
    return body;
  }


  public String getHeaderValue(String key) {
    return headers.get(key);
  }

  public boolean containsHeader(String key) {
    return headers.containsKey(key);
  }


  private boolean isValidVerb(String verb) {

    return validVerbs.contains(verb);
  }

  public HashMap<String,String> getHeaders() {
    return headers; 
  }

  public boolean isValidRequest() {

    boolean validRequestLine = uri != null && verb != null && httpVersion != null;
    boolean validBody = true;
    if(headers.containsKey("Content-Length")
      && Integer.parseInt(headers.get("Content-Length")) != body.length){
        validBody = false;
      }
    return validRequestLine && isValidVerb(verb) && validBody;
  }

  private int getBodyLength(){
    if(headers.containsKey("Content-Length")){
      return Integer.parseInt(headers.get("Content-Length"));
    }
    return 0;
  }


  public void test() {

    System.out.println(verb + "\n" + uri + "\n" + httpVersion);

    for (Map.Entry<String, String> entry : headers.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      System.out.println("Key: " + key + "\n" + "Value: " + value + "\n");
    }

    if (body.length > 0) {
      System.out.println(new String(body));
    }
    System.out.println("Valid Request " + isValidRequest());
    System.out.println("-----------------------------------------------");
  }

}
