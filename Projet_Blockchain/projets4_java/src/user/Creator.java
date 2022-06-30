package user;


import blockchain.Bloc;
import blockchain.Blockchain;
import utils.HashUtil;

public class Creator extends User{

	public Creator() {
		super("Creator");
	}

	// ça crée le génésis, le bloc n°0
	public void creationGenesis(Blockchain blockchain) {
		Bloc bloc = new Bloc(0, "0", null);
		bloc.setHash(HashUtil.calculHashBloc(bloc));
		bloc.setHashMerkle(HashUtil.applySha256("Genesis"));
		blockchain.ajouterBloc(bloc);
	}
}
