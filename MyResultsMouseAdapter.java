import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class is used as a mouse listener implementation to react/execute code when a 
 * user clicks on the 'back' button in the results page GUI.
 */
public class MyResultsMouseAdapter extends MouseAdapter 
{
    
    private GUIInterface gUIInterfaceJFrame;
    private ResultsGUI resultsGUI;
    
    /**
     * Allows this object to be constructed with the given configuration values.
     * @param aParentJFrame GUIInterface, A reference to the parent overall framewindow component.
     * @param aGameBoardJPanel GameBoardGUI, A reference to the game board from where this listener was called.
     */
    public MyResultsMouseAdapter(GUIInterface aParentJFrame, ResultsGUI aResultsJPanel)
    {
        this.gUIInterfaceJFrame = aParentJFrame;
        this.resultsGUI = aResultsJPanel;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) 
    {     
        // Remove results GUI component from frame/window.
        this.gUIInterfaceJFrame.remove(this.resultsGUI);
        this.resultsGUI = null;
        
        // Create a new main menu GUI component and add to frame/window.
        MainMenuGUI newMainMenuGUI = new MainMenuGUI(this.gUIInterfaceJFrame);
        newMainMenuGUI.launchMainMenuGUI();
    }
    
}
