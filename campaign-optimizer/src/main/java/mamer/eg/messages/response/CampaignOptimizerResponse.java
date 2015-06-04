package mamer.eg.messages.response;

import java.util.ArrayList;
import java.util.List;

public class CampaignOptimizerResponse {
	private Integer totalNoImp;
	private Integer totalRevenue;
	private List<CampaignSalesQuotas> campaignSalesQuotas;
	
	public CampaignOptimizerResponse(Integer totalNoImp, Integer totalRevenue) {
		super();
		this.totalNoImp = totalNoImp;
		this.totalRevenue = totalRevenue;
		this.campaignSalesQuotas = new ArrayList<CampaignSalesQuotas>();
	}
	
	public CampaignOptimizerResponse(Integer totalNoImp, Integer totalRevenue, List<CampaignSalesQuotas> campaignSalesQuotas) {
		this.totalNoImp = totalNoImp;
		this.totalRevenue = totalRevenue;
		this.campaignSalesQuotas = campaignSalesQuotas;
	}

	public Integer getTotalNoImp() {
		return totalNoImp;
	}

	public Integer getTotalRevenue() {
		return totalRevenue;
	}

	public List<CampaignSalesQuotas> getCampaignSalesQuotas() {
		return campaignSalesQuotas;
	}
	
	public void addCampaignSalesQuota(CampaignSalesQuotas info) {
		this.campaignSalesQuotas.add(info);
	}
	
	public static class CampaignSalesQuotas{
		private String customer;
		private Integer noCampaigns;
		private Integer totalImpressions;
		private Integer customerRevenue;
		
		public CampaignSalesQuotas(String customer, Integer noCampaigns,
				Integer totalImpressions, Integer customerRevenue) {
			super();
			this.customer = customer;
			this.noCampaigns = noCampaigns;
			this.totalImpressions = totalImpressions;
			this.customerRevenue = customerRevenue;
		}

		public String getCustomer() {
			return customer;
		}

		public Integer getNoCampaigns() {
			return noCampaigns;
		}

		public Integer getTotalImpressions() {
			return totalImpressions;
		}

		public Integer getCustomerRevenue() {
			return customerRevenue;
		}

		@Override
		public boolean equals(Object obj) {
			CampaignSalesQuotas toCompare=(CampaignSalesQuotas)obj;
			return this.totalImpressions.equals(toCompare.getTotalImpressions()) &&
			this.customer.equals(toCompare.getCustomer())&&
			this.customerRevenue.equals(toCompare.getCustomerRevenue())&&
			this.noCampaigns.equals(toCompare.noCampaigns);
		}
	}
}
