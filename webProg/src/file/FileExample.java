package file;

import java.io.File;

public class FileExample {

	public static File[] sortFileArray(File[] fileArray) {
		File temp;
		File[] newAry = null;
		for (int j = 0; j < fileArray.length - 1; j++) {
			for (int i = 0; i < fileArray.length - 1; i++) {
				if ((fileArray[i].isDirectory() ? "/" + fileArray[i].getName() : fileArray[i].getName())
						.compareToIgnoreCase((fileArray[i + 1].isDirectory() ? "/" + fileArray[i + 1].getName()
								: fileArray[i + 1].getName())) > 0) {
					temp = fileArray[i];
					fileArray[i] = fileArray[i + 1];
					fileArray[i + 1] = temp;
				}
			}
		}
		newAry = fileArray;

		return newAry;
	}

	public static void main(String[] args) {
		String osName = System.getProperty("user.name");
		System.out.println(osName);

		String path = "/home/" + osName + "/git/javaScript/javaScript/WebContent";
		File temp = new File(path);
		File[] fileList = temp.listFiles();

		for (File file : sortFileArray(fileList)) {

			System.out.println("/" + file.getName());

			if (file.isDirectory()) {
				String addPath = path + "/" + file.getName();
				File addTemp = new File(addPath);
				File[] addList = addTemp.listFiles();

				for (File addFile : sortFileArray(addList)) {
					if (addFile.getName().indexOf("html") != -1 || addFile.getName().indexOf("jsp") != -1) {
						System.out.println("    " + addFile.getName());

					} else if (addFile.isDirectory()) {
						System.out.println("    /" + addFile.getName());

						String dupPath = path + "/" + file.getName() + "/" + addFile.getName();
						File dupTemp = new File(dupPath);
						File[] dupList = dupTemp.listFiles();

						for (File dupFile : sortFileArray(dupList)) {
							if (dupFile.getName().indexOf("html") != -1 || dupFile.getName().indexOf("jsp") != -1) {
								System.out.println("        " + dupFile.getName());
							} else if (dupFile.isDirectory()) {
								System.out.println("        /" + dupFile.getName());
							}

						} // dup loop

					}

				} // sup loop

			}

		} // main loop

	} // end of main

} // end of class
