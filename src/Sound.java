import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/**
 * Created by Eddy on 2015-06-10.
 */
public class Sound // Holds one audio file
{
    private AudioClip song; // Sound player
    private String songPath; // Sound path
    Sound(String filename)
    {
        try
        {
            songPath = filename; // Get the Sound URL
            song = Applet.newAudioClip(new URL("file:" + songPath)); // Load the Sound
        }
        catch(Exception e){
            System.out.println(songPath + " is invalid");
        }
    }
    public void playSound()
    {
        System.out.println("playing " + songPath);
        song.loop(); // Play
    }
    public void stopSound()
    {
        song.stop(); // Stop
    }
    public void playSoundOnce()
    {
        song.play(); // Play only once
    }
}
//code for this class copied from Java Tutorial:
// http://www.dreamincode.net/forums/topic/14083-incredibly-easy-way-to-play-sounds/

