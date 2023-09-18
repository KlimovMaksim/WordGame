package ru.klimov.wordgame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordGame {
    public static String word;
    public static boolean[] guessedLetters;
    public static HashSet wrongLetters = new HashSet();
    public static String validChars = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    //public static int errors;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    public static List<String> readWordsFromFile(String fileName){
        List<String> wordsList = new ArrayList<String>();
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)){

            String line;
            while ((line = bufferedReader.readLine()) != null){
                line.trim();
                wordsList.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return wordsList;
    }

    public static String chooseRandomWord(){
        String word;
        List<String> wordsList = readWordsFromFile("src/main/resources/words.txt");
        Random random = new Random();

        word = wordsList.get(random.nextInt(wordsList.size()));
        return word;
    }

    public static void checkLetter(Character letter){
        if (word.contains(letter.toString())){
            System.out.println("Буква '" + letter + "' есть в слове!");
            for (int i =0; i < word.length(); i++){
                if (letter == word.charAt(i)) {
                    guessedLetters[i] = true;
                }
            }
        }
        else {
            //errors++;
            wrongLetters.add(letter);
            System.out.println("Буква '" + letter + "' отсутствует в слове!");
        }
    }

    public static void displayHuman(){
        // отображение виселицы
        System.out.println("_____");
        System.out.println("|   |");
        switch (wrongLetters.size()){
            case 0:
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                break;
            case 1:
                System.out.println("|   0");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                break;
            case 2:
                System.out.println("|   0");
                System.out.println("|   |");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                break;
            case 3:
                System.out.println("|   0");
                System.out.println("|  /|");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                break;
            case 4:
                System.out.println("|   0");
                System.out.println("|  /|\\");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                break;
            case 5:
                System.out.println("|   0");
                System.out.println("|  /|\\");
                System.out.println("|  /");
                System.out.println("|");
                System.out.println("|");
                break;
            case 6:
                System.out.println("|   0");
                System.out.println("|  /|\\");
                System.out.println("|  / \\");
                System.out.println("|");
                System.out.println("|");
                break;
        }
        System.out.println("|_______");

        // отображение слова
        System.out.print("Слово: ");
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (guessedLetters[i]){
                System.out.print(letter + " ");
            }
            else {
                System.out.print("_ ");
            }
        }
        System.out.println();
    }

    public static void gameOver(){
        if (isDefeat()){
            System.out.println("ВЫ ПРОИГРАЛИ!");
        }
        else{
            System.out.println("ВЫ ВЫИГРАЛИ!");
        }
    }

    public static boolean isVictory(){
        int count = 0;
        for (boolean b: guessedLetters) {
            if (!b){
                break;
            }
            else{
                count++;
            }
        }
        return count == guessedLetters.length;
    }

    public static boolean isDefeat(){
        return wrongLetters.size() >= 6;
    }

    public static boolean isLetterInput(String line){
        if ((line.length() != 1) || (!validChars.contains(line))){
            System.out.println("Ошибка! Введите букву.");
            return false;
        }
        else{
            System.out.println("Введена буква '" + line + "'.");
            return true;
        }
    }

    public static void gameLoop() {
        String line;
        char letter;
        word = chooseRandomWord();
        //errors = 0;
        guessedLetters = new boolean[word.length()];
        displayHuman();
        while (!isVictory() && !isDefeat()){
            System.out.print("Введите букву: ");
            line = scanner.next();
            if (!isLetterInput(line)) {
                continue;
            }
            letter = line.charAt(0);
            checkLetter(letter);
            displayHuman();
        }
    }

    public static void menu() {
        int command;
        boolean continueGame = true;
        System.out.println("Запущена игра Виселица.");
        while (continueGame) {
            System.out.println("Меню: (введите число для выбора)");
            System.out.println("1. Играть");
            System.out.println("2. Выйти");
            command = scanner.nextInt();
            switch (command) {
                case 1:
                    gameLoop();
                    gameOver();
                    break;
                case 2:
                    continueGame = false;
                    break;
            }
        }
    }
}




