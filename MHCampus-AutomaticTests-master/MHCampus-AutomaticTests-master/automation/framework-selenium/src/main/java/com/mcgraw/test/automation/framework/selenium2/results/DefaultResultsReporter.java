package com.mcgraw.test.automation.framework.selenium2.results;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.mcgraw.test.automation.framework.core.common.ResourceUtils;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * Default implementation for custom results reporter<br>
 * Using Velocity for merging templates with provided results<br>
 *
 * @see <a href="http://velocity.apache.org/">http://velocity.apache.org/</a>
 *
 * @author Andrei Varabyeu
 *
 */
public class DefaultResultsReporter implements IResultReporter {

	/** Filename of HTML index file */
	private static final String INDEX_FILENAME = "index.html";

	/** Report output directory */
	private File outputDirectory;

	/** Report's index file */
	private File reportFile;

	/**
	 * Creates output directory in case if not created. Initializes report index
	 * file
	 *
	 * @throws IOException
	 */
	private void prepareDirectory() throws IOException {
		if (null != outputDirectory && null != reportFile) {
			return;
		}
		Logger.info("Generating file with failures...");
		File testOutput = new File("test-output");
		if (!testOutput.exists()) {
			Logger.debug("Test-output directory is not created. Creating...");
			testOutput.mkdir();
		}
		outputDirectory = new File(testOutput, "groupedFailuresReport");
		if (outputDirectory.exists()) {
			FileUtils.deleteQuietly(new File(INDEX_FILENAME));
		} else if (!outputDirectory.mkdir()) {
			throw new IOException("Directory: "
					+ outputDirectory.getAbsolutePath() + " was not created");

		}
		Logger.info("Directory created: " + outputDirectory.getAbsolutePath());
		reportFile = new File(outputDirectory, "index.html");

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.mcgraw.test.automation.framework.selenium2.results.IResultReporter#report(java.util.Map)
	 */
	@Override
	synchronized public void report(Map<?, ?> results) throws Exception {
		prepareDirectory();
		VelocityContext context = new VelocityContext();
		context.put("failures", results);
		Logger.debug("Output directory: " + outputDirectory.getAbsolutePath());
		generateFile(reportFile, context);
		copyClasspathResources(outputDirectory);
	}

	/**
	 * Merge provided results with Velocity template and saves to the index file
	 *
	 * @param file
	 *            - index file
	 * @param context
	 *            - Velocity context with provided test results
	 * @throws Exception
	 */
	private void generateFile(File file, VelocityContext context)
			throws Exception {
		file.createNewFile();
		Writer writer = new BufferedWriter(new FileWriter(file));
		try {
			Velocity.mergeTemplate("reports/index.html.vm", "UTF-8", context,
					writer);
			writer.flush();
		} finally {
			writer.close();
		}
	}

	/**
	 * Copies additional resources such a JavaScript scripts and CSS stylesheets
	 *
	 * @param outputDirectory
	 * @throws IOException
	 */
	private void copyClasspathResources(File outputDirectory)
			throws IOException {
		copyClasspathResource(outputDirectory, "reports/css/global.css",
				"css/global.css");
		copyClasspathResource(outputDirectory, "reports/js/global.js",
				"js/global.js");
		copyClasspathResource(outputDirectory, "reports/js/jquery.js",
				"js/jquery.js");
		copyClasspathResource(outputDirectory, "reports/js/scope_test.js",
				"js/scope_test.js");
	}

	/**
	 * Copies resource into destination directory in case if such file doesn't
	 * exists
	 *
	 * @param outputDirectory
	 * @param resourceName
	 * @param targetFileName
	 * @throws IOException
	 */
	protected void copyClasspathResource(File outputDirectory,
			String resourceName, String targetFileName) throws IOException {
		File resourceFile = new File(outputDirectory, targetFileName);
		if (!resourceFile.exists()) {
			InputStream resourceStream = ResourceUtils
					.getResourceAsInputStream(resourceName);
			FileUtils.copyInputStreamToFile(resourceStream, resourceFile);
		}
	}

}
