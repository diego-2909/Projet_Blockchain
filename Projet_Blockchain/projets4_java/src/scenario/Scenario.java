package scenario;

import java.io.IOException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import blockchain.Blockchain;
import interfaceGraphique.UtilsTransac;
import transactionAleatoire.Aleatoire;
import transactionAleatoire.transactionAleatoire;
import user.Coinbase;
import user.Creator;
import user.Mineur;
import user.User;
import utils.JsonUtils;

public class Scenario {

	@SuppressWarnings("deprecation")
	public static void scenario( int difficulty, 
								int nbBloc, 
								int nbUtilisateur, 
								Blockchain blockchain, 
								List<User> listUser, 
								Coinbase coinbase, 
								Creator creator,
								JTextArea jTextArea_minage,
								JScrollPane jScrollPane_minage) {
		
		// on attend 3s que l'interface graphique charge
     	try {
     		Thread.sleep(3000);
     	} catch (InterruptedException e) {
     		e.printStackTrace();
     	}

		// ~~ BCB.1: création genesis par creator
		creator.creationGenesis(blockchain);
		
		// ~~ BCB.2: Phase "Helicopter money"
		coinbase.HelicopterMoney(blockchain, listUser, 50);

		// ~~ BCB.3: creation Thread pour les transaction aleatoire
		transactionAleatoire processusIndependantTransactionAleatoire = new transactionAleatoire(blockchain, listUser);
		processusIndependantTransactionAleatoire.start();
				
		// ~~ BCB.4: on choisi un mineur au hasard 
		Mineur mineur = new Mineur(Aleatoire.userHasard(listUser).getPrenom(), blockchain, jTextArea_minage);
				
		// minage
		while (blockchain.getNbBloc() < nbBloc) {
			mineur.miner();
			jTextArea_minage.append("\n");
			UtilsTransac.scrollAuto(jScrollPane_minage);
		}
				
		processusIndependantTransactionAleatoire.stop();
		
		try {
			JsonUtils.blockchainToJson(blockchain);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
