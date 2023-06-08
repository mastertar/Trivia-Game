import mayflower.*;
import java.lang.*;

public class Button extends Actor {
    private World link;
    private String value;
    private String currentScreen;
    private boolean pressed;
    
    // Constructor that sets the image and link
    // Sets pressed as false
    public Button(MayflowerImage image, World link) {
        setImage(image);
        this.link = link;
        value = null;
        pressed = false;
    }
    
    // Constructor that sets the image, link, and value
    // Sets pressed as false
    public Button(MayflowerImage image, World link, String value) {
        setImage(image);
        this.link = link;
        this.value = value;
        pressed = false;
    }
    
    // Constructor that sets the image, value, and currentScreen
    // Sets pressed as false
    public Button(MayflowerImage image, String value, String currentScreen) { 
        setImage(image);
        this.currentScreen = currentScreen;
        this.value = value;
        pressed = false;
    }
    
    // Constructor that sets the image, value
    // Sets pressed as false
    public Button(MayflowerImage image, String value) {
        setImage(image);
        this.value = value;
        link = null;
        pressed = false;
    }
    
    // Returns String - value
    public String getValue() {
        return value;
    }
    
    // Sets pressed to true
    // Returns: void
    public void disable() {
        pressed = true;
    }
    
    // Code that runs forever, that checks to see if a button is pressed
    // if the currentScreen is null and value is null, then call the evaluateValueAndCurrentScreen method to find the correct action
    // if the value is null, then call the evaluateValue method to find the correct action
    // if link is not null, then set the world to link
    public void act() {
        if(Mayflower.mousePressed(this) && !pressed) {
            if(currentScreen != null && value != null) {
                evaluateValueAndCurrentScreen();
            }
            if(value != null) {
                evaluateValue();
            }
            if(link != null) {
                Mayflower.setWorld(this.link);
            }
        }
    }
    
    // Evaluates the value and currentScreen for a button
    // if the currentScreen equals "topicSelection", then check to see if value is "back",
    // if so, then set the link to startWorld
    // if the value equals any of the categories, then set the link to the playscreen
    // if the currentScreen equals "playscreen",  then disable the buttons on the playscreen,
    // and set the userChoice to the absolute value of the letter minus A
    // if the currentScreen equals "leaderboardscreen" and value equals "home",
    // then set the link to startWorld
    // if the currentScreen is "start" and the value is "leaderboard",' then set the link to leaderboard
    // Returns: void
    public void evaluateValueAndCurrentScreen() {
        if(currentScreen.equals("topicSelection")) {
            if(value.equals("back")) {
                    this.link = Main.getMayflower().getStartWorld();
            } else if(value.equals("brain-teasers") || value.equals("general") || value.equals("entertainment") || value.equals("for-kids")) {
                Main.getMayflower().getStartWorld().getTopicScreen().setPlayScreen(new PlayScreen(value));
                this.link = Main.getMayflower().getStartWorld().getTopicScreen().getPlayScreen();
            }
        } else if(currentScreen.equals("playscreen")) {
            PlayScreen playscreen = Main.getMayflower().getStartWorld().getTopicScreen().getPlayScreen();
            playscreen.disableButtons();   
            playscreen.setUserChoice(Math.abs(value.charAt(0) - 'A'));
        } else if(currentScreen.equals("leaderboardscreen") && value.equals("home")) {
            this.link = Main.getMayflower().getStartWorld();
        } else if(currentScreen.equals("start") && value.equals("leaderboard")) {
            this.link = new LeaderboardScreen();
        }
    }
    
    // Evaluates the value of a button
    // if the button's value is "exit", then exit the program
    // else if the value is "saveuser", and the user has inputted a name, then set the link to a new LeaderboardScreen()
    // if the value is "nextQuestion" then it clears the playscreen and generates a new screen
    // Returns: void
    public void evaluateValue() {
        if(value.equals("exit")) {
            System.exit(0);
        } else if(value.equals("saveuser")) {
            Main.getMayflower().getStartWorld().getTopicScreen().getPlayScreen().getUsernameScreen().saveUser();
            if(!Main.getMayflower().getStartWorld().getTopicScreen().getPlayScreen().getUsernameScreen().getName().equals("")) {
                link = new LeaderboardScreen();
            }
        } else if(value.equals("nextQuestion")) {
            PlayScreen playscreen = Main.getMayflower().getStartWorld().getTopicScreen().getPlayScreen();
            playscreen.clearScreen();
            playscreen.generateScreen();
        }
    }
}