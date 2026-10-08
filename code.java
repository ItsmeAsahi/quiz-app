import javax.swing.*;
import java.awt.*;

public class code extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    String registeredUsername = "student";
    String registeredPassword = "1234";

    public code() {

        setTitle("Online Quiz Application");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(240, 245, 255));

        JLabel title = new JLabel("ONLINE QUIZ APPLICATION");
        title.setBounds(75, 35, 350, 35);
        title.setFont(new Font("Arial", Font.BOLD, 21));
        title.setForeground(new Color(25, 60, 120));
        panel.add(title);

        JLabel subtitle = new JLabel("Test Your Knowledge");
        subtitle.setBounds(155, 75, 200, 25);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(subtitle);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(60, 125, 100, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 125, 250, 30);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(60, 175, 100, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 175, 250, 30);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 235, 130, 40);
        loginButton.setBackground(new Color(35, 110, 220));
        loginButton.setForeground(Color.WHITE);
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(250, 235, 130, 40);
        panel.add(registerButton);

        loginButton.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.equals(registeredUsername)
                    && password.equals(registeredPassword)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful!"
                );

                openQuizMenu();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password."
                );
            }
        });

        registerButton.addActionListener(e -> {

            JTextField newUsername = new JTextField();
            JPasswordField newPassword = new JPasswordField();

            JPanel registerPanel =
                    new JPanel(new GridLayout(2, 2, 10, 10));

            registerPanel.add(new JLabel("Username:"));
            registerPanel.add(newUsername);

            registerPanel.add(new JLabel("Password:"));
            registerPanel.add(newPassword);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    registerPanel,
                    "Create Account",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {

                String username =
                        newUsername.getText().trim();

                String password =
                        new String(newPassword.getPassword());

                if (username.isEmpty() || password.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Username and password cannot be empty."
                    );

                } else {

                    registeredUsername = username;
                    registeredPassword = password;

                    JOptionPane.showMessageDialog(
                            this,
                            "Registration Successful!"
                    );

                    usernameField.setText(username);
                    passwordField.setText("");
                }
            }
        });

        add(panel);
        setVisible(true);
    }


    void openQuizMenu() {

        JFrame menu = new JFrame("Quiz Menu");

        menu.setSize(450, 350);
        menu.setLocationRelativeTo(this);
        menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        menu.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel heading = new JLabel(
                "SELECT A SUBJECT",
                SwingConstants.CENTER
        );

        heading.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        JButton javaButton =
                new JButton("Java Quiz");

        JButton pythonButton =
                new JButton("Python Quiz");

        JButton cppButton =
                new JButton("C++ Quiz");

        JButton logoutButton =
                new JButton("Logout");

        panel.add(heading);
        panel.add(javaButton);
        panel.add(pythonButton);
        panel.add(cppButton);
        panel.add(logoutButton);

        javaButton.addActionListener(e -> {
            menu.dispose();
            startJavaQuiz();
        });

        pythonButton.addActionListener(e -> {
            menu.dispose();
            startPythonQuiz();
        });

        cppButton.addActionListener(e -> {
            menu.dispose();
            startCppQuiz();
        });

        logoutButton.addActionListener(e -> {
            menu.dispose();

            usernameField.setText("");
            passwordField.setText("");

            JOptionPane.showMessageDialog(
                    this,
                    "You have been logged out."
            );
        });

        menu.add(panel);
        menu.setVisible(true);
    }


    void startJavaQuiz() {

        String[] questions = {

            "Which keyword is used to inherit a class in Java?",

            "Which method is the starting point of a Java program?",

            "Which collection does not allow duplicates?",

            "Which keyword is used to create an object?",

            "Which package contains ArrayList?"
        };


        String[][] options = {

            {"implements", "extends", "inherits", "super"},

            {"start()", "run()", "main()", "execute()"},

            {"List", "ArrayList", "Set", "Vector"},

            {"class", "new", "object", "create"},

            {"java.io", "java.util", "java.lang", "java.sql"}
        };


        int[] answers = {
            1,
            2,
            2,
            1,
            1
        };


        conductQuiz(
                "JAVA QUIZ",
                questions,
                options,
                answers
        );
    }


    void startPythonQuiz() {

        String[] questions = {

            "Which symbol is used to create a comment in Python?",

            "Which function is used to display output?",

            "Which data type stores multiple values in order?",

            "Which keyword is used to define a function?",

            "Which extension is used for Python files?"
        };


        String[][] options = {

            {"//", "#", "/*", "--"},

            {"display()", "show()", "print()", "output()"},

            {"list", "int", "float", "bool"},

            {"function", "def", "fun", "define"},

            {".java", ".cpp", ".py", ".python"}
        };


        int[] answers = {
            1,
            2,
            0,
            1,
            2
        };


        conductQuiz(
                "PYTHON QUIZ",
                questions,
                options,
                answers
        );
    }


    void startCppQuiz() {

        String[] questions = {

            "Which symbol is used to end a statement in C++?",

            "Which keyword is used to create a class?",

            "Which function is the starting point of a C++ program?",

            "Which header is commonly used for input and output?",

            "Which operator accesses members through an object?"
        };


        String[][] options = {

            {".", ";", ":", ","},

            {"object", "struct", "class", "define"},

            {"start()", "run()", "main()", "execute()"},

            {"iostream", "stdio", "string", "math"},

            {".", "->", "::", "*"}
        };


        int[] answers = {
            1,
            2,
            2,
            0,
            0
        };


        conductQuiz(
                "C++ QUIZ",
                questions,
                options,
                answers
        );
    }


    void conductQuiz(
            String subject,
            String[] questions,
            String[][] options,
            int[] answers) {

        int score = 0;

        for (int i = 0; i < questions.length; i++) {

            int choice =
                    JOptionPane.showOptionDialog(
                            this,
                            questions[i],
                            subject + " - Question "
                                    + (i + 1),
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options[i],
                            options[i][0]
                    );


            if (choice == answers[i]) {
                score++;
            }
        }


        int total = questions.length;

        int wrong = total - score;

        double percentage =
                score * 100.0 / total;


        JOptionPane.showMessageDialog(
                this,

                subject
                + "\n\n"
                + "Total Questions: "
                + total
                + "\n"
                + "Correct Answers: "
                + score
                + "\n"
                + "Wrong Answers: "
                + wrong
                + "\n"
                + "Percentage: "
                + String.format(
                        "%.2f",
                        percentage
                )
                + "%",

                "Quiz Result",

                JOptionPane.INFORMATION_MESSAGE
        );


        int again =
                JOptionPane.showConfirmDialog(
                        this,
                        "Do you want to attempt another quiz?",
                        "Continue",
                        JOptionPane.YES_NO_OPTION
                );


        if (again == JOptionPane.YES_OPTION) {

            openQuizMenu();

        }
    }


    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new code();

        });
    }
}