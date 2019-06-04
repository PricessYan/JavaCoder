package org.ranther.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class FileOpe {

	private String path, file;

	public FileOpe(String path, String file) {
		this.path = path;
		this.file = file;
	}

	public FileOpe(String file) {
		this.path = "log\\";
		this.file = file;
	}

	public FileOpe() {
		this.path = "log\\";
		this.file = "log.txt";
	}

	public String PathGet() {
		return this.path;
	}

	public String FileGet() {
		return this.file;
	}

	public void deleteEmptyFile() {
		deleteEmptyFile(this.path);
	}

	public void deleteEmptyDir() {
		deleteEmptyDir(this.file);
	}

	public abstract void deleteEmptyFile(String path);

	public abstract void deleteEmptyDir(String path);

	public String getSourceLog(String path, String file) {
		String strlog = "";
		File filepath = new File(path);
		File logfile = new File(filepath, file);
		try {
			FileReader fr = new FileReader(logfile);
			BufferedReader br = new BufferedReader(fr);
			while (true) {
				String str = br.readLine();
				if (str == null) {
					break;
				}
				strlog += str + "\r\n";
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			System.out.println("文件未找到");
		} catch (IOException e) {
			System.out.println("文件无法读取");
		}
		strlog = strlog.trim();
		return strlog;
	}

	public String getSourceLog(String file) {
		return getSourceLog(this.path, file);
	}

	public String getSourceLog() {
		return getSourceLog(this.path, this.file);
	}

	public abstract String logWriter(String path, String file, int logtype, String logtext);

	public String logWriter(String file, int logtype, String logtext) {
		return logWriter(this.path, file, logtype, logtext);
	}

	public String logWriter(int logtype, String logtext) {
		return logWriter(this.path, this.file, logtype, logtext);
	}

}
