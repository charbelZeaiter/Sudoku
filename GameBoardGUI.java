import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This Class is used to create, load and display the actual game board GUI
 * part of the game .
 */
public class GameBoardGUI extends JLabel{
    
    private GUIInterface parentFrame;
    private JLabel assistsRemaining;
    private JLabel[][] jLabelGrid;
    private JButton assistButton;
    private JLabel timer;
    private CountdownTimer countdown;
    
    /**
     * Used to construct and set initial values of the object.
     * @param aParentFrame GUIInterface, A reference to the parent component to add itself to.
     */
    public GameBoardGUI(GUIInterface aParentFrame)
    {
        this.parentFrame = aParentFrame;
    }
    
    /**
     * Loads the game board GUI with all the features of the difficulty level
     * specified.
     * @param aLevel GameDifficulty, The enumerated difficulty level of the game.
     */
    public void loadGameBoardGUI(GameDifficulty aLevel)
    {
        // Setting layout for current 'this' component.
        this.setLayout(new GridBagLayout());
        
        // Customize with certain backgrounds based on difficulty level.
        if(aLevel == GameDifficulty.BEGINNER)
        {
            Icon newBgImageIcon = new ImageIcon("src/beginnerBg.png");
            this.setIcon(newBgImageIcon);
        }
        else if(aLevel == GameDifficulty.INTERMEDIATE)
        {
            Icon newBgImageIcon = new ImageIcon("src/intermediateBg.png");
            this.setIcon(newBgImageIcon);
        }
        else if(aLevel == GameDifficulty.ADVANCED)
        {
            Icon newBgImageIcon = new ImageIcon("src/advancedBg.png");
            this.setIcon(newBgImageIcon);
        }
        
        // Preparing layout constraints object to be used to adjust layout for subsequent components.
        GridBagConstraints myConstraints = new GridBagConstraints();
        
        // Creating the title/heading for the GUI page and adding to overall Gameboard panel.
        Icon newTitleImageIcon = new ImageIcon("src/playTitle.png");
        JLabel screenTitle = new JLabel(newTitleImageIcon);
        myConstraints.gridx = 0;
        myConstraints.gridy = 0;
        myConstraints.gridwidth = 5;
        this.add(screenTitle, myConstraints);
        
        // Creating the timer box and adding to overall Gameboard panel.
        JLabel timer = new JLabel("timer", JLabel.CENTER);
        timer.setOpaque(true);
        timer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        myConstraints.gridx = 4;
        myConstraints.gridy = 2;
        myConstraints.gridwidth = 1;
        myConstraints.fill = GridBagConstraints.HORIZONTAL;
        this.add(timer, myConstraints);
        this.timer = timer;
        
        // Creating countdown timer to display time remaining
        this.countdown = new CountdownTimer(aLevel, this, parentFrame);
        
        // Preparing Game Board button mouse listener.
        MouseListener newButtonMouseListener = new MyGBButtonMouseAdapter(this.parentFrame, this);
        
        // Creating the 'assist' button and adding to overall Gameboard panel.
        JButton assistButton = new JButton("Assist");
        assistButton.setName("Assist");
        myConstraints.gridx = 4;
        myConstraints.gridy = 4;
        myConstraints.gridwidth = 1;
        myConstraints.fill = GridBagConstraints.HORIZONTAL;
        this.add(assistButton, myConstraints);
        this.assistButton = assistButton;
        
        // Creating the 'assists remaining' box and adding to overall Gameboard panel.
        JLabel assistsRemaining = new JLabel("   "+Game.assistCount+"   ", JLabel.CENTER);
        assistsRemaining.setOpaque(true);
        assistsRemaining.setName("Assists Remaining");
        assistsRemaining.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        myConstraints.gridx = 4;
        myConstraints.gridy = 6;
        myConstraints.gridwidth = 1;
        myConstraints.fill = GridBagConstraints.HORIZONTAL;
        this.add(assistsRemaining, myConstraints);
        this.assistsRemaining = assistsRemaining;
        
        // Creating the 'back' button and adding to overall Gameboard panel. 
        JButton backButton = new JButton("Back");
        backButton.setName("Back");
        backButton.addMouseListener(newButtonMouseListener);
        myConstraints.gridx = 0;
        myConstraints.gridy = 6;
        myConstraints.gridwidth = 1;
        myConstraints.fill = GridBagConstraints.HORIZONTAL;
        this.add(backButton, myConstraints);
        
        // Column padding using empty box.
        myConstraints.gridx = 3;
        myConstraints.gridy = 0;
        myConstraints.gridwidth = 1;
        myConstraints.gridheight = 4;
        this.add(Box.createRigidArea(new Dimension(15,30)), myConstraints);
        
        // Under title padding using empty box.
        myConstraints.gridx = 0;
        myConstraints.gridy = 1;
        myConstraints.gridwidth = 5;
        myConstraints.gridheight = 1;
        this.add(Box.createRigidArea(new Dimension(250,50)), myConstraints);
        
        // Under grid padding using empty box.
        myConstraints.gridx = 0;
        myConstraints.gridy = 5;
        myConstraints.gridwidth = 5;
        myConstraints.gridheight = 1;
        this.add(Box.createRigidArea(new Dimension(20,10)), myConstraints);
        
        // Creating sudoku grid.
        JPanel sudokuGridPanel  = new JPanel();
        sudokuGridPanel.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
        
        // Setting Sudoku JPanel layout.
        sudokuGridPanel.setLayout(new GridLayout(9, 9, 0, 0));
        
        // Preparing mouse listener to be used by each of the boxes on user interaction.
        MouseListener newMyMouseAdapter = new MyGBGridMouseAdapter(this.parentFrame, this, this.assistButton);
        
        // Create 2D array to store all JLabels (for later access).
        this.jLabelGrid = new JLabel[9][9];
        
        // Create grid boxes in a loop.
        for(int row=0;row<9;row++)
        {   
            for(int col=0;col<9;col++)
            {   
                JLabel boxLabel = new JLabel("   ", JLabel.CENTER);
                
                // Setting the features of created box.
                boxLabel.setFocusable(true);
                boxLabel.setName(col+","+row);
                boxLabel.setFont(new Font("Serif", Font.BOLD, 20));
                boxLabel.setOpaque(true);
                boxLabel.setBackground(Color.WHITE);
                boxLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                boxLabel.addMouseListener(newMyMouseAdapter);
                
                // Depending on the box position, associate it with a particular boarder in order
                // to get the bored look we want. (these define the thick Sudoku grid boarder lines.)
                if(row == 3 || row == 6)
                {
                    boxLabel.setBorder(BorderFactory.createMatteBorder(4, 1, 1, 1, Color.BLACK));
                }
                
                if (col == 3 || col == 6)
                {
                    boxLabel.setBorder(BorderFactory.createMatteBorder(1, 4, 1, 1, Color.BLACK));
                }
                
                if ( (row == 3 || row == 6) && (col == 3 || col == 6))
                {
                    boxLabel.setBorder(BorderFactory.createMatteBorder(4, 4, 1, 1, Color.BLACK));
                }
                
                // Save box reference in array.
                this.jLabelGrid[row][col] = boxLabel;
                
                // Add box to overall sudoku panel.
                sudokuGridPanel.add(boxLabel);
            }
        }
        
        // With formating, add the sudoku panel to the overall gameboard GUI panel.
        myConstraints.gridx = 0;
        myConstraints.gridy = 2;
        myConstraints.gridwidth = 3;
        myConstraints.gridheight = 3;
        this.add(sudokuGridPanel, myConstraints);
        
    }
    
