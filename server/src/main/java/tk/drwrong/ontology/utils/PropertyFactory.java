package tk.drwrong.ontology.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFactory {
	private static String propFileName = "config.properties";
	public Properties prop;
	
	public PropertyFactory() throws FileNotFoundException{
		prop = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			throw new FileNotFoundException("property file: '" +  propFileName + "' not found");
		}
	}
}
