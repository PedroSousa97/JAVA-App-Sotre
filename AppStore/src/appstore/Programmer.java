package appstore;


import java.util.*;

/**
 *
 * @authors Irina, Pedro, Cristóvão
 */
public class Programmer {
    //instance variables
    private String name;
    private double totalValue;
    private HashMap <String, App> programmer;
    
    //constructors
    public Programmer(){
        name = "";
        programmer = new HashMap<>();
        totalValue = 0;
    }

    public Programmer(String name) {
        this.name = name;
        programmer = new HashMap<>();
        totalValue = 0;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void updateTotalValue(double totalValue) {
        this.totalValue += totalValue;
    }


    public HashMap<String, App> getProgrammer() {
        return programmer;
    }

    public void setProgrammer(HashMap<String, App> programmer) {
        this.programmer = programmer;
    }
    
    //adds the app developed by the programmer
    public boolean addAppToProgrammer(App app){
        if(programmer.containsKey(app.getName())){
            System.out.println("An app with this name already exists.");
            return false;
        }
        else{
            programmer.put(app.getName(), app);
            return true;
        }
    }
    
    
    @Override
    public String toString() {
        String text;
        text = getName() + " :";
        text += "\n\t";
        for(App a: programmer.values())
            text += a.getName() + "\n\t";
        return text;
    }
}
