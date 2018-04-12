package com.mcgraw.test.automation.tests.lti2;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.mcgraw.test.automation.framework.core.testng.Assert;
import com.mcgraw.test.automation.tests.lti2.Lti2TestUtil.validateTcprofileResponse;

public class Lti2BasicTest {
	Lti2TestUtil testUtil = new Lti2TestUtil();

	@Test(description = "Testing set #00")
	public void testSet00() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(0);
		assertTrue(valTcPro.result&& valTcPro.statusCode == 302,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #01")
	public void testSet01() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(1);
		Assert.verifyTrue(valTcPro.result && valTcPro.statusCode == 302,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #02")
	public void testSet02() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(2);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #03")
	public void testSet03() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(3);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #04")
	public void testSet04() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(4);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #05")
	public void testSet05() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(5);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #06")
	public void testSet06() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(6);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #07")
	public void testSet07() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(7);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #08")
	public void testSet08() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(8);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #09")
	public void testSet09() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(9);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #10")
	public void testSet10() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(10);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #11")
	public void testSet11() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(11);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #12")
	public void testSet12() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(12);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #13")
	public void testSet13() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(13);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #14")
	public void testSet14() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(14);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #15")
	public void testSet15() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(15);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #16")
	public void testSet16() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(16);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #17")
	public void testSet17() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(17);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #18")
	public void testSet18() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(18);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #19")
	public void testSet19() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(19);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #20")
	public void testSet20() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(20);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #21")
	public void testSet21() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(21);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #22")
	public void testSet22() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(22);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #23")
	public void testSet23() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(23);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #24")
	public void testSet24() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(24);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #25")
	public void testSet25() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(25);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #26")
	public void testSet26() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(26);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #27")
	public void testSet27() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(27);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #28")
	public void testSet28() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(28);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #29")
	public void testSet29() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(29);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	@Test(description = "Testing set #30")
	public void testSet30() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(30);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==302, "The actual status code was " + valTcPro.statusCode);
	}

	// TODO: Adjust for positve test. This test send all the required and thus we get a positive result.
	@Test(description = "Testing set #31")
	public void testSet31() throws Exception {
		validateTcprofileResponse valTcPro = testUtil.validateTcProfile(31);
		Assert.verifyTrue(valTcPro.result,
				"Expected missing capabilities: "
						+ valTcPro.expectedMissingCapabilities.toString()
						+ "Given capabilities: " + valTcPro.capabilities.toString());
		Assert.verifyTrue(valTcPro.statusCode==200, "The actual status code was " + valTcPro.statusCode);
	}



}
