
package appstore;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/**
 *
 * @authors Irina, Pedro, Cristóvão
 */
public class Purchase {
   //instance variables
    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    private int purchaseId;
    private Date purchaseDate;
    private double purchasePrice;
    private HashMap <String, App> appsToBuy;
    
    //constructors
    public Purchase(){
        purchaseId = 0;
        purchaseDate = new Date();
        purchasePrice = 0;
        appsToBuy = new HashMap<>();
    }

    public Purchase(int purchaseId, Date purchaseDate, double purchasePrice) {
        this.purchaseId = purchaseId;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        appsToBuy = new HashMap<>();
    }
    
    //gettters and setters
    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
    
    
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public HashMap<String, App> getAppsToBuy() {
        return appsToBuy;
    }

    public void setAppsToBuy(HashMap<String, App> appsToBuy) {
        this.appsToBuy = appsToBuy;
    }


    public boolean addAppToPurchase(App app){
        if(appsToBuy.containsKey(app.getName())){
            System.out.println("You have already chosen this app.");
            return false;
        }
        else{
            appsToBuy.put(app.getName(), app);
            return true;
        }
    }

    @Override
    public String toString() {
        String text;
        text = "\tPurchase nmber: " + purchaseId + " ocured on date: " + sdf.format(purchaseDate) + "\n";
        text += "\tThe following apps where bought: \n";
        for(App p: appsToBuy.values())
            text += "\t"+p.getName() + "\n";
        text += "\tTotal amount paid: " + String.format("%.2f", purchasePrice)+"\n";
        return text;
    }
      
}
