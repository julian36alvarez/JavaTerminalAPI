package com.juliancho.jobsearch.cli;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class CLIHelpValidator implements IParameterValidator {
    @Override
    public void validate(String name, String value) throws ParameterException {
        boolean actualValu = Boolean.parseBoolean(value);
        if (actualValu) {
            throw new ParameterException("Helps");
        }
    }
}
