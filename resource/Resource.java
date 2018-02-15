package resource;
import filereaders.*;
public class Resource {

  private final String DIRINDEX = "index.html";
  private String absolutePath;
  private boolean isScript = false;
  private boolean isProtected = false;


  public Resource(HttpdConfig configFile, String uri) {
    this.absolutePath  = resolveAbsolutePath(uri,configFile);
  }


  private boolean isFile(String uri) {
    if( uri.contains(".") ) {
      return true;
    }
    return false;
  }

  private String resolveAbsolutePath(String uri, HttpdConfig configFile) {
    String docRoot = configFile.getConfigValue("DocumentRoot");
    docRoot = docRoot.substring(0,docRoot.length()-1); // Remove slash since uri will have one
    String absPath = "";
    if(configFile.getAliasValue(uri) != null) {
      absPath = configFile.getAliasValue(uri);
    } else if( configFile.getScriptValue(uri) != null) {
      absPath = configFile.getScriptValue(uri);
      isScript = true;
    } else {
      if(uri.length() > 1)
        absPath = docRoot + uri.substring(1);
    }
    if( isFile(uri) == false ) {
      absPath = absPath + DIRINDEX;
    }
    return absPath;
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

  public static void main(String[] args) {

    HttpdConfig config = new HttpdConfig("conf/httpd.conf");
    config.load();
    Resource test = new Resource(config,"/tommySucksDick1.pdf");
    System.out.println(test.absolutePath());


  }

}