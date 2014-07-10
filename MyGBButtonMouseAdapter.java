import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

/**
 * Class is used as a mouse listener implementation to react/execute code when a 
 * user clicks on the 'back' button in the game board GUI.
 */
public class MyGBButtonMouseAdapter extends MouseAdapter{
    
    private GUIInterface gUIInterfaceJFrame;
    private GameBoardGUI gameBoardJPanel;
    
    /**
     * All this object to be constructed with the given configuration values.
     * @param aParentJFrame GUIInterface, A reference to the parent overall frame window component.
     * @param aGameBoardJPanel GameBoardGUI, A reference to the game board from where this listener was called.
     */
    public MyGBButtonMouseAdapter(GUIInterface aParentJFrame, GameBoardGUI aGameBoardJPanel) 
    {
        this.gUIInterfaceJFrame = aParentJFrame;
        this.gameBoardJPanel = aGameBoardJPanel;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        
        // Gets the actual reference of the object where the event occurred.
        JButton buttonClicked = (JButton) e.getComponent();
        
        // If button name was 'back' then execute code.
        if(buttonClicked.getName().equals("Back"))
        {           
            // Stop the timer.
    		gameBoardJPanel.stopTimer();
            
    		// Removing current GUI and losing reference so it can be deleted by the garbage collector.
    		this.gUIInterfaceJFrame.remove(this.gameBoardJPanel);
            this.gameBoardJPanel = null;
            
            // Create new main menu GUI.
            MainMenuGUI newMainMenuGUI = new MainMenuGUI(this.gUIInterfaceJFrame);
            newMainMenuGUI.launchMainMenuGUI();
            
        }
        
    }
    
}
