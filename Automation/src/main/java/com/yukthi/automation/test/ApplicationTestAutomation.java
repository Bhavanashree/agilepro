package com.yukthi.automation.test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.yukthi.automation.AutomationContext;
import com.yukthi.automation.ExecutableFactory;
import com.yukthi.automation.IApplicationConfiguration;
import com.yukthi.automation.StateConfiguration;
import com.yukthi.ccg.xml.DefaultParserHandler;
import com.yukthi.ccg.xml.XMLBeanParser;
import com.yukthi.utils.exceptions.InvalidConfigurationException;
import com.yukthi.utils.exceptions.InvalidStateException;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Main class which executes the test suites of tha application.
 * 
 * @author akiran
 */
public class ApplicationTestAutomation
{
	private static Logger logger = LogManager.getLogger(ApplicationTestAutomation.class);

	/**
	 * Free marker configuraton.
	 */
	private static Configuration freemarkerConfiguration = new Configuration();

	/**
	 * Factory for creating executables - steps and validators.
	 */
	private static ExecutableFactory executableFactory;

	/**
	 * Default parser for xml parsing which in turn uses
	 * {@link #executableFactory}.
	 */
	private static DefaultParserHandler defaultParserHandler = new DefaultParserHandler();

	/**
	 * The log.
	 **/
	private static String LOG = ".log";

	/**
	 * Loads state configuration file specified in application configuration.
	 * 
	 * @param appConfig
	 *            Application configuration where state configuration file is
	 *            specified.
	 * @return loaded state configuration.
	 */
	private static StateConfiguration loadStateConfiguration(IApplicationConfiguration appConfig) throws Exception
	{
		logger.debug("Loading state configuration file - {}", appConfig.getStateConfigurationFile());

		File stateConfigFile = new File(appConfig.getStateConfigurationFile());

		if(!stateConfigFile.exists())
		{
			System.err.println("Invalid state configuration file - " + stateConfigFile);
			System.exit(-1);
		}

		FileInputStream fis = new FileInputStream(stateConfigFile);

		StateConfiguration stateConfiguration = new StateConfiguration(appConfig);

		XMLBeanParser.parse(fis, stateConfiguration, defaultParserHandler);

		fis.close();

		return stateConfiguration;
	}

	/**
	 * Loads test suites from the test suite folder specified by app
	 * configuration.
	 * 
	 * @param appConfig
	 *            Application config to be used
	 * @return Test suites mapped by name.
	 */
	private static Map<String, TestSuite> loadTestSuites(IApplicationConfiguration appConfig)
	{
		logger.debug("Loading test suites from folder - {}", appConfig.getTestSuiteFolder());

		File testCaseFolder = new File(appConfig.getTestSuiteFolder());

		if(!testCaseFolder.exists() && !testCaseFolder.isDirectory())
		{
			System.err.println("Invalid test suite folder specified - " + testCaseFolder);
			System.exit(-1);
		}

		Map<String, TestSuite> testSuites = new HashMap<>();

		// load the test suites recursively

		// stack to maintain folder and sub folders
		Stack<File> folders = new Stack<>();
		folders.push(testCaseFolder);

		// filter to filter xml files and add sub folder to stack
		FileFilter fileFiler = new FileFilter()
		{
			@Override
			public boolean accept(File pathname)
			{
				if(!pathname.getName().toLowerCase().endsWith(".xml"))
				{
					return false;
				}

				if(pathname.isDirectory())
				{
					folders.push(pathname);
				}

				TestSuite testSuite = new TestSuite();

				try
				{
					FileInputStream fis = new FileInputStream(pathname);
					XMLBeanParser.parse(fis, testSuite, defaultParserHandler);
					testSuites.put(testSuite.getName(), testSuite);
					fis.close();
				} catch(Exception ex)
				{
					throw new InvalidStateException(ex, "An error occurred while loading test suite from file: {}", pathname.getPath());
				}

				return false;
			}
		};

		// loop till scanning is completed on test folder and its sub folders
		while(!folders.isEmpty())
		{
			folders.pop().listFiles(fileFiler);
		}

		// validate test suites has valid dependencies
		for(TestSuite testSuite : testSuites.values())
		{
			if(testSuite.getDependencies() == null)
			{
				continue;
			}

			for(String dependency : testSuite.getDependencies())
			{
				if(!testSuites.containsKey(dependency))
				{
					throw new InvalidConfigurationException("Invalid dependency '{}' specified for test suite - {}", dependency, testSuite.getName());
				}
			}
		}

		return testSuites;
	}

