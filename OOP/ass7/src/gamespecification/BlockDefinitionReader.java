package gamespecification;

import factory.BlocksFromSymbolsFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class BlockDefinitionReader {
	public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {

		String line;
		String mainString = "";
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(reader);
			while ((line = bufferedReader.readLine()) != null) {
				String newLine;
				if (!line.contains("level_name")) {
					newLine = line.replaceAll("\\s+", "");
				} else {
					newLine = line;
				}
				if (!newLine.isEmpty()) {
					if (!newLine.contains("#") && !newLine.equals("END_LEVEL")) {
						mainString = mainString + newLine + "\n";
					}
				}

			}
		} catch (IOException e) {
			System.out.println("Something went wrong while reading from the level definitions file!");
		} finally {
			if (bufferedReader != null) { // Exception might have happened at constructor
				try {
					bufferedReader.close();
				} catch (IOException e) {
					System.out.println(" Failed closing the file !");
				}
			}
		}

		return new BlocksFromSymbolsFactory();
	}
}
