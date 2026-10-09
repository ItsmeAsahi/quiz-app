import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class code extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Active Session
    private String loggedInUser = "";

    // Quiz State
    private String currentSubject = "";
    private List<QuizQuestion> activeQuestions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    // UI Elements for Dynamic Updates
    private JLabel dashboardWelcomeLabel;
    private JLabel dashboardQuizzesLabel;
    private JLabel dashboardAccuracyLabel;
    private JLabel questionLabel;
    private ModernButton[] optionButtons;
    private JLabel progressLabel;

    // Colors
    private final Color bgColor = new Color(245, 247, 250);
    private final Color primaryColor = new Color(67, 97, 238);
    private final Color textColor = new Color(43, 45, 66);
    private final Color cardColor = Color.WHITE;

    public code() {
        setTitle("Online Quiz Application");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        DatabaseHelper.initializeDatabase();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(bgColor);

        // Build Screens
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createRegisterPanel(), "Register");
        mainPanel.add(createDashboardPanel(), "Dashboard");
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
        card.setBounds(150, 60, 400, 380);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        JLabel title = new JLabel("Welcome Back", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(textColor);
        title.setBounds(0, 30, 400, 40);
        card.add(title);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(50, 100, 300, 20);
        card.add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(50, 125, 300, 35);
        card.add(usernameField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(50, 175, 300, 20);
        card.add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 200, 300, 35);
        card.add(passwordField);

        ModernButton loginBtn = new ModernButton("Login", primaryColor, Color.WHITE);
        loginBtn.setBounds(50, 260, 145, 40);
        loginBtn.addActionListener(e -> {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());
            
            if (DatabaseHelper.authenticateUser(user, pass)) {
                loggedInUser = user;
                loadDashboard();
                usernameField.setText("");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        card.add(loginBtn);

        ModernButton registerBtn = new ModernButton("Register", new Color(108, 117, 125), Color.WHITE);
        registerBtn.setBounds(205, 260, 145, 40);
        registerBtn.addActionListener(e -> {
            cardLayout.show(mainPanel, "Register");
            usernameField.setText("");
            passwordField.setText("");
        });
        card.add(registerBtn);

        panel.add(card);
        return panel;
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(bgColor);

        JPanel card = new JPanel(null);
        card.setBackground(cardColor);
        card.setBounds(150, 60, 400, 380);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(0, 30, 400, 40);
        card.add(title);

        JLabel userLabel = new JLabel("New Username");
        userLabel.setBounds(50, 100, 300, 20);
        card.add(userLabel);

        JTextField regUsernameField = new JTextField();
        regUsernameField.setBounds(50, 125, 300, 35);
        card.add(regUsernameField);

        JLabel passLabel = new JLabel("New Password");
        passLabel.setBounds(50, 175, 300, 20);
        card.add(passLabel);

        JPasswordField regPasswordField = new JPasswordField();
        regPasswordField.setBounds(50, 200, 300, 35);
        card.add(regPasswordField);

        ModernButton submitBtn = new ModernButton("Sign Up", primaryColor, Color.WHITE);
        submitBtn.setBounds(50, 260, 300, 40);
        submitBtn.addActionListener(e -> {
            String newUsername = regUsernameField.getText().trim();
            String newPassword = new String(regPasswordField.getPassword());

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.");
                return;
            }
            
            if (DatabaseHelper.registerUser(newUsername, newPassword)) {
                JOptionPane.showMessageDialog(this, "Registration Successful! You can now log in.");
                regUsernameField.setText("");
                regPasswordField.setText("");
                cardLayout.show(mainPanel, "Login");
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        card.add(submitBtn);

        ModernButton backBtn = new ModernButton("Back to Login", new Color(108, 117, 125), Color.WHITE);
        backBtn.setBounds(50, 310, 300, 40);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        card.add(backBtn);

        panel.add(card);
        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(bgColor);

        dashboardWelcomeLabel = new JLabel("Welcome, User!", SwingConstants.CENTER);
        dashboardWelcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        dashboardWelcomeLabel.setForeground(textColor);
        dashboardWelcomeLabel.setBounds(0, 40, 700, 40);
        panel.add(dashboardWelcomeLabel);

        // Statistics Card
        JPanel statsCard = new JPanel(new GridLayout(2, 1, 10, 10));
        statsCard.setBackground(cardColor);
        statsCard.setBounds(200, 110, 300, 120);
        statsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                new EmptyBorder(20, 20, 20, 20)));

        dashboardQuizzesLabel = new JLabel("Total Quizzes Taken: 0", SwingConstants.CENTER);
        dashboardQuizzesLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        statsCard.add(dashboardQuizzesLabel);

        dashboardAccuracyLabel = new JLabel("Overall Accuracy: 0%", SwingConstants.CENTER);
        dashboardAccuracyLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        statsCard.add(dashboardAccuracyLabel);
        
        panel.add(statsCard);

        ModernButton takeQuizBtn = new ModernButton("Take a Quiz", primaryColor, Color.WHITE);
        takeQuizBtn.setBounds(200, 270, 300, 50);
        takeQuizBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        panel.add(takeQuizBtn);

        ModernButton logoutBtn = new ModernButton("Logout", new Color(220, 53, 69), Color.WHITE);
        logoutBtn.setBounds(250, 340, 200, 40);
        logoutBtn.addActionListener(e -> {
            loggedInUser = "";
            cardLayout.show(mainPanel, "Login");
        });
        panel.add(logoutBtn);

        return panel;
    }

    private void loadDashboard() {
        dashboardWelcomeLabel.setText("Welcome, " + loggedInUser + "!");
        
        int[] stats = DatabaseHelper.getUserStats(loggedInUser);
        int quizzesTaken = stats[0];
        int totalCorrect = stats[1];
        int totalQuestions = stats[2];

        dashboardQuizzesLabel.setText("Total Quizzes Taken: " + quizzesTaken);
        
        int accuracy = totalQuestions == 0 ? 0 : (int) Math.round((totalCorrect * 100.0) / totalQuestions);
        dashboardAccuracyLabel.setText("Overall Accuracy: " + accuracy + "%");
        
        cardLayout.show(mainPanel, "Dashboard");
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(bgColor);

        JLabel title = new JLabel("Select a Subject", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBounds(0, 60, 700, 40);
        panel.add(title);

        ModernButton javaBtn = new ModernButton("Java Quiz", primaryColor, Color.WHITE);
        javaBtn.setBounds(200, 140, 300, 50);
        javaBtn.addActionListener(e -> loadQuiz("Java", getJavaQuestions()));

        ModernButton pythonBtn = new ModernButton("Python Quiz", primaryColor, Color.WHITE);
        pythonBtn.setBounds(200, 210, 300, 50);
        pythonBtn.addActionListener(e -> loadQuiz("Python", getPythonQuestions()));

        ModernButton cppBtn = new ModernButton("C++ Quiz", primaryColor, Color.WHITE);
        cppBtn.setBounds(200, 280, 300, 50);
        cppBtn.addActionListener(e -> loadQuiz("C++", getCppQuestions()));

        ModernButton backBtn = new ModernButton("Back to Dashboard", Color.GRAY, Color.WHITE);
        backBtn.setBounds(250, 370, 200, 40);
        backBtn.addActionListener(e -> loadDashboard());

        panel.add(javaBtn);
        panel.add(pythonBtn);
        panel.add(cppBtn);
        panel.add(backBtn);

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

        questionLabel = new JLabel("Question text?", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
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
        
        ModernButton quitBtn = new ModernButton("Quit Quiz", new Color(220, 53, 69), Color.WHITE);
        quitBtn.setBounds(275, 430, 150, 35);
        quitBtn.addActionListener(e -> loadDashboard());
        panel.add(quitBtn);

        return panel;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(bgColor);

        JLabel title = new JLabel("Quiz Completed!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setBounds(0, 40, 700, 50);
        panel.add(title);

        JLabel scoreDisplay = new JLabel();
        scoreDisplay.setFont(new Font("Segoe UI", Font.BOLD, 36));
        scoreDisplay.setForeground(primaryColor);
        scoreDisplay.setHorizontalAlignment(SwingConstants.CENTER);
        scoreDisplay.setBounds(0, 100, 700, 60);
        panel.add(scoreDisplay);

        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBounds(250, 170, 200, 200);
        chartContainer.setOpaque(false);
        panel.add(chartContainer);

        JLabel legend = new JLabel("<html><font color='#2ECC71'>■ Correct</font> &nbsp; <font color='#E74C3C'>■ Incorrect</font></html>", SwingConstants.CENTER);
        legend.setBounds(0, 380, 700, 30);
        panel.add(legend);

        ModernButton dashBtn = new ModernButton("Return to Dashboard", primaryColor, Color.WHITE);
        dashBtn.setBounds(225, 430, 250, 50);
        dashBtn.addActionListener(e -> loadDashboard());
        panel.add(dashBtn);

        panel.putClientProperty("scoreLabel", scoreDisplay);
        panel.putClientProperty("chartContainer", chartContainer);
        return panel;
    }

    private void loadQuiz(String subject, List<QuizQuestion> questions) {
        currentSubject = subject;
        activeQuestions = questions;
        Collections.shuffle(activeQuestions);
        currentQuestionIndex = 0;
        score = 0;

        updateQuizUI();
        cardLayout.show(mainPanel, "Quiz");
    }

    private void updateQuizUI() {
        progressLabel.setText(currentSubject + " Quiz - Question " + (currentQuestionIndex + 1) + " / " + activeQuestions.size());
        QuizQuestion currentQ = activeQuestions.get(currentQuestionIndex);
        
        questionLabel.setText("<html><div style='text-align: center;'>" + currentQ.question + "</div></html>");
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(currentQ.options[i]);
        }
    }

    private void handleAnswer(int selectedIndex) {
        if (selectedIndex == activeQuestions.get(currentQuestionIndex).correctIndex) {
            score++;
        }
        currentQuestionIndex++;
        if (currentQuestionIndex < activeQuestions.size()) {
            updateQuizUI();
        } else {
            showResults();
        }
    }

    private void showResults() {
        DatabaseHelper.saveScore(loggedInUser, currentSubject, score, activeQuestions.size());

        JPanel resultPanel = (JPanel) mainPanel.getComponent(5); // Result panel is index 5
        
        JLabel scoreLabel = (JLabel) resultPanel.getClientProperty("scoreLabel");
        scoreLabel.setText("You Scored: " + score + " / " + activeQuestions.size());

        JPanel chartContainer = (JPanel) resultPanel.getClientProperty("chartContainer");
        chartContainer.removeAll();
        chartContainer.add(new PerformanceChart(score, activeQuestions.size() - score));
        chartContainer.revalidate();
        chartContainer.repaint();

        cardLayout.show(mainPanel, "Result");
    }

    // --- Data Models ---
    class QuizQuestion {
        String question;
        String[] options;
        int correctIndex;

        public QuizQuestion(String q, String[] opts, int ans) {
            this.question = q;
            this.options = opts;
            this.correctIndex = ans;
        }
    }

    // --- Quiz Data Generators ---
    private List<QuizQuestion> getJavaQuestions() {
        List<QuizQuestion> list = new ArrayList<>();
        list.add(new QuizQuestion("Which keyword inherits a class in Java?", new String[]{"implements", "extends", "inherits", "super"}, 1));
        list.add(new QuizQuestion("Which method is the starting point of a Java program?", new String[]{"start()", "run()", "main()", "execute()"}, 2));
        list.add(new QuizQuestion("Which collection does not allow duplicates?", new String[]{"List", "ArrayList", "Set", "Vector"}, 2));
        list.add(new QuizQuestion("Which keyword creates an object?", new String[]{"class", "new", "object", "create"}, 1));
        list.add(new QuizQuestion("Which package contains ArrayList?", new String[]{"java.io", "java.util", "java.lang", "java.sql"}, 1));
        return list;
    }

    private List<QuizQuestion> getPythonQuestions() {
        List<QuizQuestion> list = new ArrayList<>();
        list.add(new QuizQuestion("Which symbol creates a comment in Python?", new String[]{"//", "#", "/*", "--"}, 1));
        list.add(new QuizQuestion("Which function displays output?", new String[]{"display()", "show()", "print()", "output()"}, 2));
        list.add(new QuizQuestion("Which data type stores multiple values in order?", new String[]{"list", "int", "float", "bool"}, 0));
        list.add(new QuizQuestion("Which keyword defines a function?", new String[]{"function", "def", "fun", "define"}, 1));
        list.add(new QuizQuestion("Which extension is used for Python files?", new String[]{".java", ".cpp", ".py", ".python"}, 2));
        return list;
    }

    private List<QuizQuestion> getCppQuestions() {
        List<QuizQuestion> list = new ArrayList<>();
        list.add(new QuizQuestion("Which symbol ends a statement in C++?", new String[]{".", ";", ":", ","}, 1));
        list.add(new QuizQuestion("Which keyword creates a class?", new String[]{"object", "struct", "class", "define"}, 2));
        list.add(new QuizQuestion("Which function starts a C++ program?", new String[]{"start()", "run()", "main()", "execute()"}, 2));
        list.add(new QuizQuestion("Which header is used for input and output?", new String[]{"iostream", "stdio", "string", "math"}, 0));
        list.add(new QuizQuestion("Which operator accesses members through an object?", new String[]{".", "->", "::", "*"}, 0));
        return list;
    }

    // --- Custom UI Components ---
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
                public void mouseEntered(MouseEvent e) { setBackground(hoverColor); }
                public void mouseExited(MouseEvent e) { setBackground(normalColor); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    class PerformanceChart extends JPanel {
        private int correct, wrong;

        public PerformanceChart(int correct, int wrong) {
            this.correct = correct;
            this.wrong = wrong;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int total = correct + wrong;
            if (total == 0) return;

            int correctAngle = (int) Math.round(((double) correct / total) * 360);
            int wrongAngle = 360 - correctAngle;

            g2.setColor(new Color(46, 204, 113));
            g2.fillArc(10, 10, 180, 180, 90, -correctAngle);

            g2.setColor(new Color(231, 76, 60));
            g2.fillArc(10, 10, 180, 180, 90 - correctAngle, -wrongAngle);
        }
    }

    // --- Local Database Logic ---
    static class DatabaseHelper {
        private static final String USERS_FILE = "users.csv";
        private static final String SCORES_FILE = "scores.csv";

        public static void initializeDatabase() {
            try {
                new File(USERS_FILE).createNewFile();
                new File(SCORES_FILE).createNewFile();
            } catch (IOException e) {
                System.out.println("Could not create database files.");
            }
        }

        public static boolean registerUser(String username, String password) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.split(",")[0].equals(username)) return false; 
                }
            } catch (IOException ignored) {}

            try (FileWriter fw = new FileWriter(USERS_FILE, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(username + "," + password);
                return true;
            } catch (IOException e) { return false; }
        }

        public static boolean authenticateUser(String username, String password) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) return true;
                }
            } catch (IOException e) {}
            return false;
        }

        public static void saveScore(String username, String subject, int score, int total) {
            try (FileWriter fw = new FileWriter(SCORES_FILE, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(username + "," + subject + "," + score + "," + total);
            } catch (IOException ignored) {}
        }

        // Returns {totalQuizzesTaken, totalCorrectAnswers, totalQuestionsAttempted}
        public static int[] getUserStats(String username) {
            int quizzesTaken = 0;
            int totalScore = 0;
            int totalQuestions = 0;

            try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4 && parts[0].equals(username)) {
                        quizzesTaken++;
                        totalScore += Integer.parseInt(parts[2]);
                        totalQuestions += Integer.parseInt(parts[3]);
                    }
                }
            } catch (IOException | NumberFormatException ignored) {}

            return new int[]{quizzesTaken, totalScore, totalQuestions};
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(code::new);
    }
}