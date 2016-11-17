package mamer.eg.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mamer.eg.messages.request.CampaignOptimizerRequest;
import mamer.eg.messages.request.CampaignOptimizerRequest.CustomerCampaignInformation;
import mamer.eg.messages.response.CampaignOptimizerResponse;
import mamer.eg.messages.response.CampaignOptimizerResponse.CampaignSalesQuotas;

public class BasicCampaignOptmizer implements CampaignOptimizer {

	public BasicCampaignOptmizer() {
	}
	
	@Override
	public CampaignOptimizerResponse optimize(CampaignOptimizerRequest request) {
		List<CustomerCampaignInformation> customerCampaignInfos = request
				.getcustomerCampaignInfo();
		
		int[] customerCombinations = chunckedFindOptimum(customerCampaignInfos);

		List<CampaignOptimizerResponse.CampaignSalesQuotas> campSalesQuotas = new ArrayList<CampaignOptimizerResponse.CampaignSalesQuotas>();
		int totalNoImpressions = 0;
		int totalRevenue = 0;
		for (int i = 1; i < customerCombinations.length; i++) {
			CustomerCampaignInformation next = customerCampaignInfos.get(i);
			int totalNoCamp = customerCombinations[i];
			int totalCustImpressions = next.getImpPerCampaign() * totalNoCamp;
			int customerRevenue = next.getPricePerCampaign() * totalNoCamp;
			campSalesQuotas.add(new CampaignSalesQuotas(next.getCustomer(),
					totalNoCamp, totalCustImpressions, customerRevenue));
			totalNoImpressions += totalCustImpressions;
			totalRevenue += customerRevenue;
		}

		return new CampaignOptimizerResponse(totalNoImpressions, totalRevenue,
				campSalesQuotas);
	}

	int[] chunckedFindOptimum(
			List<CustomerCampaignInformation> customerCampaignInfos) {
		Integer monthlyAdInventory = customerCampaignInfos.get(0)
				.getMonthlyAdInventory();
		int INDEX_STEP =1;
		if(monthlyAdInventory > Integer.MAX_VALUE/2){
			BigInteger gcd = BigInteger.valueOf(monthlyAdInventory);
			
			for (int i=1 ; i< customerCampaignInfos.size();i++) {
				Integer impPerCampaign = customerCampaignInfos.get(i).getImpPerCampaign();
				gcd = gcd.gcd(BigInteger.valueOf(impPerCampaign));
			}
			INDEX_STEP = gcd.intValue();
		}
		
			
		
		Integer scaledMinv = monthlyAdInventory/INDEX_STEP;
		int[] maxValuePerSize = new int[scaledMinv + 1];
		int[] whichCustomer = new int[scaledMinv + 1];
		int[] customerCombination = new int[customerCampaignInfos.size()];

		for (int i = 0; i < whichCustomer.length; i++) {
			whichCustomer[i] = -1;
		}
		for (int i = 0; i < maxValuePerSize.length; i++) {
			maxValuePerSize[i] = 0;
		}
		
		
		
		for (int n = 0; n < customerCampaignInfos.size(); n++) {
			CustomerCampaignInformation next = customerCampaignInfos.get(n);
			int weight = (next.getImpPerCampaign() != null) ? (next
					.getImpPerCampaign()/INDEX_STEP) : 0;
			Integer value = next.getPricePerCampaign();
			for (int i = 0; i < scaledMinv + 1; i++) {
					
		
					if (weight != 0
							&& weight <= i
							&& (value + maxValuePerSize[i - weight] > maxValuePerSize[i])) {
						maxValuePerSize[i] = value + maxValuePerSize[i - weight];
						whichCustomer[i] = n;
					}
		
				}
			}
		
		int inventoryLeft = whichCustomer.length - 1;
		int suitableCamp = whichCustomer[inventoryLeft];
		int[] custCombMap = new int[customerCampaignInfos.size()];
		while (suitableCamp != -1 && inventoryLeft > 0) {
			customerCombination[suitableCamp]++;
			CustomerCampaignInformation next = customerCampaignInfos
					.get(suitableCamp);
			int weight = next.getImpPerCampaign()/INDEX_STEP;
			custCombMap[suitableCamp] = customerCombination[suitableCamp];
			inventoryLeft = inventoryLeft - weight;
			suitableCamp = whichCustomer[inventoryLeft];
		}

		return custCombMap;
	}
	int[] findOptimum(
			List<CustomerCampaignInformation> customerCampaignInfos) {
		Integer monthlyAdInventory = customerCampaignInfos.get(0)
				.getMonthlyAdInventory();
		Integer scaledMinv = monthlyAdInventory;
		int[] maxValuePerSize = new int[scaledMinv + 1];
		int[] whichCustomer = new int[scaledMinv + 1];
		int[] customerCombination = new int[customerCampaignInfos.size()];

		for (int i = 0; i < whichCustomer.length; i++) {
			whichCustomer[i] = -1;
		}
		for (int i = 0; i < maxValuePerSize.length; i++) {
			maxValuePerSize[i] = 0;
		}
		
		for (int i = 0; i < scaledMinv + 1; i++) {
				for (int n = 0; n < customerCampaignInfos.size(); n++) {
					CustomerCampaignInformation next = customerCampaignInfos.get(n);
					int weight = (next.getImpPerCampaign() != null) ? (next
							.getImpPerCampaign()) : 0;
					Integer value = next.getPricePerCampaign();
		
					if (weight != 0
							&& weight <= i
							&& (value + maxValuePerSize[i - weight] > maxValuePerSize[i])) {
						maxValuePerSize[i] = value + maxValuePerSize[i - weight];
						whichCustomer[i] = n;
					}
		
				}
			}
		
		int inventoryLeft = whichCustomer.length - 1;
		int suitableCamp = whichCustomer[inventoryLeft];
		int[] custCombMap = new int[customerCampaignInfos.size()];
		while (suitableCamp != -1 && inventoryLeft > 0) {
			customerCombination[suitableCamp]++;
			CustomerCampaignInformation next = customerCampaignInfos
					.get(suitableCamp);
			int weight = next.getImpPerCampaign();
			custCombMap[suitableCamp] = customerCombination[suitableCamp];
			inventoryLeft = inventoryLeft - weight;
			suitableCamp = whichCustomer[inventoryLeft];
		}

		return custCombMap;
	}

}
