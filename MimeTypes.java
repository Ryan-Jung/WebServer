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
          for(int token = 0; token < numberOfTokens; token++) {
            System.out.print(tokens[token] + " " );
          }
          System.out.println("");
        }
      } 
    }
  }

  public static void main(String[] args) {
    MimeTypes test = new MimeTypes("mime.types");
    test.load();
  }


}
