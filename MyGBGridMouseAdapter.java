import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Class is used as a grid box mouse listener implementation to react/execute code when a 
 * user clicks on a box/cell to select and deselect it in the game board GUI.
 */
public class MyGBGridMouseAdapter extends MouseAdapter{
    
    private GUIInterface gUIInterfaceJFrame;
    private GameBoardGUI gameBoardJPanel;
    private JLabel lastSelectedJLabel;
    private KeyListener lastKeyListener;
    private MouseListener lastAssistMouseListener;
    private JButton assistButton;
    
    /**
     * Allows this object to be constructed with the given configuration values.
     * @param aParentJFrame GUIInterface, A reference to the parent overall framewindow component.
     * @param aGameBoardJPanel GameBoardGUI, A reference to the game board from where this listener was called.
     * @param aAssistButton JButton, A reference to the assist button.
     */
    public MyGBGridMouseAdapter(GUIInterface aParentJFrame, GameBoardGUI aGameBoardJPanel, JButton aAssistButton)
    {
        this.gUIInterfaceJFrame = aParentJFrame;
        this.gameBoardJPanel = aGameBoardJPanel;
        this.lastSelectedJLabel = null;
        this.lastKeyListener = null;
        this.lastAssistMouseListener = null;
        this.assistButton = aAssistButton;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) 
    {   
        // Get the  triggered event box/cell.
        JLabel selectedJLabel = (JLabel) e.getComponent();
        
        // Create mouse and key listeners in case they are needed depending on the scenario.
        MouseListener newAssistMouseListener = new MyGBAssistMouseAdapter(this.gUIInterfaceJFrame, this.gameBoardJPanel, selectedJLabel);
        KeyListener newKeyListener = new MyGBGridKeyAdapter(selectedJLabel, this.assistButton, newAssistMouseListener, this.gUIInterfaceJFrame, this.gameBoardJPanel);
        
        // If last box/cell is not the same then effectively 'select' current box/cell.
        if(this.lastSelectedJLabel != selectedJLabel)
        {   
            // Add required key listener to cell and change its background.
            selectedJLabel.addKeyListener(newKeyListener);
            selectedJLabel.setBackground(Color.GREEN);
            selectedJLabel.requestFocus();
            
            // Add required mouse listner to assist button for selected cell/box.
            this.assistButton.addMouseListener(newAssistMouseListener); 
        }
        
        // If last selected box does exist.
        if(this.lastSelectedJLabel != null)
        {
            // If the box has been greyed out (assisted), leave it as it is, otherwise
        	// change it to white
        	if(this.lastSelectedJLabel.getBackground() == Color.LIGHT_GRAY){
        		this.lastSelectedJLabel.setBackground(Color.LIGHT_GRAY);
        	}else{
            	this.lastSelectedJLabel.setBackground(Color.WHITE);        		
        	}
        	
        	// Remove listeners associate with last box/cell.
            this.lastSelectedJLabel.removeKeyListener(this.lastKeyListener);
            this.assistButton.removeMouseListener(lastAssistMouseListener);
        }
        
        // If the same box/cell is again selected.
        if(this.lastSelectedJLabel == selectedJLabel)
        {   
            // de-select it.
        	this.lastSelectedJLabel = null;
            this.lastKeyListener = null;
            this.lastAssistMouseListener = null;
        }
        else
        {   
            // add a new set of listeners.
            this.lastKeyListener = newKeyListener;
            this.lastSelectedJLabel = selectedJLabel;
            this.lastAssistMouseListener = newAssistMouseListener;
        }
        
        // Refresh/repaint/revalidate the gameboard.
        this.gUIInterfaceJFrame.validate();
        this.gUIInterfaceJFrame.repaint();
        
    }
    
}
