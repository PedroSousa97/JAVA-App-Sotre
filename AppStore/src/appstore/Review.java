
package appstore;

/**
 *
 * @authors Irina, Pedro, Cristóvão
 */
public class Review {
    //instance variables
    private String userName;
    private String comment;
    private int rating;
    
    //constructors
    public Review(){
        this.userName = "";
        this.rating = 0;
        this.comment = "";
    }
    
    public Review(String userName, int rating){
        this.userName = userName;
        this.rating = rating;
        this.comment = "The user did not add a comment";
    }
    
    public Review(String userName, int rating, String comment){
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
    }
    
    //getters and setters
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "\n"+userName+": " + "\nRating: " + rating + "\nComment: " + comment;
                
    }

}
