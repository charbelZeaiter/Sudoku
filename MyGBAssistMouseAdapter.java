import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

/**
 * Class is used as a mouse listener implementation to react/execute code when a 
 * user clicks on the assist button in the game board GUI.
 */
public class MyGBAssistMouseAdapter extends MouseAdapter {
    
    private GUIInterface gUIInterfaceJFrame;
    private GameBoardGUI gameBoardJPanel;
    private JLabel gridJLabel;
    
    /**
     * Allows this object to be constructed with the given configuration values.
     * @param aParentJFrame GUIInterface, A reference to the parent overall frame window component.
     * @param aGameBoardJPanel GameBoardGUI, A reference to the game board from where this listener was called.
     * @param aGridJLabel JLabel, A reference to the grid label/box that is currently needed assistance with.
     */
    public MyGBAssistMouseAdapter(GUIInterface aParentJFrame, GameBoardGUI aGameBoardJPanel, JLabel aGridJLabel)
    {
        this.gUIInterfaceJFrame = aParentJFrame;
        this.gameBoardJPanel = aGameBoardJPanel;
        this.gridJLabel = aGridJLabel;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) 
    {
    	
        // Only assist if JLabel/box/cell passed in is in fact selected with a green background.
    	if(this.gridJLabel.getBackground() == Color.GREEN){
	        if(Game.assistCount > 0){	    	
	            Game.assistCount--;
	
	        	// Take off highlight.
		        this.gridJLabel.setBackground(Color.WHITE);
		        
		        // Getting grid coordinates.
		        String[] part = this.gridJLabel.getName().split(",");
		        int xPos = Integer.parseInt(part[0]);
		        int yPos = Integer.parseInt(part[1]);
		
		        // If box has not been filled, decrease count for emptyBoxes
	            if(Main.g.get(xPos, yPos) == 0){
	            	Game.emptyBoxes--;
	            }	
		      
		        // Set Value of grid cell.
		        int val = Main.g.hint(xPos, yPos);
		        this.gameBoardJPanel.setSolidGridCellValue(xPos, yPos, val);
		        Main.g.place(xPos, yPos, val);
		        
	        
		        // update number of assists left, new code here
		        // Set value of Remaining tips/assists.
		        this.gameBoardJPanel.setRemainingAssists(Game.assistCount);
		        
	            //Checks if board is completed and solved
	            if(Game.emptyBoxes == 0){
	            	if(Main.g.isSolved()){
	            		gameBoardJPanel.stopTimer();
	            		this.gUIInterfaceJFrame.remove(gameBoardJPanel);
	                    gameBoardJPanel = null;
	                    
	                    // Launch SUCCESS page
	                	ResultsGUI newResultsGUI = new ResultsGUI(gUIInterfaceJFrame, ResultType.SUCCESS);
	                	newResultsGUI.launchResultsGUI();            		
	            	}
	            }
		        
	        }		        
        }
    }
}
