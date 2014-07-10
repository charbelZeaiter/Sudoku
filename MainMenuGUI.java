import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This Class is used to create and display the actual main menu GUI
 * part of the game .
 */
public class MainMenuGUI extends JLabel {
    
    private GUIInterface parentFrame;
    
    /**
     * Used to construct and set initial values of the object.
     * @param aParentFrame GUIInterface, A reference to the parent component to add itself to.
     */
    public MainMenuGUI(GUIInterface aParentFrame)
    {
        this.parentFrame = aParentFrame;
        
        // Create a sub panel for the navigation buttons.
        JPanel buttonGridPanel  = new JPanel();
        buttonGridPanel.setLayout(new GridLayout(4, 1, 0, 50));
        buttonGridPanel.setOpaque(false);
        
        // Create main menu title/heading graphic.
        Icon newImageIcon = new ImageIcon("src/mainMenuTitle.png");
        JLabel screenTitle = new JLabel(newImageIcon);
        buttonGridPanel.add(screenTitle);
        
        // Create mouse adapter to be added to buttons.
        MouseListener newMyMouseAdapter = new MyMainMenuMouseAdapter(this.parentFrame, this);
        
        // Create beginner button and add to button panel.
        JButton beginnerButton = new JButton("Beginner");
        beginnerButton.setName("beginnerButton");
        beginnerButton.addMouseListener(newMyMouseAdapter);
        buttonGridPanel.add(beginnerButton);
        
        // Create intermediate button and add to button panel.
        JButton intermediateButton = new JButton("Intermediate");
        intermediateButton.setName("intermediateButton");
        intermediateButton.addMouseListener(newMyMouseAdapter);
        buttonGridPanel.add(intermediateButton);
        
        // Create advanced button and add to button panel.
        JButton advancedButton = new JButton("Advanced");
        advancedButton.setName("advancedButton");
        advancedButton.addMouseListener(newMyMouseAdapter);
        buttonGridPanel.add(advancedButton);
        
        // Set main menu overall layout.
        this.setLayout(new BorderLayout());
        
        // Set main menu default background.
        Icon newBgImageIcon = new ImageIcon("src/intermediateBg.png");
        this.setIcon(newBgImageIcon);
        
        // Add all created components to main menu panel.
        this.add(buttonGridPanel, BorderLayout.CENTER);
        this.add(Box.createRigidArea(new Dimension(100,80)), BorderLayout.NORTH);
        this.add(Box.createRigidArea(new Dimension(100,80)), BorderLayout.SOUTH);
        this.add(Box.createRigidArea(new Dimension(100,100)), BorderLayout.EAST);
        this.add(Box.createRigidArea(new Dimension(100,100)), BorderLayout.WEST);
    }
    
    /**
     * Used to launch (or display) the actual main menu GUI after it has been set up with 
     * the initial constructor. 
     */
    public void launchMainMenuGUI()
    {    
        // Add current main menu GUI to main GUI frame/window. 
        this.parentFrame.add(this);
        
        // Refresh/repaint/revalidate the gameboard.
        this.parentFrame.validate();
        this.parentFrame.repaint();
    }
    
}
