package user;


import java.util.List;
import blockchain.Blockchain;
import blockchain.Transaction;

public class Coinbase extends User{

	public Coinbase() {
		super("Coinbase");
	}
	
	
	//phase helicopterMoney
	public void HelicopterMoney(Blockchain blockchain, List<User> listUser, int montant) {
		for (int i=1 ; i<listUser.size() ; i++)
			blockchain.ajouterTransaction( new Transaction(this, listUser.get(i), montant) );
	}
}
