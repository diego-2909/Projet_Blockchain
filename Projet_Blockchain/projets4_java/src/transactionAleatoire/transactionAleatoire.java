package transactionAleatoire;


import java.util.List;
import blockchain.Blockchain;
import blockchain.Transaction;
import user.User;
import utils.Utils;

public class transactionAleatoire extends Thread {
	Blockchain blockchain;
	List<User> listUser;
	
	public transactionAleatoire(Blockchain blockchain, List<User> listUser) {
		super();
		this.blockchain = blockchain;
		this.listUser = listUser;
	}
	
	// fait des transactions aléatoires entre deux utilisateurs aléatoires d'un montant entre 0 et 1000000
	public void run(){
		for (int i=0 ; i<800 ; i++){
			User emetteur;
			User receveur;
			
			do {
					emetteur = Aleatoire.userHasard(listUser);
					receveur = Aleatoire.userHasard(listUser);
			} while(emetteur.equals(receveur)); // Pour ne pas avoir le même emetteur et receveur
			
			Transaction t = new Transaction(emetteur, receveur, Utils.genererInt(0,100000));
			blockchain.ajouterTransaction(t);
			
			// temps entre la création de 2 transactions aléatoires 
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}