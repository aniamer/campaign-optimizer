package mamer.eg.service.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import mamer.eg.messages.request.CampaignOptimizerRequest;
import mamer.eg.messages.request.CampaignOptimizerRequest.CustomerCampaignInformation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAlgorithmUtilities {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		CampaignOptimizerRequest.CustomerCampaignInformation customerCampaignInformation = new CampaignOptimizerRequest.CustomerCampaignInformation();
		customerCampaignInformation.setMonthlyAdInventory(32356000L);
		List<CustomerCampaignInformation> list = new ArrayList<CustomerCampaignInformation>();
		list.add(customerCampaignInformation);
		list.add(new CampaignOptimizerRequest.CustomerCampaignInformation("Lorem",3500000,400));
		list.add(new CampaignOptimizerRequest.CustomerCampaignInformation("Dolor",8000000,730));
		list.add(new CampaignOptimizerRequest.CustomerCampaignInformation("SIT",10000000,1000));
		list.add(new CampaignOptimizerRequest.CustomerCampaignInformation("Amet",1500000,160));
		list.add(new CampaignOptimizerRequest.CustomerCampaignInformation("Mauris",1000000,100));
		
		HashMap<Integer, CustomerCampaignInformation> findOptimum = BasicCampaignOptmizer.findOptimum(list);
		for (Integer wieght : findOptimum.keySet()) {
			System.out.println(wieght+"----"+ToStringBuilder.reflectionToString(findOptimum.get(wieght)));
		}
	}

}
