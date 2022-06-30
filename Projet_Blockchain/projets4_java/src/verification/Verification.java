package verification;

import blockchain.Blockchain;
import utils.HashUtil;
import utils.MerkleTreeRoot;

public class Verification {
	
	
	static public StringBuilder valideGenesis(Blockchain blockchain) {
		StringBuilder builder = new StringBuilder();
		
		if(blockchain.getListBloc().get(0).getIndex() != 0) {
			builder.append("ERREUR avec le bloc génésis\n");
		}	
		
		return builder;
	}
	
	static public StringBuilder valideChainageHashBlock(Blockchain blockchain) {
		
		StringBuilder builder = new StringBuilder();
		
		for ( int i=1; i<blockchain.getNbBloc(); i++) {
			
			if( blockchain.getListBloc().get(i-1).getHash() != blockchain.getListBloc().get(i).getPreviousHash()) {
				
				builder.append("ERREUR bloc " + i + "le chainage ne correspond pas \n");
				builder.append("bloc " + i + ", hash : " + blockchain.getListBloc().get(i-1).getHash());
				builder.append("bloc " + i + ", hash : " + blockchain.getListBloc().get(i-1).getHash());
			}
		}
		
		return builder;
		
	}
	
	static public StringBuilder valideHashBloc(Blockchain blockchain) {
		StringBuilder builder = new StringBuilder();
		
		for(int i=0; i<blockchain.getNbBloc(); i++) {
			
			if (blockchain.getListBloc().get(i).getHash() != HashUtil.calculHashBloc(blockchain.getListBloc().get(i))) {
				
				builder.append("ERREUR block " + i + " le hash du block est FAUX " );
				builder.append("Hash annoncé" + blockchain.getListBloc().get(i).getHash() );
				builder.append("Hash calculé" + HashUtil.calculHashBloc(blockchain.getListBloc().get(i)) );

			}
			
		}
		return builder;
	}
	
	static public StringBuilder valideHashMerkleTreeRoot(Blockchain blockchain) {
		StringBuilder builder = new StringBuilder();
		
		for ( int i=1; i<blockchain.getNbBloc();i++) {
			
			if ( blockchain.getListBloc().get(i).getHashMerkle() != MerkleTreeRoot.arbreMerkle(blockchain.getListBloc().get(0).getTransaction())) {
				builder.append("ERREUR Bloc " + i + ", le HashRoot est FAUX \n ");
				builder.append("hashRoot annoncé: " + blockchain.getListBloc().get(i).getHashMerkle() + " \n");
				builder.append("hashRoot calculé: " + MerkleTreeRoot.arbreMerkle(blockchain.getListBloc().get(0).getTransaction()) + " \n") ;
			}
			
		}
		
		return builder;
		
	}
	
	static public StringBuilder verificationBlockChain(Blockchain blockchain) {
		StringBuilder builder = new StringBuilder();
		builder.append(valideGenesis(blockchain) + "\n");
		builder.append(valideChainageHashBlock(blockchain));
		builder.append(valideHashBloc(blockchain));
		builder.append(valideHashMerkleTreeRoot(blockchain));
		
		builder.append("Vérification de la blockchain terminé. \n ");
		
		return builder;
		
	}

}
