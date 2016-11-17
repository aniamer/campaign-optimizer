package mamer.eg.service.impl;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import mamer.eg.messages.request.CampaignOptimizerRequest;
import mamer.eg.messages.request.CampaignOptimizerRequest.CustomerCampaignInformation;
import mamer.eg.messages.response.CampaignOptimizerResponse;


public class ConcurrentCampaignOptimizer implements CampaignOptimizer{
	ForkJoinPool pool;

	public MaxValPerSizeChunk findMaxPerWieght(List<CustomerCampaignInformation> ccinfo, Integer weightLength) throws InterruptedException{
		pool = new ForkJoinPool();
		MaxValueFinder maxValueFinder = new MaxValueFinder(weightLength, ccinfo);
		pool.execute(maxValueFinder);
		do
	      {
			TimeUnit.SECONDS.sleep(1l);
	         System.out.printf("******************************************\n");
	         System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
	         System.out.printf("Main: Active Threads: %d\n", pool.getActiveThreadCount());
	         System.out.printf("Main: Task Count: %d\n", pool.getQueuedTaskCount());
	         System.out.printf("Main: Steal Count: %d\n", pool.getStealCount());
	         System.out.printf("******************************************\n");
	         
	      } while (!maxValueFinder.isDone());
		pool.shutdown();
		MaxValPerSizeChunk result = maxValueFinder.join();
		return result;
	}
	@Override
	public CampaignOptimizerResponse optimize(CampaignOptimizerRequest request) {
		
		return null;
	}
}
