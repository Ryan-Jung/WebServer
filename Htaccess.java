import java.io.IOException;
import java.util.HashMap;
public class Htaccess extends ConfigurationReader {

  Htpassword userFile;
  String authType;
  String authName;
  String require; 
  HashMap<String,String> htAccessValues;

  Htaccess(String file) {
    super(file);
    htAccessValues = new HashMap<String,String>();
    this.load();
  }

  public void load() {
    while( hasMoreLines() ) {
      parseLine( nextLine() );
    }
    authType = htAccessValues.get("AuthUserFile");
    authName = htAccessValues.get("AuthName");
    require = htAccessValues.get("Require");
    try {
      userFile = new Htpassword( htAccessValues.get("AuthUserFile"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void parseLine(String line) {
    String currentLine = nextLine(); 
    currentLine.replace("\"","");
    String[] tokens = currentLine.split("\\s+");
    int numberOfTokens = tokens.length;
    if(numberOfTokens == 2 )  {
      htAccessValues.put( tokens[0], tokens[1]);
    }
  }

  public boolean isAuthorized (String username, String password) {
    return userFile.verifyPassword(username,password);
  }









}
