package mamer.eg.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RecursiveTask;

import mamer.eg.messages.request.CampaignOptimizerRequest.CustomerCampaignInformation;

public class MaxValueFinder extends RecursiveTask<List<MaxValPerSizeChunk>> {

	Integer weightLength;
	Integer startFrom;
	List<CustomerCampaignInformation> cCampInfo;
	MaxValPerSizeChunk[] maxValuePerSize; 

	public MaxValueFinder(Integer weightLength, List<CustomerCampaignInformation> cCampInfo, Integer startFrom) {
		this.weightLength = weightLength;
		this.cCampInfo = cCampInfo;
		this.startFrom=startFrom;
		
	}

	protected List<MaxValPerSizeChunk> computeDirectly() {
		MaxValPerSizeChunk maxValPerSizeChunk = new MaxValPerSizeChunk(0,0);
		maxValuePerSize[0]=maxValPerSizeChunk;
		List<MaxValPerSizeChunk> results = new LinkedList<MaxValPerSizeChunk>();
		for (int i = 0; i < weightLength ; i++) {
			for (int n = 0; n < cCampInfo.size(); n++) {
				CustomerCampaignInformation next = cCampInfo.get(n);
				int weight = (next.getImpPerCampaign() != null) ? (next
						.getImpPerCampaign()) : 0;
				Integer value = next.getPricePerCampaign();
				
				if (weight != 0
						&& weight <= i) {
					Integer prevMaxVal = maxValuePerSize[i-weight] == null ? 0 :maxValuePerSize[i - weight].maxVal;
					Integer curMaxVal = maxValuePerSize[i] == null ?0 :maxValuePerSize[i].maxVal;
						if((value + prevMaxVal > curMaxVal)){
							maxValPerSizeChunk.maxVal = value+prevMaxVal;
							maxValPerSizeChunk.whichCustomer = n;
							maxValuePerSize[i] = maxValPerSizeChunk;
							results.add(maxValPerSizeChunk);
						}
					}
				}
	
			}	
		maxValuePerSize=null;
		return results;
		}
	
	
	
	@Override
	protected List<MaxValPerSizeChunk> compute() {
		List<MaxValPerSizeChunk> copiedList=new ArrayList<MaxValPerSizeChunk>();
		if(weightLength >30000000){
				ArrayList<MaxValueFinder> list = new ArrayList<MaxValueFinder>(); 
				int chunkLength = weightLength/2;
				for(int i = 0 ; i< weightLength ; i+=chunkLength) {
					MaxValueFinder task = new MaxValueFinder(chunkLength, cCampInfo,i);
					task.fork();
					MaxValueFinder task2 = new MaxValueFinder(chunkLength, cCampInfo,i);
					copiedList.addAll(task2.compute());
					copiedList.addAll(task.join());
					
				}
			return copiedList;
		}else{
			if(maxValuePerSize == null)
				maxValuePerSize = new MaxValPerSizeChunk[weightLength];
			else
				maxValuePerSize=Arrays.copyOf(maxValuePerSize, weightLength);
			return computeDirectly();
		}
			
		
	}

}