    /**
     * Used to launch (or display) the actual gameboard GUI after it has been set up using 
     * the gameboard load function. 
     */
    public void launchGameBoardGUI()
    {   
        // Add current gameboard GUI to main GUI frame/window. 
        this.parentFrame.add(this);
        
        // Refresh/repaint/revalidate the gameboard.
        this.parentFrame.validate();
        this.parentFrame.repaint();
    }
    
    /**
     * Used to set a Sudoku grid box to a correct value that cannot be changed by the user. Used after the gameboard
     * load function to set the starting values of the board. 
     * @param aX Integer, The X postion.
     * @param aY Integer, The Y postion.
     * @param aValue Integer, The value to be put in the grid box/cell.
     */
    public void setSolidGridCellValue(int aX, int aY, int aValue)
    {
        // Get box/cell in position.
        JLabel cell = this.jLabelGrid[aY][aX];
        
        // Set to desired value.
        cell.setText(" "+aValue+" ");
        
        // Modify its looks so that users know what cannot be changed.
        cell.setBackground(Color.LIGHT_GRAY);
        
        // Get any mouse listeners previously created with it and remove them
        // rendering it unaccessible from the users end.
        MouseListener[] mouseListenerList = cell.getMouseListeners();
        
        for(MouseListener listener : mouseListenerList)
        {
            cell.removeMouseListener(listener);
        }
        
        // Refresh/repaint/revalidate the gameboard.
        this.parentFrame.validate();
        this.parentFrame.repaint();
    }
    
    /**
     * Used to set a Sudoku grid box to a correct value (this value can be changed by
     * the user. Used after the gameboard load and launch functions usually by the users entering in
     * values with the keyboard. 
     * @param aX Integer, The X position.
     * @param aY Integer, The Y position.
     * @param aValue Integer, The value to be put in the grid box/cell
     */
    public void setGridCellValue(int aX, int aY, int aValue)
    {   
        // Get corresponding box/cell and set its value.
        this.jLabelGrid[aY][aX].setText(" "+aValue+" ");
        
        // Refresh/repaint/revalidate the gameboard.
        this.parentFrame.validate();
        this.parentFrame.repaint();
    }
    
    /**
     * Used to get a Sudoku grid box value. Used after the gameboard load function.
     * @param aX Integer, The X position.
     * @param aY Integer, The Y position.
     * @return Integer, The value of the Sudoku box/cell.
     */
    public int getGridCellValue(int aX, int aY)
    {
        String cellTextValue = this.jLabelGrid[aY][aX].getText().trim();
        return Integer.parseInt(cellTextValue);
    }
    
    /**
     * Sets the amount of remaining user assists to display in the assist box on the gameboard
     * from the back end. Called after the gameboard load function.
     * @param aAssistsRemaining Integer, The number of assists remaining to set in the assist box.
     */
    public void setRemainingAssists(int aAssistsRemaining)
    {   
        // Set value in assist box.
        this.assistsRemaining.setText(Integer.toString(aAssistsRemaining));
        
        // Refresh/repaint/revalidate the gameboard.
        this.parentFrame.validate();
        this.parentFrame.repaint();
    }
    
    /**
     * Gets the amount of remaining user assists that is displayed in the assist box on the gameboard.
     * Called after the gameboard load function.
     * @return Integer, The number of assists remaining.
     */
    public int getRemainingAssists()
    {   
        // Get and return assist value.
        String remainingAssists = this.assistsRemaining.getText();
        return Integer.parseInt(remainingAssists);
    }
    
    /**
     * Sets the remaining time left to be displayed in the time box on the gameboard.
     * @param aTimerValue String, The time remaining in the format of mm:ss. 
     */
    public void setTimer(String aTimerValue)
    {
        this.timer.setText(aTimerValue);
    }
    
    /**
     * Stops the existing countdown timer.
     */
    public void stopTimer()
    {
    	this.countdown.stopTimer();
    }
    
}
