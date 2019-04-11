package course.oop.players;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExistingPlayers {

	//Singleton Behavior
	private static ExistingPlayers instance = new ExistingPlayers();
	public static ExistingPlayers getInstance() {
		return instance;
	}
	
	//member attributes
	//hash from username (what equality is based on) -> Player object
	private Map<String,Player> players;
	private ExistingPlayers() {
		loadMap();
	}
	
	public void savePlayers() {
		try {
			FileOutputStream fos = new FileOutputStream("players.ser");
		    ObjectOutputStream oos = new ObjectOutputStream(fos);
		    oos.writeObject(players);
	        oos.close();
	        fos.close();
	        System.out.println("Serialized Player data is saved in players.ser");
		}
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadMap() {
		ObjectInputStream ois;
		FileInputStream fis;
		try {
			fis = new FileInputStream("players.ser");
	        ois = new ObjectInputStream(fis);
	        Object deserialized = ois.readObject();
			ois.close();
	        fis.close();
	        if (deserialized instanceof HashMap<?, ?>) {
		        players = (HashMap<String, Player>)deserialized;
	        }
	        else throw new InvalidObjectException("Object is not instance of HashMap");
		}
		catch (FileNotFoundException fnfe) {
			players = new HashMap<String,Player>();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	    catch (ClassNotFoundException cnfe) {
	    	cnfe.printStackTrace();
	    }
	}
	
	public void add(Player player) {
		players.put(player.getUsername(),player);
	}
	
	@SuppressWarnings("rawtypes")
	public ObservableList<Player> getRepresentations() {
		
		ObservableList<Player> oL = FXCollections.observableArrayList();

		Set set = players.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			oL.add(   ((Player)mentry.getValue())   );
		}
		
		return oL;
	}
}
