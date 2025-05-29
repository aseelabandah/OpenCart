package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass{

	@Test(dataProvider= "LoginData" , dataProviderClass = DataProviders.class , groups="Datadriven") //getting data provider from a different class
	public void verify_loginDDT (String email , String pwd , String exp)
	{
		logger.info("*** Starting TC003_LoginDDT ***");

		try
		{
			//homepage
			HomePage hp = new HomePage(driver);

			logger.info("*** Click on My Account link ***");
			hp.clickMyAccount();
			logger.info("*** Click on Login link ***");
			hp.clickLogin();

			//Login
			LoginPage lg = new LoginPage(driver);

			logger.info("*** Enter Password and Email ***");
			lg.setEmail(email);
			lg.setPassword(pwd);
			lg.clickLogin();

			//My account
			MyAccountPage acc = new MyAccountPage(driver);
			boolean targetPage = acc.isMyAccountExists();

			if(exp.equalsIgnoreCase("valid"))
			{

				if (targetPage==true)
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

			if (exp.equalsIgnoreCase("invalid"))
			{
				if(targetPage==true)
				{
					acc.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(true);
				}
			}
		}
		catch(Exception e)
		{

			logger.error("Exception during login test: ", e);
		}


		finally
		{
			logger.info("*** Finished TC003_LoginDDT***");
		}
	}


}
