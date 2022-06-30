package blockchain;

import java.util.ArrayList;
import java.util.List;

import user.User;

public class Blockchain {
	
	private int nbBloc = 0;
	private int difficulty;
	private List<Bloc> listBloc = new ArrayList<>();
	private List<Transaction> listTransactionGlobal = new ArrayList<>();
	private int indiceLastTransacUse = -1;
	private int indiceTransaction = -1;
	private List<User> listUser;
	
	

	public Blockchain(int difficulty, List<User> listUser) {
		this.difficulty = difficulty;
		this.listUser = listUser;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public void ajouterBloc(Bloc bloc) {
			listBloc.add(bloc);
			this.nbBloc++;
	}
	
	public Bloc dernierBloc() {
		return listBloc.get(listBloc.size()-1);
	}
	
	public String toString() {
	    StringBuilder builder = new StringBuilder();

	    for (Bloc bloc : listBloc) {
	      builder.append("bloc n°" + bloc.getIndex() + " : \n\n") ;
	      builder.append("liste de transaction du block:\n") ;
	      for(int i=0; i<bloc.getNombreTransaction();i++)
	    	 builder.append("	  "+bloc.getTransaction().get(i).getMessage() + "\n");
	
	      builder.append("\n" + bloc.toString());
	    }
	    
	    return builder.toString();
	  }
	
	public Transaction derniereTransaction() {
		if (listTransactionGlobal.size() > 0)
			return listTransactionGlobal.get(listTransactionGlobal.size()-1);
		return null;
	}
	
	public void ajouterTransaction(Transaction transaction) {
		listTransactionGlobal.add(transaction);
		this.indiceTransaction++;
	}
	
	public List<Transaction> getListeTransactionGlobal() {
		return listTransactionGlobal;
	}
	
	public List<Bloc> getListBloc(){
		return listBloc;
	}
	
	public int getIndiceLastTransacUse() {
		return indiceLastTransacUse;
	}
	
	public void setIndiceLastTransacUse(int value) {
		this.indiceLastTransacUse = value;
	}
	
	public int getNbBloc() {
		return nbBloc;
	}
	
	
	public int getIndiceTransaction() {
		return indiceTransaction;
	}
	
	public List<User> getListuser() {
		return listUser;
	}
	
}
