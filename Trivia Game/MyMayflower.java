import mayflower.*;

public class MyMayflower extends Mayflower  {
    private static StartScreen startWorld;
    
    public MyMayflower() {
        //Create a window with 800x600 resolution
        super("Big Brains Trivia", 800, 600);
        
        // Create and initialize startWorld variable and sets the world
        startWorld = new StartScreen();
        Mayflower.setWorld(startWorld);
    }

    public void init() {
        //Change this to true to run this program in fullscreen mode
        Mayflower.setFullScreen(false);
    }
    
    // Returns StartScreen - startWorld
    public static StartScreen getStartWorld() {
        return startWorld;
    }
}