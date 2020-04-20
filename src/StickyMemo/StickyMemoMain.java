package StickyMemo;

// import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StickyMemoMain {

	public static List<Memo> MemoArr = new ArrayList<Memo>();

	public static FileCheckers fc = new FileCheckers();

	public static String DEFAULTCOLOR = "Yellow";
	public static String DEFAULTTITLE = "Title";
	public static String DEFAULTTEXTAREA = "New Note";

	public class FilePaths {
		public String PATH = "C:/";
		public String DIRNAME = PATH + "\\StickyMemosOut\\";
		public String NAME = "note_";
		public String EXTENSION = ".txt";

		public String SETTINGSNAME = "settings.txt";

		public FilePaths() {
			PATH = System.getProperty("user.dir");
			DIRNAME = PATH + "\\StickyMemosOut\\";
		}
	}

	public static void main(String[] args) {
		StickyMemoMain.FilePaths names = new StickyMemoMain().new FilePaths();
		// FlatLightLaf.install();
		/*
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
			UIManager.put( "ScrollBar.showButtons", true );
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
			System.err.println("Look and feel not set.");
		}
		 */
		// later on check multiple files in directory
		try {
			// if dir exists
			if (fc.checkDirExists(names.DIRNAME)) {
				File[] files = fc.getFileList(names.DIRNAME);
				// if no files in dir
				if (files.length == 0) {
					newPostMain(names.NAME, names.EXTENSION, names.DIRNAME, names.SETTINGSNAME);
					return;
					// else if there are files in dir
				} else {
					String[] lineArr = {};
					String colorName = DEFAULTCOLOR;
					String titleText = DEFAULTTITLE;
					String fileContent = DEFAULTTEXTAREA;
					if (fc.checkFileExists(names.DIRNAME, names.SETTINGSNAME)) {
						String colors = fc.readFile(names.DIRNAME, names.SETTINGSNAME);
						lineArr = colors.split("\n");
						if (colors == "") {
							newPostMain(names.NAME, names.EXTENSION, names.DIRNAME, names.SETTINGSNAME);
							return;
						}
						int locationOffset = 0;
						for (File file : files) {
							String fileName = file.getName();
							if (fileName.equals(names.SETTINGSNAME) && files.length > 1) {
								continue;
							}
							for (String colorOfPost : lineArr) {
								String[] splitLine = colorOfPost.split(" ");
								if (splitLine[0].equals(fileName)) {
									colorName = splitLine[1];
									titleText = splitLine[2];
									break;
								}
							}
							fileContent = fc.readFile(names.DIRNAME, fileName);
							if (fileContent == "")
								fileContent = DEFAULTTEXTAREA;
							createNewPostIt(fileName, names.DIRNAME, fileContent, colorName, titleText, true,
									locationOffset);
							locationOffset += 30;
							// PostIt stickyNote = new PostIt(fileName, names.DIRNAME, fileContent, count);
						}
					}
				}
				// else dir does not exist, create a new file
			} else {
				newPostMain(names.NAME, names.EXTENSION, names.DIRNAME, names.SETTINGSNAME);
			}

		} catch (IOException e1) {
			e1.printStackTrace();
			// fc.writeFile(names.DIRNAME, names.SETTINGSNAME, "");
		}
	}

	public static void newPostMain(String name, String ext, String dir, String settingName) {
		String newName = name + "1" + ext;
		try {
			fc.checkFileExists(dir, newName);
			fc.checkFileExists(dir, settingName);
			createNewPostIt(newName, dir, DEFAULTTEXTAREA, DEFAULTCOLOR, DEFAULTTITLE, true, 0);
			fc.writeFile(dir, settingName, newName + " " + DEFAULTCOLOR + " " + DEFAULTTITLE + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createNewPostIt(String newName, String dirName, String content, String colorName, String title,
			boolean start, int location) {
		// -1 is on start of program
		if (start) {
			Memo stickyNote = new Memo(newName, dirName, content, colorName, title, location);
			MemoArr.add(stickyNote);
		} else {
			// created by new note function
			int lastNum = getLastNoteNum(dirName);
			String noteName = newName + "_" + Integer.toString(lastNum + 1) + ".txt";
			try {
				fc.checkFileExists(dirName, noteName);
				Memo stickyNote = new Memo(noteName, dirName, content, colorName, title, location);
				MemoArr.add(stickyNote);
				String settingsStr = fc.readFile(dirName, "settings.txt");
				String fullStr = settingsStr + "\n" + noteName + " " + colorName + " " + title;
				fc.writeFile(dirName, "settings.txt", fullStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (MemoAllNotes.allNotes != null) {
			MemoAllNotes.allNotes.repaint();
		}
	}

	public static int getLastNoteNum(String dir) {
		File[] files = fc.getFileList(dir);
		int max = 1;
		int num = 1;
		for (int i = 0; i < files.length - 1; i++) {
			String fileName = files[i].getName();
			String[] splitName = fileName.split("[_\\.]");
			num = Integer.parseInt(splitName[1]);
			if (num > max) {
				max = num;
			}
		}
		// the last file is settings, the last note file is the 2nd to last
		return max;
	}

	public static void closePost(Memo post) {
		MemoArr.remove(post);
	}
}

/**
 * 
 * all notes -> another window that shows all the notes the user has by title.
 * clicking button would show the window.
 * 
 */
