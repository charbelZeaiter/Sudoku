import java.awt.BorderLayout;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * This Class is used to create and display the actual results page GUI
 * part of the game .
 */
public class ResultsGUI extends JLabel{
    
    private GUIInterface parentFrame;
    private ResultType givenResult;
    
    /**
     * Used to construct and set initial values of the object.
     * @param aParentFrame GUIInterface, A reference to the parent component to add itself to.
     * @param aResult ResultType, The result enumeration that will allow the GUI to display 'winner' or 'loser'. 
     */
    public ResultsGUI(GUIInterface aParentFrame, ResultType aResult)
    {
        this.parentFrame = aParentFrame;
        this.givenResult = aResult;
        
        JLabel resultLabel;
        
        // Display certain background based on game success or failure.
        if(this.givenResult == ResultType.SUCCESS)
        {
            Icon newBgImageIcon = new ImageIcon("src/winnerBg.png");
            this.setIcon(newBgImageIcon);
        }
        else if(this.givenResult == ResultType.FAILURE)
        {
            Icon newBgImageIcon = new ImageIcon("src/loserBg.png");
            this.setIcon(newBgImageIcon);
        }
        
        // Creating 'main menu' button to return to main menu.
        JButton mainMenuButton = new JButton("Back to Main Menu");
        mainMenuButton.setName("mainMenuButton");
        MouseListener newResultsMouseListener = new MyResultsMouseAdapter(this.parentFrame, this);
        mainMenuButton.addMouseListener(newResultsMouseListener);
        
        // Set the layout of the results component.
        this.setLayout(new BorderLayout());
        
        // Add button to reuslts GUI component.
        this.add(mainMenuButton, BorderLayout.SOUTH);
    }
    
    /**
     * Used to launch (or display) the actual results page GUI after it has been set up with 
     * the initial constructor. 
     */
    public void launchResultsGUI()
    {   
        // Add current main menu GUI to main GUI frame/window. 
        this.parentFrame.add(this);
        
        // Refresh/repaint/revalidate the gameboard.
        this.parentFrame.validate();
        this.parentFrame.repaint();
    }
    
    
}
