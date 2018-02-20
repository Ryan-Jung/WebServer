package filereaders;
import java.util.HashMap;
import java.util.Map;


public class HttpdConfig extends ConfigurationReader {

    private HashMap<String,String> aliases;
    private HashMap<String,String> scriptAliases;
    private HashMap<String,String> configInfo;

    public HttpdConfig(String fileName) {
      super(fileName);
      aliases = new HashMap<String,String>();
      scriptAliases = new HashMap<String,String>();
      configInfo = new HashMap<String,String>();
      load();
    }

    public String getConfigValue(String key) {
      return configInfo.get(key);
    }

    public String getScriptValue(String key) {
      return scriptAliases.get(key);
    }

    public String getAliasValue(String key) {
      return aliases.get(key);
    }

    private String getValue(String currentLine) {
        int openQuote = currentLine.indexOf(' ') + 1;
        int closeQuote = currentLine.length();
        String value = currentLine.substring(openQuote,closeQuote);
        return value.replace("\"","");
    }

    private String getValueInQuotes(String currentLine) {
        int openQuote = currentLine.indexOf('"') + 1;
        int closeQuote = currentLine.lastIndexOf('"');
        return currentLine.substring(openQuote,closeQuote);
    }

    private String getAlias(String currentLine) {
      int firstSpace = currentLine.indexOf('/');
      int lastSpace = currentLine.lastIndexOf(' ');
      return currentLine.substring(firstSpace,lastSpace);
    }


    public String getAliasNameIfAlias(String uri){
      for(String alias : aliases.keySet()){
        if(uri.contains(alias)){
          return alias;
        }
      }
      return "";
    }


    public String getScriptNameIfScript(String uri){
      for(String scriptAlias : scriptAliases.keySet()){
        if(uri.contains(scriptAlias)){
          return scriptAlias;
        }
      }
      return "";
    }


    private void loadAlias(String currentLine) {
        String key = getAlias(currentLine);
        String value = getValueInQuotes(currentLine);
        aliases.put(key,value);
    }


    private void loadScriptAlias(String currentLine){
        String key = getAlias(currentLine);
        String value = getValueInQuotes(currentLine);
        scriptAliases.put(key,value);
    }


    private void loadConfigInfo(String currentLine, String key) {
        String value = getValue(currentLine);
        configInfo.put(key,value);
    }


    public void load() {
      while(hasMoreLines()) {
        String currentLine = nextLine();
        if(currentLine.trim().length() > 0 ) {
          String keyType = currentLine.substring(0, currentLine.indexOf(" "));
          if(keyType.equals("Alias")) {
            loadAlias(currentLine);
          } else if( keyType.equals("ScriptAlias")){
            loadScriptAlias(currentLine);
          } else {
            loadConfigInfo(currentLine, keyType);
          }
        }
      }
    }
}
