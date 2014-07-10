
/**
 * This is the class where the main function is held. It creates a 
 * new GUIInterface and launches the Main Menu
 */
public class Main {
    
    public static void main(String[] args) 
    {  
                GUIInterface newGUIInterface = new GUIInterface();
                
                MainMenuGUI newMainMenuGUI = new MainMenuGUI(newGUIInterface);
                newMainMenuGUI.launchMainMenuGUI();                
    }
    
    public static Game g;
}
