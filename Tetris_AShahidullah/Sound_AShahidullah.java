import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

/**
 * Creates the sounds used in Tetris.
 * @author Archie Shahidullah
 * @version 1.1
 * @since 1.0
 */

public class Sound_AShahidullah extends JFrame 
{
	
	/**
	 * The audio clip to be played.
	 */
   public static Clip clip;
   
//   public static boolean loop;
   
   /**
    * Identifies whether the audio clip is to be played.
    */
   private static boolean play = true;

   /**
    * Creates a JFrame where the selected audio clip is played in; looping the clip if it is the background music.
    * @param sound the name and extension (must be .WAV) of the audio clip
    */
   public Sound_AShahidullah(String sound) 
   {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("Test Sound Clip");
      this.setSize(300, 200);
      this.setVisible(false);

      try {
         URL url = this.getClass().getClassLoader().getResource(sound);
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         clip = AudioSystem.getClip();
         clip.open(audioIn);
         if(sound.equals("Tetris.wav"))
        		 clip.loop(Clip.LOOP_CONTINUOUSLY);
         else
        	 clip.start();
      } catch(UnsupportedAudioFileException ex) {
         ex.printStackTrace();
      } catch(IOException ex) {
         ex.printStackTrace();
      } catch(LineUnavailableException ex) {
         ex.printStackTrace();
      }
   }
   
   /**
    * Toggles whether the clip will play.
    */
   public void toggle()
   {
	   play ^= true;
	   
	   if(!play)
		   clip.stop();
	   else
		   clip.start();
   }
   
   /**
    * Stops the clip from playing.
    */
   public void stop()
   {
	   clip.stop();
   }
   
   /**
    * Starts the clip.
    */
   public void start()
   {
	   clip.start();
   }
   
//   public void checkSound()
//   {
//	   if(TetrisGame_AShahidullah.time >= 83000)
//		   TetrisGame_AShahidullah.background = new Sound_AShahidullah("Tetris.wav");
//   }
   
}
