package Powerball;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Powerball {

    // Constants for maximum numbers
    private static final int MAX_WHITE = 69;
    private static final int MAX_POWER = 26;
    private static final int NUM_WHITE = 6;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Generate winning numbers
        int[] winningNumbers = generateWinningNumbers();
        System.out.println("Welcome to Powerball!");

        // Get user numbers (manual or random)
        int[] userNumbers = getUserNumbers(scanner);

        // Calculate number of matches
        int matches = calculateMatches(userNumbers, winningNumbers);

        // Calculate prize based on matches and Powerball
        int prize = calculatePrize(userNumbers, winningNumbers);

        // Display results
        System.out.println("\nWinning Numbers: " + Arrays.toString(winningNumbers));
        System.out.println("Your Numbers   : " + Arrays.toString(userNumbers));
        System.out.println("Total Matches  : " + matches);
        System.out.println("Prize          : $" + prize);

        scanner.close();
    }

    /** Generates winning Powerball numbers (6 white + 1 Powerball) */
    private static int[] generateWinningNumbers() {
        Random random = new Random();
        int[] numbers = new int[NUM_WHITE + 1]; // Last number is Powerball

        Set<Integer> uniqueNumbers = new HashSet<>();
        int i = 0;
        while (uniqueNumbers.size() < NUM_WHITE) {
            int num = random.nextInt(MAX_WHITE) + 1;
            if (uniqueNumbers.add(num)) {
                numbers[i++] = num;
            }
        }

        // Powerball
        numbers[NUM_WHITE] = random.nextInt(MAX_POWER) + 1;
        return numbers;
    }

    /** Gets user numbers: manual input or random selection */
    private static int[] getUserNumbers(Scanner scanner) {
        int[] userNumbers = new int[NUM_WHITE + 1];
        Random random = new Random();

        System.out.println("Enter 1 to choose your numbers, or 2 for random numbers:");
        int choice = scanner.nextInt();

        if (choice == 1) {
            // Manual entry
            Set<Integer> uniqueNumbers = new HashSet<>();
            System.out.println("Enter 6 unique numbers between 1 and " + MAX_WHITE + ":");
            int i = 0;
            while (uniqueNumbers.size() < NUM_WHITE) {
                int num = scanner.nextInt();
                if (num < 1 || num > MAX_WHITE) {
                    System.out.println("Number out of range! Enter between 1 and " + MAX_WHITE);
                } else if (!uniqueNumbers.add(num)) {
                    System.out.println("Duplicate number! Enter a different number.");
                } else {
                    userNumbers[i++] = num;
                }
            }

            // Powerball number
            int powerball = 0;
            while (powerball < 1 || powerball > MAX_POWER) {
                System.out.println("Enter the Powerball number between 1 and " + MAX_POWER + ":");
                powerball = scanner.nextInt();
            }
            userNumbers[NUM_WHITE] = powerball;

        } else if (choice == 2) {
            // Random numbers
            Set<Integer> uniqueNumbers = new HashSet<>();
            int i = 0;
            while (uniqueNumbers.size() < NUM_WHITE) {
                int num = random.nextInt(MAX_WHITE) + 1;
                if (uniqueNumbers.add(num)) {
                    userNumbers[i++] = num;
                }
            }
            userNumbers[NUM_WHITE] = random.nextInt(MAX_POWER) + 1;

        } else {
            System.out.println("Invalid choice! Using random numbers.");
            return generateWinningNumbers();
        }

        return userNumbers;
    }

    /** Calculates how many white numbers match */
    private static int calculateMatches(int[] userNumbers, int[] winningNumbers) {
        int matches = 0;
        Set<Integer> winningSet = new HashSet<>();
        for (int i = 0; i < NUM_WHITE; i++) {
            winningSet.add(winningNumbers[i]);
        }
        for (int i = 0; i < NUM_WHITE; i++) {
            if (winningSet.contains(userNumbers[i])) {
                matches++;
            }
        }

        // Powerball counts as extra match
        if (userNumbers[NUM_WHITE] == winningNumbers[NUM_WHITE]) {
            matches++; // Optional: handle separately if desired
        }
        return matches;
    }

    /** Calculates prize based on matches and Powerball */
    private static int calculatePrize(int[] userNumbers, int[] winningNumbers) {
        int matches = 0;
        boolean powerballMatch = userNumbers[NUM_WHITE] == winningNumbers[NUM_WHITE];

        Set<Integer> winningSet = new HashSet<>();
        for (int i = 0; i < NUM_WHITE; i++) {
            winningSet.add(winningNumbers[i]);
        }
        for (int i = 0; i < NUM_WHITE; i++) {
            if (winningSet.contains(userNumbers[i])) matches++;
        }

        // Prize logic
        if (matches == 5 && powerballMatch) return 50_000_000;
        else if (matches == 5) return 1_000_000;
        else if (matches == 4 && powerballMatch) return 50_000;
        else if (matches == 4) return 100;
        else if (matches == 3 && powerballMatch) return 100;
        else if (matches == 3) return 7;
        else if (matches == 2 && powerballMatch) return 7;
        else if (matches == 1 && powerballMatch) return 4;
        else if (powerballMatch) return 4;
        else return 0;
    }
}
