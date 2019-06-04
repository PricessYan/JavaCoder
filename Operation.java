package org.ranther.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Operation extends FileOpe {

	public Operation(String path, String file) {
		super(path, file);
	}

	public Operation(String file) {
		super(file);
	}

	public Operation() {
		super();
	}

	public void deleteEmptyFile(String path) {
		File filepath = new File(path);
		if (filepath.isDirectory()) {
			File[] filelist = filepath.listFiles();
			for (File file : filelist) {
				if (file.isFile() && file.length() == 0) {
					System.out.println(file.getAbsolutePath() + "被删除");
					file.delete();
				}
			}
		}
	}

	public void deleteEmptyDir(String path) {
		File filepath = new File(path);
		if (filepath.isDirectory()) {
			File[] filelist = filepath.listFiles();
			for (File file : filelist) {
				if (file.isDirectory()) {
					File[] sublist = file.listFiles();
					if (sublist.length == 0) {
						System.out.println(file.getAbsolutePath() + "被删除");
						file.delete();
					}
				}
			}
		}
	}

	public String logWriter(String path, String file, int logtype, String logtext) {
		String logstr = "";
		File filepath = new File(path);
		filepath.mkdirs();
		File logfile = new File(filepath, file);
		try {
			FileWriter fw = new FileWriter(logfile, true);
			BufferedWriter bw = new BufferedWriter(fw);
			String status;
			switch (logtype) {
			case 0:
				status = "Info,";
				break;
			case 1:
				status = "Warn,";
				break;
			case 2:
				status = "Error,";
				break;
			default:
				status = "Other,";
			}
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("y-MM-dd HH:mm:ss ");
			Date date = c.getTime();
			String strdate = sdf.format(date);
			logstr = strdate + status + logtext;
			if (logfile.length() == 0) {
				bw.write(logstr);
			} else {
				bw.newLine();
				bw.append(logstr);
			}
			System.out.println("日志写入成功");
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("文件无法书写");
		}
		return logtext;
	}

}
