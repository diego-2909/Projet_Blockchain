package utils;

import java.util.ArrayList;
import java.util.List;
import blockchain.Transaction;

public class MerkleTreeRoot {
		
	public static String arbreMerkle (List<Transaction> listeTransactions) {
		ArrayList<String> listeRoot = new ArrayList<String>();
		int nbRoot = listeTransactions.size();
		
		// 1er parcours pour transformer les transaction en hash
		for (int i=0 ; i<nbRoot ; i++) {
			listeRoot.add(HashUtil.applySha256( listeTransactions.get(i).getMessage() ));
		}
		
		// boucle qui calcule le root du block
		while(nbRoot != 1) {
			for (int i=0 ; i<(nbRoot-nbRoot%2) ; i+=2) {
				listeRoot.set(i/2, HashUtil.applySha256( listeRoot.get(i)+listeRoot.get(i+1) ));
			}
			if(nbRoot%2 == 1) {
				listeRoot.set(nbRoot/2, listeRoot.get(nbRoot-1));		
				nbRoot = nbRoot/2+1;
			}
			else
				nbRoot /=2;
		}
		// resultat
		return listeRoot.get(0);
	}
	
}

