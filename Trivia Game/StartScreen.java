import mayflower.*;

public class StartScreen extends World {
    
    Button playButton;
    Button settingsButton;
    private TopicScreen topicScreen;
    
    // Constructor that creates topic screen, sets background image, adds title image,
    // creates and adds play, exit, and leaderboard button
    public StartScreen() {
        topicScreen = new TopicScreen();
        
        setBackground(new MayflowerImage("img/machabg.png"));
        
        MayflowerImage titleImage = new MayflowerImage("img/title.png");
        titleImage.scale(350, 47);
        addObject(new Image(titleImage), 225, 70);
        
        MayflowerImage play = new MayflowerImage("img/play.png");
        play.scale(150,150);
        addObject(new Button(play, topicScreen, "topicScreen"), 325, 225);
        
        MayflowerImage leaderboard = new MayflowerImage("img/leaderboardButton.png");
        addObject(new Button(leaderboard, "leaderboard", "start"), 262, 409);
        
        MayflowerImage exit = new MayflowerImage("img/exitbutton.png");
        addObject(new Button(exit, "exit"), 312, 485);
    }
    
    // Returns TopicScreen - topicScreen
    public TopicScreen getTopicScreen() {
        return topicScreen;
    }
    
    public void act() {
        
    }
}