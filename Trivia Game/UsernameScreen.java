import mayflower.*;
public class UsernameScreen extends World {
    private String name;
    private MyMayflower mayflower;
    private PlayScreen playscreen;
    private boolean toggle;
    private Timer timer;
    
    // Constructor that adds all the onscreen elements including the "textbox", continue button,
    // and inputfield background. Also, sets the background
    // Sets name to empty String, toggle to false, and mayflower to mymayflower in the main class
    public UsernameScreen() {
        name = "";
        mayflower = Main.getMayflower();
        toggle = false;
        playscreen = mayflower.getStartWorld().getTopicScreen().getPlayScreen();
        
        setBackground(new MayflowerImage("img/machabg.png"));
        MayflowerImage titleImage = new MayflowerImage("img/title.png");
        titleImage.scale(350, 47);
        addObject(new Image(titleImage), 225, 70);        
        MayflowerImage nameImage = new MayflowerImage("img/entername.png");
        nameImage.scale(217, 25);
        addObject(new Image(nameImage), 12, 275);        
        MayflowerImage scoreImage = new MayflowerImage("img/finalscore.png");
        scoreImage.scale(131, 25);
        addObject(new Image(scoreImage), 12, 325);
        MayflowerImage inputfield = new MayflowerImage("img/inputField.png");
        addObject(new Image(inputfield), 235, 265);
        addObject(new Button(new MayflowerImage("img/continuebutton.png"), "saveuser"), 262, 400);
        timer = new Timer(500000000);
        showText(Integer.toString(playscreen.getUser().getScore()), 235, 350, Color.BLACK);
    }
    
    // Gets the user from playscreen, and sets username
    public void saveUser() {
        Person user = playscreen.getUser();
        user.setUsername(name);
    }
    
    // Returns name
    public String getName() {
        return name;
    }
    
    // Checks to see if a keyboard letter or backspace is pressed, and adds or removes character
    // respectively
    // Toggles show text to create cursor feel using Timer
    public void act() {
        int key = mayflower.getKey();
        if(mayflower.isKeyPressed(key)) {
            if((key >= 65 && key <= 90) && name.length() < 15 ) {
                name += (char) key;
            }
            if(key == 8 && name.length() > 0) {
                name = name.substring(0, name.length()-1);
            }
        }
        if(toggle) {
            showText(name+ "|", 240, 300, Color.BLACK);
        } else {
            showText(name, 240, 300, Color.BLACK);
        }
        if(timer.isDone()) {
            toggle = !toggle;
            timer.reset();
        }
    }
}