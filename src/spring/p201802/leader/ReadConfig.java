package spring.p201802.leader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.ho.yaml.Yaml;
import org.ho.yaml.YamlStream;


public class ReadConfig {

	public static void main(String[] args) throws IOException {
		
		/*String value = getProperty("common.properties","maxUploadFileSzie");
		System.out.println(value);*/
		
		
		 HashMap yamMap =Yaml.loadType(Thread.currentThread().getContextClassLoader().getResourceAsStream("common.yaml"),HashMap.class);
		 System.out.println(yamMap.get("user"));
		 HashMap yamMap2 = (HashMap)(yamMap.get("user"));
		 System.out.println(yamMap2.get("name"));
		 System.out.println(yamMap.get("spring"));
		 HashMap yamMap3 = (HashMap)(yamMap.get("spring"));
		 System.out.println(yamMap3.get("datasource"));
		 HashMap yamMap4 = (HashMap)(yamMap3.get("datasource"));
		 System.out.println(yamMap4.get("url"));
	}
	
	
	public static String getProperty(String FileName,String propertyname) throws IOException{
		 Properties props = new Properties();
		 props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(FileName));
		 return props.getProperty(propertyname);
	}

}
