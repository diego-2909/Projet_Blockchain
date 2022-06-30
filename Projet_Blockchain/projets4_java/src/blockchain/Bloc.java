package blockchain;


import java.util.List;

import utils.Date;
import utils.MerkleTreeRoot;

public class Bloc {
	private int index;
	private String previousHash;
	private String hash;
	private String hashMerkle;
	private Date timeStamp = new Date();
	private int nonce=0;
	private int nbTransaction;
	private List<Transaction> transactions;
	
	public Bloc(int index, String previousHash, List<Transaction> transactions) {
		this.index = index;
		this.previousHash = previousHash;
		this.transactions = transactions;
		if (index > 0) { // index>0 car le premier bloc n'a pas de transactions.
			this.nbTransaction = transactions.size();
			this.hashMerkle = MerkleTreeRoot.arbreMerkle(transactions);
		}
	}

	
	public int getIndex() {
		return index;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public String getHash() {
		return hash;
	}

	public String getHashMerkle() {
		return hashMerkle;
	}

	public int getNonce() {
		return nonce;
	}

	public int getNombreTransaction() {
		return nbTransaction;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public void setHashMerkle(String hashMerkle) {
		this.hashMerkle = hashMerkle;
	}

	public void setNonce(int nonce) {
		this.nonce = nonce;
	}
	
	public List<Transaction> getTransaction() {
		return transactions;
	}
	

	public String toString() {
		return "[index                      : " + index + "\n"
		   	 + "previousHash          : " + previousHash + "\n"
			 + "hash                        : " + hash + "\n"
			 + "hashMerkle              : " + hashMerkle + "\n"
			 + "timeStamp               : " + timeStamp + "\n"
			 + "nonce                      : " + nonce + "\n"
			 + "nombreTransaction : "+ nbTransaction + "]\n"
			 + "▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬\n";
	}
	
	
}