import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.prefs.Preferences;
import java.io.*;

// Registration Frame
public class Logininterface extends JFrame implements ActionListener {
    private JLabel usernameLabel, passwordLabel, emailLabel;
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JToggleButton showPasswordToggle; // add a toggle button to show/hide the password
    private ArrayList<String[]> userList;
    private JPanel panel_1;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JRadioButton Showpass;

    public Logininterface() {
        // Set up the JFrame
        setTitle("Registration Form");
        setSize(572, 554);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the ArrayList
        userList = new ArrayList<>();


        // Create the form panel and add the components
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(8, 12, 16));
        formPanel.setLayout(null);

        // Add the panel to the frame
        getContentPane().add(formPanel);
        
        panel_1 = new JPanel();
        panel_1.setBackground(new Color(16, 20, 26));
        panel_1.setBounds(113, 161, 346, 329);
        formPanel.add(panel_1);
        panel_1.setLayout(null);
        
                registerButton = new JButton("Register");
                registerButton.setForeground(new Color(255, 255, 255));
                registerButton.setBackground(new Color(26, 136, 44));
                registerButton.setBounds(51, 256, 242, 32);
                panel_1.add(registerButton);
                
                showPasswordToggle = new JToggleButton("Show Password");
                showPasswordToggle.setFont(new Font("Tahoma", Font.PLAIN, 8));
                showPasswordToggle.setForeground(new Color(255, 255, 255));
                showPasswordToggle.setBackground(new Color(26, 136, 44));
                showPasswordToggle.setBounds(51, 222, 101, 23);
                panel_1.add(showPasswordToggle);
                JLabel label = new JLabel("");
                label.setBounds(180, 129, 279, 90);
                panel_1.add(label);
                passwordField = new JPasswordField(20);
                passwordField.setForeground(new Color(255, 255, 255));
                passwordField.setBackground(new Color(8, 12, 16));
                passwordField.setBounds(52, 179, 241, 32);
                panel_1.add(passwordField);
                passwordLabel = new JLabel("Password");
                passwordLabel.setBounds(51, 149, 84, 32);
                panel_1.add(passwordLabel);
                passwordLabel.setForeground(new Color(255, 255, 255));
                emailField = new JTextField(20);
                emailField.setForeground(new Color(255, 255, 255));
                emailField.setBackground(new Color(8, 12, 16));
                emailField.setBounds(51, 119, 241, 32);
                panel_1.add(emailField);
                
                        // Create the form components
                        usernameLabel = new JLabel("Username");
                        usernameLabel.setBounds(51, 87, 84, 32);
                        panel_1.add(usernameLabel);
                        usernameLabel.setForeground(new Color(255, 255, 255));
                        
                                usernameField = new JTextField(20);
                                usernameField.setForeground(new Color(255, 255, 255));
                                usernameField.setBackground(new Color(8, 12, 16));
                                usernameField.setBounds(51, 54, 242, 35);
                                panel_1.add(usernameField);
                                emailLabel = new JLabel("Email");
                                emailLabel.setBounds(51, 23, 84, 32);
                                panel_1.add(emailLabel);
                                emailLabel.setForeground(new Color(255, 255, 255));
                                
                                lblNewLabel = new JLabel("Sign in to GitHub");
                                lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 30));
                                lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                lblNewLabel.setForeground(new Color(255, 255, 255));
                                lblNewLabel.setBounds(153, 87, 273, 81);
                                formPanel.add(lblNewLabel);
                                
                                lblNewLabel_1 = new JLabel("Logo");
                                lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Andrew Tan\\Downloads\\SMALLGITHUB.png"));
                                lblNewLabel_1.setBounds(31, 43, 487, 461);
                                formPanel.add(lblNewLabel_1);
                showPasswordToggle.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (showPasswordToggle.isSelected()) {
                            passwordField.setEchoChar((char) 0); // show password
                        } else {
                            passwordField.setEchoChar('*'); // hide password
                        }
                    }
                });
                registerButton.addActionListener(this);

        // Make the frame visible
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Get the user input and add it to the ArrayList
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // Check if password is strong enough
        boolean isStrongPassword = password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$");

        if (!isStrongPassword) {
            JOptionPane.showMessageDialog(this, "Password must be at least 12 characters long and contain a combination of uppercase and lowercase letters, numbers, and symbols", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] user = {username, email, password};
        userList.add(user);

        // Switch to the login form
        new LoginForm(userList);

        // Dispose the registration form
        dispose();
    }


    public static void main(String[] args) {
        new Logininterface();
    }
}

