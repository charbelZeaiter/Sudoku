import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Class is used as a key listener implementation to react/execute code when a 
 * user presses a key when a box/cell is selected in the game board GUI.
 */
public class MyGBGridKeyAdapter extends KeyAdapter {
    
    private JLabel currentJLabel;
    private JButton assistButton;
    private MouseListener newAssistMouseListener;
    private GUIInterface gUIInterfaceJFrame;
    private GameBoardGUI gameBoardJPanel;
    
    /**
     * Allows this object to be constructed with the given configuration values.
     * @param aSelectedJLabel JLabel, A reference to the box/cell in which its value is to be modified.
     * @param aAssistButton JButton, A reference to the assist button.
     * @param aNewAssistMouseListener MouseListener, A reference to the assist button mouse listener.
     * @param aParentJFrame GUIInterface, A reference to the parent overall framewindow component.
     * @param aGameBoardJPanel GameBoardGUI, A reference to the game board from where this listener was called.
     */
    public MyGBGridKeyAdapter(JLabel aSelectedJLabel, JButton aAssistButton, MouseListener aNewAssistMouseListener, GUIInterface aParentJFrame, GameBoardGUI aGameBoardJPanel)
    {
        this.currentJLabel = aSelectedJLabel;
        this.assistButton = aAssistButton;
        this.newAssistMouseListener = aNewAssistMouseListener;
        this.gUIInterfaceJFrame = aParentJFrame;
        this.gameBoardJPanel = aGameBoardJPanel;
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
        // Get the pressed key.
        char keyPressed = e.getKeyChar();
        
        // Validate it against the only numbers that its allowed to be.
        if(keyPressed >= '1' && keyPressed <= '9')
        {   
            // Change the look of the label denoting the action. 
            this.currentJLabel.setBackground(Color.WHITE);
            
            // Remove the key listener & assist button listener from box/cell.
            this.currentJLabel.removeKeyListener(this);
            this.assistButton.removeMouseListener(newAssistMouseListener);
            
            // Getting key pressed info from GUI grid.
            String[] part = this.currentJLabel.getName().split(",");
            int xPos = Integer.parseInt(part[0]);
            int yPos = Integer.parseInt(part[1]);
            int valueEntered = keyPressed - 48;
            
            
            // Code here to call back-end processing
            // interface function. On success execute
            // the below command.
            if(Main.g.get(xPos, yPos) == 0){
            	Game.emptyBoxes--;
            }
            
            Main.g.place(xPos, yPos, valueEntered);
            
            // Changes JLabel value to entered number.
            this.currentJLabel.setText(" "+keyPressed+" ");
            
            //Checks if board is completed and solved
            if(Game.emptyBoxes == 0){
            	if(Main.g.isSolved()){
            		gameBoardJPanel.stopTimer();
                    this.gUIInterfaceJFrame.remove(gameBoardJPanel);
                    gameBoardJPanel = null;
                	ResultsGUI newResultsGUI = new ResultsGUI(gUIInterfaceJFrame, ResultType.SUCCESS);
                	newResultsGUI.launchResultsGUI();   
            	}
            }
        }
        

    }
    
}
