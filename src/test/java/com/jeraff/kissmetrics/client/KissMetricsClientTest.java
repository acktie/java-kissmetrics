package com.jeraff.kissmetrics.client;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalURLFetchServiceTestConfig;

public class KissMetricsClientTest {
    private static KissMetricsClient client;
    private static LocalServiceTestHelper helper; 

    

    @BeforeClass
    public static void setupClient() {
    	helper = new LocalServiceTestHelper(new LocalURLFetchServiceTestConfig());
        client = new KissMetricsClient(System.getProperty("KISS_API"), "arinTesting");
    }

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testRecord() throws KissMetricsException {
        KissMetricsResponse resp = client.record("loggedin").getResponse();
        Assert.assertEquals(200, resp.getStatus());

        resp = client.record("purchased", new KissMetricsProperties().put("sku", "abcedfg"))
                     .getResponse();
        Assert.assertEquals(200, resp.getStatus());
    }

    @Test
    public void testAlias() throws KissMetricsException {
        KissMetricsResponse resp = client.alias("arin@jeraff.com").getResponse();
        Assert.assertEquals(200, resp.getStatus());
    }

    @Test
    public void testSet() throws KissMetricsException {
        KissMetricsResponse resp = client.set(new KissMetricsProperties().put("age", 33)).getResponse();
        Assert.assertEquals(200, resp.getStatus());
    }

    @Test
    public void testURLEncoding() throws KissMetricsException {
        KissMetricsResponse resp = client
                .set(new KissMetricsProperties().put("encodeDeez", "This is cool")).getResponse();
        Assert.assertEquals(200, resp.getStatus());

        resp = client.set(new KissMetricsProperties().put("2 things", "This & that")).getResponse();
        Assert.assertEquals(200, resp.getStatus());

        resp = client.set(new KissMetricsProperties().put("some symbols?", "? question & and % percent"))
                     .getResponse();

        Assert.assertEquals(200, resp.getStatus());
    }

    @Test
    public void testInternationalCharacters() throws KissMetricsException {
        String chineseString = "??/??";

        KissMetricsResponse resp = client
                .set(new KissMetricsProperties().put("intlChars", chineseString)).getResponse();
        Assert.assertEquals(200, resp.getStatus());
    }
}
