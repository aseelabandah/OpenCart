package testCases;



import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass{

	@Test(groups={"Sanity","Master"})
	public void verifyLogin()

	{

		try
		{
			logger.info("*** Starting TC002_LoginTest ***");
			
			//homepage
			HomePage hp = new HomePage(driver);

			logger.info("*** Click on My Account link ***");
			hp.clickMyAccount();
			logger.info("*** Click on Login link ***");
			hp.clickLogin();

			//Login
			LoginPage lg = new LoginPage(driver);

			logger.info("*** Enter Password and Email ***");
			lg.setEmail(p.getProperty("email"));
			lg.setPassword(p.getProperty("password"));
			lg.clickLogin();

			//My account
			MyAccountPage acc = new MyAccountPage(driver);
			boolean targetPage = acc.isMyAccountExists();
			if (targetPage) 
			{
			    logger.info("Test Passed");
			    Assert.assertTrue(true);
			    acc.clickLogout();
			}
			else
			{
			    logger.error("Test Failed - My Account page did not load after login.");
			    Assert.assertTrue(false, "My Account page did not load after login.");
			}

		}
		catch(Exception e)
		{

			logger.error("Exception during login test: ", e);
		}


		finally
		{
			logger.info("*** Finished TC002_LoginTest ***");
		}
	}




}
