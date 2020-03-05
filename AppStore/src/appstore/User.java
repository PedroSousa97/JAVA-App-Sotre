package appstore;

import java.util.Date;
import java.util.HashMap;


/**
 *
 * @authors Irina, Pedro, Cristóvão
 */
public class User implements Discount, Comparable<User> {

    //instance variables
    private final int recommendedDiscount = 5;
    private int age;
    private int userId;
    private String name;
    private boolean premium;
    private int recommendations;
    private HashMap<Integer, Purchase> purchases;
    private HashMap<String, App> boughtApps;
    private double totalAmountPaid;

    //constructors
    public User() {
        age = 0;
        userId = 0;
        name = "";
        premium = false;
        purchases = new HashMap<>();
        boughtApps = new HashMap<>();
        totalAmountPaid = 0;
        recommendations = 0;
    }
    
    
    public User(String name, int age, int userId, boolean premium) {
        this.name = name;
        this.age = age;
        this.userId = userId;
        this.premium = premium;
        purchases = new HashMap<>();
        boughtApps = new HashMap<>();
        totalAmountPaid = 0;
        this.recommendations = 0;
    }

    
        public User(String name, int age, int userId, boolean premium, int recommendations) {
        this.name = name;
        this.age = age;
        this.userId = userId;
        this.premium = premium;
        purchases = new HashMap<>();
        boughtApps = new HashMap<>();
        totalAmountPaid = 0;
        this.recommendations = recommendations;
    }
    
    //getters and setters
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public void updateTotalAmount(double price) {
        this.totalAmountPaid += price;
    }

    public int getRecommendedDiscount() {
        return recommendedDiscount;
    }

    public int getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(int recommendations) {
        this.recommendations = recommendations;
    }

    public boolean isPremium() {
        return premium;
    }

    public HashMap<Integer, Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(HashMap<Integer, Purchase> purchases) {
        this.purchases = purchases;
    }

    public HashMap<String, App> getBoughtApps() {
        return boughtApps;
    }

    public void setBoughtApps(HashMap<String, App> boughtApps) {
        this.boughtApps = boughtApps;
    }

    //adds the app to the apps that user bought
    public boolean addAppToUser(App app) {
        boughtApps.put(app.getName(), app);
        return true;

    }
    
    //adds the purchase details to the user purchases
    public boolean addPurchaseToUser(Purchase purchase) {
        if (purchases.containsKey(purchase.getPurchaseId())) {
            // System.out.println("You have already bought this.");
            return false;
        } else {
            purchases.put(purchase.getPurchaseId(), purchase);
            return true;
        }
    }
    
    //verifies if the app was already bought by this user
    public boolean isPurchased(String appName) {
        if (boughtApps.containsKey(appName)) {
            return true;
        } else {
            return false;
        }
    }
    
    //checks if the subscription of the app expired for this user
    public boolean checkIfSubscriptionExpired(String appName, Date date) {
        for (Purchase p : purchases.values()) {
            if (p.getAppsToBuy().containsKey(appName)) {
                if (p.getAppsToBuy().get(appName).isSubscription()) {
                    if (p.getPurchaseDate().before(date)) {
                        return true;
                    } else {
                        //System.out.println("Not before");
                    }
                } else {
                    //System.out.println("No subscription");
                }

            } else {
                //System.out.println("Does not exist" + p.getAppsToBuy());
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.userId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return this.userId == other.userId;
    }

    @Override
    public String toString() {
        String text;
        text = "User " + name + " :\n";
        text += "Made the following purchases: \n";
        for (Purchase p : purchases.values()) {
            text += p.toString() + "\n";
        }
        return text;
    }

    @Override
    public int compareTo(User o) {
        return this.userId - o.userId;
    }

    @Override
    public double applyDiscount(int percentage, double purchasePrice) {
        return purchasePrice * ((double) percentage / 100);
    }

}
