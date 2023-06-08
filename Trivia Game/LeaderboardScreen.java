import java.util.*;
import java.io.*;
import mayflower.*;

public class LeaderboardScreen extends World {
    protected static TreeMap<Integer,ArrayList<String>> map;
    
    // Constructor that adds title, leaderboard, and home button
    // Reads in leaderboard after decompression, and compresses it again
    // If a user just finished playing, then it adds them to the map
    public LeaderboardScreen() {   
        setBackground(new MayflowerImage("img/machabg.png"));
        MayflowerImage titleImage = new MayflowerImage("img/title.png");
        titleImage.scale(350, 47);
        addObject(new Image(titleImage), 225, 70);
        map = new TreeMap<>(Collections.reverseOrder());
        Compression.decompress();
        try{
            readIn();
        } catch(Exception e){
            System.out.println("leaderboard.txt not found");
        }
        if(Main.getMayflower().getStartWorld().getTopicScreen() != null && Main.getMayflower().getStartWorld().getTopicScreen().getPlayScreen() != null && Main.getMayflower().getStartWorld().getTopicScreen().getPlayScreen().getUser() != null) {
            Person user = Main.getMayflower().getStartWorld().getTopicScreen().getPlayScreen().getUser();
            add(user);
            print();
            Main.getMayflower().getStartWorld().getTopicScreen().getPlayScreen().resetUser();
        }
        Compression.compress();
        MayflowerImage home = new MayflowerImage("img/home.png");
        home.scale(150,150);
        addObject(new Button(home, "home", "leaderboardscreen"), 325, 425);
        addObject(new Image(new MayflowerImage("img/leaderboardback.png")), 150, 187);
        displayLeaderboard();
    }
    
    // Displays leaderboard onto the screen
    // Shows name at x and y value and their score, with points or point appended to it
    // based on the score
    // Only top 5 scores participants are displayed, and if multiple have the same score,
    // then the participant who got the score first is displayed
    public void displayLeaderboard() {
        int counter = 0;
        int x = 160;
        int y = 235;
        for(int score : map.keySet()) {
               for(String name : map.get(score)) {
                    if(score != 1) {
                        showText(name + " : " + score + " points!", x, y, Color.BLACK);
                    } else {
                        showText(name + " : " + score + " point!", x, y, Color.BLACK);
                    }
                    y += 35;
                    counter++;
                    if(counter == 5) {
                        return;
                    }
                }
        }
    }
    
    // Reads file through scanner
    // Compression.answer is a variable in the Compression class that points to the file name
    // Reads and puts all the scores and names into the map
    private void readIn() throws FileNotFoundException {
        Scanner in = new Scanner(Compression.answer);
        while(in.hasNextLine()) {
            int score = in.nextInt();
            String[] names = in.nextLine().trim().split("\\s");
            map.put(score , new ArrayList<>(Arrays.asList(names)));
        }
    }
    
    // Adds passed in Person p to map at score
    public void add(Person p) { 
        int score = p.getScore();
        String name = p.getUsername();
        if(map.get(score) == null) {
            map.put(score , new ArrayList<>());
        }
        map.get(score).add(name);
    }
    
    // Prints the leaderboard onto the file, with FileWriter and PrintWriter
    // Formats the leaderboard file as Score Name Name...
    // if the file cannot be found, then it alerts the user that the file cannot be created
    public void print() {
        try{
           PrintWriter out = new PrintWriter(new FileWriter("leaderboard.txt"));
           for(int score : map.keySet()) {
               out.print(score + " ");
               for(String name : map.get(score)) {
                    out.print(name + " ");
               }
               out.println();
            }
           out.close();
       } catch (IOException e){
           System.out.println("File cannot be created");
       }
    }
    
    public void act() {
        
    }
}