package mamer.eg.service.impl;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.print.attribute.standard.RequestingUserName;

import mamer.eg.messages.request.CampaignOptimizerRequest;
import mamer.eg.messages.request.CampaignOptimizerRequest.CustomerCampaignInformation;
import mamer.eg.messages.response.CampaignOptimizerResponse;
import mamer.eg.messages.response.CampaignOptimizerResponse.CampaignSalesQuotas;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class BasicCampaignOptmizerTest {
	Logger logger = LoggerFactory.getLogger(getClass()); 
	CampaignOptimizer optmizer;
	CampaignOptimizerRequest request;
	CampaignOptimizerResponse response;
	
	public BasicCampaignOptmizerTest(CampaignOptimizerRequest request, CampaignOptimizerResponse response) {
		this.request=request;
		this.response=response;
	}
	
	@Parameters(name = "test case {index})")
	public static Collection<Object[]> testData(){
		CampaignOptimizerRequest.CustomerCampaignInformation customerCampaignInformation1 = new CampaignOptimizerRequest.CustomerCampaignInformation();
		customerCampaignInformation1.setMonthlyAdInventory(32356000);
		CampaignOptimizerRequest.CustomerCampaignInformation customerCampaignInformation2 = new CampaignOptimizerRequest.CustomerCampaignInformation();
		customerCampaignInformation2.setMonthlyAdInventory(50000000);
		CampaignOptimizerRequest.CustomerCampaignInformation customerCampaignInformation3 = new CampaignOptimizerRequest.CustomerCampaignInformation();
		customerCampaignInformation3.setMonthlyAdInventory(2000000000);
		return Arrays.asList(new Object[][]{
				{
					new CampaignOptimizerRequest(Arrays.asList(new CampaignOptimizerRequest.CustomerCampaignInformation[]{
							customerCampaignInformation1,
							new CampaignOptimizerRequest.CustomerCampaignInformation("Acme",2000000,200),
							new CampaignOptimizerRequest.CustomerCampaignInformation("Lorem",3500000,400),
							new CampaignOptimizerRequest.CustomerCampaignInformation("Ipsum",2300000,210),
							 new CampaignOptimizerRequest.CustomerCampaignInformation("Dolor",8000000,730),
							 new CampaignOptimizerRequest.CustomerCampaignInformation("SIT",10000000,1000),
							 new CampaignOptimizerRequest.CustomerCampaignInformation("Amet",1500000,160),
							 new CampaignOptimizerRequest.CustomerCampaignInformation("Mauris",1000000,100)})),
					new CampaignOptimizerResponse(32000000,3620,Arrays.asList(new CampaignSalesQuotas[]{ 
							new CampaignSalesQuotas("Acme", 0, 0, 0),
							new CampaignSalesQuotas("Lorem", 8,28000000,3200),
							new CampaignSalesQuotas("Ipsum", 0, 0, 0),
							new CampaignSalesQuotas("Dolor", 0, 0, 0),
							new CampaignSalesQuotas("SIT", 0, 0, 0),
							new CampaignSalesQuotas("Amet", 2, 3000000, 320),
							new CampaignSalesQuotas("Mauris", 1, 1000000, 100),
							}))
				},
				{
					new CampaignOptimizerRequest(Arrays.asList(new CampaignOptimizerRequest.CustomerCampaignInformation[]{
							customerCampaignInformation2,
							new CampaignOptimizerRequest.CustomerCampaignInformation("Acme",1,0),
							new CampaignOptimizerRequest.CustomerCampaignInformation("Lorem",2,2),
							new CampaignOptimizerRequest.CustomerCampaignInformation("Ipsum",3,2),
							 new CampaignOptimizerRequest.CustomerCampaignInformation("Dolor",70000,71000),
							 new CampaignOptimizerRequest.CustomerCampaignInformation("Mauris",49000000,50000000)})),
					new CampaignOptimizerResponse(50000000,51014000,Arrays.asList(new CampaignSalesQuotas[]{ 
							new CampaignSalesQuotas("Acme", 0, 0, 0),
							new CampaignSalesQuotas("Lorem", 10000, 20000, 20000),
							new CampaignSalesQuotas("Ipsum", 0, 0, 0),
							new CampaignSalesQuotas("Dolor", 14, 980000, 994000),
							new CampaignSalesQuotas("Mauris", 1,49000000,50000000),
							}))
				},
				{
					new CampaignOptimizerRequest(Arrays.asList(new CampaignOptimizerRequest.CustomerCampaignInformation[]{
							customerCampaignInformation3,
							new CampaignOptimizerRequest.CustomerCampaignInformation("Acme", 1000000, 5000),
							new CampaignOptimizerRequest.CustomerCampaignInformation("Lorem",2000000,9000),
							new CampaignOptimizerRequest.CustomerCampaignInformation("Ipsum",3000000,20000)})),
							new CampaignOptimizerResponse(2000000000,13330000,Arrays.asList(new CampaignSalesQuotas[]{ 
									new CampaignSalesQuotas("Acme", 2,2000000,10000),
									new CampaignSalesQuotas("Lorem", 0,0,0),
									new CampaignSalesQuotas("Ipsum",666,1998000000,13320000)
							}))
				},
		});
	}
	@Before
	public void setUp() throws Exception {
		optmizer = new BasicCampaignOptmizer();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCampaignOptimizerInput() {
	
		CampaignOptimizerResponse returnedResp = optmizer.optimize(request);
		List<CampaignSalesQuotas> campaignSalesQuotas = returnedResp.getCampaignSalesQuotas();
		List<CampaignSalesQuotas> testResponseItems = response.getCampaignSalesQuotas();
		assertNotNull(returnedResp);
		assertThat(returnedResp.getTotalNoImp(),  is(response.getTotalNoImp()));
		assertThat(returnedResp.getTotalRevenue(),  is(response.getTotalRevenue()));
		assertNotNull(campaignSalesQuotas);
		assertThat(campaignSalesQuotas.size(), is(testResponseItems.size()));
		
		for (int i =0; i< campaignSalesQuotas.size();i++) {
			assertEquals(campaignSalesQuotas.get(i),testResponseItems.get(i));
		}
		
	
	}


}
