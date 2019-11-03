import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

public class Sound_AShahidullah extends JFrame 
{
	
   public Sound_AShahidullah(String sound) 
   {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setTitle("Test Sound Clip");
      this.setSize(300, 200);
      this.setVisible(false);

      try {
         URL url = this.getClass().getClassLoader().getResource(sound);
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         Clip clip = AudioSystem.getClip();
         clip.open(audioIn);
         if(sound.equals("music.wav"))
        		 clip.loop(Integer.MAX_VALUE);
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
   
}