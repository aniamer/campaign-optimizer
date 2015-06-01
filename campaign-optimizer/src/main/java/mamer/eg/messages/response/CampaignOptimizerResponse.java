package mamer.eg.messages.response;

import java.util.ArrayList;
import java.util.List;

public class CampaignOptimizerResponse {
	private Long totalNoImp;
	private Double totalRevenue;
	private List<CampaignSalesQuotas> campaignSalesQuotas;
	
	public CampaignOptimizerResponse(Long totalNoImp, Double totalRevenue) {
		super();
		this.totalNoImp = totalNoImp;
		this.totalRevenue = totalRevenue;
		this.campaignSalesQuotas = new ArrayList<CampaignSalesQuotas>();
	}
	
	public CampaignOptimizerResponse(Long totalNoImp, Double totalRevenue, List<CampaignSalesQuotas> campaignSalesQuotas) {
		this.totalNoImp = totalNoImp;
		this.totalRevenue = totalRevenue;
		this.campaignSalesQuotas = campaignSalesQuotas;
	}

	public Long getTotalNoImp() {
		return totalNoImp;
	}

	public Double getTotalRevenue() {
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
		private Long noCampaigns;
		private Long totalImpressions;
		private Long customerRevenue;
		
		public CampaignSalesQuotas(String customer, Long noCampaigns,
				Long totalImpressions, Long customerRevenue) {
			super();
			this.customer = customer;
			this.noCampaigns = noCampaigns;
			this.totalImpressions = totalImpressions;
			this.customerRevenue = customerRevenue;
		}

		public String getCustomer() {
			return customer;
		}

		public Long getNoCampaigns() {
			return noCampaigns;
		}

		public Long getTotalImpressions() {
			return totalImpressions;
		}

		public Long getCustomerRevenue() {
			return customerRevenue;
		}
	}
}
