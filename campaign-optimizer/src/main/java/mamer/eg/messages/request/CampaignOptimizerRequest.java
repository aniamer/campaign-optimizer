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
		private Long monthlyAdInventory;
		private String customer;
		private Long impPerCampaign;
		private Double pricePerCampaign;
		public CustomerCampaignInformation(){}
		public CustomerCampaignInformation(String customer,
				Long impPerCampaign, Double pricePerCampaign) {
			this.customer = customer;
			this.impPerCampaign = impPerCampaign;
			this.pricePerCampaign = pricePerCampaign;
		}

		public void setMonthlyAdInventory(Long monthlyAdInventory) {
			this.monthlyAdInventory = monthlyAdInventory;
		}
		
		public void setCustomer(String customer) {
			this.customer = customer;
		}

		public void setImpPerCampaign(Long impPerCampaign) {
			this.impPerCampaign = impPerCampaign;
		}

		public void setPricePerCampaign(Double pricePerCampaign) {
			this.pricePerCampaign = pricePerCampaign;
		}
		
		public String getCustomer() {
			return customer;
		}

		public Long getImpPerCampaign() {
			return impPerCampaign;
		}

		public Double getPricePerCampaign() {
			return pricePerCampaign;
		}
		public Long getMonthlyAdInventory() {
			return monthlyAdInventory;
		}
	}
	
}

