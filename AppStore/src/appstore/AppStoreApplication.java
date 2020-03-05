package appstore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

/**
 *
 * @authors Irina, Pedro, Cristóvão
 */
public class AppStoreApplication {

    /**
     * Java BufferedReader class is used to read the text from a character-based
     * input stream. It can be used to read data line by line by readLine()
     * method. It makes the performance fast. It inherits Reader class.
     */
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss"); //setting the date format
    File usersFile;
    File developersFile;
    File appsFile;
    File purchasesFile;

    //instance variables
    private int userId;
    private int purchaseId;
    private TreeSet<User> users;
    private TreeSet<Programmer> programmers;
    private TreeSet<App> apps;
    private ArrayList<Review> reviews;
    private ArrayList<Purchase> purchases;
    private Category categoryWithDiscount;
    private double totalAmount;
    Calendar today;

    public AppStoreApplication() throws IOException {
        this.appsFile = new File("apps.txt");
        this.developersFile = new File("developers.txt");
        this.usersFile = new File("users.txt");
        this.purchasesFile = new File("purchases.txt");
        this.userId = 1;
        this.purchaseId = 1;
        users = new TreeSet(Comparator.comparing(User::getUserId));
        programmers = new TreeSet(Comparator.comparing(Programmer::getName));
        apps = new TreeSet(Comparator.comparing(App::getName));
        purchases = new ArrayList();
        reviews = new ArrayList();
        this.totalAmount = 0;
        categoryWithDiscount = Category.BUSINESS;
        today = Calendar.getInstance();
    }

