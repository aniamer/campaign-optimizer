package mamer.eg.messages.request;

import java.util.ArrayList;
import java.util.List;

public class CampaignOptimizerRequest {
	private List<CustomerCampaignInformation> customerCampaignInfos = new ArrayList<>();

	public CampaignOptimizerRequest() {
		super();
		this.customerCampaignInfos  = new ArrayList<>();
	}
	public CampaignOptimizerRequest(List<CustomerCampaignInformation> customerCampaignInfos) {
		this.customerCampaignInfos  = customerCampaignInfos;
	}
	

	
	public List<CustomerCampaignInformation> getcustomerCampaignInfo() {
		return customerCampaignInfos;
	}
	
	public void addCustomerInfo(CustomerCampaignInformation info){
		this.customerCampaignInfos.add(info);
	}

	public static class CustomerCampaignInformation{
		private Integer monthlyAdInventory;
		private String customer;
		private Integer impPerCampaign;
		private Integer pricePerCampaign;
		public CustomerCampaignInformation(){}
		public CustomerCampaignInformation(String customer,
				Integer impPerCampaign, Integer pricePerCampaign) {
			this.customer = customer;
			this.impPerCampaign = impPerCampaign;
			this.pricePerCampaign = pricePerCampaign;
		}

		public void setMonthlyAdInventory(Integer monthlyAdInventory) {
			this.monthlyAdInventory = monthlyAdInventory;
		}
		
		public void setCustomer(String customer) {
			this.customer = customer;
		}

		public void setImpPerCampaign(Integer impPerCampaign) {
			this.impPerCampaign = impPerCampaign;
		}

		public void setPricePerCampaign(Integer pricePerCampaign) {
			this.pricePerCampaign = pricePerCampaign;
		}
		
		public String getCustomer() {
			return customer;
		}

		public Integer getImpPerCampaign() {
			return impPerCampaign;
		}

		public Integer getPricePerCampaign() {
			return pricePerCampaign;
		}
		public Integer getMonthlyAdInventory() {
			return this.monthlyAdInventory;
		}
	}
	
}

