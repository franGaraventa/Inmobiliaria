package utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class FileTypeFilter extends FileFilter{

	private final String descripcion;
	private final String extension;
	
	public FileTypeFilter(String extension,String descripcion) {
		this.extension = extension;
		this.descripcion = descripcion;
	}
	
	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		}
		return file.getName().endsWith(extension);
	}

	@Override
	public String getDescription() {
		return descripcion + String.format("(*%s)", extension);
	}

}
