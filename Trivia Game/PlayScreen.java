import mayflower.*;
import java.util.*;
import java.io.*;

public class PlayScreen extends World {
    private Button playButton;
    private Button a;
    private Button b;
    private Button c;
    private Button d;
    private Question questionObj;
    private Stack<Question> questions;
    private int choice;
    private String category;
    private Person user;
    private int numChoices;
    private UsernameScreen usernameScreen;
    
    // Constructor that initializes questions, user, and sets the category as category passed in
    public PlayScreen(String category) {
        questions = new Stack<>();
        user = new Person();
        setCategory(category);
    }
    
    // Resets user by setting it to null
    public void resetUser() {
        user = null;
    }
    
    // Parameters: String - category
    // sets category to category passed in
    // calls createQuestions and generateScreen
    public void setCategory(String category) {
        this.category = category;
        createQuestions();
        generateScreen();
    }
    
    // Returns usernameScreen
    public UsernameScreen getUsernameScreen() {
        return usernameScreen;
    }
    
    // Adds all text and images that are on the screen other than the questions and answers
    public void drawStaticElements() {
        a = new Button(new MayflowerImage("img/choiceRegular.png"), "A", "playscreen");
        addObject(a, 12, 195);
        b = new Button(new MayflowerImage("img/choiceRegular.png"), "B", "playscreen");
        addObject(b, 12, 295);
        
        if(numChoices > 2) {
            c = new Button(new MayflowerImage("img/choiceRegular.png"), "C", "playscreen");
            addObject(c, 12, 395);
            d = new Button(new MayflowerImage("img/choiceRegular.png"), "D", "playscreen");
            addObject(d, 12, 495);
        }
        
        addObject(new Image(new MayflowerImage("img/topbar.png")), 5, 5);
        showText("Lives: " + user.getLives(), 15, 30, Color.BLACK);
        showText("Score: " + user.getScore(), 125, 30, Color.BLACK);
        showText("Category: " + category, 505, 30, Color.BLACK);
        MayflowerImage titleImage = new MayflowerImage("img/title.png");
        titleImage.scale(186, 25);
        addObject(new Image(titleImage), 250, 10);
    }
    
    // Generates screen.
    // Sets background, gets first question off of the Stack questions, unless questions is
    // empty or the user doesn't have any more lives
    // if the questions stack is empty or the user has no more lives, then the screen will progress to the
    // username screen
    public void generateScreen() {
        setBackground(new MayflowerImage("img/machabg.png"));
        if(!questions.isEmpty() && user.getLives() != 0) {
            questionObj = questions.pop();
        } else {
            usernameScreen = new UsernameScreen();
            Mayflower.setWorld(usernameScreen);
        }     
        
        String question = questionObj.getQuestion();
        ArrayList<String> answers = questionObj.getAnswers();
        numChoices = answers.size();
        
        display(question, 30, 75, 25, Color.BLACK);
        generateAnswerChoices(answers);
        drawStaticElements();
    }
    
    // Disables all buttons
    public void disableButtons() {
        a.disable();
        b.disable();
        if(numChoices > 2) {
            c.disable();
            d.disable();
        }
    }
    
    // Parameters: String str - string to display, int x, int y - x and y position
    // int size - font size, Color color for color of text
    // Sets font to "chalkboard" and size to size passed in
    // showsText up to 60 characters, then moves y-position down to create new line
    public void display(String str, int x, int y, int size, Color color) {
        setFont("Chalkboard", size);
        String running = "";
        for(int i = 0; i < str.length(); i++) {
            if(i!=0 && i%60==0) {
                if(!str.substring(i, i+1).equals(" ") && !str.substring(i-1, i).equals(" ")) {
                    running += "-";
                }
                showText(running, x, y, Color.BLACK);
                y += size;
                running = "";
            }
            running += str.substring(i, i+1);
        }
        showText(running, x, y, color);
    }
    
    // Displays answer choices on the screen
    public void generateAnswerChoices(ArrayList<String> answers) {
        int x = 45;
        int y = 250;
        for(int i = 0; i < answers.size(); i++) {
            display(answers.get(i), x, y, 25, Color.DARK_GRAY);
            y += 100;
        }
    }
    
    // Adds all the questions from the specified category to the questions stack
    // then the randomizes the stack
    public void createQuestions() {
        try {
            Scanner in = new Scanner(new FileReader("categories/"+category + ".txt"));
            while(in.hasNextLine()) {
                ArrayList<String> answers = new ArrayList<String>();
                String question = in.nextLine();
                question = question.substring(3);
                String correctAnswer = in.nextLine();
                correctAnswer = correctAnswer.substring(2);
                String nl = in.nextLine();
                while(!nl.equals("")) {
                    answers.add(nl.substring(2));
                    if(in.hasNextLine()) {
                        nl = in.nextLine();
                    } else {
                        break;
                    }
                }
                questions.push(new Question(question, correctAnswer, answers));
            }
        } catch(FileNotFoundException e) {
            System.out.println("Please ensure all files are downloaded correctly");
        }
        Collections.shuffle(questions);
    }
    
    // Returns user
    public Person getUser() {
        return user;
    }
    
    // Parameters: int choice - user's choice from 0 to 3
    // changes the image for the correct answer from a gray border with transparent
    // background to something that is green 
    public void setUserChoice(int choice) {
        if(choice != questionObj.getCorrectAnswerChoice()) {
            int yChoice = choice * 100 + 195;
            MayflowerImage incorrectImg = new MayflowerImage("img/choiceIncorrect.png");
            Image incorrect = new Image(incorrectImg);
            addObject(incorrect, 12, yChoice);
            user.decLives();
        } else {
            user.incScore();
        }
        int yCorrect = questionObj.getCorrectAnswerChoice() * 100 + 195;
        MayflowerImage correctImg = new MayflowerImage("img/choiceCorrect.png");
        Image correct = new Image(correctImg);
        addObject(correct, 12, yCorrect);
        
        MayflowerImage next = new MayflowerImage("img/play.png");
        next.scale(150, 150);
        addObject(new Button(next, "nextQuestion"), 630, 430);
        
    }
    
    public void act() {
        
    }
    
    // Clears screen by removing all objects and adding blank lines
    public void clearScreen() {
        removeObjects(getObjects());
        for(int i = 0; i < 600; i++) {
            showText("", 30, 50+i*25, Color.BLACK);
            showText("", 45, 50+i*25, Color.BLACK);
        }
    }
}
