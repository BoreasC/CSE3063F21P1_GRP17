import java.io.*;
import org.json.simple.parser.ParseException;
/**
 * Main class of Marmara CSE Student Registiration System
 * Please visit the link for documents{@link <a href="https://github.com/BoreasC/CSE3063F21P1_GRP17%7D">...</a>
 * @author Emir Said Haliloğlu
 * @author Ahmet Can Bağırgan
 * @version 2.0.1
 */
public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        RegistrationController rc = RegistrationController.getInstance();
        rc.startRegistration();

    }

}