package sound;

import java.util.ArrayList;

public class MixingDesk {
	private ArrayList<AudioPlayer> players = new ArrayList<AudioPlayer>();
	private Boolean muted = false;

	public MixingDesk(){

	}

	public void addAudioPlayer(String sound, Boolean playOnce){

		if(muted){
			//a.togglePaused();
		}else{
			AudioPlayer a = new AudioPlayer(sound, playOnce);
			players.add(a);
			a.start();
		}


	}

	public void toggleMute(){
		for(AudioPlayer a : players){
			a.togglePaused();
		}
		muted = !muted;
	}

	public static void main(String[] args) throws InterruptedException {

		MixingDesk desk = new MixingDesk();
		desk.addAudioPlayer("MenuMusic.wav", false);
		Thread.sleep(1000);

		desk.toggleMute();
		desk.addAudioPlayer("DyingDude.wav", true);
		desk.addAudioPlayer("DyingDude.wav", true);
		desk.addAudioPlayer("DyingDude.wav", true);

		Thread.sleep(1000);


		desk.toggleMute();

	}

}
