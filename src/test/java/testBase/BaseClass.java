package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager; //log4j2
import org.apache.logging.log4j.Logger; //log4j2
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass {

	public	static WebDriver driver;
	public Logger logger;
	public Properties p;

	@BeforeClass (groups= {"Sanity","Regression","Master","DataDriven"})
	@Parameters({"os","browser"})
	public void setup(String os , String br) throws IOException
	{
		//Set up properties file 
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);

		//Set up logger
		logger = LogManager.getLogger(this.getClass());


		//Remote execution
		if (p.getProperty("execution_env").equalsIgnoreCase("remote")) 
		{
			MutableCapabilities options;

			// Set browser options based on the selected browser
			switch (br.toLowerCase()) {
			case "chrome":
				ChromeOptions chromeOptions = new ChromeOptions();
				options = chromeOptions;
				break;
			case "firefox":
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				options = firefoxOptions;
				break;
			case "edge":
				EdgeOptions edgeOptions = new EdgeOptions();
				options = edgeOptions;
				break;
			default:
				System.out.println("No matching browser");
				return;
			}

			// Set the platformName (OS) as a capability
			switch (os.toLowerCase()) {
			case "windows":
				options.setCapability("platformName", "Windows");
				break;
			case "linux":
				options.setCapability("platformName", "Linux");
				break;
			case "mac":
				options.setCapability("platformName", "macOS");
				break;
			default:
				System.out.println("No matching OS");
				return;
			}
			// Create RemoteWebDriver instance
			
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
			 }

			if(p.getProperty("execution_env").equalsIgnoreCase("local")) 
			{
				//Set up xml parameters 
				switch(br.toLowerCase())
				{
				case "chrome": driver = new ChromeDriver(); break;
				case "edge": driver = new EdgeDriver(); break;
				case "firefox": driver = new FirefoxDriver(); break;
				default: System.out.println("Invalid browser name"); return;

				}
			}


			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			driver.get(p.getProperty("appURL")); //Reading data from properties file 
			driver.manage().window().maximize();

		}

		@AfterClass (groups= {"Sanity","Regression","Master","DataDriven"})
		public void tearDown()
		{
			driver.quit();
		}

		public String randomString()
		{
			String generatedString =RandomStringUtils.randomAlphabetic(5);
			return generatedString;
		}

		public String randomNumber()
		{
			String generatedNumber =RandomStringUtils.randomNumeric(10);
			return generatedNumber;
		}

		public String randomAlphaNumeric()
		{
			String generatedAlphaNumeric =RandomStringUtils.randomAlphanumeric(10) + "@";
			return generatedAlphaNumeric;
		}

		public String captureScreen(String tname) throws IOException {

			String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

			String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
			File targetFile=new File(targetFilePath);

			sourceFile.renameTo(targetFile);

			return targetFilePath;

		}
	}
