package se.skaro.hextcgbot.application;

import org.apache.commons.cli.*;
import org.apache.log4j.PropertyConfigurator;

public class Main {

    private static final String LOG4J_PATH_OPT = "log4jPath";
    private static final String APP_CONTEXT_PATH_OPT = "appContextPath";
    private static final String PROPERTIES_PATH_OPT = "propertiesPath";

    public static void main(String[] args) {
    	
        try {
        	
            Options options = new Options();
            Option log4jPathOption = new Option(LOG4J_PATH_OPT, true, "log4j configuration file path");
            Option applicationContextPathOption = new Option(APP_CONTEXT_PATH_OPT, true, "Application context file path");
            Option propertiesPathOption = new Option(PROPERTIES_PATH_OPT, true, "Properties file path");
            log4jPathOption.setRequired(true);
            applicationContextPathOption.setRequired(true);
            propertiesPathOption.setRequired(true);
            options.addOption(log4jPathOption).addOption(applicationContextPathOption).addOption(propertiesPathOption);

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            PropertyConfigurator.configure(cmd.getOptionValue(LOG4J_PATH_OPT));
            new WorkerBotStartup(cmd.getOptionValue(APP_CONTEXT_PATH_OPT), cmd.getOptionValue(PROPERTIES_PATH_OPT)).startBot();
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
    }
}
