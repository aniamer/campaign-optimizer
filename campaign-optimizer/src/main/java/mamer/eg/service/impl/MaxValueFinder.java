package mamer.eg.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.RecursiveTask;

import mamer.eg.messages.request.CampaignOptimizerRequest.CustomerCampaignInformation;

public class MaxValueFinder extends RecursiveTask<MaxValPerSizeChunk>{

	Integer weightLength;
	List<CustomerCampaignInformation> cCampInfo;
	MaxValPerSizeChunk[] maxValuePerSize; 

	public MaxValueFinder(Integer weightLength, List<CustomerCampaignInformation> cCampInfo) {
		this.weightLength = weightLength;
		this.cCampInfo = cCampInfo;
		maxValuePerSize= new MaxValPerSizeChunk[cCampInfo.size()];
	}

	protected MaxValPerSizeChunk computeDirectly() {
		MaxValPerSizeChunk[] valResult= new MaxValPerSizeChunk[cCampInfo.size()];
		MaxValPerSizeChunk finalResult= null;
		
		
		for (int i = 1; i < cCampInfo.size(); i++) {
			CustomerCampaignInformation customerCampaignInformation = cCampInfo
					.get(i);
			Integer weight = customerCampaignInformation.getImpPerCampaign();
			
			if (weight <= weightLength) {
				Integer value = customerCampaignInformation.getPricePerCampaign();
				valResult[i] = new MaxValPerSizeChunk(maxValuePerSize[i].maxVal+value, i);
			}else{
				valResult[i] = new MaxValPerSizeChunk(0, i);
			}
		}
		
		finalResult = valResult[1];
		for (int i = 1; i < cCampInfo.size(); i++) {
			if(valResult[i].maxVal > finalResult.maxVal)
				finalResult = valResult[i];
		}
		
		return finalResult;
		}
	
	
	
	@Override
	protected MaxValPerSizeChunk compute() {
		List<MaxValueFinder> taskList= new LinkedList<MaxValueFinder>();
		for (int i = 1; i < cCampInfo.size(); i++) {
			CustomerCampaignInformation customerCampaignInformation = cCampInfo
					.get(i);
			Integer weight = customerCampaignInformation.getImpPerCampaign();
			int newW = weightLength;
			if (weight <= weightLength) {
				newW -= weight;
				MaxValueFinder task=new MaxValueFinder(newW,
						cCampInfo);
				taskList.add(task);
			}else{
				maxValuePerSize[i] = new MaxValPerSizeChunk(0, i);
			}
		}
		
		boolean flag = false;
		for (ListIterator<MaxValueFinder> iterator = taskList.listIterator(); iterator.hasNext();) {
			
			if(iterator.nextIndex()%2==0){
				MaxValueFinder next = (MaxValueFinder) iterator.next();
				
				if(flag)
					next.join();
				else
					next.fork();
				flag = !flag;
			}else{
				MaxValueFinder first = (MaxValueFinder) iterator.next();
				first.compute();
				
			}
				
				
			
			
		}
//		for (MaxValueFinder maxValueFinder : taskList) {
//			MaxValPerSizeChunk result = maxValueFinder.join();
//			maxValuePerSize[result.whichCustomer]= result;
//		}
		MaxValPerSizeChunk computeDirectly = this.computeDirectly();
		return computeDirectly;
	}

}
