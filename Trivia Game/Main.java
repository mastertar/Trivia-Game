import mayflower.*;

public class Main {
    private static MyMayflower mayflower;
    
    // Constructor that creates and initializes MyMayflower object
    public static void main(String[] args) {
        mayflower = new MyMayflower();
    }
    
    // Returns MyMayflower - mayflower
    public static MyMayflower getMayflower() {
        return mayflower;
    }
}