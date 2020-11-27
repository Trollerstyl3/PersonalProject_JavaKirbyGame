/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This class opens up the wav files and play the audio clip when needed
 */

// Please don't remove the packages because I have many folders
package src.util;

//import all packages needed
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// Create the AudioPlayer class that will open the wav files and play audio when needed
public class AudioPlayer
{
  // Clip is where you save the wav file in and then play from when needed
  private Clip c;
  
  //constructor to create audioplayer based on file name
  public AudioPlayer(String fileName) {
    try
    {
      File file = new File(fileName);
      if (file.exists())
      {
        AudioInputStream sound = AudioSystem.getAudioInputStream(file);
        c = AudioSystem.getClip();
        c.open(sound);
      }
    } catch (Exception e)
    {
      
    }
    
  }
  
  //method play to play the audio
  public void play()
  {
    c.setFramePosition(0);
    c.start();
  }
  
  //method to set looping of audio to forever
  public void loop()
  {
    c.loop(Clip.LOOP_CONTINUOUSLY);
  }
  
  //method to stop the playing of the clip
  public void stop()
  {
    c.stop();
  }
}