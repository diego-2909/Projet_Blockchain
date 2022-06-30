package user;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import blockchain.Bloc;
import blockchain.Blockchain;
import blockchain.Transaction;
import utils.HashUtil;
import utils.Utils;

public class Mineur extends User{
	Blockchain blockhain;
	private JTextArea jTextArea_minage;
	
	public Mineur(String prenom, Blockchain blockhain, JTextArea jTextArea_minage) {
		super(prenom);
		this.blockhain = blockhain;
		this.jTextArea_minage = jTextArea_minage;
	}
	
	public List<Transaction> choixTransaction() {
		int nbTransaction = Utils.genererInt(1,10);
		List<Transaction> listeTransaction = new ArrayList<>();
		
		jTextArea_minage.append("Le mineur "+this.getPrenom()+" prend "+nbTransaction+" transaction dans la liste de transaction global\n");
		
		// on verifie qu'il y a assez de transaction en stock sinon on reduit nbTransaction
		if ((blockhain.getIndiceLastTransacUse()+nbTransaction)>(blockhain.getListeTransactionGlobal().size()-1)) {
			nbTransaction = (blockhain.getListeTransactionGlobal().size()-1) - blockhain.getIndiceLastTransacUse();
			jTextArea_minage.append("pas assez de transac il prend donc "+nbTransaction+" tansaction\n");
		}
		if (nbTransaction != 0) {
			blockhain.setIndiceLastTransacUse(blockhain.getIndiceLastTransacUse()+1);
			//on ajoute les transaction à mettre dans le bloc dans une nouvelle liste de transaction independante
			for (int i=0 ; i<nbTransaction ; i++) {
				listeTransaction.add(blockhain.getListeTransactionGlobal().get(blockhain.getIndiceLastTransacUse()+i));
			}
			// on affiche les transaction prit par le mieur
			for (int i=0 ; i<listeTransaction.size() ; i++)
				jTextArea_minage.append("t"+(blockhain.getIndiceLastTransacUse()+i)+"║"+listeTransaction.get(i).getMessage()+"\n");
			jTextArea_minage.append("\n");
			// on actuallise l'indice de la dernier transaction utilisé
			blockhain.setIndiceLastTransacUse(blockhain.getIndiceLastTransacUse()+nbTransaction-1);
		}
		else
			jTextArea_minage.append("plus de transaction dispo\n");
		return listeTransaction;
	}
	
	

	public void miner() {
		List<Transaction> listeTransaction = choixTransaction();
		
		if (listeTransaction.size() != 0) {		
			Bloc b = new Bloc(blockhain.getNbBloc(), blockhain.dernierBloc().getHash(), listeTransaction);
			
			do {
				b.setHash(HashUtil.calculHashBloc(b));
				b.setNonce(b.getNonce()+1);
			} while (!b.getHash().substring(0, blockhain.getDifficulty()).equals(Utils.zeros(blockhain.getDifficulty())));
			b.setNonce(b.getNonce()-1);
			
			jTextArea_minage.append("Le mineur "+this.getPrenom()+" créé le bloc n°"+b.getIndex()+"\n");
			jTextArea_minage.append(b.toString()+"\n");
			blockhain.ajouterBloc(b);
		}
		else
			jTextArea_minage.append("plus de transaction dispo le mineur ne peut pas creer de block\n");
	}
	
	
}
