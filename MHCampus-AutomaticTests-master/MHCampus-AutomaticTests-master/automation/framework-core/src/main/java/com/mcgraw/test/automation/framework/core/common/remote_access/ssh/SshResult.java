package com.mcgraw.test.automation.framework.core.common.remote_access.ssh;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.mcgraw.test.automation.framework.core.common.SpecialSymbols;

/**
 * SSH command execution result
 *
 * @author yyudzitski
 */
public class SshResult {

	private int exitcode;

	private String output;

	private String errorOutput;

	private static final Map<String, String> escapeChars = new HashMap<String, String>() {
		private static final long serialVersionUID = -1L;

		{
			/** Newline character */
			put(SpecialSymbols.NEW_LINE.toString(), "{@n}");
			/** Carriage-return character */
			put(SpecialSymbols.CARRIAGE_RETURN.toString(), "{@r}");
			/** Tabulation character */
			put(SpecialSymbols.TAB.toString(), "{@t}");
			/** Form-feed character */
			put(SpecialSymbols.FORM_FEET.toString(), "{@f}");
		}
	};

	/**
	 * Constructor
	 *
	 * @param exitcode
	 * @param result
	 */
	public SshResult(int exitcode, String result, String error_string) {
		this.exitcode = exitcode;
		this.output = result;
		this.errorOutput = error_string;
	}

	/**
	 * Get exit code
	 *
	 * @return exit code
	 */
	public int getExitCode() {
		return this.exitcode;
	}

	/**
	 * Get command output
	 *
	 * @return output
	 */
	public String getOutput() {
		return this.output;
	}

	public String getErrorOutput() {
		return this.errorOutput;
	}

	/**
	 * Convert exit code and result to string
	 *
	 * @return exit code and result as string
	 */
	@Override
	public String toString() {
		return "Exit code is: " + exitcode + ", result is:\n" + output;
	}

	/**
	 * Removing non-printable symbols from the SSH output results except for
	 * formatting characters (tabulations, newlines, carriage-returns,
	 * form-feeds). It is especially important when dealing with Win OS due to a
	 * lot of unprintable characters in the output results in that case.
	 *
	 * @param sshOutput
	 *            - SSH output to process
	 * @return
	 */
	public static String removeSpecialSymbols(String sshOutput) {
		if (sshOutput == null) {
			return null;
		}
		for (String formChar : escapeChars.keySet()) {
			sshOutput = sshOutput.replaceAll(formChar,
					escapeChars.get(formChar));
		}
		sshOutput = sshOutput.replaceAll("\\p{Cntrl}", "");
		for (String formChar : escapeChars.keySet()) {
			sshOutput = sshOutput.replaceAll(
					Pattern.quote(escapeChars.get(formChar)), formChar);
		}
		return sshOutput;
	}

	public boolean successExitCode() {
		return this.getExitCode() == 0;
	}

	public boolean successfull() {
		return successExitCode() && this.errorOutput.isEmpty();
	}

}