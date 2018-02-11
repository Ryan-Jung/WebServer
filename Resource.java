public class Resource {

  private final String DOCINDEX = "index.html";
  private String absolutePath;
  private boolean isScript = false;
  private boolean isProtected = false;

  private boolean isFile(String uri) {
    if( uri.contains(".") ) {
      return true; 
    }
    return false;
  }

  private String resolveAbsolutePath(String uri, HttpdConfig configFile) {
    String docRoot = configFile.getConfigValue("DocumentRoot");
    String absPath; 
    if(configFile.getAliasValue(uri) != null) {
      absPath = configFile.getAliasValue(uri);
    } else if( configFile.getScriptValue(uri) != null) {
      absPath = configFile.getScriptValue(uri);
      isScript = true;
    } else {
      absPath = docRoot + uri;
    }
    if( isFile(uri) == false ) {
      absPath = absPath + DOCINDEX;
    }
    return absPath;
  }

  Resource(HttpdConfig configFile, String uri) {
    this.absolutePath  = resolveAbsolutePath(uri,configFile);
  }

  public String absolutePath(){
    return absolutePath;
  }

  public boolean isScript(String script) {
    return isScript; 
  }

  public boolean isProtected() {
    return isProtected;
  }

}
