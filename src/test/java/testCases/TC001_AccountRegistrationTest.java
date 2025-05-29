package testCases;



import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{

	@Test(groups={"Regression","Master"})
	public void verify_account_registration()

	{
		try {
			
		logger.info("*** Starting TC001_AccountRegistrationTest ***");
		HomePage hp = new HomePage(driver);

		logger.info("*** Click on My Account link ***");
		hp.clickMyAccount();
		logger.info("*** Click on Register link ***");
		hp.clickRegister();


		AccountRegistrationPage accReg = new AccountRegistrationPage(driver);
		
		logger.info("*** Providing Customer details ***");
		accReg.setFirstName("john");
		accReg.setLastName("assel");
		accReg.setEmail(randomString()+ "@gmail.com");
		accReg.setTelephone(randomNumber());
		
		String password = randomAlphaNumeric();
		accReg.setPassword(password);
		accReg.setConfirmPassword(password);
		
		accReg.setPrivacyPolicy();
		accReg.clickContinue();

		logger.info("*** Validating expected message ***");
		String confmsg =accReg.getConfirmationMsg();
		
		if (confmsg.equals("Your Account Has Been Created!"))
		 {
			logger.info("Test Passed");
			Assert.assertTrue(true);
		 }
		else
		{
			logger.error("Test Failed");
			Assert.assertTrue(false);
		}
		}
		catch(Exception e)
		{
			
			logger.error("Exception during login test: ", e);

		}
		
		
		finally
		{
		logger.info("**** finished TC001_AccountRegistrationTest *** ");
		}

	}

}
