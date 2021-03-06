package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;

public class AudioPlayer extends Thread {
	private static final int EXTERNAL_BUFFER_SIZE = 1024;
	File soundFile;
	AudioInputStream audioInputStream;
	private volatile boolean finished;
	private volatile boolean paused;

	private boolean runOnce;

	/**
	 * creates a new Audio Player Thread with specified soundFileName. If
	 * runonce flag is false it will continue looping forever until you call
	 * stopPlayer() method on audioplayer
	 *
	 *
	 * @param soundFileName
	 * @param runOnce
	 */
	public AudioPlayer(String soundFileName, boolean runOnce) {
		this.runOnce = runOnce;
		this.paused = false;

		// sound we are playing
		soundFile = new File("Assets/sounds/" + soundFileName);
		audioInputStream = null;
	}

	public boolean getRunOnce(){
		return this.runOnce;
	}

	public void run() {
		finished = false;

		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e) {

			e.printStackTrace();
			System.exit(1);
		}

		AudioFormat audioFormat = audioInputStream.getFormat();

		SourceDataLine line = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);

		try {
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		line.start();
		while (!finished) {

			int nBytesRead = 0;
			byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
			while (nBytesRead != -1 && !finished) {

				try {

					nBytesRead = audioInputStream
						.read(abData, 0, abData.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (nBytesRead >= 0) {
					int nBytesWritten = line.write(abData, 0, nBytesRead);
				}

				if (paused) {
					try {
						synchronized (this) {
							wait();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			line.drain();
			try {
				audioInputStream.close();
				audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			}
			if (runOnce) {
				break;
			}
		}
		line.close();
		finished = false;
	}

	/**
	 * Terminates audio playing
	 */
	public void stopPlayer() {
		finished = true;
		// stop changes variable in player class that causes the whole Audio
		// PLayer to terminate.
	}

	public void togglePaused() {
		paused = !paused;
		if(!paused){
			synchronized(this){
				notifyAll();
			}
		}
	}

	public void changeVolume(int volume) {
		Clip clip;
		try {
			clip = AudioSystem.getClip();

			clip.open(audioInputStream);

			FloatControl gainControl = (FloatControl) clip
				.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-80.0f);

		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * for testing
	 *
	 * @throws InterruptedException
	 */
	public static void main(String args[]) throws InterruptedException {
		AudioPlayer a = new AudioPlayer("timer1.wav", false);
		a.start();
		Thread.sleep(1000);
		a.togglePaused();

		Thread.sleep(3000);

		a.togglePaused();
		a = new AudioPlayer("laugh.wav", true);
		a.start();
		a.stopPlayer();

	}

}
