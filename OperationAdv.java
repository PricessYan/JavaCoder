package org.ranther.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OperationAdv extends FileOpe {

	public OperationAdv(String path, String file) {
		super(path, file);
	}

	public OperationAdv(String file) {
		super(file);
	}

	public OperationAdv() {
		super();
	}

	public void deleteEmptyFile(String path) {
		File filepath = new File(path);
		if (filepath.isDirectory()) {
			File[] filelist = filepath.listFiles();
			for (File file : filelist) {
				if (file.isDirectory()) {
					deleteEmptyFile(file.getAbsolutePath());
				} else if (file.isFile() && file.length() == 0) {
					System.out.println(file.getAbsolutePath() + "被删除");
					file.delete();
				}
			}
		}
	}

	public void deleteEmptyDir(String path) {
		File filepath = new File(path);
		String p = filepath.getPath();
		String ap = filepath.getAbsolutePath();
		if (!p.equalsIgnoreCase(ap)) {
			return;
		}
		if (filepath.isDirectory()) {
			File[] filelist = filepath.listFiles();
			if (filelist.length == 0) {
				System.out.println(filepath.getAbsolutePath() + "被删除");
				filepath.delete();
			} else {
				while (true) {
					for (File file : filelist) {
						if (file.isDirectory()) {
							deleteEmptyDir(file.getAbsolutePath());
						}
					}
					filepath = filepath.getParentFile();
					if (filepath == null) {
						break;
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
		String str = getSourceLog(path, file);
		try {
			FileWriter fw = new FileWriter(logfile);
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
			bw.write(logstr + "\r\n" + str);
			bw.close();
			fw.close();
			System.out.println("日志写入成功");
		} catch (IOException e) {
			System.out.println("文件无法书写");
		}
		return logtext;
	}

}
