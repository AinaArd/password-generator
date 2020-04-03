package ru.itis.pass_generator;

import org.apache.commons.io.FileUtils;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Generator {
    private static String symbols = "abcdefghijklmnopqrstuvwxyz";
    private static String task = "";
    private static List<String> constants = Arrays.asList("Sony", "Hewlett", "Packard");
    private static String result = "";
    private Map<Integer, String> alphabet = new HashMap<>();

    public void start() {
        System.out.println("What you what to do? Type 2 or 3 to demonstrate tasks or exit to stop the program: ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        switch (command) {
            case "2":
                task = command;
                break;
            case "3":
                task = "2";
                break;
            case "exit":
                System.exit(0);
            default:
                System.out.println("Incorrect input. Please try again.");
                start();
        }
    }

    public void generate() {
        if (task.equals("2")) {
            symbols += "0123456789";
            Password password = new Password(7, LocalDateTime.now().plusDays(14));
            long start = System.nanoTime();
            String passwordValue = new Random().ints(password.getLength(), 0, symbols.length())
                    .mapToObj(symbols::charAt)
                    .map(Object::toString)
                    .collect(Collectors.joining());
            long end = System.nanoTime() - start;
            System.out.println(end);
            password.setValue(passwordValue);
            write(password);
            System.out.println("Password was generated. The result: " + password.toString());
        } else {
            fillInAlphabet();
            generatePassword();
            comparePasswords();
        }
    }

    private void generatePassword() {
        getFirstLetter();
        getSecondLetter();
        getThirdLetter();
        getFourthLetter();
    }

    private void getFirstLetter() {
        int index = constants.get(0).length() + constants.get(2).length();
        if (index > 26) {
            index %= 26;
        }
        result += alphabet.get(index);
    }

    private void getSecondLetter() {
        String secondWord = constants.get(1);
        int length = secondWord.length();
        String lastLetter = String.valueOf(secondWord.charAt(length - 1));
        for (Map.Entry<Integer, String> entry : alphabet.entrySet()) {
            if (lastLetter.equals(entry.getValue())) {
                String letter = alphabet.get(entry.getKey() - 1);
                if (lastLetter.equals("a")) {
                    result += "z";
                } else {
                    result += letter;
                }
                break;
            }
        }
    }

    private void getThirdLetter() {
        String thirdWord = constants.get(2);
        int numOfLetters = thirdWord.length();
        if (numOfLetters % 2 == 1) {
//            нечетный кейс
            int index = numOfLetters / 2;
            String middleLetter = String.valueOf(constants.get(2).charAt(index));
            for (Map.Entry<Integer, String> entry : alphabet.entrySet()) {
                if (middleLetter.equals(entry.getValue())) {
                    String letter = alphabet.get(entry.getKey() + 1);
                    if (letter.equals("z")) {
                        result += "a";
                    } else {
                        result += letter;
                    }
                    break;
                }
            }
        } else {
//            четный кейс
            int index = numOfLetters / 2 - 1;
            String middleLetter = String.valueOf(thirdWord.charAt(index));
            for (Map.Entry<Integer, String> entry : alphabet.entrySet()) {
                if (middleLetter.equals(entry.getValue())) {
                    String letter = alphabet.get(entry.getKey() - 1);
                    if (letter.equals("z")) {
                        result += "a";
                    } else {
                        result += letter;
                    }
                    break;
                }
            }
        }
    }

    private void getFourthLetter() {
        String firstLetter = String.valueOf(constants.get(0).charAt(0)).toLowerCase();
        for (Map.Entry<Integer, String> entry : alphabet.entrySet()) {
            if (firstLetter.equals(entry.getValue())) {
                String letter = alphabet.get(entry.getKey() + 1);
                if (letter.equals("z")) {
                    result += "a";
                } else {
                    result += letter;
                }
                break;
            }
        }
    }

    private void fillInAlphabet() {
        int index = 1;
        for (int i = 0; i < symbols.length(); i++) {
            alphabet.put(index, String.valueOf(symbols.charAt(index - 1)));
            index++;
        }
    }

    private String maskPassword() {
        String maskedPassword = "";
        for (int i = 0; i < result.length(); i++) {
            maskedPassword += "*";
        }
        return maskedPassword;
    }

    public void comparePasswords() {
        System.out.println("Enter user password, please: ");
        Console console = System.console();
        console.printf("Comparing password%n");
        char[] passwordArray = console.readPassword("Enter your secret password: ");
        console.printf("Password entered was: %s%n", new String(passwordArray));

        if (Arrays.equals(passwordArray, result.toCharArray())) {
            System.out.println("Password is correct");
        } else {
            System.out.println("Password is incorrect");
        }
    }

    private void write(Password password) {
        String path = "C:\\AinaArd\\PasswordGenerator\\src\\main\\resources\\random_passwords.txt";
        File file = new File(path);
        try {
            FileUtils.writeStringToFile(file, password.toString() + "\n", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
