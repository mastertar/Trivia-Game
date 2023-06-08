import java.util.*;

public class Person {
    private String username;
    private int score;
    private int lives;
    
    // Constructor that sets score to 0, and lives to 3
    public Person() {
        this.lives = 3;
        this.score = 0;
    }
    
    // Returns username
    public String getUsername() {
       return username;
    }
    
    // Returns score
    public int getScore() {
       return score;
    }
    
    // Increments score
    // Returns; void
    public void incScore() {
        score++;
    }
    
    // Returns lives
    public int getLives() {
       return lives;
    }
    
    // Decrements lives
    public void decLives() {
        lives--;
    }
    
    // Sets username to passed in username
    public void setUsername(String username) {
       this.username = username;
    }
    
    // Returns String format of person call
    public String toString() {
        return username + " " + score;
    }
}