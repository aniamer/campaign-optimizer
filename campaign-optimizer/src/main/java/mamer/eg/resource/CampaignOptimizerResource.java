package mamer.eg.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import mamer.eg.messages.provider.CsvMappingProvider;
import mamer.eg.messages.request.CampaignOptimizerRequest;
import mamer.eg.messages.response.CampaignOptimizerResponse;
import mamer.eg.service.impl.BasicCampaignOptmizer;
import mamer.eg.service.impl.CampaignOptimizer;

@Path("/campaignOptimizer")
@Produces({MediaType.APPLICATION_JSON,CsvMappingProvider.CSV})
@Consumes({MediaType.APPLICATION_JSON,CsvMappingProvider.CSV})

public class CampaignOptimizerResource {
	private CampaignOptimizer optimizer;
	public CampaignOptimizerResource() {
		optimizer = new BasicCampaignOptmizer();
	}
	
	@POST
    @Path("/")
	public CampaignOptimizerResponse process(CampaignOptimizerRequest request){
		return optimizer.optimize(request);
	}
}
