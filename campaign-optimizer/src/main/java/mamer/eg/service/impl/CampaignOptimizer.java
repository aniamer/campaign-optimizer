package mamer.eg.service.impl;

import mamer.eg.messages.request.CampaignOptimizerRequest;
import mamer.eg.messages.response.CampaignOptimizerResponse;

public interface CampaignOptimizer {
	public CampaignOptimizerResponse optimize(CampaignOptimizerRequest request);
}
