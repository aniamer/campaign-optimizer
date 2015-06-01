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
	
	@Parameters
	public static Collection<Object[]> testData(){
		return Arrays.asList(new Object[][]{
					
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
		CampaignOptimizerRequest request = new CampaignOptimizerRequest(Arrays.asList(new adf[]{{new CampaignOptimizerRequest.CustomerCampaignInformation("Ipsum",2300000L,210.00),
			 new CampaignOptimizerRequest.CustomerCampaignInformation("Lorem",3500000L,400.00),
			 new CampaignOptimizerRequest.CustomerCampaignInformation("Dolor",8000000L,730.00),
			 new CampaignOptimizerRequest.CustomerCampaignInformation("SIT,1"0000000L,1000.00),
			 new CampaignOptimizerRequest.CustomerCampaignInformation("Amet,"1500000L,160.00),
			 new CampaignOptimizerRequest.CustomerCampaignInformation("Mauri"s,1000000L,100.00)}));
		 
		
		
		
		CampaignOptimizerResponse returnedResp = optmizer.optimize(request);
		
		assertNotNull(returnedResp);
		assertThat(returnedResp.getTotalNoImp(),  is(response.getTotalNoImp()));
		assertThat(returnedResp.getTotalRevenue(),  is(response.getTotalRevenue()));
		assertNotNull(returnedResp.getCampaignSalesQuotas());
		assertThat(returnedResp.getCampaignSalesQuotas().size(), is(response.getCampaignSalesQuotas().size()));
		assertEquals(returnedResp.getCampaignSalesQuotas(),response.getCampaignSalesQuotas());
	
	}

}
