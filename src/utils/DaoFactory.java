package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {
	
	private static String classname = null;
	private static Properties prop = null; 
	private DaoFactory(){
		
	}
	static{
		//∂¡≈‰÷√Œƒº˛
		InputStream in = DaoFactory.class.getClassLoader().
				getResourceAsStream("daoconfig.properties");
		prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Object newInstance(String iname){		
		try {
			classname = prop.getProperty(iname);
			return Class.forName(classname).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
