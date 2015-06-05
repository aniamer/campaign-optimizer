package mamer.eg.service.impl;

import java.util.List;
import java.util.concurrent.Callable;

import mamer.eg.messages.request.CampaignOptimizerRequest.CustomerCampaignInformation;

public class ConcurrentCampaignOptimizer implements Callable<int[]>{
	private List<CustomerCampaignInformation> customerCampaignInfos;
	private int[] valuesForWeight;
	private int[] whichCustomer;
	
	public ConcurrentCampaignOptimizer(int chunkSize, List<CustomerCampaignInformation> customerCampaignInfos) {
		this.valuesForWeight=new int[chunkSize];
		this.whichCustomer=new int[chunkSize];
		this.customerCampaignInfos = customerCampaignInfos;
	}
	
	@Override
	public int[] call() throws Exception {

		for (int i = 0; i < whichCustomer.length; i++) {
			whichCustomer[i] = -1;
		}
		for (int i = 0; i < valuesForWeight.length; i++) {
			valuesForWeight[i] = 0;
		}
		
		for (int i = 0; i < valuesForWeight.length; i++) {
			for (int n = 0; n < customerCampaignInfos.size(); n++) {
				CustomerCampaignInformation next = customerCampaignInfos.get(n);
				int weight = (next.getImpPerCampaign() != null) ? (next
						.getImpPerCampaign()) : 0;
				Integer value = next.getPricePerCampaign();

				if (weight != 0
						&& weight <= i
						&& (value + valuesForWeight[i - weight] > valuesForWeight[i])) {
					valuesForWeight[i] = value + valuesForWeight[i - weight];
					whichCustomer[i] = n;
				}

			}
		}
		
		return whichCustomer;
	}

}