// Login Frame
class LoginForm extends JFrame implements ActionListener {
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, forgotPasswordButton, loadRememberedUserButton;
    private JCheckBox rememberMeCheckBox;
    private ArrayList<String[]> userList;

    private ArrayList<String[]> registeredUsers = new ArrayList<>();
    private static final String USERNAME_PREF_KEY = "username";
    private static final String PASSWORD_PREF_KEY = "password";
    private static final String REMEMBER_ME_PREF_KEY = "remember_me";
    private JPanel Inner;
    private JLabel Sign;
    private JLabel Logo;
  

    public LoginForm(ArrayList<String[]> userList) {
        // Set up the JFrame
        setTitle("Login Form");
        setSize(572, 554);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the userList
        this.userList = userList;

        // Create the form components
        usernameLabel = new JLabel("Username or Email:");
        passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        rememberMeCheckBox = new JCheckBox("Remember Me");
        rememberMeCheckBox.setSelected(getRememberMeState());

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Logininterface();
            }
        });

        loadRememberedUserButton = new JButton("Load Remembered User");
        loadRememberedUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FileReader fr = new FileReader("credentials.txt");
                    BufferedReader br = new BufferedReader(fr);
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println("Credentials: " + line);
                        String[] credentials = line.split(",");
                        registeredUsers.add(credentials);
                    }
                    br.close();
                    String[] userNames = new String[registeredUsers.size()];
                    for (int i = 0; i < registeredUsers.size(); i++) {
                        userNames[i] = registeredUsers.get(i)[0];
                    }
                    int option = JOptionPane.showOptionDialog(null, "Registered Users:\n" + String.join("\n", userNames), "Registered Users", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{ "OK" }, "OK");
                    if (option == JOptionPane.YES_OPTION) {
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Create the form panel and add the components
        JPanel Outer = new JPanel();
        Outer.setBackground(new Color(8, 12, 16));

        // Add the register button to the bottom of the frame
        getContentPane().add(Outer, BorderLayout.CENTER);
        Outer.setLayout(null);
        
        Inner = new JPanel();
        Inner.setBounds(39, 168, 484, 288);
        Inner.setBackground(new Color(16, 20, 26));
        Outer.add(Inner);
                Inner.setLayout(null);
        
                // Create the form components
                usernameLabel = new JLabel("Username or Email:");
                usernameLabel.setBounds(53, 11, 171, 40);
                usernameLabel.setForeground(new Color(255, 255, 255));
                Inner.add(usernameLabel);
                
                        usernameField = new JTextField(20);
                        usernameField.setBounds(53, 57, 391, 32);
                        usernameField.setForeground(new Color(255, 255, 255));
                        usernameField.setBackground(new Color(8, 12, 16));
                        Inner.add(usernameField);
                        passwordField = new JPasswordField(20);
                        passwordField.setBounds(53, 138, 391, 32);
                        passwordField.setForeground(new Color(255, 255, 255));
                        passwordField.setBackground(new Color(8, 12, 16));
                        Inner.add(passwordField);
                        
                                loginButton = new JButton("Login");
                                loginButton.setBounds(53, 219, 391, 32);
                                loginButton.setForeground(new Color(255, 255, 255));
                                loginButton.setBackground(new Color(26, 136, 44));
                                Inner.add(loginButton);
                                
                                        forgotPasswordButton = new JButton("Forgot Password");
                                        forgotPasswordButton.setBounds(331, 109, 113, 23);
                                        forgotPasswordButton.setBackground(new Color(16, 20, 26));
                                        forgotPasswordButton.setForeground(new Color(41, 119, 191));
                                        Inner.add(forgotPasswordButton);
                                        
                                                rememberMeCheckBox = new JCheckBox("Remember Me");
                                                rememberMeCheckBox.setBounds(53, 177, 185, 23);
                                                rememberMeCheckBox.setBackground(new Color(16, 20, 26));
                                                rememberMeCheckBox.setForeground(new Color(41, 119, 191));
                                                Inner.add(rememberMeCheckBox);
                                                rememberMeCheckBox.setSelected(getRememberMeState());
                                                
                                                passwordLabel = new JLabel("Password:");
                                                passwordLabel.setBounds(53, 100, 92, 40);
                                                passwordLabel.setForeground(new Color(255, 255, 255));
                                                Inner.add(passwordLabel);
                                                
                                                        registerButton = new JButton("Register");
                                                        registerButton.setBounds(88, 481, 391, 23);
                                                        registerButton.setForeground(new Color(255, 255, 255));
                                                        registerButton.setBackground(new Color(26, 136, 44));
                                                        Outer.add(registerButton);
                                                        
                                                        Sign = new JLabel("Log in to GitHub");
                                                        Sign.setFont(new Font("Arial", Font.BOLD, 30));
                                                        Sign.setHorizontalAlignment(SwingConstants.CENTER);
                                                        Sign.setForeground(new Color(255, 255, 255));
                                                        Sign.setBounds(39, 92, 488, 75);
                                                        Outer.add(Sign);
                                                        
                                                        Logo = new JLabel("");
                                                        Logo.setIcon(new ImageIcon("C:\\Users\\Andrew Tan\\Downloads\\SMALLGITHUB.png"));
                                                        Logo.setBounds(20, 29, 536, 475);
                                                        Outer.add(Logo);
                                                        registerButton.addActionListener(new ActionListener() {
                                                            public void actionPerformed(ActionEvent e) {
                                                                new Logininterface();
                                                            }
                                                        });
                                        forgotPasswordButton.addActionListener(this);
                                loginButton.addActionListener(this);
        setVisible(true);

        // If "Remember Me" was checked, populate the fields with the saved credentials
        if (rememberMeCheckBox.isSelected()) {
            String[] savedCredentials = getSavedCredentials();
            if (savedCredentials != null) {
                usernameField.setText(savedCredentials[0]);
                passwordField.setText(savedCredentials[1]);
            }
        }
    }

    private boolean getRememberMeState() {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        return prefs.getBoolean(REMEMBER_ME_PREF_KEY, false);
    }

    private void saveRememberMeState(boolean rememberMe) {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        prefs.putBoolean(REMEMBER_ME_PREF_KEY, rememberMe);
    }

    private String[] getSavedCredentials() {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        String username = prefs.get(USERNAME_PREF_KEY, null);
        String password = prefs.get(PASSWORD_PREF_KEY, null);
        if (username != null && password != null) {
            return new String[] { username, password };
        } else {
            return null;
        }
    }

    private void saveCredentials(String username, String password) {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        prefs.put(USERNAME_PREF_KEY, username);
        prefs.put(PASSWORD_PREF_KEY, password);
    }

    public void actionPerformed1(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check if the entered username and password match any of the users in the list
        boolean matchFound = false;
        for (String[] user : userList) {
            if (user[0].equals(username) && user[1].equals(password)) {
                matchFound = true;
                break;
            }
        }

        if (matchFound) {
            // If "Remember Me" is checked, save the username and password
            if (rememberMeCheckBox.isSelected()) {
                saveCredentials(username, password);
                saveRememberMeState(true);
            } else {
                saveRememberMeState(false);
            }
            // Open the main application
            new Logininterface();
            // Close the login form
            dispose();
        } else {
            // Display an error message if no matching user is found
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }
    
    // Welcome Frame
    class WelcomeForm extends JFrame {
        private JLabel welcomeLabel;
        private JButton signOutButton;
        private JLabel Pic;

        public WelcomeForm(String username) {
            // Set up the JFrame
            setTitle("Welcome");
            setSize(543, 512);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create the welcome message label
            welcomeLabel = new JLabel("You're Welcome, " + username + "!");
            welcomeLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 30));
            welcomeLabel.setBounds(0, 425, 527, 48);
            welcomeLabel.setForeground(new Color(255, 255, 255));

         // Create the sign-out button
            signOutButton = new JButton("Sign Out");
            signOutButton.setForeground(new Color(255, 255, 255));
            signOutButton.setBackground(new Color(26, 136, 44));
            signOutButton.setBounds(428, 5, 89, 23);
            signOutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new LoginForm(userList);
                    dispose();
                }
            });

            // Create the panel and add the label
            JPanel panel = new JPanel();
            panel.setBackground(new Color(16, 20, 26));
            panel.setLayout(null);
            panel.add(welcomeLabel);
            panel.add(signOutButton);

            // Add the panel to the frame and make it visible
            getContentPane().add(panel);
            
            Pic = new JLabel("");
            Pic.setBounds(-104, 34, 670, 395);
            Pic.setIcon(new ImageIcon("C:\\Users\\Andrew Tan\\Downloads\\maomao"));
            panel.add(Pic);
            setVisible(true);
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Get the user input and check if it matches any of the registered users
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        boolean isUserFound = false;
        for (String[] user : userList) {
            if (user[0].equals(username) || user[1].equals(username)) {
                if (user[2].equals(password)) {
                    isUserFound = true;
                    new WelcomeForm(user[0]);
                    dispose();
                    break;
                }
            }
        }
        if (!isUserFound) {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
