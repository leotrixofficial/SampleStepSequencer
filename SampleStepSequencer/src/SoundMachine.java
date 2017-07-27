// *************************************
// * Written by Ethan 'Leotrix' Wright *
// * Created: 25 June 2017			   *
// * Last updated: 27 June 2017		   *
// *************************************
import javax.swing.*;

public abstract class SoundMachine {
	protected JFrame frame = new JFrame();
	protected int BPM = 120;
	public SoundMachine() {
		// frame settings
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null); // no layout manager
		frame.setSize(600,400);
	}
}