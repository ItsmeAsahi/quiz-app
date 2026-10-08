import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class code extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private String registeredUsername = "student";
    private String registeredPassword = "1234";

    // Quiz State
    private String currentSubject = "";
    private String[] currentQuestions;
    private String[][] currentOptions;
    private int[] currentAnswers;
    private int currentQuestionIndex = 0;
    private int score = 0;

    // UI Elements for Quiz
    private JLabel questionLabel;
    private ModernButton[] optionButtons;
    private JLabel progressLabel;

    // Colors
    private final Color bgColor = new Color(245, 247, 250);
    private final Color primaryColor = new Color(67, 97, 238);
    private final Color primaryHover = new Color(58, 12, 163);
    private final Color textColor = new Color(43, 45, 66);
    private final Color cardColor = Color.WHITE;

    public code() {
        setTitle("Online Quiz Application");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(bgColor);

        // Build Screens
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createRegisterPanel(), "Register"); // Added Registration Screen
        mainPanel.add(createMenuPanel(), "Menu");
        mainPanel.add(createQuizPanel(), "Quiz");
        mainPanel.add(createResultPanel(), "Result");

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(bgColor);

        JPanel card = new JPanel(null);
        card.setBackground(cardColor);
        card.setBounds(150, 40, 400, 380);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        JLabel title = new JLabel("Welcome Back", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(textColor);
        title.setBounds(0, 30, 400, 40);
        card.add(title);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLabel.setBounds(50, 100, 300, 20);
        card.add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(50, 125, 300, 35);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(5, 10, 5, 10)));
        card.add(usernameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLabel.setBounds(50, 175, 300, 20);
        card.add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 200, 300, 35);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(5, 10, 5, 10)));
        card.add(passwordField);

        ModernButton loginBtn = new ModernButton("Login", primaryColor, Color.WHITE);
        loginBtn.setBounds(50, 260, 145, 40); // Adjusted size to fit register button
        loginBtn.addActionListener(e -> {
            if (usernameField.getText().trim().equals(registeredUsername) &&
                new String(passwordField.getPassword()).equals(registeredPassword)) {
                cardLayout.show(mainPanel, "Menu");
                usernameField.setText("");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        card.add(loginBtn);

        ModernButton registerBtn = new ModernButton("Register", new Color(108, 117, 125), Color.WHITE);
        registerBtn.setBounds(205, 260, 145, 40); // Placed next to login button
        registerBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "Register");
            usernameField.setText("");
            passwordField.setText("");
        });
        card.add(registerBtn);

        ModernButton closeBtn = new ModernButton("Exit Application", new Color(220, 53, 69), Color.WHITE);
        closeBtn.setBounds(50, 310, 300, 40);
        closeBtn.addActionListener(e -> System.exit(0));
        card.add(closeBtn);

        panel.add(card);
        return panel;
    }

    // New Registration Panel
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(bgColor);

        JPanel card = new JPanel(null);
        card.setBackground(cardColor);
        card.setBounds(150, 40, 400, 380);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(textColor);
        title.setBounds(0, 30, 400, 40);
        card.add(title);

        JLabel userLabel = new JLabel("New Username");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        userLabel.setBounds(50, 100, 300, 20);
        card.add(userLabel);

        JTextField regUsernameField = new JTextField();
        regUsernameField.setBounds(50, 125, 300, 35);
        regUsernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(5, 10, 5, 10)));
        card.add(regUsernameField);

        JLabel passLabel = new JLabel("New Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        passLabel.setBounds(50, 175, 300, 20);
        card.add(passLabel);

        JPasswordField regPasswordField = new JPasswordField();
        regPasswordField.setBounds(50, 200, 300, 35);
        regPasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                new EmptyBorder(5, 10, 5, 10)));
        card.add(regPasswordField);

        ModernButton submitBtn = new ModernButton("Sign Up", primaryColor, Color.WHITE);
        submitBtn.setBounds(50, 260, 300, 40);
        submitBtn.addActionListener(e -> {
            String newUsername = regUsernameField.getText().trim();
            String newPassword = new String(regPasswordField.getPassword());

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                registeredUsername = newUsername;
                registeredPassword = newPassword;
                JOptionPane.showMessageDialog(this, "Registration Successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                regUsernameField.setText("");
                regPasswordField.setText("");
                cardLayout.show(mainPanel, "Login");
            }
        });
        card.add(submitBtn);

        ModernButton backBtn = new ModernButton("Back to Login", new Color(108, 117, 125), Color.WHITE);
        backBtn.setBounds(50, 310, 300, 40);
        backBtn.addActionListener(e -> {
            regUsernameField.setText("");
            regPasswordField.setText("");
            cardLayout.show(mainPanel, "Login");
        });
        card.add(backBtn);

        panel.add(card);
        return panel;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(bgColor);

        JLabel title = new JLabel("Select a Subject", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(textColor);
        title.setBounds(0, 60, 700, 40);
        panel.add(title);

        ModernButton javaBtn = new ModernButton("Java Quiz", primaryColor, Color.WHITE);
        javaBtn.setBounds(200, 140, 300, 50);
        javaBtn.addActionListener(e -> loadQuiz("Java", getJavaQuestions(), getJavaOptions(), getJavaAnswers()));

        ModernButton pythonBtn = new ModernButton("Python Quiz", primaryColor, Color.WHITE);
        pythonBtn.setBounds(200, 210, 300, 50);
        pythonBtn.addActionListener(e -> loadQuiz("Python", getPythonQuestions(), getPythonOptions(), getPythonAnswers()));

        ModernButton cppBtn = new ModernButton("C++ Quiz", primaryColor, Color.WHITE);
        cppBtn.setBounds(200, 280, 300, 50);
        cppBtn.addActionListener(e -> loadQuiz("C++", getCppQuestions(), getCppOptions(), getCppAnswers()));

        ModernButton logoutBtn = new ModernButton("Logout", Color.GRAY, Color.WHITE);
        logoutBtn.setBounds(250, 370, 200, 40);
        logoutBtn.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        panel.add(javaBtn);
        panel.add(pythonBtn);
        panel.add(cppBtn);
        panel.add(logoutBtn);

        return panel;
    }

    private JPanel createQuizPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(bgColor);

        progressLabel = new JLabel("Question 1 / 5", SwingConstants.CENTER);
        progressLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        progressLabel.setForeground(Color.GRAY);
        progressLabel.setBounds(0, 30, 700, 30);
        panel.add(progressLabel);

        questionLabel = new JLabel("Question text goes here?", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        questionLabel.setForeground(textColor);
        questionLabel.setBounds(50, 80, 600, 80);
        panel.add(questionLabel);

        optionButtons = new ModernButton[4];
        int yOffset = 180;
        for (int i = 0; i < 4; i++) {
            final int index = i;
            optionButtons[i] = new ModernButton("Option", cardColor, textColor);
            optionButtons[i].setBorder(BorderFactory.createLineBorder(primaryColor, 1));
            optionButtons[i].setBounds(150, yOffset, 400, 45);
            optionButtons[i].addActionListener(e -> handleAnswer(index));
            panel.add(optionButtons[i]);
            yOffset += 60;
        }

        ModernButton cancelBtn = new ModernButton("Quit Quiz", new Color(220, 53, 69), Color.WHITE);
        cancelBtn.setBounds(275, 430, 150, 35);
        cancelBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        panel.add(cancelBtn);

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(bgColor);

        JLabel title = new JLabel("Quiz Completed!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(textColor);
        title.setBounds(0, 80, 700, 50);
        panel.add(title);

        JLabel scoreDisplay = new JLabel();
        scoreDisplay.setFont(new Font("Segoe UI", Font.BOLD, 48));
        scoreDisplay.setForeground(primaryColor);
        scoreDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        scoreDisplay.setBounds(0, 150, 700, 60);
        panel.add(scoreDisplay);

        ModernButton menuBtn = new ModernButton("Back to Menu", primaryColor, Color.WHITE);
        menuBtn.setBounds(250, 280, 200, 50);
        menuBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        panel.add(menuBtn);

        panel.putClientProperty("scoreLabel", scoreDisplay);
        return panel;
    }

    private void loadQuiz(String subject, String[] questions, String[][] options, int[] answers) {
        currentSubject = subject;
        currentQuestions = questions;
        currentOptions = options;
        currentAnswers = answers;
        currentQuestionIndex = 0;
        score = 0;

        updateQuizUI();
        cardLayout.show(mainPanel, "Quiz");
    }

    private void updateQuizUI() {
        progressLabel.setText(currentSubject + " Quiz - Question " + (currentQuestionIndex + 1) + " / " + currentQuestions.length);
        questionLabel.setText("<html><div style='text-align: center;'>" + currentQuestions[currentQuestionIndex] + "</div></html>");

        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(currentOptions[currentQuestionIndex][i]);
        }
    }

    private void handleAnswer(int selectedIndex) {
        if (selectedIndex == currentAnswers[currentQuestionIndex]) {
            score++;
        }

        currentQuestionIndex++;

        if (currentQuestionIndex < currentQuestions.length) {
            updateQuizUI();
        } else {
            showResults();
        }
    }

    private void showResults() {
        JPanel resultPanel = (JPanel) mainPanel.getComponent(4); // Index changed due to Register panel insertion
        JLabel scoreLabel = (JLabel) resultPanel.getClientProperty("scoreLabel");
        scoreLabel.setText(score + " / " + currentQuestions.length);
        cardLayout.show(mainPanel, "Result");
    }

    // --- Quiz Data ---
    private String[] getJavaQuestions() { return new String[]{"Which keyword is used to inherit a class in Java?", "Which method is the starting point of a Java program?", "Which collection does not allow duplicates?", "Which keyword is used to create an object?", "Which package contains ArrayList?"}; }
    private String[][] getJavaOptions() { return new String[][]{{"implements", "extends", "inherits", "super"}, {"start()", "run()", "main()", "execute()"}, {"List", "ArrayList", "Set", "Vector"}, {"class", "new", "object", "create"}, {"java.io", "java.util", "java.lang", "java.sql"}}; }
    private int[] getJavaAnswers() { return new int[]{1, 2, 2, 1, 1}; }

    private String[] getPythonQuestions() { return new String[]{"Which symbol is used to create a comment in Python?", "Which function is used to display output?", "Which data type stores multiple values in order?", "Which keyword is used to define a function?", "Which extension is used for Python files?"}; }
    private String[][] getPythonOptions() { return new String[][]{{"//", "#", "/*", "--"}, {"display()", "show()", "print()", "output()"}, {"list", "int", "float", "bool"}, {"function", "def", "fun", "define"}, {".java", ".cpp", ".py", ".python"}}; }
    private int[] getPythonAnswers() { return new int[]{1, 2, 0, 1, 2}; }

    private String[] getCppQuestions() { return new String[]{"Which symbol is used to end a statement in C++?", "Which keyword is used to create a class?", "Which function is the starting point of a C++ program?", "Which header is commonly used for input and output?", "Which operator accesses members through an object?"}; }
    private String[][] getCppOptions() { return new String[][]{{".", ";", ":", ","}, {"object", "struct", "class", "define"}, {"start()", "run()", "main()", "execute()"}, {"iostream", "stdio", "string", "math"}, {".", "->", "::", "*"}}; }
    private int[] getCppAnswers() { return new int[]{1, 2, 2, 0, 0}; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(code::new);
    }

    // Custom UI Component for modern rounded buttons with hover animations
    class ModernButton extends JButton {
        private Color normalColor;
        private Color hoverColor;

        public ModernButton(String text, Color normalColor, Color textColor) {
            super(text);
            this.normalColor = normalColor;
            this.hoverColor = normalColor.darker();
            setForeground(textColor);
            setBackground(normalColor);
            setFocusPainted(false);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { setBackground(hoverColor); }
                @Override
                public void mouseExited(MouseEvent e) { setBackground(normalColor); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            super.paintComponent(g);
            g2.dispose();
        }
    }
}