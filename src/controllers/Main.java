package controllers;

import java.util.Scanner;

public class Main {

    public static void main( String[] args ) {
        Scanner kb = new Scanner(System.in);
        while(true) {
            System.out.println("\n" +
                "1) Survey\n" +
                "2) Test\n");
            char opt = kb.nextLine().charAt(0);
            switch (opt) {
                case '1': {
                    new SurveyMain().go();
                    exit();
                }
                case '2': {
                    new TestMain().go();
                    exit();
                }
                default:
                    System.out.println("Invalid response");
            }
        }
    }

    private static void exit() {
        System.exit(0);
    }
}
