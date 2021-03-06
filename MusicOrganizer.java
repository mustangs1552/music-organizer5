import java.util.ArrayList;
import java.util.Random;

/**
 * A class to hold details of audio tracks.
 * Individual tracks may be played.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class MusicOrganizer
{
    // An ArrayList for storing music tracks.
    private ArrayList<Track> tracks;
    // A player for the music tracks.
    private MusicPlayer player;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;
    private Random rand = new Random();
    private ArrayList<Track> shuffList = new ArrayList<Track>();

    /**
     * Create a MusicOrganizer
     */
    public MusicOrganizer()
    {
        tracks = new ArrayList<Track>();
        player = new MusicPlayer();
        reader = new TrackReader();
        readLibrary("audio");
        System.out.println("Music library loaded. " + getNumberOfTracks() + " tracks.");
        System.out.println();
    }
    
    /**
     * Add a track file to the collection.
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename)
    {
        tracks.add(new Track(filename));
    }
    
    /**
     * Add a track to the collection.
     * @param track The track to be added.
     */
    public void addTrack(Track track)
    {
        tracks.add(track);
    }
    
    /**
     * Play a track in the collection.
     * @param index The index of the track to be played.
     */
    public void playTrack(int index)
    {
        if(indexValid(index)) {
            Track track = tracks.get(index);
            player.startPlaying(track.getFilename());
            System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
        }
    }
    
    /**
     * Return the number of tracks in the collection.
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks()
    {
        return tracks.size();
    }
    
    /**
     * List a track from the collection.
     * @param index The index of the track to be listed.
     */
    public void listTrack(int index)
    {
        if(validIndex(index))
        {
			System.out.print("Track " + index + ": ");
	        Track track = tracks.get(index);
	        System.out.println(track.getDetails());
		}
    }
    
    /**
     * Show a list of all the tracks in the collection.
     */
    public void listAllTracks()
    {
        System.out.println("Track listing: ");

		int i = 0;
        for(i; i < tracks.size(); i++)
		{
            System.out.println(i + " is " + track.getDetails());
        }
        /*do
        {
			if(tracks.size() > 0) System.out.println(i + " is " + tracks.get(i).getDetails());
			i++;
        } while(i < tracks.size());*/
        System.out.println();
    }
    
    /**
     * List all tracks by the given artist.
     * @param artist The artist's name.
     */
    public void listByArtist(String artist)
    {
        for(Track track : tracks) {
            if(track.getArtist().contains(artist)) {
                System.out.println(track.getDetails());
            }
        }
    }
    
    /**
     * Remove a track from the collection.
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index)
    {
        if(validIndex(index)) {
            tracks.remove(index);
        }
    }
    
    /**
     * Play the first track in the collection, if there is one.
     */
    public void playFirst()
    {
        if(tracks.size() > 0) {
            player.startPlaying(tracks.get(0).getFilename());
        }
    }
    
    /**
     * Stop the player.
     */
    public void stopPlaying()
    {
        player.stop();
    }

    /**
     * Determine whether the given index is valid for the collection.
     * Print an error message if it is not.
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index)
    {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;
        
        if(index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        }
        else if(index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }
    
    private void readLibrary(String folderName)
    {
        ArrayList<Track> tempTracks = reader.readTracks(folderName, ".mp3");

        // Put all thetracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }
    
    // 4.43 && 4.45
    private int FindRandomNum()
    {
        boolean isDebug = false;
        
         // Get random number that is within the length of the track list
         int randNum = rand.nextInt(tracks.size());
         
         if(isDebug) System.out.println("Random number is " + randNum + ".");
         return randNum;
    }
    
    // 4.43
    public void PlayRandomTrack()
    {
        boolean isDebug = false;
        
        if(tracks.size() == 1) playFirst();
        else
        {
            int trackI = FindRandomNum();
            playTrack(trackI);
            
            if(isDebug) System.out.println("Playing " + tracks.get(trackI).getDetails() + " at index " + trackI + ".");
        }
    }

    // 4.45
    public void CreateShuffledList()
    {
        boolean isDebug = true;
        
        // Find the indexes for the list
        if(isDebug) System.out.println("BEGINNING OF CHECKS =============================");
        // Fill list
        int[] usedI = new int[tracks.size()];
        for(int i = 0; i < usedI.length; i++) usedI[i] = FindRandomNum();
        // Shows list
        if(isDebug)
        {
            System.out.println("FILLLING LIST");
            for(int i = 0; i < usedI.length; i++) System.out.println("usedI[" + i + "] = " + usedI[i] + ".");
        }
        
        // Check list for doubles
        for(int i = 0; i < tracks.size(); i++)
        {
            boolean foundInstance = false;
            
            // Go through each position
            for(int ii = 0; ii < usedI.length; ii++)
            {
                if(i == usedI[ii])
                {
                    if(!foundInstance) foundInstance = true;
                    else
                    {
                        usedI[ii]++;
                        if(usedI[ii] >= usedI.length) usedI[ii] = 0;
                        
                        // Check if same as another after change
                        boolean foundMatch = true;
                        while(foundMatch)
                        {
                            if(isDebug) System.out.println("CHECKING LIST");
                            
                            for(int iii = 0; iii < usedI.length; iii++)
                            {
                                foundMatch = false;
                                if(ii != iii && usedI[ii] == usedI[iii])
                                {
                                    usedI[ii]++;
                                    if(usedI[ii] >= usedI.length) usedI[ii] = 0;
                                    foundMatch = true;
                                }
                            }
                            
                            // Shows list
                            if(isDebug) for(int d = 0; d < usedI.length; d++) System.out.println("usedI[" + d + "] = " + usedI[d] + ".");
                        }
                    }
                }
            }
        }
        if(isDebug) System.out.println("NO MORE CHECKS ==================================");
        
        // Assign each song to their proper index
        if(isDebug) System.out.println("ADDING SONGS ====================================");
        for(int i = 0; i < usedI.length; i++)
        {
            shuffList.add(tracks.get(usedI[i]));
            if(isDebug) System.out.println("Added " + tracks.get(usedI[i]).getDetails() + ".");
        }
        if(isDebug) System.out.println("SONGS ADDED =====================================");
    }

	// 4.14
    public boolean validIndex(int iToCheck)
	{
		if(iToCheck < tracks.size() && iToCheck >= 0) return true;
		else return false;
	}
	
	// 4.30
	public void multiplesOfFive()
	{
		int i = 10;
		while(i <= 95)
		{
			if(i % 5 == 0 && i % 10 != 0) System.out.println(i);
			else if(i == 55) System.out.println(i);
			
			i++;
		}
	}
	
	// 4.31
	public void SumToTen()
	{
		int i = 1;
		int sum = 0;
		while(i <= 10)
		{
			sum += i;
			i++;
		}
		System.out.println(sum);
	}
	
	// 4.32
	public void Sum(int a, int b)
	{
		int i = a;
		int sum = 0;
		while(i <= b)
		{
			sum += i;
			i++;
		}
		System.out.println(sum);
	}
	
	// 4.33
	int lastI = 0;
	public boolean isPrime(int num)
	{
		int i = 2;
		while (i < num)
		{
			if(num % i == 0) return false;
			i++;
		}
		
		return true;
	}
}
