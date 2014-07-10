import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Class is used as a mouse listener implementation to react/execute code when a 
 * user clicks on a button in the main menu GUI.
 */
public class MyMainMenuMouseAdapter extends MouseAdapter {
    
    private GUIInterface gUIInterfaceJFrame;
    private MainMenuGUI mainMenuJPanel;
    
    /**
     * Allows this object to be constructed with the given configuration values.
     * @param aParentJFrame GUIInterface, A reference to the parent overall framewindow component.
     * @param aGameBoardJPanel GameBoardGUI, A reference to the game board from where this listener was called.
     */
    public MyMainMenuMouseAdapter(GUIInterface aParentJFrame, MainMenuGUI aMainMenuJPanel)
    {
        this.gUIInterfaceJFrame = aParentJFrame;
        this.mainMenuJPanel = aMainMenuJPanel;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) 
    {   
        // Gey button name.
        String buttonName = e.getComponent().getName();
        
        // Declare level variable.
        GameDifficulty level;
        
        // Remove main menu GUI component from window/frame.
        this.gUIInterfaceJFrame.remove(mainMenuJPanel);
        mainMenuJPanel = null;
        
        // Create a new game board GUI.
        GameBoardGUI newGameBoardGUI = new GameBoardGUI(gUIInterfaceJFrame);
        
        // Choose level depending on the button clicked.
        if(buttonName.equals("beginnerButton"))
        {
            level = GameDifficulty.BEGINNER;
        }
        else if(buttonName.equals("intermediateButton"))
        {
            level = GameDifficulty.INTERMEDIATE;
        }
        else
        {
            level = GameDifficulty.ADVANCED;
        }
        
        // Creates a new game based on the level selected and then 
        // loads the new board on to the gameboard GUI
        Main.g = new Game(level);
        newGameBoardGUI.loadGameBoardGUI(level);
        int row, col;
        int val;
        for(row = 0; row < 9; row++){
        	for(col = 0; col < 9; col++){
        		val = Main.g.get(row, col);
        		if(val != 0){
        			newGameBoardGUI.setSolidGridCellValue(row, col, val);
        		}
        	}
        }
        
        // Launch game board display.
        newGameBoardGUI.launchGameBoardGUI(); 
    }
    
    @Override
    public void mouseEntered(MouseEvent e) 
    {   
        
        // Get button name.
        String buttonName = e.getComponent().getName();
        
        // Change background of main menu component depending on the button entered.
        if(buttonName.equals("beginnerButton"))
        {
            Icon newBgImageIcon = new ImageIcon("src/beginnerBg.png");
            this.mainMenuJPanel.setIcon(newBgImageIcon);
        }
        else if(buttonName.equals("intermediateButton"))
        {
            Icon newBgImageIcon = new ImageIcon("src/intermediateBg.png");
            this.mainMenuJPanel.setIcon(newBgImageIcon);
        }
        else
        {
            Icon newBgImageIcon = new ImageIcon("src/advancedBg.png");
            this.mainMenuJPanel.setIcon(newBgImageIcon);
        }
        
        // Refresh/repaint and revalidate frame/window.
        this.gUIInterfaceJFrame.validate();
        this.gUIInterfaceJFrame.repaint();
    }
    
}
