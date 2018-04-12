package com.mcgraw.test.automation.framework.core.runner.cli;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.HashMap;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

/**
 * Boolean {@link OptionHandler} that (unlike the standard
 * {@link BooleanOptionHandler} allows values to be set to false explicitly
 * (using e.g. '-myOpt false') rather than only returning false when the option
 * is <em>omitted</em>.
 */
public class ExplicitBooleanOptionHandler extends OptionHandler<Boolean> {
	private static final Map<String, Boolean> ACCEPTABLE_VALUES = new HashMap<String, Boolean>() {

		private static final long serialVersionUID = -8499365884433840598L;

		{
			put("true", TRUE);
			put("on", TRUE);
			put("yes", TRUE);
			put("1", TRUE);
			put("false", FALSE);
			put("off", FALSE);
			put("no", FALSE);
			put("0", FALSE);
		}
	};

	public ExplicitBooleanOptionHandler(CmdLineParser parser, OptionDef option,
			Setter<? super Boolean> setter) {
		super(parser, option, setter);
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		// end of arg list or next arg is another option
		if ((params.size() == 0) || params.getParameter(0).startsWith("-")) {
			setter.addValue(TRUE);
			return 0;
		} else {
			setter.addValue(getBoolean(params.getParameter(0)));
			return 1;
		}
	}

	private Boolean getBoolean(String parameter) throws CmdLineException {
		String valueStr = parameter.toLowerCase();
		if (!ACCEPTABLE_VALUES.containsKey(valueStr)) {
			throw new CmdLineException(owner, String.format(
					"\"%s\" is not a legal boolean value", valueStr));
		}
		return ACCEPTABLE_VALUES.get(valueStr);
	}

	@Override
	public String getDefaultMetaVariable() {
		return "[VAL]";
	}
}
