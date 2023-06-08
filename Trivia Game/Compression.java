import java.util.*;
import java.io.*;
public class Compression {
    
    static class Node implements Comparable<Node>{
        int freq;
        char c;
        Node left , right;
        Node(int freq , char c){
            this.freq = freq;
            this.c = c;
        }
        public int compareTo(Node o){
            return this.freq - o.freq;
        }
        public String toString(){
            return freq + " " + c;
        }
    }
    static TreeMap<Character , Integer> frequencies = new TreeMap<>(); // Frequency map -> Char : Times it appears in total ( String )
    static TreeMap<Character , String> encodings = new TreeMap<>(); // Encodings map -> Char : new encoding -> based on frequency
    static PriorityQueue<Node> queue = new PriorityQueue<>(); // PQ to do the huffman process
    static String total = "" , name = "" , answer = ""; // Stores all the text in the file, the name of the file, and the answer ( decompression )
    static Node root = null; // Root of the tree built by the frequencies
    static Scanner user = new Scanner(System.in) , file = null;// Console and file scanner
    static BufferedReader dFile = null; // Decrypt file to read byte by byte
    static void reset(){ // Empty all instance variables to restart for each compress or decompress
        frequencies = new TreeMap<>();
        encodings = new TreeMap<>();
        queue = new PriorityQueue<>();
        total = "";
        root = null;
    }
    // takes in the file and stores its name and initializes the scanner
    static void getFile() throws IOException{
        file = new Scanner(new FileReader("leaderboard.txt"));
    }
    static void update(){
        //Updates the frequency map based on the char count in the total string
        for(char c : total.toCharArray()){
            frequencies.put(c , frequencies.get(c)==null?1:frequencies.get(c)+1);
        }
        //Adds all items to the PQ to begin huffman
        for(char item : frequencies.keySet()){
            queue.add(new Node(frequencies.get(item) , item));
        }
    }
    //Reads in the file and appends new line after each line read => trims off extra new line at end
    static void read(){
        while(file.hasNextLine()){
            total+=file.nextLine() + "\n";
        }
        total = total.trim();
    }
    //Builds the tree using the huffman process
    static void buildTree(){
        //While there are 2+ nodes
        while(queue.size()>1){
            //Poll the first two
            Node a = queue.poll() , b = queue.poll();
            //add them
            Node temp = new Node(a.freq + b.freq , (char)0);
            //create a parent node
            temp.left = a;
            temp.right = b;
            //add the parent to the queue
            queue.add(temp);
        }
        // the last item remaining is the root
        root = queue.poll();
    }
    static void dfs(String path , Node curr) throws Exception {
        if(curr.c!=0){ // when reaching a leaf ( Since all leaves have c!=0 ( could have done left==null && right==null for more proper leaf check ) ) 
            encodings.put(curr.c , path); // add the encoding
            return;
        }
        dfs(path+"1" , curr.left); // if not a leaf, dfs on both children
        dfs(path+"0" , curr.right); // ^^
    }
    static void createFile(String fileName)
    {
        //Take all the encodings and add them to a string
        String bits = "";
        for(char c : total.toCharArray()){
            bits+=encodings.get(c);
        }
        String freq = Integer.toBinaryString(frequencies.size()); // How many letters are there -> This many 5 bytes will have to be read
        freq=repeat("0", 32 - freq.length())+freq;
        for(char item : frequencies.keySet()){ // For every letter -> Append frequency as a 32 bit signed integer then the character as a 8 bit signed integer
            String fBin =  Integer.toBinaryString(frequencies.get(item));
            fBin=repeat("0", 32-fBin.length())+fBin;
            String iBin = Integer.toBinaryString(item);
            iBin=repeat("0", 8 - iBin.length())+iBin;
            freq+=fBin+iBin;
        }
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter("leaderboard.txt"));
            freq+=bits;
            int counter = 0;
            // Since we are writing in bytes, length (mod 8) should be 0 , so we append 1s to the end and keep track of how many ones added
            while(freq.length()%8!=0) {
                freq+='1';
                counter++;
            }
            String temp = Integer.toBinaryString(counter); // Make a 32 bit signed integer based on number of 1s appended
            temp = repeat("0", 32 - temp.length())+temp;
            freq = temp+freq;
            for(int i = 0 ; i<freq.length();i+=8){
                   out.write((char)(int)Integer.valueOf(freq.substring(i,i+8) , 2)); // Write out each byte to the file
            }
            out.close();
        } catch(Exception e){
            ;
        }
    }
    static void convertBits(){
        //Convert the charcters in the zpr file to 8 bit signed integers
        String temp = "";
        for(char d : total.toCharArray()){
            String c = ""+Integer.toBinaryString(d);
            c=repeat("0", 8-c.length())+c;
            temp+=c;
        }
        total = temp;
    }
    static void convertFrequencies(){
        // The first 4 bytes -> Count of 1s added at end
        int extra =  Integer.valueOf(total.substring(0,32),2);
        total = total.substring(32);
        //Next 4 bytes -> Num letters
        int numLetters = Integer.valueOf(total.substring(0,32),2);
        int pointer = 32;
        //For every letter -> read in freq , letter
        for(int i = 0 ; i<numLetters;i++){
            int frequencyOfLetter = Integer.valueOf(total.substring(pointer,pointer+=32),2);
            char letter = (char)(int) Integer.valueOf(total.substring(pointer , pointer+=8) , 2);
            frequencies.put(letter, frequencyOfLetter);
        }
        //The rest of the string is the tree traversal
        total = total.substring(pointer , total.length()-extra);
        for(char item : frequencies.keySet()){ // For each frequency, add it to othe pq for huffman
            queue.add(new Node(frequencies.get(item) , item));
        }
    }
    static String getAnswer( int pointer , Node curr){
        //if pointer reached the end of the string, return the built string 
        if(pointer==total.length())
            return answer+curr.c;
        // if pointer is at 0
        if(total.charAt(pointer)=='0'){
            // If not possible to go left, means that current node is a leaf -> has a char
            if(curr.left==null){
                // add that char then continue dfs but return back to root
                answer+=curr.c;
                return getAnswer( pointer , root);
            } else 
            // else keep following path until reach a leaf
                return getAnswer(pointer+1 , curr.right);
        } else {
            // Same procces as going left
             if(curr.right==null){
                answer+=curr.c;
                return getAnswer( pointer , root);
            } else 
                return getAnswer(pointer+1 , curr.left);
        }
    }
    static void getDFile() throws IOException{//Initializes the buffered read based on file inputted
        dFile = new BufferedReader(new FileReader("leaderboard.txt"));
    }
    static void readD(){ // Reads the file byte by byte
        try{
            int token = dFile.read();
            while(token!=-1){
                total+=(char)token;
                token = dFile.read();
            }
        }   catch (IOException e){
            
        }
    }
    static void printD(){ // Prints the answer returned by the getAnswer method to a file with a .txxt extension
        try{
            PrintWriter out = new PrintWriter(new FileWriter("leaderboard.txt"));
            out.println(getAnswer(0 , root));
            out.close();
        } catch (Exception e){
            ;
        }
    }
    static void decompress() {
        //Calls the following methods to decompress the file inputted 
        //and resets the class afterwards
        try {
            getDFile();
        } catch(IOException e) {
            System.out.println("Compressed File Not Found!\n");
        }
        readD();
        convertBits();
        try {
            convertFrequencies();
        } catch(Exception e) {
            System.out.println("FILE ERROR");
        }
        buildTree();
        try {
            dfs("",root);
        } catch(Exception e) {
            System.out.println("FILE ERROR");
        }
        answer = "";
        printD();
        reset();
    }
    static String repeat(String str, int occurances) {
        String temp = "";
        for(int i = 0; i < occurances; i++) {
            temp+=str;
        }
        return temp;
    }
    static void compress() {
        //Compresses the file given and writes it out by calling the following methods
        //and resets the class afterwards
        try {
            getFile();
        } catch(IOException e) {
            System.out.println("Decompressed File Not Found!\n");
        }
        read();
        update();
        buildTree();
        try {
            dfs("" , root);
        } catch (Exception e) {
            System.out.println("FILE ERROR");
        }
        createFile("leaderboard.txt");
        reset();
    }
}