    //the main function of the program
    //here is defined the operations to do for each option
    public void startApplication() throws IOException, ParseException {
        // main loop
        while (true) {
            //checks if end of the week
            //if so, saves the number of purchases last week
            //and starts counting the weekly purchases again (sets weekly purchases to 0)

            if (checkIfEndOfTheWeek()) {
                Iterator<App> iterator1 = apps.iterator();
                while (iterator1.hasNext()) {
                    App app = iterator1.next();
                    app.setNrOfPurchasesLastWeek(app.getWeeklyPurchases());
                    app.setWeeklyPurchases(0);
                    //System.out.println(app.getNrOfPurchasesLastWeek()+"----"+app.getWeeklyPurchases());
                }
            }

            //checks if end of the month
            //if so, generates a random category which will have the discount this month
            if (checkIfEndOfTheMonth()) {
                int randomCategory = new Random().nextInt(8);
                for (Category c : Category.values()) {
                    if (c.ordinal() == randomCategory) {
                        categoryWithDiscount = c;
                    }
                }
            }

            //System.out.println(categoryWithDiscount);
            String optionLine;
            int option;

            showMainMenu();//shows the main menu
            System.out.println("Please choose an option:");

            try {
                optionLine = reader.readLine();
                option = Integer.parseInt(optionLine);

                switch (option) {
                    case 1:
                        while (true) {
                            String optionRegisterLine, answer;
                            int optionRegister;
                            boolean registered;

                            System.out.println("\n========================================");
                            System.out.println("1 - Register new user");
                            System.out.println("2 - Register new programmer");
                            System.out.println("0 - Previous menu");
                            System.out.println("Please choose an option:");
                            System.out.println("========================================");

                            try {
                                optionRegisterLine = reader.readLine();
                                optionRegister = Integer.parseInt(optionRegisterLine);

                                if (optionRegister == 0) {
                                    break;
                                }

                                switch (optionRegister) {
                                    case 1:
                                        while (true) {
                                            //returns true if the user was successfully registered, false otherwise
                                            registered = registerUser();

                                            if (!registered) {
                                                System.out.println("ERROR: It was not possible to register new user");
                                            } else {
                                                System.out.println("A new user was registered successfuly!");
                                            }

                                            System.out.println("\nDo you want to add a new user? [Y/N]");
                                            answer = reader.readLine();

                                            while (!validateAnswer(answer)) {
                                                System.out.println("An invalid answer was entered. ");
                                                System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
                                                answer = reader.readLine();
                                            }

                                            if (!answer.equals("Y")) {
                                                break;
                                            }
                                        }
                                        break;
                                    case 2:
                                        while (true) {
                                            registered = registerProgrammer();

                                            if (!registered) {
                                                System.out.println("ERROR: It was not possible to register new programmer");
                                            } else {
                                                System.out.println("A new programmer was registered successfuly!");
                                            }

                                            System.out.println("\nDo you want to add a new programmer? [Y/N]");
                                            answer = reader.readLine();

                                            while (!validateAnswer(answer)) {
                                                System.out.println("An invalid answer was entered. ");
                                                System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
                                                answer = reader.readLine();
                                            }

                                            if (!answer.equals("Y")) {
                                                break;
                                            }
                                        }
                                        break;
                                    default:
                                        System.out.println("Invalid Option");
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("Format Error: Invalid Option");
                            }
                        }

                        break;
                    case 2:
                        while (true) {
                            boolean added;
                            String answer;

                            added = addApp();

                            if (!added) {
                                System.out.println("ERROR: It was not possible to add the app");
                            } else {
                                System.out.println("The app was added successfuly!");
                            }

                            System.out.println("\nDo you want to add a new app? [Y/N]");
                            answer = reader.readLine();

                            while (!validateAnswer(answer)) {
                                System.out.println("An invalid answer was entered. ");
                                System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
                                answer = reader.readLine();
                            }

                            if (!answer.equals("Y")) {
                                break;
                            }
                        }
                        break;

                    case 3: {
                        listUsers();
                        listProgrammers();
                        break;
                    }
                    case 4: {
                        while (true) {
                            boolean purchased;
                            String answer;

                            purchased = makePurchase();

                            if (!purchased) {
                                System.out.println("ERROR: It was not possible to make the purchase.");
                            } else {
                                System.out.println("Thank you for your purchase!");
                                purchaseId++;
                            }

                            System.out.println("\nDo you want to make another purchase? [Y/N]");
                            answer = reader.readLine();

                            while (!validateAnswer(answer)) {
                                System.out.println("An invalid answer was entered. ");
                                System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
                                answer = reader.readLine();
                            }

                            if (!answer.equals("Y")) {
                                break;
                            }
                        }
                        break;
                    }
                    case 5: {
                        while (true) {
                            boolean reviewed;
                            String answer;

                            reviewed = review();

                            if (!reviewed) {
                                System.out.println("ERROR: It was not possible to review the app.");
                            } else {
                                System.out.println("Thank you for the review!");
                            }

                            System.out.println("\nDo you want to make another review? [Y/N]");
                            answer = reader.readLine();

                            while (!validateAnswer(answer)) {
                                System.out.println("An invalid answer was entered. ");
                                System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
                                answer = reader.readLine();
                            }

                            if (!answer.equals("Y")) {
                                break;
                            }
                        }
                        break;
                    }
                    case 6:
                        while (true) {
                            String optionAppListLine;
                            int optionAppList;

                            System.out.println("\n========================================");
                            System.out.println("1 - ...by category");
                            System.out.println("2 - ...by user rank");
                            System.out.println("0 - Previous menu");
                            System.out.println("Please choose an option:");
                            System.out.println("========================================");

                            try {
                                optionAppListLine = reader.readLine();
                                optionAppList = Integer.parseInt(optionAppListLine);

                                if (optionAppList == 0) {
                                    break;
                                }

                                switch (optionAppList) {
                                    case 1:
                                        String categoryLine,
                                         userCategory = "";
                                        int category = 0;

                                        System.out.println("Please choose one category:");
                                        System.out.println("1. GAMES ");
                                        System.out.println("2. BUSINESS ");
                                        System.out.println("3. EDUCATION ");
                                        System.out.println("4. LIFESTYLE ");
                                        System.out.println("5. ENTERTAINMENT");
                                        System.out.println("6. UTILITIES ");
                                        System.out.println("7. TRAVEL ");
                                        System.out.println("8. HEALTH & FITNESS");

                                        try {
                                            categoryLine = reader.readLine();
                                            category = Integer.parseInt(categoryLine);
                                        } catch (NumberFormatException e) {
                                            System.out.println("Error: Invalid Option");
                                        }

                                        Category[] categories = Category.values();
                                        for (int i = 0; i < categories.length; i++) {
                                            if (categories[i].ordinal() == category - 1) {
                                                userCategory = categories[i].name();

                                            }
                                        }
                                        getAppsByCategory(userCategory);
                                        break;
                                    case 2:
                                        String answer;
                                        double rating;

                                        System.out.println("Please enter the minimum rating:");
                                        answer = reader.readLine();
                                        rating = Double.parseDouble(answer);

                                        getAppsByRating(rating);
                                        break;
                                    default:
                                        System.out.println("Invalid Option");
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("Format Error: Invalid Option");
                            }
                        }
                        break;

                    case 7:
                        while (true) {
                            String optionOrderedAppListLine;
                            int optionOrderedAppList;

                            System.out.println("\n========================================");
                            System.out.println("1 - ...by name");
                            System.out.println("2 - ...by number of purchases");
                            System.out.println("3 - ...by rating");
                            System.out.println("0 - Previous menu");
                            System.out.println("Please choose an option:");
                            System.out.println("========================================");

                            try {
                                optionOrderedAppListLine = reader.readLine();
                                optionOrderedAppList = Integer.parseInt(optionOrderedAppListLine);

                                if (optionOrderedAppList == 0) {
                                    break;
                                }

                                switch (optionOrderedAppList) {
                                    case 1:
                                        System.out.println("\n------------- APPS ORDERED BY NAME -------------");
                                        apps.forEach((temp) -> {
                                            System.out.print(temp.getName() + "\n");
                                        });
                                        break;
                                    case 2:
                                        ArrayList<App> appsByNrOfPurchases = new ArrayList<>();

                                        apps.forEach((temp) -> {
                                            appsByNrOfPurchases.add(temp);
                                        });

                                        Collections.sort(appsByNrOfPurchases);

                                        System.out.println("\n------------- APPS ORDERED BY NR OF PURCHASES -------------");
                                        for (App app : appsByNrOfPurchases) {
                                            System.out.println(app.getName() + "-" + app.getNrOfPurchases());
                                        }
                                        break;
                                    case 3:
                                        ArrayList<App> appsByRating = new ArrayList<>();

                                        apps.forEach((temp) -> {
                                            appsByRating.add(temp);
                                        });

                                        Comparator<App> byRating = Comparator.comparing(App::getRating);
                                        Collections.sort(appsByRating, byRating);

                                        System.out.println("\n------------- APPS ORDERED BY RATING -------------");
                                        for (App app : appsByRating) {
                                            System.out.println(app.getName() + "-" + app.getRating());
                                        }

                                        break;
                                    default:
                                        System.out.println("Invalid Option");
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("Format Error: Invalid Option");
                            }
                        }
                        break;
                    case 8:
                        while (true) {
                            String appName, answer;

                            System.out.println("Please choose the app of which you want to see the reviews:");
                            Iterator<App> iterator = apps.iterator();
                            System.out.println("---------- Apps ----------");
                            while (iterator.hasNext()) {
                                App app = iterator.next();
                                System.out.println(app.getName());
                            }
                            System.out.println("--------------------------");
                            appName = reader.readLine();

                            while (!validateName(appName)) {
                                System.out.println("An invalid name was entered. ");
                                System.out.println("Please use only letters, digits, underscore and dot for the name. ");
                                System.out.println("The name can start only with letters. ");
                                System.out.println("\nPlease enter another names. ");
                                appName = reader.readLine();
                            }

                            getReviewsOf(appName);

                            System.out.println("\nDo you want to see another review? [Y/N]");
                            answer = reader.readLine();

                            while (!validateAnswer(answer)) {
                                System.out.println("An invalid answer was entered. ");
                                System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
                                answer = reader.readLine();
                            }

                            if (!answer.equals("Y")) {
                                break;
                            }
                        }
                        break;
                    case 9:
                        getUsersByValuePaid();
                        break;
                    case 10:
                        while (true) {
                            String programmerName, answer;
                            listProgrammers();

                            System.out.println("Please enter the programmer name: ");

                            programmerName = reader.readLine();

                            while (!validateProgrammerName(programmerName)) {
                                System.out.println("An invalid name was entered. ");
                                System.out.println("\nPlease enter another name. ");
                                programmerName = reader.readLine();
                            }

                            Iterator<Programmer> iteratorP = programmers.iterator();
                            while (iteratorP.hasNext()) {
                                Programmer p = iteratorP.next();
                                if (p.getName().equals(programmerName)) {
                                    System.out.println(p.getName() + "---" + p.getTotalValue());
                                }

                            }
                            System.out.println("Do you want to chose another programmer?[Y/N]");
                            answer = reader.readLine();
                            while (!validateAnswer(answer)) {
                                System.out.println("An invalid answer was entered. ");
                                System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
                                answer = reader.readLine();
                            }
                            if (!answer.equals("Y")) {
                                break;
                            }
                        }

                        break;
                    case 11:
                        showTotalAmount();
                        break;
                    case 12:
                        passOneDay();
                        break;
                    case 13:
                        passOneYear();
                        break;
                    case 0:
                        System.out.println("Hope to see you soon ^_^");
                        break;
                    default:
                        System.out.println("Invalid Option!");
                }

                // terminate the program
                if (option == 0) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Format Error: invalid option!");
            }

        }

    }

    //get all the data from files
    public void getFileData() throws ParseException {

        // getting file data
        String usersFileData = usersFile.getData();
        String developersFileData = developersFile.getData();
        String appsFileData = appsFile.getData();
        String purchasesFileData = purchasesFile.getData();

        // spiliting the string
        String[] usersData = usersFileData.split("\n");
        String[] developersData = developersFileData.split("\n");
        String[] appsData = appsFileData.split("\n");
        String[] purchasesData = purchasesFileData.split("\n");

        // filling the users with data
        for (int i = 0; i < usersData.length; i++) {
            // splitting by ","
            String[] usersInfo = usersData[i].split(",");

            String userName = usersInfo[0];
            int userAge = Integer.parseInt(usersInfo[1]);
            boolean premium = usersInfo[2].equals("true");
            int recommendations = Integer.parseInt(usersInfo[3]);

            User user = new User(userName, userAge, userId, premium, recommendations);

            if (users.add(user)) {
                userId++;
            }
        }

        // filling the developers with data
        for (int i = 0; i < developersData.length; i++) {
            String developerName = developersData[i];

            Programmer programmer = new Programmer(developerName);

            if (programmers.add(programmer)) {
                //
            }

        }

        // filling the apps treeset with data
        for (int i = 0; i < appsData.length; i++) {
            // splitting by ","
            String[] appsInfo = appsData[i].split(",");

            String appName = appsInfo[0];
            double appPrice = Double.parseDouble(appsInfo[1]);
            String programmerName = appsInfo[2];
            boolean subscription = appsInfo[3].equals("0");
            String category = appsInfo[4];

            App app = new App(appName, appPrice, programmerName, category, subscription);

            Iterator<Programmer> iterator = programmers.iterator();
            while (iterator.hasNext()) {
                Programmer programmer = iterator.next();
                if (programmer.getName().equals(programmerName)) {
                    if (programmer.addAppToProgrammer(app)) {
                        if (apps.add(app)) {
                            //
                        }
                    }
                }
            }
        }

        // filling the purchases with data
        for (int i = 0; i < purchasesData.length; i++) {
            // splitting by ","

            
            String[] purchasesInfo = purchasesData[i].split(",");

            String userName = purchasesInfo[0];
            Date purchaseDate = sdf.parse(purchasesInfo[1]);
            double purchasePrice = Double.parseDouble(purchasesInfo[2]);
            String[] appNames = {purchasesInfo[3]};
            double[] appPrice = {purchasePrice};

            boolean success;
            success = addNewPurchase(purchaseId, purchaseDate, purchasePrice, userName, appNames, appPrice);
            if (success) {
                purchaseId++;
            }

        }

    }

    //shows the main option menu
    private void showMainMenu() {

        System.out.println("\n============== App Store ==============");
        System.out.println("1 - Register");
        System.out.println("2 - Add new app");
        System.out.println("3 - Show all users and programmers");
        System.out.println("4 - Buy/Subscribe to an app");
        System.out.println("5 - Add review to an app");
        System.out.println("6 - List apps by ... ");
        System.out.println("7 - Show apps ordered by ...");
        System.out.println("8 - Show reviews of an app");
        System.out.println("9 - Show users ordered by values they paid");
        System.out.println("10 - Show programmer total amount");
        System.out.println("11 - Show application total amount");
        System.out.println("12 - Skip one day");
        System.out.println("13 - Skip one year");
        System.out.println("0 - Exit");
        System.out.println("========================================");

    }

    //----------------ADDING USERS,PROGRAMMERS,APPS AND REVIEWS---------------
    //this function is called when user chooses to register an user
    private boolean registerUser() throws IOException {
        String name, ageLine, sure, premiumLine, userRecommended;
        int age;
        boolean premium;

        System.out.println("Please enter user`s name:");
        name = reader.readLine();

        while (!validateName(name)) {
            System.out.println("Please use only letters, digits, underscore and dot for the name. ");
            System.out.println("The name can start and finish only with letters. ");
            System.out.println("\nPlease enter another name. ");
            name = reader.readLine();
        }

        if (validateUserName(name)) {
            System.out.println("An invalid name was entered. This name may already exist.");
            return false;
        }

        System.out.println("Please enter user`s age:");
        ageLine = reader.readLine();

        while (!validateAge(ageLine)) {
            System.out.println("An invalid age was entered. ");
            System.out.println("\nPlease enter another age. ");
            ageLine = reader.readLine();
        }

        age = Integer.parseInt(ageLine);

        System.out.println("Is this a preimum user?\nPress 'Y' + enter to confirm, and 'N' + enter to cancel.");
        premiumLine = reader.readLine();

        while (!validateAnswer(premiumLine)) {
            System.out.println("An invalid answer was entered. ");
            System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
            premiumLine = reader.readLine();
        }

        premium = premiumLine.equals("Y");

        System.out.println("Did one of our user recommend you this AppStore? If so, please enter his name. If not press enter to continue.");
        userRecommended = reader.readLine();

        //looks for the user and increases it recommendations value
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getName().equals(userRecommended)) {
                user.setRecommendations(user.getRecommendations() + 1);
            }
        }

        System.out.println("Confirm that the data is correct.\nPress 'Y' + enter to confirm, and 'N' + enter to cancel.");
        sure = reader.readLine();

        while (!validateAnswer(sure)) {
            System.out.println("An invalid answer was entered. ");
            System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
            sure = reader.readLine();
        }

        if (!sure.equals("Y")) {
            return false;
        } else {
            //returns true if the user was added successfully to the treeset
            return addNewUser(name, age, premium);
        }
    }

    //this function adds a new user to the treeset
    public boolean addNewUser(String name, int age, boolean premium) {
        User user = new User(name, age, userId, premium);
        boolean success;

        success = users.add(user);
        userId++;
        return success;
    }

    //this function is called when user chooses to register a programmer
    private boolean registerProgrammer() throws IOException {
        String name, sure;

        System.out.println("Please enter programmer`s name:");
        name = reader.readLine();

        while (!validateName(name)) {
            System.out.println("Please use only letters, digits, underscore and dot for the name. ");
            System.out.println("The name can start and finish only with letters. ");
            System.out.println("\nPlease enter another name. ");
            name = reader.readLine();
        }

        if (validateProgrammerName(name)) {
            System.out.println("An invalid name was entered. This name may already exist.");
            return false;
        }

        System.out.println("Are you sure?\nPress 'Y' + enter to confirm, and 'N' + enter to cancel.");
        sure = reader.readLine();

        while (!validateAnswer(sure)) {
            System.out.println("An invalid answer was entered. ");
            System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
            sure = reader.readLine();
        }

        if (!sure.equals("Y")) {
            return false;
        } else {
            return addNewProgrammer(name);
        }
    }

    //this function adds a new programmer to the treeset
    private boolean addNewProgrammer(String name) {
        Programmer programmer = new Programmer(name);
        boolean success;

        success = programmers.add(programmer);
        return success;
    }

    //this function is called when user chooses to add a new app
    private boolean addApp() throws IOException {
        String name, sure, priceLine, programmerName, subscriptionLine, categoryLine;
        boolean subscription;
        double price;
        int category = 0;
        String userCategory = "";

        System.out.println("Please enter the name of the app:");
        name = reader.readLine();

        while (!validateName(name)) {
            System.out.println("Please use only letters, digits, underscore and dot for the name. ");
            System.out.println("The name can start only with letters. ");
            System.out.println("\nPlease enter another name. ");
            name = reader.readLine();
        }

        if (validateAppName(name)) {
            System.out.println("An invalid name was entered. This name may already exist.");
            return false;
        }

        System.out.println("Please enter the price of the app:");
        priceLine = reader.readLine();

        while (!validatePrice(priceLine)) {
            System.out.println("An invalid price was entered. ");
            System.out.println("\nPlease enter the price again. ");
            priceLine = reader.readLine();
        }
        price = Double.parseDouble(priceLine);

        System.out.println("Please enter the name of the programmer that developed this app:");
        programmerName = reader.readLine();

        while (!validateProgrammerName(programmerName)) {
            System.out.println("An invalid name was entered. ");
            System.out.println("\nPlease enter the name again. ");
            programmerName = reader.readLine();
        }

        System.out.println("Do you want to add one-year subscription?\nPress 'Y' + enter to confirm, and 'N' + enter to cancel.");
        subscriptionLine = reader.readLine();

        while (!validateAnswer(subscriptionLine)) {
            System.out.println("An invalid answer was entered. ");
            System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
            subscriptionLine = reader.readLine();
        }

        subscription = subscriptionLine.equals("Y");

        System.out.println("Please choose one category:");
        System.out.println("1. GAMES ");
        System.out.println("2. BUSINESS ");
        System.out.println("3. EDUCATION ");
        System.out.println("4. LIFESTYLE ");
        System.out.println("5. ENTERTAINMENT");
        System.out.println("6. UTILITIES ");
        System.out.println("7. TRAVEL ");
        System.out.println("8. HEALTH & FITNESS");

        try {
            categoryLine = reader.readLine();
            category = Integer.parseInt(categoryLine);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid Option");
        }

        Category[] categories = Category.values();

        for (int i = 0; i < categories.length; i++) {
            if (categories[i].ordinal() == category - 1) {
                userCategory = categories[i].name();
            }
        }

        System.out.println("Are you sure?\nPress 'Y' + enter to confirm, and 'N' + enter to cancel.");
        sure = reader.readLine();

        while (!validateAnswer(sure)) {
            System.out.println("An invalid answer was entered. ");
            System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
            sure = reader.readLine();
        }

        if (!sure.equals("Y")) {
            return false;
        } else {
            return addNewApp(name, price, programmerName, userCategory, subscription);
        }
    }

    //this function adds a new app to the treeset
    private boolean addNewApp(String name, double price, String programmerName, String userCategory, boolean subscription) {
        App app = new App(name, price, programmerName, userCategory, subscription);

        Iterator<Programmer> iterator = programmers.iterator();
        while (iterator.hasNext()) {
            Programmer programmer = iterator.next();
            if (programmer.getName().equals(programmerName)) {
                if (programmer.addAppToProgrammer(app)) {
                    if (apps.add(app)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //this function is called when user chooses to review an app
    private boolean review() throws IOException {
        String userName, sure, ratingLine, appName, comment;
        int rating;

        System.out.println("Please enter your user name:");
        userName = reader.readLine();

        while (!validateUserName(userName)) {
            System.out.println("An invalid name was entered. ");
            System.out.println("\nPlease enter the name again. ");
            userName = reader.readLine();
        }

        System.out.println("Please choose the app you want to review:");
        Iterator<App> iterator = apps.iterator();
        System.out.println("---------- Apps ----------");
        while (iterator.hasNext()) {
            App app = iterator.next();
            System.out.println(app.getName());
        }
        System.out.println("--------------------------");
        appName = reader.readLine();

        while (!validateAppName(appName)) {
            System.out.println("An invalid name was entered. ");
            System.out.println("\nPlease enter the name again. ");
            appName = reader.readLine();
        }

        Iterator<User> useriterator = users.iterator();
        while (useriterator.hasNext()) {
            User user = useriterator.next();
            if (user.getName().equals(userName)) {
                if (!user.isPurchased(appName)) {
                    System.out.println("You did not purchase this app.");
                    return false;
                }
            }
        }

        Iterator<App> appiterator = apps.iterator();
        while (appiterator.hasNext()) {
            App app = appiterator.next();
            if (app.getName().equals(appName)) {
                for (Review r : app.getReviews().values()) {
                    if (r.getUserName().equals(userName)) {
                        System.out.println("You have already reviewed this app. ");
                        return false;
                    }
                }
            }
        }

        System.out.println("Please review this app:");
        System.out.println("Please give a rating from 1 to 5");
        ratingLine = reader.readLine();

        while (!validateRating(ratingLine)) {
            System.out.println("An invalid rating was entered. ");
            System.out.println("\nPlease enter the rating again. ");
            ratingLine = reader.readLine();
        }
        rating = Integer.parseInt(ratingLine);

        System.out.println("Please add acomment");
        comment = reader.readLine();

        System.out.println("Are you sure?\nPress 'Y' + enter to confirm, and 'N' + enter to cancel.");
        sure = reader.readLine();

        while (!validateAnswer(sure)) {
            System.out.println("An invalid answer was entered. ");
            System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
            sure = reader.readLine();
        }

        if (!sure.equals("Y")) {
            return false;
        } else {
            return addReviewToApp(userName, rating, comment, appName);
        }

    }

    //this function adds a new review to app and to the treeset
    private boolean addReviewToApp(String userName, int rating, String comment, String appName) {
        Review review;

        if (comment.equals("")) {
            review = new Review(userName, rating);
        } else {
            review = new Review(userName, rating, comment);
        }

        Iterator<App> iterator = apps.iterator();
        while (iterator.hasNext()) {
            App app = iterator.next();
            if (app.getName().equals(appName)) {
                if (app.addReviewToApp(review)) {
                    if (reviews.add(review)) {
                        app.countReviews();
                        app.updateRating(rating);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //-------------------MAKING A PURCHASE----------------------
    //this function is called when the user chooses to make a purchase
    private boolean makePurchase() throws IOException {
        String name, sure, userName;
        String[] appNames;
        Calendar purchaseDate;
        double purchasePrice = 0;

        System.out.println("Please enter the name of the user who is buying:");
        userName = reader.readLine();

        while (!validateUserName(userName)) {
            System.out.println("An invalid name was entered. ");
            System.out.println("\nPlease enter another name. ");
            userName = reader.readLine();
        }

        System.out.println("Please choose the apps you want to buy by writing the names of the apps as follows: \" App1,App2\":");
        Iterator<App> iterator = apps.iterator();
        System.out.println("---------- Apps ----------");
        while (iterator.hasNext()) {
            App app = iterator.next();
            System.out.println(app.toString());
        }
        System.out.println("--------------------------");
        name = reader.readLine();
        appNames = name.split(",");

        for (int i = 0; i < appNames.length; i++) {
            while (!validateAppName(appNames[i])) {
                System.out.println("An invalid name was entered. ");
                System.out.println("Please use only letters, digits, underscore and dot for the name. ");
                System.out.println("The name can start only with letters. ");
                System.out.println("\nPlease enter another names. ");
                name = reader.readLine();
                appNames = name.split(",");
            }
        }

        Iterator<User> iteratorUser1 = users.iterator();
        while (iteratorUser1.hasNext()) {
            User user = iteratorUser1.next();
            if (user.getName().equals(userName)) {
                for (int i = 0; i < appNames.length; i++) {
                    //check if has subs check if expired
                    if (user.checkIfSubscriptionExpired(appNames[i], today.getTime())) {
                        System.out.println("Subscription to " + appNames[i] + " app expired. Do you want to renew the subscription?");
                        user.getBoughtApps().remove(appNames[i]);
                        break;
                    } else {
                        while (user.isPurchased(appNames[i])) {

                            System.out.println("You already bought " + appNames[i] + " app ");
                            return false;
                        }
                    }
                }
            }
        }

        System.out.println("\nPress 'Y' + enter to confirm, and 'N' + enter to cancel.");
        sure = reader.readLine();

        while (!validateAnswer(sure)) {
            System.out.println("An invalid answer was entered. ");
            System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
            sure = reader.readLine();
        }

        double[] appPrice = new double[10];

        if (!sure.equals("Y")) {
            return false;
        } else {

            for (int i = 0; i < appNames.length; i++) {
                Iterator<App> iterator1 = apps.iterator();
                while (iterator1.hasNext()) {
                    App app = iterator1.next();
                    if (app.getName().equals(appNames[i])) {

                        appPrice[i] = app.getPrice();
                        //check if belongs to the 5 apps of last week which had less purchases
                        if (checkIfFiveApps(app)) {
                            appPrice[i] -= app.applyDiscount(app.getFiveAppsDiscount(), app.getPrice());
                            System.out.println("The " + app.getName() + " app had less purchases last week so a 15% discount was applied. After applying discount: " + String.format("%.2f", appPrice[i]));

                        } else if (app.getCategory().equals(categoryWithDiscount.name())) {
                            appPrice[i] -= app.applyDiscount(app.getMonthlyDiscount(), appPrice[i]);
                            System.out.println("The" + app.getName() + " app belongs to " + app.getCategory() + " category, so a 5% discount was applied. After applying discount: " + String.format("%.2f", appPrice[i]));
                        }

                        Iterator<User> iteratorUser = users.iterator();
                        while (iteratorUser.hasNext()) {
                            User user = iteratorUser.next();
                            if (user.getName().equals(userName)) {
                                if (user.isPremium()) {
                                    appPrice[i] -= app.applyDiscount(app.getPremiumUserDiscount(), appPrice[i]);
                                    System.out.println("You are a premium user. You got a 40% discount to " + app.getName() + " app. After applying discount: " + String.format("%.2f", appPrice[i]));
                                }
                            }
                        }

                        purchasePrice += appPrice[i];
                    }
                }
            }

            purchasePrice -= applyRecomendedDiscount(userName, purchasePrice);

            System.out.println("\nFor this purchase you will have to pay " + String.format("%.2f", purchasePrice) + "$.Do you want to ontinue with the payment?[Y/N]");
            sure = reader.readLine();
            while (!validateAnswer(sure)) {
                System.out.println("An invalid answer was entered. ");
                System.out.println("\nPlease press 'Y' + enter to confirm, and 'N' + enter to cancel ");
                sure = reader.readLine();
            }
            if (!sure.equals("Y")) {
                return false;
            } else {
                today = Calendar.getInstance();
                purchaseDate = today;
                return addNewPurchase(purchaseId, purchaseDate.getTime(), purchasePrice, userName, appNames, appPrice);
            }
        }
    }

    //this function adds the purchase to the tree set and to the user
    private boolean addNewPurchase(int purchaseId, Date purchaseDate, double purchasePrice, String userName, String[] appNames, double[] appPrice) {
        Purchase purchase = new Purchase(purchaseId, purchaseDate, purchasePrice);

        //updates total value of the programmer
        for (int j = 0; j < appNames.length; j++) {
            Iterator<Programmer> iteratorP = programmers.iterator();
            while (iteratorP.hasNext()) {
                Programmer programmer = iteratorP.next();
                for (App a : programmer.getProgrammer().values()) {
                    if (a.getName().equals(appNames[j])) {
                        programmer.updateTotalValue(appPrice[j]);
                        //System.out.println(programmer.getName()+"---"+ programmer.getTotalValue());
                    }
                }
            }
        }

        //counts the weekly purchases
        for (int i = 0; i < appNames.length; i++) {
            Iterator<App> iterator1 = apps.iterator();
            while (iterator1.hasNext()) {
                App app = iterator1.next();
                if (app.getName().equals(appNames[i])) {
                    if (purchase.addAppToPurchase(app)) {
                        app.countPurchases();
                        app.countWeeklyPurchases();
                    }
                }
            }
        }

        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getName().equals(userName)) {
                if (user.addPurchaseToUser(purchase)) {
                    user.updateTotalAmount(purchasePrice);
                    for (int i = 0; i < appNames.length; i++) {
                        Iterator<App> iterator1 = apps.iterator();
                        while (iterator1.hasNext()) {
                            App app = iterator1.next();
                            if (app.getName().equals(appNames[i])) {
                                if (user.addAppToUser(app)) {
                                    if (purchases.add(purchase)) {
                                        totalAmount += purchasePrice;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    //------------------------LISTING------------------------
    private void listUsers() {
        System.out.println("\n------------- USERS -------------");
        users.forEach((temp) -> {
            System.out.print(temp + "\n");
        });
    }

    private void listProgrammers() {
        System.out.println("\n---------- PROGRAMMERS ----------");
        programmers.forEach((temp) -> {
            System.out.print(temp + "\n");
        });
    }

    private void getAppsByCategory(String category) {
        Iterator<App> iterator = apps.iterator();
        while (iterator.hasNext()) {
            App app = iterator.next();
            if (app.getCategory().equals(category)) {
                System.out.println(app.toString());
            }
        }
    }

    private void getAppsByRating(double rating) {
        Iterator<App> iterator = apps.iterator();
        while (iterator.hasNext()) {
            App app = iterator.next();
            if (app.getRating() >= rating) {
                System.out.println(app.toString());
            }
        }
    }

    private void getReviewsOf(String name) {
        Iterator<App> iterator = apps.iterator();
        while (iterator.hasNext()) {
            App app = iterator.next();
            if (app.getName().equals(name)) {
                for (Review r : app.getReviews().values()) {
                    System.out.println(r.toString() + "\n");
                }
            }
        }
    }

    private void getUsersByValuePaid() {
        ArrayList<User> userByValue = new ArrayList<>();

        users.forEach((temp) -> {
            userByValue.add(temp);
        });

        Comparator<User> byValue = Comparator.comparing(User::getTotalAmountPaid);
        Collections.sort(userByValue, byValue);

        System.out.println("\n------------- USERS ORDERED BY VALUES THEY PAID -------------");
        for (User u : userByValue) {
            System.out.println(u.getName() + "-" + String.format("%.2f", u.getTotalAmountPaid()));
        }
    }

    //---------------------DATE CHECKING---------------------
    private boolean checkIfEndOfTheWeek() {
        if (today.get(Calendar.DAY_OF_WEEK) == 7) {
            return true;
        }
        return false;
    }

    private boolean checkIfEndOfTheMonth() {
        if (today.get(Calendar.DAY_OF_MONTH) == today.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            return true;
        }

        return false;
    }

    private void passOneDay() {
        today.add(Calendar.DAY_OF_WEEK, 1);
        System.out.println("One day has passed: " + sdf.format(today.getTime()));

    }

    private void passOneYear() {
        today.add(Calendar.YEAR, 1);
        System.out.println("One year has passed: " + sdf.format(today.getTime()));

    }

    //---------------------DISCOUNT RELATED------------------------
    private double applyRecomendedDiscount(String userName, double purchasePrice) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getName().equals(userName)) {
                if (user.getRecommendations() > 0) {
                    purchasePrice = user.applyDiscount(user.getRecommendedDiscount(), purchasePrice);
                    user.setRecommendations(user.getRecommendations() - 1);
                    System.out.println("You recoomended this AppStore to another user, so you got 5% discount to this purchase.");
                } else {
                    purchasePrice = 0;
                }
            }
        }
        return purchasePrice;
    }

    //checks if the app is one of the first 5 apps with least purchases last week
    private boolean checkIfFiveApps(App app) {

        ArrayList<App> appsByPurchasesLastWeek = new ArrayList<>();

        apps.forEach((temp) -> {
            appsByPurchasesLastWeek.add(temp);
        });

        Comparator<App> byPurchaseseLastWeek = Comparator.comparing(App::getNrOfPurchasesLastWeek);
        Collections.sort(appsByPurchasesLastWeek, byPurchaseseLastWeek);

        for (int i = 0; i < 5; i++) {
            if (appsByPurchasesLastWeek.get(i).equals(app)) {
                return true;
            }
        }

        return false;
    }

    //shows total amount earned by app store
    private void showTotalAmount() {
        System.out.println("Total Amount: " + String.format("%.2f", totalAmount));
    }

    //--------------------------VALIDATING-----------------------
    //validating the name
    //it only can start with letters
    //it can have dot, underscore, digits, letters
    //it can not finish with dot
    private boolean validateName(String str) {
        return ((!str.isEmpty())
                && (str.matches("^[a-zA-Z]([._](?![._])|[a-zA-Z0-9]){1,20}[a-zA-Z0-9]$")));
    }

    //validating age
    private boolean validateAge(String age) {
        return ((!age.isEmpty())
                && (age.matches("^[0-9]{2}")));
    }
    
    //validating [Y/N] answer
    private boolean validateAnswer(String answer) {
        return ((!answer.isEmpty())
                && (answer.equals("Y")) || (answer.equals("N")));
    }

    //validating price (dot format)
    private boolean validatePrice(String price) {
        return ((!price.isEmpty())
                && (price.matches("[0-9]+([.][0-9]{1,2})?")));
    }

    //validating rating (digits between 1 and 5)
    private boolean validateRating(String rating) {
        return ((!rating.isEmpty())
                && (rating.matches("^[1-5]{1}")));
    }

    //validating programmer name
    //checks is the name already exists
    private boolean validateProgrammerName(String name) {

        if (validateName(name)) {
            Iterator<Programmer> iterator = programmers.iterator();
            while (iterator.hasNext()) {
                Programmer programmer = iterator.next();
                if (programmer.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    //validating user name
    //checks is the name already exists
    private boolean validateUserName(String name) {

        if (validateName(name)) {
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()) {
                User user = iterator.next();
                if (user.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    //validating app name
    //checks is the name already exists
    private boolean validateAppName(String name) {

        if (validateName(name)) {
            Iterator<App> iterator = apps.iterator();
            while (iterator.hasNext()) {
                App app = iterator.next();
                if (app.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

}
