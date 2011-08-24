package com.adaptiweb.utils.livefile;

import java.io.File;
import java.io.IOException;


public interface LiveFile {
	
	interface FileLoader {
		void loadFile(File file) throws IOException; 
	}
	
	void checkChanges(FileLoader loader);

	void addFileLoader(FileLoader loader);
	
	void refresh();

	File getFile();

}
