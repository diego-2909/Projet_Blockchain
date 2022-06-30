package interfaceGraphique;


import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import blockchain.Blockchain;
import blockchain.Transaction;

public class UtilsTransac extends Thread{
	private JScrollPane jScrollPane_transaction;
	private Blockchain blockchain;
	private JTextArea jTextArea_transaction;
	
	public UtilsTransac(Blockchain blockchain, JTextArea jTextArea_transaction, JScrollPane jScrollPane_transaction) {
		super();
		this.blockchain = blockchain;
		this.jTextArea_transaction = jTextArea_transaction;
		this.jScrollPane_transaction = jScrollPane_transaction;
	}
	
	public void run() {
		// boucle pour actualiser l'affichage
	    int temp = blockchain.getIndiceTransaction();
	    while(true) {
	    	Transaction transaction = blockchain.derniereTransaction();
	    	
	    	if( (temp < blockchain.getIndiceTransaction()) && (transaction != null) ) {
	    		jTextArea_transaction.append("\nt"+blockchain.getIndiceTransaction()+" "+transaction.getMessage());
	    		scrollAuto(jScrollPane_transaction);
		   		temp = blockchain.getIndiceTransaction();
	    	}
	    	
	    	
	    	try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    
		}	   	
	}


	public static void scrollAuto(JScrollPane scrollPane) {
	    JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
	    
	    AdjustmentListener scroller = new AdjustmentListener() {
	        @Override
	        public void adjustmentValueChanged(AdjustmentEvent e) {
	            Adjustable adjustable = e.getAdjustable();
	            adjustable.setValue(verticalBar.getMaximum());
	            // We have to remove the listener, otherwise the
	            // user would be unable to scroll afterwards
	            verticalBar.removeAdjustmentListener(this);
	        }
	    };
	    verticalBar.addAdjustmentListener(scroller);
	}


}
