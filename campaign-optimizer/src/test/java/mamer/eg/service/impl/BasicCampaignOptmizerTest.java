package mamer.eg.service.impl;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.print.attribute.standard.RequestingUserName;

import mamer.eg.messages.request.CampaignOptimizerRequest;
import mamer.eg.messages.response.CampaignOptimizerResponse;
import mamer.eg.messages.response.CampaignOptimizerResponse.CampaignSalesQuotas;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BasicCampaignOptmizerTest {
	CampaignOptimizer optmizer;
	CampaignOptimizerRequest request;
	CampaignOptimizerResponse response;
	
	public BasicCampaignOptmizerTest(CampaignOptimizerRequest request, CampaignOptimizerResponse response) {
		this.request=request;
		this.response=response;
	}
	
	@Parameters(name = "test case {index})")
	public static Collection<Object[]> testData(){
		return Arrays.asList(new Object[][]{
				{
					new CampaignOptimizerRequest(Arrays.asList(new CampaignOptimizerRequest.CustomerCampaignInformation[]{new CampaignOptimizerRequest.CustomerCampaignInformation("Ipsum",2300000,210),
							 new CampaignOptimizerRequest.CustomerCampaignInformation("Lorem",3500000,400),
							 new CampaignOptimizerRequest.CustomerCampaignInformation("Dolor",8000000,730),
							 new CampaignOptimizerRequest.CustomerCampaignInformation("SIT",10000000,1000),
							 new CampaignOptimizerRequest.CustomerCampaignInformation("Amet",1500000,160),
							 new CampaignOptimizerRequest.CustomerCampaignInformation("Mauris",1000000,100)})),
					new CampaignOptimizerResponse(32000000L,3620.00,Arrays.asList(new CampaignSalesQuotas[]{ 
							new CampaignSalesQuotas("Acme", 0L, 0L, 0L),
							new CampaignSalesQuotas("Lorem", 8L,28000000L,3200L),
							new CampaignSalesQuotas("Ipsum", 0L, 0L, 0L),
							new CampaignSalesQuotas("Dolor", 0L, 0L, 0L),
							new CampaignSalesQuotas("SIT", 0L, 0L, 0L),
							new CampaignSalesQuotas("Amet", 2L, 3000000L, 320L),
							new CampaignSalesQuotas("Mauris", 1L, 1000000L, 100L),
							}))
				}
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
		
		assertNotNull(returnedResp);
		assertThat(returnedResp.getTotalNoImp(),  is(response.getTotalNoImp()));
		assertThat(returnedResp.getTotalRevenue(),  is(response.getTotalRevenue()));
		assertNotNull(returnedResp.getCampaignSalesQuotas());
		assertThat(returnedResp.getCampaignSalesQuotas().size(), is(response.getCampaignSalesQuotas().size()));
		assertEquals(returnedResp.getCampaignSalesQuotas(),response.getCampaignSalesQuotas());
	
	}

}
