import java.util.HashMap;
import java.util.Map;


public class HttpdConfig extends ConfigurationReader {
    
    private HashMap<String,String> aliases;
    private HashMap<String,String> scriptAliases;
    private HashMap<String,String> configInfo;

    HttpdConfig(String fileName) {
      super(fileName);
      aliases = new HashMap<String,String>();
      scriptAliases = new HashMap<String,String>();
      configInfo = new HashMap<String,String>();
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

    void test() {

      System.out.println("Printing Config shit: \n"); 
      for (Map.Entry<String,String> entry : configInfo.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();
        System.out.println("Key: " + key + "\n" + "Value: " + value + "\n");
      }
      System.out.println("Printing Aliases: \n");
      for (Map.Entry<String,String> entry : aliases.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();
        System.out.println("Key: " + key + "\n" + "Value: " + value + "\n");
      }
      System.out.println("Printing Script Aliases: \n");
      for (Map.Entry<String,String> entry : scriptAliases.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();
        System.out.println("Key: " + key + "\n" + "Value: " + value + "\n");
      }
    }

    public static void main(String[] args) {
      HttpdConfig test = new HttpdConfig("conf/httpd.conf");
      test.load();
      test.test();
    }

}
