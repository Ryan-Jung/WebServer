package resource;
import filereaders.*;
import java.io.File;

public class Resource {

  private static String DIRINDEX = "index.html";
  private String absolutePath;
  private boolean isScript = false;
  private String queryString = ""; 
  private HttpdConfig configFile;


  public Resource(HttpdConfig configFile, String uri) {
    if(configFile.getConfigValue("DirectoryIndex") != null){
      this.DIRINDEX = configFile.getConfigValue("DirectoryIndex");
    }
    this.configFile = configFile;
    this.absolutePath  = resolveAbsolutePath(uri,configFile);
  }


  private boolean isFile(String uri) {

    if( uri != null && uri.contains(".") || isScript ) {
      return true;
    }

    return false;
  }


  private String resolveAbsolutePath(String uri, HttpdConfig configFile) {

    String absPath = "";
    absPath = modifyUri(uri);

    if( isFile(uri) == false ) {
      absPath = absPath + DIRINDEX;
    }
    return absPath;
  }


  public String modifyUri(String uri){

    String docRoot = configFile.getConfigValue("DocumentRoot");
    docRoot = docRoot.substring(0,docRoot.length()-1); // Remove slash since uri will have one
    String modifiedUri = "";
    if(configFile.getAliasNameIfAlias(uri).length() > 0){

      String alias = configFile.getAliasNameIfAlias(uri);
      String aliasValue = configFile.getAliasValue(alias);
      modifiedUri = uri.replace(alias, aliasValue);

    } else if(configFile.getScriptNameIfScript(uri).length() > 0 ) {

      String scriptAlias = configFile.getScriptNameIfScript(uri);
      String scriptValue = configFile.getScriptValue(scriptAlias);
      modifiedUri = uri.replace(scriptAlias,scriptValue);
      isScript = true;
      if( modifiedUri.contains("?") ) {
        queryString = modifiedUri.substring( modifiedUri.indexOf("?") + 1);
        modifiedUri = modifiedUri.substring(0, modifiedUri.indexOf("?"));
      }


    } else {
        modifiedUri = docRoot + uri;
    }
    return modifiedUri;
  }


  public String getAbsolutePath(){

    return absolutePath;
  }


  public boolean isScript() {

    return isScript;
  }

  public String getQuery() {
    return queryString;
  }

  public boolean isProtected() {

    String accesFileName = ".htaccess";

    if(configFile.getConfigValue("AccessFileName") != null){
      accesFileName = configFile.getConfigValue("AccessFileName");
    }

    String directoryToCheck = absolutePath.substring(0,absolutePath.lastIndexOf('/') + 1);
    File accessFile = new File(directoryToCheck + accesFileName);

    return accessFile.exists();
  }

  public String getDirectory(){
    return  absolutePath.substring(0,absolutePath.lastIndexOf('/') + 1);
  }
}
