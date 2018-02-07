import java.util.HashMap;
import java.util.Map;

public class MimeTypes extends ConfigurationReader {

  HashMap<String,String> mimeTypes;

  MimeTypes(String fileName) {
    super(fileName);
    mimeTypes = new HashMap<String,String>();
  }

  public void load() {
    while(hasMoreLines()) {
      String currentLine = nextLine();
      if(currentLine.startsWith("#") == false){
        // use regular expression to tokenize the string seperated with spaces
        String[] tokens = currentLine.split("\\s+");
        int numberOfTokens = tokens.length;
        if(numberOfTokens != 1) {
          String value = tokens[0];
          String key;
          for(int token = 1; token < numberOfTokens; token++) {
            key = tokens[token];
            mimeTypes.put(key,value);
          }
        }
      } 
    }
  }

 

  public void test() {
    for(Map.Entry<String,String> entry: mimeTypes.entrySet()){
      String key = entry.getKey();
      String value = entry.getValue();
      System.out.println("Key: " + key + "\n" + "Value: " + value + "\n");
    }
  }

  public static void main(String[] args) {
    MimeTypes test = new MimeTypes("mime.types");
    test.load();
    test.test();
  }


}
