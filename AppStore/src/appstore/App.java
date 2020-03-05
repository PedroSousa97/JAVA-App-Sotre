package appstore;

import java.util.Objects;
import java.util.HashMap;

/**
 *
 * @author Irina
 */
public class App implements Discount, Comparable<App> {

    //instance variables
    private String name;
    private double price;
    private String programmerName;
    private String category;
    boolean subscription;
    private HashMap<String, Review> reviews;
    private int nrOfReviews;
    private double rating;
    private int nrOfPurchases;
    private int nrOfPurchasesLastWeek;
    private int weeklyPurchases;
    private final int fiveAppsDiscount = 15;
    private final int monthlyDiscount = 5;
    private final int premiumUserDiscount = 40;

    //constructors
    public App() {
        name = "";
        price = 0;
        programmerName = "";
        category = "";
        subscription = false;
        reviews = new HashMap<>();
        nrOfReviews = 0;
        nrOfPurchases = 0;
        nrOfPurchasesLastWeek = 0;
        weeklyPurchases = 0;
        rating = 0;
    }

    public App(String name, double price, String programmerName, String category, boolean subscription) {
        this.name = name;
        this.price = price;
        this.programmerName = programmerName;
        this.category = category;
        this.subscription = subscription;
        reviews = new HashMap<>();
        nrOfPurchases = 0;
        nrOfPurchasesLastWeek = 0;
        weeklyPurchases = 0;
    }
    
    //getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProgrammerName() {
        return programmerName;
    }

    public void setProgrammerName(String programmerName) {
        this.programmerName = programmerName;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getFiveAppsDiscount() {
        return fiveAppsDiscount;
    }

    public int getMonthlyDiscount() {
        return monthlyDiscount;
    }

    public int getPremiumUserDiscount() {
        return premiumUserDiscount;
    }
 
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isSubscription() {
        return subscription;
    }

    public void setSubscription(boolean subscription) {
        this.subscription = subscription;
    }

    public HashMap<String, Review> getReviews() {
        return reviews;
    }

    public void setReviews(HashMap<String, Review> reviews) {
        this.reviews = reviews;
    }

    public double getRating() {
        return rating;
    }

    public void updateRating(double rating) {
        this.rating = (this.rating + rating) / nrOfReviews;
    }

    public int getNrOfReviews() {
        return nrOfReviews;
    }

    public void countReviews() {
        this.nrOfReviews++;
    }

    public int getNrOfPurchases() {
        return nrOfPurchases;
    }

    public void countPurchases() {
        this.nrOfPurchases++;
    }

    public int getNrOfPurchasesLastWeek() {
        return nrOfPurchasesLastWeek;
    }

    public void setNrOfPurchasesLastWeek(int nrOfPurchasesLastWeek) {
        this.nrOfPurchasesLastWeek = nrOfPurchasesLastWeek;
    }

    public int getWeeklyPurchases() {
        return weeklyPurchases;
    }

    public void setWeeklyPurchases(int weeklyPurchases) {
        this.weeklyPurchases = weeklyPurchases;
    }
 
    public void countWeeklyPurchases() {
        this.weeklyPurchases++;
    }
  
    
    public boolean addReviewToApp(Review review) {
        if (reviews.containsKey(review.getUserName())) {
            System.out.println("You have already reviewed this app.");
            return false;
        } else {
            reviews.put(review.getUserName(), review);
            return true;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.name);
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
        final App other = (App) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        String text;
        text = "App name: " + name + (subscription ? " (with subscription)" : " (no subscription)") + "\nRating: " + rating + "\nPrice: " + price + "\nCategory: " + category + "\nDeveloped by: " + programmerName + "\n";
        text += "-----Reviews----- \n";
        for (Review r : reviews.values()) {
            text += r.toString() + "\n";
        }
        return text;
    }

    
    @Override
    public int compareTo(App o) {
        return this.nrOfPurchases - o.nrOfPurchases;
    }

    @Override
    public double applyDiscount(int percentage, double appPrice) {
        return appPrice*((double)percentage/100);
    }

}
