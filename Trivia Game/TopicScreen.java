import mayflower.*;
import java.util.*;
import java.io.*;

public class TopicScreen extends World {
    private String category;
    public static ArrayList<Question> questions;
    private PlayScreen playscreen;
    
    // Constructor that creates onscreen elements including the category buttons,
    // the back button, and the title. It also sets the background
    public TopicScreen() {
        setBackground(new MayflowerImage("img/machabg.png"));
        MayflowerImage titleImage = new MayflowerImage("img/title.png");
        titleImage.scale(350, 47);
        addObject(new Image(titleImage), 225, 70);
        MayflowerImage categoryImage = new MayflowerImage("img/categories.png");
        categoryImage.scale(128, 25);
        addObject(new Image(categoryImage), 336, 200);
        MayflowerImage brainTeasers = new MayflowerImage("img/brainteasersbutton.png");
        brainTeasers.scale(276,74);
        addObject(new Button(brainTeasers,"brain-teasers", "topicSelection"), 114, 240);
        MayflowerImage entertainment = new MayflowerImage("img/entertainmentbutton.png");
        entertainment.scale(276,74);
        addObject(new Button(entertainment, "entertainment", "topicSelection"), 410, 240);
        MayflowerImage forKids = new MayflowerImage("img/forkidsbutton.png");
        forKids.scale(276,74);
        addObject(new Button(forKids, "for-kids", "topicSelection"), 114, 324);
        MayflowerImage general = new MayflowerImage("img/generalbutton.png");
        general.scale(276,74);
        addObject(new Button(general,"general", "topicSelection"), 410, 324);        
        MayflowerImage back = new MayflowerImage("img/back.png");
        back.scale(100,100);
        addObject(new Button(back, "back", "topicSelection"), 690, 10);
    }
    
    // Parameters: PlayScreen: playscreen
    // Sets playscreen to the playscreen passed in
    // Returns: void
    public void setPlayScreen(PlayScreen playscreen) {
        this.playscreen = playscreen;
    }
    
    // Returns PlayScreen - playscreen
    public PlayScreen getPlayScreen() {
        return playscreen;
    }

    public void act() {
        
    }
}
