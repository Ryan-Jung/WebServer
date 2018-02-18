package filereaders;
import java.io.IOException;
import java.util.HashMap;
public class Htaccess extends ConfigurationReader {

  String authType;
  String authName;
  String require;
  HashMap<String,String> htAccessValues;

  public Htaccess(String file) {
    super(file);
    htAccessValues = new HashMap<String,String>();
    load();
  }

  public void load() {
    while( hasMoreLines() ) {
      parseLine( nextLine() );
    }
    authType = htAccessValues.get("AuthUserFile");
    authName = htAccessValues.get("AuthName");
    require = htAccessValues.get("Require");

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

  public String getAuthType(){
    return authType;
  }

  public Htpassword createPasswordFile() throws IOException{
    return new Htpassword(htAccessValues.get("AuthUserFile"));
  }


}
