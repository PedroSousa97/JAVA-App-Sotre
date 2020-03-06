# Java-App-Sotre

The aim of this project was to develop an application that allows managing an “App Store”. The overall goal of this application is to allow the insertion and the consultation of the relevant data.

### Application Overview and Objectives

The “App Store” application consists of several components: users, programmers, applications developed by the programmers, app reviews and purchases made by the users.
This program implements a solution that will perform all the required operations:
* Registering new users;
* Adding new programmers in the applications;
* Adding new apps with the necessary information;
* Listing the users and the programmers in the application;
* Simulating a purchase of an app;
* Review an app;
* Making selective app listings;
* Making ordered app listings;
* Listing the reviews of a specific app;
* Listing the users according to the values they paid;
* Showing the total amount of money gained by the application;
* Showing the total amount of money each programmer gained from his apps.

### Implementation

In order to implement the desired application, we had to implement some key concepts and techniques of java programming language. This section describes each of the previous mentioned components and how we implemented these concepts in order to achieve our goal.

Firstly, before starting the application, the program loads the data from files (programmers, users, apps). This data is saved in TreeSets - one of the most important implementations of the SortedSet interface in Java that uses a Tree for storage. 
We chose this implementation because the ordering of the elements is maintained by a set using their natural ordering whether or not an explicit comparator is provided. This must be consistent with equals if it is to correctly implement the Set interface.
When comparing the elements of the app , the TreeSet constructor permitted us to provide a Comparator at set creation time. Also, TreeSet implements the SortedSet interface so duplicate values are not allowed.

#### Users
The users can be of two type: normal users and premium users. Each user in our application is identified with a user ID or name. This is because two users cannot have the same username. In addition, each of them is characterized by age (which cannot be less than 16 years old), purchases they made and the amount of money they paid for all apps they purchased.
In the users file we have the following line format: username,age,true/false,digit. The “true/false” is for verifying if the user is premium or not and the last digit represents how many recommendation this user made to other users.
When buying app(s), there is a discount of 40% applied to that/those app(s) according to the type of the user (premium user). To achieve that we overridden in the App class the method of Discount Interface, which applies this discount to the price of each bought app.

#### Programmers
For this application, we also supposed that two programmers cannot have the same name. Each of them is characterized by the apps they developed, and the total amount of money they earn from users purchases.

#### Apps
Each app is characterized by a unique name, a price, a programmer, an average rating and by its reviews. Also, each of them belongs to one of the categories enumerated in the enum class.
When adding an app, the user will be asked if the app is with subscription or not. As subscription is for a period of one year, there is an option in our main menu where you can skip one year. After that, if buying the app again, the user will be asked if he wants to renew the subscription.

#### Reviews
Each user can rate apps only after they purchased them. In addition to the rating, the user can add a comment. As the comment is optional, the Review class has two constructors. Users can add just one review to an app. If the user tries to comment the app again, an error message will be shown.

#### Purchases
Purchases occurs on a certain date and at a given price. The price depends on how many apps where bought and on the applied discounts. After purchase, the user is asked if he wants to continue with the payment.
Monthly, there is a category that has a discount of 5%. In order to verify that, there is a “skip a day” option in the main menu to see how a category is randomly generated at the end of the month.

#### Validations
For a better functioning of the application, we used validation methods to insure that input data is clean. For example, a username cannot start with a digit or a dot, or a user cannot be 200 years old.

#### Some of the important programming concepts implemented in this application are:
* Definition and use of methods and variables;
* Initialization of objects using constructors;
* TreeSets and TreeMap;
* ArrayLists;
* Enum;
* Date and Calendar;
* Comparable and Comparator Interfaces;
* "Overriding" methods;
* Encapsulation;
* Exceptions handling.

## Authors

* **Pedro Henrique Santos Sousa**
* **Irina Vasilița**
* **Cristóvão Spínola Gouveia**
