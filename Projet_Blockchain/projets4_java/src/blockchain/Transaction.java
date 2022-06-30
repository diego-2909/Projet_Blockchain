package blockchain;

import user.User;

public class Transaction {
	
	String receveur;
	String envoyeur;
	String message;
	int montant;
	
	public Transaction(User envoyeur, User receveur, int montant) {
		super();
		this.receveur = receveur.getPrenom();
		this.envoyeur = envoyeur.getPrenom();
		this.montant= montant;
		this.message = "Source: " + this.envoyeur + " -> Destination: " + this.receveur + ", montant = " + montant;	
	}
	
	public String getMessage() {
		return message;
	}
	
	
}