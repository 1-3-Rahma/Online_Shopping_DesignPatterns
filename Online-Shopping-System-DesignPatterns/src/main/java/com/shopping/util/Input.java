package com.shopping.util;

import java.util.Scanner;

public final class Input {
    private Input() {}

    public static String readNonBlank(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isBlank()) return s;
            System.out.println("Please enter a non-empty value.");
        }
    }

    public static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String raw = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(raw);
                if (v < min || v > max) {
                    System.out.println("Enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    public static int readIntMin(Scanner sc, String prompt, int min) {
        while (true) {
            System.out.print(prompt);
            String raw = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(raw);
                if (v < min) {
                    System.out.println("Enter a number >= " + min + ".");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    public static double readDoubleMin(Scanner sc, String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            String raw = sc.nextLine().trim();
            try {
                double v = Double.parseDouble(raw);
                if (v < min) {
                    System.out.println("Enter a number >= " + min + ".");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    public static boolean readYesNo(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("y") || s.equals("yes")) return true;
            if (s.equals("n") || s.equals("no")) return false;
            System.out.println("Please answer y or n.");
        }
    }
}
