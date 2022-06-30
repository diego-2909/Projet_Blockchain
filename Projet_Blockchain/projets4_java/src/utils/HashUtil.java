package utils;
import java.security.MessageDigest;

import blockchain.Bloc;

public class HashUtil {
 
 //Applies Sha256 to a string and returns the result. 
 public static String applySha256(String input){
  
  try {
   MessageDigest digest = MessageDigest.getInstance("SHA-256");
         
   //Applies sha256 to our input, 
   byte[] hash = digest.digest(input.getBytes("UTF-8"));
         
   StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
   for (int i = 0; i < hash.length; i++) {
    String hex = Integer.toHexString(0xff & hash[i]);
    if(hex.length() == 1) hexString.append('0');
    hexString.append(hex);
   }
   return hexString.toString();
  }
  catch(Exception e) {
   throw new RuntimeException(e);
  }
 }
 
 //calcule le hash du bloc comprenant l'index, le timestamp, le hash précédent, la nonce+ le hash de l'arbre de merkle
 static public String calculHashBloc(Bloc bloc) {
		String temp = bloc.getIndex() + " " 
					+ bloc.getTimeStamp() + " " 
					+ bloc.getPreviousHash() + " " 
					+ bloc.getNonce()+ " " 
					+ bloc.getHashMerkle() ;
		return HashUtil.applySha256(temp);
	}
 
}
