public class Resource {

  private HttpdConfig configFile;
  private String httpVersion;
  private String uri; 

  Resource(HttpdConfig configFile, String uri, String httpVersion) {
    this.configFile = configFile;
    this.uri = uri; 
    this.httpVersion = httpVersion; 
  }

  public String absolutePath(){
    return configFile.getConfigValue("ServerRoot") + uri;
  }

  public boolean isScript(String script) {
    String scriptValue = configFile.getScriptValue(script);
    if(scriptValue == null) {
      return false;
    }
    return true; 
  }

  public boolean isProtected() {
    return false;
  }

}
