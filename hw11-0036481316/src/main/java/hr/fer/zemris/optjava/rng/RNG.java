package hr.fer.zemris.optjava.rng;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class load desire settings from properties file 
 * @author Branko
 *
 */
public class RNG {
	/**
	 * Instance of {@linkplain IRNGProvider}
	 */
	private static IRNGProvider rngProvider;

	/**
	 * Static initialisation block
	 */
	static {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("./src/main/resources/rng-config.properties");
			prop.load(input);
			
			rngProvider = (IRNGProvider) ClassLoader.getSystemClassLoader().loadClass(prop.getProperty("rng-provider")).newInstance();

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// Stvorite primjerak razreda Properties;
		// Nad Classloaderom razreda RNG tražite InputStream prema resursu
		// rng-config.properties
		// recite stvorenom objektu razreda Properties da se učita podatcima iz
		// tog streama.
		// Dohvatite ime razreda pridruženo ključu "rng-provider"; zatražite
		// Classloader razreda
		// RNG da učita razred takvog imena i nad dobivenim razredom pozovite
		// metodu newInstance()
		// kako biste dobili jedan primjerak tog razreda; castajte ga u
		// IRNGProvider i zapamtite.
	}

	public static IRNG getRNG() {
		return rngProvider.getRNG();
	}
}