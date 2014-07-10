import java.util.Timer;
import java.util.TimerTask;

/**
 * This Class is used to create a countdown timer. It also displays the time
 * remaining on the gameboard GUI.
 */
public class CountdownTimer {
	
    /**
     * Used to construct and set initial values of the object.
     * @param level GameDifficulty, The level of difficulty of the game selected
	 * @param aGameBoardGUI GameBoardGUI, A reference to the game board from where this timer is called.
     * @param aParentFrame GUIInterface, A reference to the parent component to add itself to.
     */
	public CountdownTimer(GameDifficulty level, GameBoardGUI aGameBoardGUI, GUIInterface aParentJFrame){
		this.gUIInterfaceJFrame = aParentJFrame;
		this.aGameBoardGUI = aGameBoardGUI;
		timer = new Timer();
		
		// Adjusts the amount of time based on the difficulty selected
		if(level == GameDifficulty.BEGINNER){
			length = 10 * 60;
		}else if(level == GameDifficulty.INTERMEDIATE){
			length = 8 * 60;
		}else{
			length = 5 * 60;
		}
		
		timer.schedule(new TimerTask(){
			@Override
			public void run(){
				countdown();
			}
		}, 0, 1000);
	}
	
	/**
	 * Decreases the time remaining by 1 second and displays it on the gameboard GUI.
	 */
	private void countdown(){
		// Updates and displays the time remaining.
		aGameBoardGUI.setTimer(getMinutes()+":"+getSeconds());
		if(length > 0){
			length--;
		}else{
			// If time is up before completion, launch FAILURE screen.
			gUIInterfaceJFrame.remove(aGameBoardGUI);
			aGameBoardGUI = null;
			ResultsGUI newResultsGUI = new ResultsGUI(gUIInterfaceJFrame, ResultType.FAILURE);
			newResultsGUI.launchResultsGUI();
			timer.cancel();
		}
	}
	
	/**
	 * Calculates and returns the seconds remaining to be displayed.
	 * @return seconds String, The seconds to be displayed.
	 */
	public String getSeconds(){
		String seconds = Integer.toString(length % 60);

		// To make it always display the seconds in 2 digits.
		if(seconds.length() == 1){
			seconds = "0"+seconds;
		}
		return seconds;
	}
	
	/**
	 * Calculates and returns the minutes remaining to be displayed.
	 * @return minutes String, The minutes to be displayed.
	 */
	public String getMinutes(){
		return Integer.toString(length / 60);
	}
	
	/**
	 * Stops the timer.
	 */
	public void stopTimer(){
		timer.cancel();
	}
	
	private int length;
	private Timer timer;
	private GameBoardGUI aGameBoardGUI;
	private GUIInterface gUIInterfaceJFrame;
	
}
