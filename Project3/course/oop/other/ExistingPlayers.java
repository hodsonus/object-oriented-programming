package course.oop.other;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ExistingPlayers {

	private static ExistingPlayers instance = new ExistingPlayers();
	
	public static ExistingPlayers getInstance() {
		
		return instance;
	}
	
	
	
	
	private Map<String,Player> players;
	
	private ExistingPlayers() {
		players = new HashMap<String,Player>();
	}
	
	public void saveMap() {
		try {
			FileOutputStream fos = new FileOutputStream("players.ser");
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(players);
	        oos.close();
	        fos.close();
		}
		catch (FileNotFoundException fnfe) {
			
		}
		catch (IOException ioe) {
			
		}
	}

}
