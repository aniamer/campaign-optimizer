package mamer.eg.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import mamer.eg.messages.request.CampaignOptimizerRequest;
import mamer.eg.messages.request.CampaignOptimizerRequest.CustomerCampaignInformation;
import mamer.eg.messages.response.CampaignOptimizerResponse;

public class BasicCampaignOptmizer implements CampaignOptimizer {

	@Override
	public CampaignOptimizerResponse optimize(CampaignOptimizerRequest request) {
		List<CustomerCampaignInformation> customerCampaignInfos = request.getcustomerCampaignInfo();
		
		return null;
	}
	static HashMap<Integer,CustomerCampaignInformation> findOptimum(List<CustomerCampaignInformation> customerCampaignInfos ){
		final Long monthlyAdInventory = customerCampaignInfos.get(0).getMonthlyAdInventory()/1000000;
		LinkedList<LinkedList<Integer>> weightList = new LinkedList<LinkedList<Integer>>();
		LinkedList<Integer> valueList=new LinkedList<Integer>();
		HashMap<Integer,CustomerCampaignInformation> customerTook = new HashMap<Integer,CustomerCampaignInformation>();
		valueList.add(0);
		weightList.add(0,valueList);
		for (int n=1;n<customerCampaignInfos.size(); n++)  {
			valueList.add(0);
			weightList.add(valueList);	
			CustomerCampaignInformation next = customerCampaignInfos.get(n);			
			for(int i =1; i< monthlyAdInventory;i++){
//				CustomerCampaignInformation previous= (CustomerCampaignInformation) iterator
//						.previous();

				
				
				int prevVal=weightList.get(n-1).get(i); 
				int nextVal= Integer.MIN_VALUE; 
						
				if(next.getImpPerCampaign() != null && next.getImpPerCampaign()/1000000 <= i){
					nextVal=(next.getImpPerCampaign()*next.getPricePerCampaign())+weightList.get(n-1).get(i-(next.getImpPerCampaign()/1000000));
				}
				
				valueList.add(Math.max(prevVal, nextVal));
				
				if(nextVal > prevVal)
					customerTook.put(n,next);
			}
			
			
		}
		return customerTook;
	}

}

	
