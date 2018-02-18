package filereaders;
import java.io.IOException;
import java.util.HashMap;
public class Htaccess extends ConfigurationReader {

  String authFile;
  String authName;
  String require;
  String authType;
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
    authFile = htAccessValues.get("AuthUserFile");
    authName = htAccessValues.get("AuthName");
    authType = htAccessValues.get("AuthType");
    require = htAccessValues.get("Require");

  }

  public void parseLine(String line) {
    String currentLine = line;
    String[] tokens = currentLine.split("\\s+");
    int numberOfTokens = tokens.length;
    if(numberOfTokens == 2 )  {
      htAccessValues.put( tokens[0], tokens[1].replaceAll("\"",""));
    }
  }


  public Htpassword createPasswordFile() throws IOException{
    // for(String k : htAccessValues.keySet()){
    //   System.out.println("acces value " + k );
    // }

    //System.out.println("authfile" + authFile);
    return new Htpassword(authFile);
  }


}
