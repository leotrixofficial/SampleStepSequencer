// *************************************
// * Written by Ethan 'Leotrix' Wright *
// * Created: 25 June 2017			   *
// * Last updated: 25 July 2017		   *
// *************************************
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;
import javax.swing.*;

public class Sequencer extends SoundMachine {
	// button steps
	private int noOfSteps = 16;
	ArrayList<StepButton> buttonSteps = new ArrayList<StepButton>(noOfSteps);
	// TODO add step indicators
	// TODO sample decay
	// TODO sample pitch
	// TODO drag n drop samples
	// TODO multiple step lanes
	static final int MIN_BPM = 20, MAX_BPM = 240;
	JSlider tempoSlider = new JSlider(MIN_BPM, MAX_BPM, super.BPM);
	JLabel bpmLabel = new JLabel();
	
	private class StepButton {
		boolean selected = false;
		JButton button = new JButton();
		public StepButton() {
			button.setBackground(Color.gray);
			button.setOpaque(true);
			button.setBorderPainted(false);
		}
	}

	public Sequencer() {
		// initialise clip
		Clip clip = null;
		try {
			File sample = new File("hi.wav");
			// open sound source
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(sample);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}

		// instantiate the step buttons
		for (int addsteps_i = 0; addsteps_i < noOfSteps; addsteps_i++) {
			StepButton tempButton = new StepButton();
			int tempX = 50 + (30 * addsteps_i + 1);
			tempButton.button.setBounds(tempX, 50, 25, 25);
			buttonSteps.add(tempButton);
		}

		// for each step button
		for (StepButton eachStep : buttonSteps) {
			// action listener for button click
			eachStep.button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent actionE) {
					if (eachStep.selected) {
						eachStep.selected = false;
						eachStep.button.setBackground(Color.gray);
					} else {
						eachStep.selected = true;
						eachStep.button.setBackground(Color.red);
					}
				}
			});
			// add button to jframe
			super.frame.add(eachStep.button);
		}

		// set bounds
		tempoSlider.setBounds(80, 50, 300, 100);
		bpmLabel.setBounds(50, 75, 50, 50);
		// add to frame
		super.frame.add(tempoSlider);
		super.frame.add(bpmLabel);
		// make frame visible
		super.frame.setVisible(true);

		// sequence loop
		while (true) {
			for (int stepIndex = 0; stepIndex < noOfSteps; stepIndex++) {
				// TODO changing tempo in a separate thread
				super.BPM = tempoSlider.getValue();
				bpmLabel.setText(Integer.toString(super.BPM));
				System.out.println(stepIndex);
				try {
					if (buttonSteps.get(stepIndex).selected && clip != null) {
						// start sample clip from beginning
						clip.setFramePosition(0);
						clip.start();
					}
					// wait for next beat
					TimeUnit.MILLISECONDS.sleep((60000 / super.BPM) / 4);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}