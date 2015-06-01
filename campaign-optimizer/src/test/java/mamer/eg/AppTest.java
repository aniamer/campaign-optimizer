package mamer.eg;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import mamer.eg.messages.response.CampaignOptimizerResponse;
import mamer.eg.messages.response.CampaignOptimizerResponse.CampaignSalesQuotas;

import org.junit.Before;
import org.junit.Test;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class AppTest {
	StringWriter writer;
	ICsvBeanWriter responseWriter;
	String[] responseMapping = { "customer", "noCampaigns", "totalImpressions",
			"customerRevenue" };

	@Before
	public void setUp() {

	}

	@Test
	public void test() throws IOException {
		StringWriter writer = new StringWriter();
		ICsvBeanWriter responseWriter = new CsvBeanWriter(writer,
				CsvPreference.STANDARD_PREFERENCE);
		ArrayList<CampaignOptimizerResponse.CampaignSalesQuotas> list = new ArrayList<CampaignOptimizerResponse.CampaignSalesQuotas>();
		list.add(new CampaignOptimizerResponse.CampaignSalesQuotas("Acme",
				20000L, 2323L, 3252345L));
		for (CampaignSalesQuotas campaignSalesQuota : list) {
			responseWriter.write(campaignSalesQuota, responseMapping);

		}
		writer.write("end \n");
		System.out.println(writer.toString());
	}

}
