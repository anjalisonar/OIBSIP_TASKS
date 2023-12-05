import java.util.Random;
import java.util.Scanner;

public class NumberGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerBound = 1;
        int upperBound = 100;
        int targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;

        int attempts = 0;
        int maxAttempts = 7;
        int points = 100;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("We've selected a random number between " + lowerBound + " and " + upperBound + ".");
        System.out.println("You have " + maxAttempts + " attempts to guess the number.");

        while (attempts < maxAttempts) {
            System.out.print("Guess #" + (attempts + 1) + ": Enter your guess: ");

            // Validate user input
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid integer.");
                System.out.print("Guess #" + (attempts + 1) + ": Enter your guess: ");
                scanner.next(); // Consume invalid input
            }

            int guess = scanner.nextInt();
            attempts++;

            if (guess < lowerBound || guess > upperBound) {
                System.out.println("Please enter a number between " + lowerBound + " and " + upperBound + ".");
                continue;
            }

            if (guess == targetNumber) {
                System.out.println("Congratulations! You've guessed the correct number: " + targetNumber);
                System.out.println("You earned " + points + " points.");

                // Ask the user if they want to play again
                System.out.print("Do you want to play again? (yes/no): ");
                String playAgain = scanner.next().toLowerCase();

                if (playAgain.equals("yes")) {
                    // Reset the game
                    targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
                    attempts = 0;
                    points = 100;
                    System.out.println("New game started!");
                    continue;
                } else {
                    break; // Exit the loop if the user doesn't want to play again
                }
            } else if (guess < targetNumber) {
                System.out.println("Try a higher number. Remaining attempts: " + (maxAttempts - attempts));
            } else {
                System.out.println("Try a lower number. Remaining attempts: " + (maxAttempts - attempts));
            }

            points -= 10;
        }

        if (attempts >= maxAttempts) {
            System.out.println("Game over! You've used all your attempts.");
            System.out.println("The correct number was: " + targetNumber);
        }

        scanner.close();
    }
}
