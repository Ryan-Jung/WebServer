package response;

import request.*;
import resource.*;
import filereaders.*;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class ResponseFactory {
  private HttpdConfig configFile;
  private MimeTypes mimeTypes;

  public ResponseFactory(HttpdConfig configFile, MimeTypes mimeTypes) {
    this.configFile = configFile;
    this.mimeTypes = mimeTypes;
  }


  public Response getResponse(Request request, Resource requestResource) throws IOException {
    if (request.isValidRequest()) {
      return completeRequest(request, requestResource);
    } else {
      return new Response400(requestResource);
    }
  }


  private Response completeRequest(Request request, Resource resource) throws IOException {

    Response response = null;
    if (resource.isProtected()) {
      if (!hasAuthHeaders(request)) {
        return new Response401(resource);
      }
      else {
        if (!checkAuthorization(request, resource)) {
          return new Response403(resource);
        }
      }
    }
    switch (request.getVerb()) {
    case "GET":
      response = completeGetRequest(request, resource);
      break;
    case "PUT":
      response = completePutRequest(request, resource);
      break;
    case "HEAD":
      response = completeHeadRequest(request, resource);
      break;
    case "POST":
      response = completePostRequest(request, resource);
      break;
    case "DELETE":
      response = completeDeleteRequest(request, resource);
      break;
    }
    return response;
  }


  private Response completeGetRequest(Request request, Resource requestResource) throws IOException {

    if (ifModifiedSinceBeforeLastModified(request, requestResource)) {
      return build304Response(requestResource);
    }
    if (fileExists(requestResource)) {
        Response200 response200 = new Response200(requestResource);
        response200.addResource();
        response200.addContentHeaders(mimeTypes);
        return response200;
      }
      else {
        return new Response404(requestResource);
      }
  }



  private Response completePutRequest(Request request, Resource requestResource) throws IOException {

    File fileToCreate = new File(requestResource.getAbsolutePath());
    if(fileToCreate.createNewFile()){
      FileOutputStream fileWriter = new FileOutputStream(fileToCreate);
      fileWriter.write(request.getBody());
      fileWriter.flush();
      fileWriter.close();
      return new Response201(requestResource);
    }else{
      FileOutputStream fileWriter = new FileOutputStream(fileToCreate, false);
      fileWriter.write(request.getBody());
      fileWriter.flush();
      fileWriter.close();
      return new Response204(requestResource);
    }
  }


  private Response completeHeadRequest(Request request, Resource requestResource) throws IOException {

    if (ifModifiedSinceBeforeLastModified(request, requestResource)) {
      return build304Response(requestResource);
    }

    if (fileExists(requestResource)) {
      Response200 response200 = new Response200(requestResource);
      response200.addContentHeaders(mimeTypes);
      response200.addLastModifiedHeader();
      return response200;
    }
    return null;
  }


  private Response completePostRequest(Request request, Resource requestResource) throws IOException {
    if(fileExists(requestResource)){
      File fileToModify = new File(requestResource.getAbsolutePath());
      FileOutputStream fileWriter = new FileOutputStream(fileToModify,true);
      fileWriter.write(request.getBody());
      fileWriter.flush();
      fileWriter.close();
      return new Response200(requestResource);
    }
    return new Response404(requestResource);
  }


  private Response completeDeleteRequest(Request request, Resource requestResource) throws IOException {
    File file = new File(requestResource.getAbsolutePath());
    file.delete();
    return new Response204(requestResource);
  }


  private boolean hasAuthHeaders(Request request) throws IOException {

    if (request.containsHeader("Authorization")) {
      return true;
    }
    return false;
  }


  private boolean checkAuthorization(Request request, Resource requestResource) throws IOException {

    String accessFileName = ".htaccess";
    if (configFile.getConfigValue("AccessFileName") != null) {
      accessFileName = configFile.getConfigValue("AccessFileName");
    }

    Htaccess htaccess = new Htaccess(requestResource.getDirectory() + accessFileName);
    Htpassword htpass = htaccess.createPasswordFile();
    String authInfo = request.getHeaderValue("Authorization");
    String credentials = authInfo.split("\\s+")[1];

    return htpass.isAuthorized(credentials);
  }


  private Response304 build304Response(Resource requestResource) throws IOException{

    Response304 response304 = new Response304(requestResource);
    response304.addLastModifiedHeader();

    response304.addContentHeaders(mimeTypes);
    return response304;
  }


  private boolean fileExists(Resource requestResource) {
    File fileRequested = new File(requestResource.getAbsolutePath());
    return fileRequested.exists();
  }


  private boolean ifModifiedSinceBeforeLastModified(Request request, Resource requestResource) {
    if(!request.containsHeader("If-Modified-Since")){
      return false;
    }
    SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm:ss");
    dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));

    File resourceFile = new File(requestResource.getAbsolutePath());
    Date lastModified = new Date(resourceFile.lastModified());

    String ifModifiedSinceValue = request.getHeaderValue("If-Modified-Since").replace("GMT", "");
    Date ifModifiedSince;
    try {
      ifModifiedSince = dateFormatter.parse(ifModifiedSinceValue);
    } catch (ParseException e) {
      return false;
    }
    return lastModified.after(ifModifiedSince);
  }


}
