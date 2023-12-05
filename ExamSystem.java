import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class User {
    private String username;
    private String password;
    private String fullName;

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public void updateProfile(String newFullName, String newPassword) {
        this.fullName = newFullName;
        this.password = newPassword;
    }
}

class Question {
    private String questionText;
    private List<String> options;
    private int correctOptionIndex;

    public Question(String questionText, String[] options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = Arrays.asList(options);
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }
}

public class ExamSystem {
    private static List<User> users = new ArrayList<>();
    private static User currentUser;
    private static List<Question> questions;
    private static List<Integer> selectedAnswers;
    private static Timer timer;
    private static int remainingTimeInSeconds = 1200; // 20 minutes

    public static void main(String[] args) {
        initializeQuestions();
        login();
    }
    private static void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is the largest planet in our solar system?", new String[]{"Earth", "Jupiter", "Mars", "Venus"}, 1));
        questions.add(new Question("In physics, what is the unit of force?", new String[]{"Newton", "Watt", "Joule", "Volt"}, 0));
        questions.add(new Question("Which element is represented by the symbol 'O' on the periodic table?", new String[]{"Oxygen", "Gold", "Silver", "Iron"}, 0));
        questions.add(new Question("What is the capital of Japan?", new String[]{"Beijing", "Tokyo", "Seoul", "Bangkok"}, 1));
        questions.add(new Question("Who wrote the theory of relativity?", new String[]{"Isaac Newton", "Albert Einstein", "Stephen Hawking", "Niels Bohr"}, 1));
        questions.add(new Question("Which continent is known as the 'Land of the Rising Sun'?", new String[]{"Asia", "Europe", "North America", "Australia"}, 0));
        questions.add(new Question("In geology, what is the hardest natural substance on Earth?", new String[]{"Gold", "Diamond", "Platinum", "Graphite"}, 1));
        questions.add(new Question("What is the largest ocean on Earth?", new String[]{"Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"}, 3));
        questions.add(new Question("Which scientist formulated the laws of motion and universal gravitation?", new String[]{"Galileo Galilei", "Isaac Newton", "Nikola Tesla", "Marie Curie"}, 1));
        questions.add(new Question("What is the main component of Earth's atmosphere?", new String[]{"Oxygen", "Carbon Dioxide", "Nitrogen", "Argon"}, 2));
        questions.add(new Question("Which river is the longest in the world?", new String[]{"Nile", "Amazon", "Yangtze", "Mississippi"}, 0));
        questions.add(new Question("What is the speed of light in a vacuum?", new String[]{"300,000 km/s", "150,000 km/s", "450,000 km/s", "600,000 km/s"}, 0));
        questions.add(new Question("Which planet is known as the 'Red Planet'?", new String[]{"Mars", "Venus", "Jupiter", "Saturn"}, 0));
        questions.add(new Question("In which country can you find the Great Barrier Reef?", new String[]{"Australia", "Brazil", "Mexico", "South Africa"}, 0));
        questions.add(new Question("What is the currency of Japan?", new String[]{"Yuan", "Yen", "Rupee", "Dollar"}, 1));
        questions.add(new Question("Who is the father of modern physics?", new String[]{"Niels Bohr", "Max Planck", "Albert Einstein", "Erwin Schrödinger"}, 2));
        questions.add(new Question("What is the highest mountain in the world?", new String[]{"Mount Everest", "K2", "Kangchenjunga", "Lhotse"}, 0));
        questions.add(new Question("What is the largest desert in the world?", new String[]{"Sahara Desert", "Arabian Desert", "Gobi Desert", "Antarctica"}, 3));
        questions.add(new Question("What is the capital city of Australia?", new String[]{"Sydney", "Melbourne", "Canberra", "Perth"}, 2));
        questions.add(new Question("What is the main ingredient in guacamole?", new String[]{"Tomato", "Avocado", "Onion", "Cilantro"}, 1));
    }
    private static void login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Exam System!");

        // Ask if the user is new or returning
        System.out.println("Are you a new user or returning user?");
        System.out.println("1. New User");
        System.out.println("2. Returning User");
        int userChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (userChoice == 1) {
            createNewUser(scanner);
        } else if (userChoice == 2) {
            returningUserLogin(scanner);
        } else {
            System.out.println("Invalid choice. Please try again.");
            login();
        }
    }
    private static void createNewUser(Scanner scanner) {
        System.out.print("Enter a new username: ");
        String newUsername = scanner.nextLine();
    
        // Check if the username already exists
        while (isUsernameTaken(newUsername)) {
            System.out.println("Username is already taken. Please choose a different one.");
            System.out.print("Enter a new username: ");
            newUsername = scanner.nextLine();
        }
    
        System.out.print("Enter a password: ");
        String newPassword = scanner.nextLine();
    
        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine();
    
        // Create the new user
        currentUser = new User(newUsername, newPassword, fullName);
        users.add(currentUser);
    
        System.out.println("Account created successfully. Welcome, " + fullName + "!");
        
        // Initialize questions here
        initializeQuestions();
        
        showMainMenu(scanner);
    }
    private static boolean isUsernameTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private static void returningUserLogin(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        currentUser = validateUser(username, password);

        if (currentUser != null) {
            System.out.println("Login successful. Welcome, " + currentUser.getFullName() + "!");
            showMainMenu(scanner);
        } else {
            System.out.println("Login failed. Please try again.");
            login();
        }
    }

    private static User validateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static void showMainMenu(Scanner scanner) {
        System.out.println("Welcome, " + currentUser.getFullName() + "!");
        System.out.println("1. Start Exam");
        System.out.println("2. Update Profile");
        System.out.println("3. Change Password");
        System.out.println("4. Logout");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choice) {
            case 1:
                startExam(scanner);
                break;
            case 2:
                updateProfile(scanner);
                break;
            case 3:
                changePassword(scanner);
                break;
            case 4:
                logout(scanner);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                showMainMenu(scanner);
                break;
        }
    }

    private static void startExam(Scanner scanner) {
        selectedAnswers = new ArrayList<>();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                remainingTimeInSeconds--;
                if (remainingTimeInSeconds <= 0) {
                    autoSubmit();
                }
            }
        }, 1000, 1000);

        System.out.println("You have 20 minutes to complete the exam.");
        for (int i = 0; i < 20; i++) {
            Question question = questions.get(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestionText());
            List<String> options = question.getOptions();
            for (int j = 0; j < options.size(); j++) {
                System.out.println((j + 1) + ". " + options.get(j));
            }
            System.out.print("Select an answer (1-" + options.size() + "): ");
            int answer = scanner.nextInt();
            selectedAnswers.add(answer - 1);
        }
        autoSubmit();
    }

    private static void autoSubmit() {
        if (timer != null) {
            timer.cancel();
        }
        System.out.println("Time's up! Submitting your answers.");
        showResult();
    }

    private static void showResult() {
        int score = 0;
        for (int i = 0; i < 20; i++) {
            Question question = questions.get(i);
            int selectedAnswerIndex = selectedAnswers.get(i);
            if (selectedAnswerIndex == question.getCorrectOptionIndex()) {
                score++;
            }
        }

        System.out.println("You scored " + score + " out of 20 questions.");
        logout(new Scanner(System.in));
    }

    private static void updateProfile(Scanner scanner) {
        System.out.print("Enter your new full name: ");
        String newFullName = scanner.nextLine();
        System.out.print("Enter your new password: ");
        String newPassword = scanner.nextLine();
        currentUser.updateProfile(newFullName, newPassword);
        System.out.println("Profile updated successfully.");
        showMainMenu(scanner);
    }

    private static void changePassword(Scanner scanner) {
        System.out.print("Enter your current password: ");
        String currentPassword = scanner.nextLine();
        if (currentPassword.equals(currentUser.getPassword())) {
            System.out.print("Enter your new password: ");
            String newPassword = scanner.nextLine();
            currentUser.updateProfile(currentUser.getFullName(), newPassword);
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Incorrect current password. Please try again.");
        }
        showMainMenu(scanner);
    }

    private static void logout(Scanner scanner) {
        System.out.println("Logging out. Goodbye!");
        scanner.close();
        System.exit(0);
    }
}
