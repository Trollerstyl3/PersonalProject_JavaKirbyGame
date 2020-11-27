/* Name: Khoi Nguyen
 * Date: November 20, 2020
 * Class Description: This class SoundManager loads up the AudioPlayer for all of the sounds. This allows for the
 * program to have only one place to access all the sounds
 */

// Please don't remove the packages because I have many folders
package src.loader;

// Import the audio player to create objects with it
import src.util.AudioPlayer;

public class SoundManager
{ 
  //load audio objects and to files address put in
  public AudioPlayer swingPlayer = new AudioPlayer("src/res/swingPlayer.wav");
  public AudioPlayer swingEnemy = new AudioPlayer("src/res/swingEnemy.wav");
  public AudioPlayer damagedPlayer = new AudioPlayer("src/res/damagedPlayer.wav");
  public AudioPlayer damagedEnemy = new AudioPlayer("src/res/damagedEnemy.wav");
  public AudioPlayer backgroundMusic = new AudioPlayer("src/res/backgroundMusic.wav");
  
}