	/**
	 * Maps application configuration driver names to driver instances.
	 * 
	 * @param appConfig
	 *            Application configuration to be used.
	 * @return Loaded web drivers.
	 */
	private static WebDriver[] loadDrivers(IApplicationConfiguration appConfig) throws Exception
	{
		String driverNames[] = appConfig.getSeleniumDrivers();
		WebDriver drivers[] = new WebDriver[driverNames.length];

		for(int i = 0; i < driverNames.length; i++)
		{
			drivers[i] = (WebDriver) Class.forName(driverNames[i]).newInstance();
		}

		return drivers;
	}

	/**
	 * Executes specified test suite and its dependencies recursively.
	 * 
	 * @param context
	 *            Automation context
	 * @param testSuite
	 *            Test suite to execute
	 * @param fullExecutionDetails
	 *            Execution details to track
	 * @param logsFolder
	 *            Logs folder
	 * @param testSuiteMap
	 *            Test suite map to get dependency test suites
	 * @return True if all dependencies and test cases passed.
	 */
	private static boolean executeTestSuite(AutomationContext context, TestSuite testSuite, FullExecutionDetails fullExecutionDetails, File logsFolder, Map<String, TestSuite> testSuiteMap)
	{
		if(context.isTestSuiteExecuted(testSuite.getName()))
		{
			return context.isTestSuiteCompleted(testSuite.getName());
		}

		logger.debug("Executing test suite - {}", testSuite.getName());
		context.testSuiteInProgress(testSuite.getName());

		// if test suite has dependencies execute them first
		if(testSuite.getDependencies() != null)
		{
			TestSuite depTestSuite = null;

			for(String dependencyTestSuite : testSuite.getDependencies())
			{
				// if dependency is already completed, ignore
				if(context.isTestSuiteCompleted(dependencyTestSuite))
				{
					continue;
				}

				// if dependency is failed, skip the test case
				if(context.isTestSuiteFailed(dependencyTestSuite))
				{
					context.testSuiteFailed(testSuite.getName());
					testSuite.setStatus(TestSuiteStatus.SKIPPED);
					testSuite.setStatusMessage("Skipping as dependency test suite is failed/skipped - " + dependencyTestSuite);

					return false;
				}

				// if dependency is already in progress, then it is recursion,
				// throw error
				if(context.isTestSuiteInProgress(dependencyTestSuite))
				{
					throw new InvalidStateException("Encountered circular dependency with '{}' in test suite - {}", depTestSuite, testSuite.getName());
				}

				depTestSuite = testSuiteMap.get(dependencyTestSuite);

				executeTestSuite(context, depTestSuite, fullExecutionDetails, logsFolder, testSuiteMap);
			}
		}

		TestCaseResult testCaseResult = null;
		String testFileName = null;

		boolean successful = true;

		for(TestCase testCase : testSuite.getTestCases())
		{
			logger.debug("Executing test case '{}' in test suite - {}", testCase.getName(), testSuite.getName());

			testFileName = testSuite.getName() + "_" + testCase.getName();

			testCaseResult = testCase.execute(context);

			if(testCaseResult.getStatus() != TestStatus.SUCCESSUFUL)
			{
				successful = false;
			}

			try
			{
				FileUtils.writeStringToFile(new File(logsFolder, testFileName + LOG), testCaseResult.getExecutionLog());
				testCaseResult.setExecutionLog("");
			} catch(Exception ex)
			{
				throw new InvalidStateException(ex, "An error occurred while creating test log file - {}", new File(logsFolder, testFileName + LOG));
			}

			fullExecutionDetails.addTestResult(testSuite, testCaseResult);

			if(!successful)
			{
				break;
			}
		}

		if(successful)
		{
			testSuite.setStatus(TestSuiteStatus.SUCCESSFUL);
			context.testSuiteCompleted(testSuite.getName());
		}
		else
		{
			testSuite.setStatus(TestSuiteStatus.FAILED);
			context.testSuiteFailed(testSuite.getName());
		}

		return successful;
	}

