import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

// The GamePanel class is the main panel where the game will be drawn and updated.
public class GamePanel extends JPanel implements ActionListener{

    // Constants for the game screen dimensions and game logic.
    static final int SCREEN_WIDTH = 1300;  // Width of the game screen
    static final int SCREEN_HEIGHT = 750;  // Height of the game screen
    static final int UNIT_SIZE = 50;  // Size of each grid unit (snake and apple size)
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);  // Total number of grid units on the screen
    static final int DELAY = 175;  // Delay between game updates (controls speed)

    // Arrays to store the x and y coordinates of the snake's body parts.
    final int x[] = new int[GAME_UNITS];  // x-coordinates of the snake body parts
    final int y[] = new int[GAME_UNITS];  // y-coordinates of the snake body parts

    // Game state variables
    int bodyParts = 6;  // Initial number of body parts for the snake
    int applesEaten;  // Number of apples eaten by the snake
    int appleX;  // x-coordinate of the apple
    int appleY;  // y-coordinate of the apple
    char direction = 'R';  // Initial direction the snake is moving (R = right)
    boolean running = false;  // Flag to determine if the game is running
    Timer timer;  // Timer to control the game speed
    Random random;  // Random number generator for apple placement

    // Constructor to set up the game panel
    GamePanel(){
        random = new Random();  // Initialize the random number generator
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));  // Set the preferred size of the panel
        this.setBackground(Color.black);  // Set the background color of the panel
        this.setFocusable(true);  // Ensure the panel can gain focus to receive key events
        this.addKeyListener(new MyKeyAdapter());  // Add a key listener to the panel for controlling the snake
        startGame();  // Start the game
    }

    // Method to start the game
    public void startGame() {
        newApple();  // Create a new apple
        running = true;  // Set the game running flag to true
        timer = new Timer(DELAY,this);  // Initialize the timer with the specified delay
        timer.start();  // Start the timer
    }

    // Overridden method to paint the components (game elements) on the panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Call the superclass's paintComponent method
        draw(g);  // Draw the game elements
    }

    // Method to draw the game elements on the panel
    public void draw(Graphics g) {
        if(running) {
            // Uncomment the following lines to draw a grid on the screen
            /*
            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);  // Draw vertical grid lines
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);  // Draw horizontal grid lines
            }
            */
            g.setColor(Color.red);  // Set the color to red for the apple
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);  // Draw the apple as a filled oval

            // Draw the snake's body
            for(int i = 0; i< bodyParts;i++) {
                if(i == 0) {
                    g.setColor(Color.green);  // Set the color to green for the snake's head
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);  // Draw the head as a filled rectangle
                }
                else {
                    g.setColor(new Color(45,180,0));  // Set the color for the snake's body
                    // g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));  // Uncomment for random colors
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);  // Draw the body part as a filled rectangle
                }
            }
            // Draw the score on the screen
            g.setColor(Color.red);  // Set the color to red for the score text
            g.setFont( new Font("Ink Free",Font.BOLD, 40));  // Set the font for the score text
            FontMetrics metrics = getFontMetrics(g.getFont());  // Get the metrics for the font
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());  // Draw the score in the center
        }
        else {
            gameOver(g);  // If the game is not running, show the game over screen
        }
    }

    // Method to create a new apple at a random position
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;  // Random x-coordinate for the apple
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;  // Random y-coordinate for the apple
    }

    // Method to move the snake in the current direction
    public void move(){
        // Move the body parts to the position of the previous part
        for(int i = bodyParts;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        // Move the head in the current direction
        switch(direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;  // Move up
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;  // Move down
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;  // Move left
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;  // Move right
                break;
        }
    }

    // Method to check if the snake has eaten an apple
    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {  // If the head's position matches the apple's position
            bodyParts++;  // Increase the snake's body length
            applesEaten++;  // Increase the apple eaten count
            newApple();  // Generate a new apple
        }
    }

    // Method to check for collisions with the walls or the snake's body
    public void checkCollisions() {
        // Check if the snake's head collides with its body
        for(int i = bodyParts;i>0;i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                running = false;  // Stop the game if a collision is detected
            }
        }

        // Check if the snake's head touches the left border
        if(x[0] < 0) {
            running = false;
        }

        // Check if the snake's head touches the right border
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }

        // Check if the snake's head touches the top border
        if(y[0] < 0) {
            running = false;
        }

        // Check if the snake's head touches the bottom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        // If the game is not running, stop the timer
        if(!running) {
            timer.stop();
        }
    }

    // Method to display the game over screen
    public void gameOver(Graphics g) {
        // Draw the final score on the game over screen
        g.setColor(Color.red);  // Set the color to red for the text
        g.setFont( new Font("Ink Free",Font.BOLD, 40));  // Set the font for the score text
        FontMetrics metrics1 = getFontMetrics(g.getFont());  // Get the metrics for the font
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());  // Draw the score in the center

        // Draw the "Game Over" text
        g.setColor(Color.red);  // Set the color to red for the text
        g.setFont( new Font("Ink Free",Font.BOLD, 75));  // Set the font for the "Game Over" text
        FontMetrics metrics2 = getFontMetrics(g.getFont());  // Get the metrics for the font
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);  // Draw "Game Over" in the center
    }

    // Overridden method that is called whenever an action event occurs (timer tick)
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();  // Move the snake
            checkApple();  // Check if an apple was eaten
            checkCollisions();  // Check for collisions
        }
        repaint();  // Repaint the panel to update the game screen
    }

    // Inner class to handle keyboard input for controlling the snake
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            // Change the direction of the snake based on the arrow key pressed
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {  // Prevent the snake from reversing direction
                        direction = 'L';  // Move left
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {  // Prevent the snake from reversing direction
                        direction = 'R';  // Move right
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {  // Prevent the snake from reversing direction
                        direction = 'U';  // Move up
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {  // Prevent the snake from reversing direction
                        direction = 'D';  // Move down
                    }
                    break;
            }
        }
    }
}