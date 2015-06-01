package mamer.eg.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import mamer.eg.messages.provider.CsvMappingProvider;
import mamer.eg.messages.request.CampaignOptimizerRequest;
import mamer.eg.messages.request.CampaignOptimizerRequest.CustomerCampaignInformation;
import mamer.eg.messages.response.CampaignOptimizerResponse;

@Path("/campaignOptimizer")
@Produces({MediaType.APPLICATION_JSON,CsvMappingProvider.CSV})
@Consumes({MediaType.APPLICATION_JSON,CsvMappingProvider.CSV})

public class CampaignOptimizerResource {
	
	public CampaignOptimizerResource() {
	}
	
	@POST
    @Path("/")
	public CampaignOptimizerResponse process(CampaignOptimizerRequest request){
		List<CustomerCampaignInformation> getcustomerCampaignInfo = request.getcustomerCampaignInfo();
		CampaignOptimizerResponse campaignOptimizerResponse = new CampaignOptimizerResponse(45234523L, 1230.00);
		for (CustomerCampaignInformation customerCampaignInformation : getcustomerCampaignInfo) {
			campaignOptimizerResponse.addCampaignSalesQuota(
					new CampaignOptimizerResponse.CampaignSalesQuotas(customerCampaignInformation.getCustomer(),8L,200000L,10000L));
		}
		 
		return campaignOptimizerResponse;
	}
}
