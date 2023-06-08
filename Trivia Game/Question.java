import java.util.*;
import java.io.*;
public class Question {
    
    private String question;
    private String correctAnswer;
    private int correctAnswerChoice;
    private ArrayList<String> answers;
    
    // Constructor that intializes instance variables
    public Question(String question, String correctAnswer, ArrayList<String> answers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
        this.correctAnswerChoice = createCorrectAnswerLetter();
    }
    
    // Returns int - correctAnswerChoice
    public int createCorrectAnswerLetter() {
        return answers.indexOf(correctAnswer);
    }
    
    // Returns int - correctAnswerChoice
    public int getCorrectAnswerChoice() {
        return correctAnswerChoice;
    }
    
    // Returns String - question
    public String getQuestion() {
        return question;
    }
    
    // Returns String - correctAnswer
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    // Returns ArrayList<String> - answers
    public ArrayList<String> getAnswers() {
        return answers;
    }
}
