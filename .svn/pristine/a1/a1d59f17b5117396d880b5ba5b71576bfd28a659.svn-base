import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.table.DefaultTableModel;

public class FileLoader {
	
	DefaultTableModel table;
	
	FileLoader (DefaultTableModel model) {
		this.table = model;
	}
	
	void loadURLs(String urlName) {		
		File urls = new File(urlName);
		String row;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(urls));
			while ((row = reader.readLine())!= null) {
				table.addRow(new String[] {row, ""});
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
