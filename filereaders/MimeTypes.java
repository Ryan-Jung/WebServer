package filereaders;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MimeTypes extends ConfigurationReader {

  HashMap<String,String> mimeTypes;

  public MimeTypes(String fileName) {
    super(fileName);
    mimeTypes = new HashMap<String,String>();
    load();
  }

  public void load() {
    while(hasMoreLines()) {
      String currentLine = nextLine();
      // use regular expression to tokenize the string seperated with spaces
      String[] tokens = currentLine.split("\\s+");
      int numberOfTokens = tokens.length;
      //if more than 2 tokens, it means there is at least one key/val pair
      if(numberOfTokens >= 2) {
        String value = tokens[0];
        String key;
        for(int token = 1; token < numberOfTokens; token++) {
          key = tokens[token];
          mimeTypes.put(key,value);
        }
      }
    }
  }

  public String lookUp(String key) {
    if(mimeTypes.get(key) == null) {
      return "";
    }
    return mimeTypes.get(key);
  }

}
