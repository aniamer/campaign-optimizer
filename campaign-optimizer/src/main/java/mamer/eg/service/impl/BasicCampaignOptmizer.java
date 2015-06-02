package mamer.eg.service.impl;

import java.util.HashMap;
import java.util.List;

import mamer.eg.messages.request.CampaignOptimizerRequest;
import mamer.eg.messages.request.CampaignOptimizerRequest.CustomerCampaignInformation;
import mamer.eg.messages.response.CampaignOptimizerResponse;

public class BasicCampaignOptmizer implements CampaignOptimizer {

	@Override
	public CampaignOptimizerResponse optimize(CampaignOptimizerRequest request) {
		List<CustomerCampaignInformation> customerCampaignInfos = request.getcustomerCampaignInfo();
		
		return null;
	}
	static HashMap<CustomerCampaignInformation, Integer> findOptimum(List<CustomerCampaignInformation> customerCampaignInfos ){
		Integer monthlyAdInventory = customerCampaignInfos.get(0).getMonthlyAdInventory();
		final Integer scaledMinv = monthlyAdInventory/1000000;
		int[][] itemWeightValue = new int[customerCampaignInfos.size()][scaledMinv];
		boolean[][]  customerTook = new boolean[customerCampaignInfos.size()][scaledMinv];
		HashMap<CustomerCampaignInformation, Integer> cutomerNoCamps = new HashMap<CustomerCampaignInformation, Integer>();
		for (int n=1;n< customerCampaignInfos.size(); n++)  {
			CustomerCampaignInformation next = customerCampaignInfos.get(n);			
			for(int i =1; i< scaledMinv;i++){
				int prevVal=itemWeightValue[n-1][i]; 
				int nextVal= Integer.MIN_VALUE; 
				int weight = (next.getImpPerCampaign()!= null) ? (next.getImpPerCampaign()/1000000):0;
				if(weight != 0 && weight <= i){
					nextVal=next.getPricePerCampaign() +itemWeightValue[n-1][i-weight];
				}
				itemWeightValue[n][i]= Math.max(prevVal, nextVal);
				
				customerTook[n][i]= (nextVal > prevVal);
			}
			
			
			
	
		}
		int itemNo = 0;		
		long sumProfit=0;
		for(int n=customerCampaignInfos.size()-1, w = scaledMinv-1; n>0 ;--n){

			if(customerTook[n][w]){
				w=w-(customerCampaignInfos.get(n).getImpPerCampaign()/1000000);
				
				sumProfit+=(customerCampaignInfos.get(n).getPricePerCampaign());
				cutomerNoCamps.put(customerCampaignInfos.get(n),itemNo);
			}
			
		}
		System.out.println("sumProfit = "+sumProfit);
		
		return cutomerNoCamps;
	}

}

	
