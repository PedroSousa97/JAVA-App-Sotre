package appstore;

import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @authors Irina, Pedro, Cristóvão 
 *
 */

public class AppStore {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws IOException, ParseException {

        AppStoreApplication myAppStore = new AppStoreApplication();
        
        myAppStore.getFileData();//getting the data from the files
        
        myAppStore.startApplication();//starting the aplication
           
    }   
}