	/**
	 * Executes test suites with specified context.
	 * 
	 * @param context
	 *            Context to be used for automation.
	 * @param testSuiteMap
	 *            Test suites to execute.
	 * @param reportFolder
	 *            Folder where output report needs to be generated
	 */
	private static void executeTestSuites(AutomationContext context, Map<String, TestSuite> testSuiteMap, File reportFolder)
	{
		// create logs folder
		File logsFolder = new File(reportFolder, "logs");
		logsFolder.mkdirs();

		FullExecutionDetails fullExecutionDetails = new FullExecutionDetails();

		IApplicationConfiguration configuration = context.getAppConfiguration();
		Set<String> limitedTestSuites = configuration.getLimitedTestSuites();

		// if limited test suites are specified, only execute them and their
		// dependencies
		if(CollectionUtils.isNotEmpty(limitedTestSuites))
		{
			logger.debug("Executing limited test suites - {}", limitedTestSuites);

			TestSuite testSuite = null;

			for(String name : limitedTestSuites)
			{
				testSuite = testSuiteMap.get(name);

				if(testSuite == null)
				{
					throw new InvalidConfigurationException("Invalid test suite name '{}' specified in limited test suites", name);
				}

				executeTestSuite(context, testSuite, fullExecutionDetails, logsFolder, testSuiteMap);
			}
		}
		// if no limited test suites are specified execute all test suites
		else
		{
			for(TestSuite testSuite : testSuiteMap.values())
			{
				executeTestSuite(context, testSuite, fullExecutionDetails, logsFolder, testSuiteMap);
			}
		}

		try
		{
			Template freemarkerTemplate = new Template("report", new InputStreamReader(ApplicationTestAutomation.class.getResourceAsStream("/report-template.html")), freemarkerConfiguration);

			FileWriter writer = new FileWriter(new File(reportFolder, "test-report.html"));
			freemarkerTemplate.process(fullExecutionDetails, writer);

			writer.flush();
			writer.close();
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while generating test result report");
		}
	}

	/**
	 * Loads application configuration from sepcified file.
	 * 
	 * @param appConfigurationFile
	 *            Application config file to load.
	 * @return Loaded application config.
	 */
	private static IApplicationConfiguration loadApplicationConfiguration(File appConfigurationFile) throws Exception
	{
		FileInputStream fis = new FileInputStream(appConfigurationFile);
		IApplicationConfiguration appConfig = (IApplicationConfiguration) XMLBeanParser.parse(fis);
		fis.close();

		executableFactory = new ExecutableFactory(appConfig);
		defaultParserHandler.setCustomNodeHandler(executableFactory);

		return appConfig;
	}

	/**
	 * Automation entry point.
	 * 
	 * @param args
	 *            CMD line arguments.
	 */
	public static void main(String[] args) throws Exception
	{
		File currentFolder = new File(".");

		logger.debug("Executing from folder: " + currentFolder.getCanonicalPath());

		if(args.length != 2)
		{
			System.err.println("Invalid number of arguments specified");
			System.err.println("Syntax: java " + ApplicationTestAutomation.class.getName() + " <app-config-file> <report-folder>");
			System.exit(-1);
		}

		File appConfigurationFile = new File(args[0]);
		File reportFolder = new File(args[1]);

		if(!appConfigurationFile.exists())
		{
			System.err.println("Invalid application configuration file - " + appConfigurationFile);
			System.exit(-1);
		}

		// force delete report folder, if any
		if(reportFolder.exists())
		{
			FileUtils.forceDelete(reportFolder);
			reportFolder.mkdirs();
		}

		// load the configuration file
		IApplicationConfiguration appConfig = loadApplicationConfiguration(appConfigurationFile);

		// load state configuration
		StateConfiguration stateConfiuration = loadStateConfiguration(appConfig);

		// load test suites
		Map<String, TestSuite> testSuites = loadTestSuites(appConfig);

		// load Web drivers
		WebDriver drivers[] = loadDrivers(appConfig);

		// execute test suites
		AutomationContext context = null;

		for(WebDriver driver : drivers)
		{
			logger.debug("Executing test suites with driver: {}", driver);

			context = new AutomationContext(driver, stateConfiuration, appConfig);
			executeTestSuites(context, testSuites, reportFolder);
		}
	}
}