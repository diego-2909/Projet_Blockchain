package utils;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import blockchain.Blockchain;

public class JsonUtils {
	
	public static void blockchainToJson(Blockchain blockchain) throws IOException {
		File file = new File("blockchain.json");
		
		Gson gson = new GsonBuilder()
						.setPrettyPrinting()
						.serializeNulls()
						.disableHtmlEscaping()
						.create();
		
		FileUtils.saveFile(file, gson.toJson(blockchain));
	}
	
	public static Blockchain blockchainFromJson() throws IOException {
		String string = FileUtils.loadFile(new File("blockchain.json"));
		if (string != null) {
			Gson gson = new GsonBuilder()
					.setPrettyPrinting()
					.serializeNulls()
					.disableHtmlEscaping()
					.create();
			
			return gson.fromJson(string, Blockchain.class);
	
		}
		return null;
	}
}
