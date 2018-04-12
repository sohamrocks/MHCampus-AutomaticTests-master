package com.mcgraw.test.automation.framework.core.common;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClasspathUtils {

	public static List<File> findDirInClasspath(String... dirNames) {
		String[] dirs = System.getProperty("java.class.path").split(
				SpecialSymbols.PATH_SEPARATOR.toString());
		List<File> allFound = new ArrayList<File>();
		for (String dir : dirs) {
			List<File> foundDirs = listRecursively(new File(dir));
			if (!foundDirs.isEmpty()) {
				allFound.addAll(foundDirs);
			}
		}
		return allFound;
	}

	public static List<File> listRecursively(File rootDir, String... dirNames) {
		if (rootDir.isDirectory()) {
			File[] dirContents = rootDir.listFiles(new DirFilterEndsWith(
					dirNames));
			List<File> files = new ArrayList<File>(Arrays.asList(dirContents));
			for (File f : rootDir.listFiles()) {
				files.addAll(listRecursively(f, dirNames));
			}
			return files;
		}
		return Collections.emptyList();
	}

	private static class DirFilterEndsWith implements FileFilter {
		private List<String> endsWith;

		public DirFilterEndsWith(String... endsWith) {
			this.endsWith = Arrays.asList(endsWith);
		}

		@Override
		public boolean accept(File pathname) {
			for (String endsWithOne : endsWith) {
				if (pathname.isDirectory()
						&& pathname.getAbsolutePath().endsWith(endsWithOne))
					return true;
			}
			return false;
		}
	}
}
