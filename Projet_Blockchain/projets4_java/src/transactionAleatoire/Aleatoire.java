package transactionAleatoire;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import user.User;

public class Aleatoire {
	
	
	//renvoie une liste de prenom aleatoire
	public static List<User> listUserRandom(int nbUser) {
		
		List<User> listUser = new ArrayList<>();
		for (int i=0 ; i<nbUser ; i++ )
			listUser.add(new User(nomHasard()));
		return listUser;
	}
	
	
	//renvoie un utilisateur aléatoire parmis la liste des utilisateurs
	public static User userHasard(List<User> listUser) {
		return listUser.get( (int) (Math.random()*listUser.size()) );
	}
	
	
	// choisit un nom au hasard dans la liste de noms noms.txt
	public static String nomHasard() {
		String line = null;
		try {
			BufferedReader fichier = new BufferedReader(new FileReader("noms/noms.txt"));
		
			int abc = (int) (Math.random() * ( 11626 - 0 ));
			for(int i=0; i!=abc; i++) {
				line = fichier.readLine();
			}	
			fichier.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
}
