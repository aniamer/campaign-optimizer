package mamer.eg.messages.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import mamer.eg.messages.request.CampaignOptimizerRequest;
import mamer.eg.messages.request.CampaignOptimizerRequest.CustomerCampaignInformation;
import mamer.eg.messages.response.CampaignOptimizerResponse;
import mamer.eg.messages.response.CampaignOptimizerResponse.CampaignSalesQuotas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.constraint.ForbidSubStr;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvConstraintViolationException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

@Provider
@Produces(CsvMappingProvider.CSV)
@Consumes(CsvMappingProvider.CSV)
public class CsvMappingProvider implements
		MessageBodyWriter<CampaignOptimizerResponse>,
		MessageBodyReader<CampaignOptimizerRequest> {
	public static final String CSV = "text/csv";
	private static final Logger logger = LoggerFactory.getLogger(CsvMappingProvider.class);
	
	private String[] requestMapping = { "customer", "impPerCampaign",
			"pricePerCampaign" };
	private String[] responseMapping = { "customer", "noCampaigns",
			"totalImpressions", "customerRevenue" };

	final CellProcessor[] allProcessors = new CellProcessor[] {
			new ForbidSubStr(" "), // customer can't contain space
			new ParseLong(), // noCampaigns
			new ParseDouble() }; // customerRevenue

	final CellProcessor[] onlyfirstLine = new CellProcessor[] { new ParseLong() }; // customerRevenue

	@PostConstruct
	protected void initialize() {

	}


	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public long getSize(CampaignOptimizerResponse t, Class<?> type,
			Type genericType, Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void writeTo(CampaignOptimizerResponse t, Class<?> type,
			Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		
	try(StringWriter  respoSw = new StringWriter();
			ICsvBeanWriter responseWriter = new CsvBeanWriter(respoSw,
					CsvPreference.STANDARD_PREFERENCE);){
		List<CampaignSalesQuotas> campaignSalesQuotas = t
				.getCampaignSalesQuotas();
		for (CampaignSalesQuotas campaignSalesQuota : campaignSalesQuotas) {
			responseWriter.write(campaignSalesQuota, responseMapping);
		}
		responseWriter.flush();
		respoSw.append(t.getTotalNoImp() + ", " + t.getTotalRevenue() + ""
				+ "\n");
		entityStream.write(respoSw.toString().getBytes());
	}
	}

	@Override
	public boolean isReadable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return true;
	}

	@Override
	public CampaignOptimizerRequest readFrom(
			Class<CampaignOptimizerRequest> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		CampaignOptimizerRequest request = null;
		try (InputStreamReader reqR = new InputStreamReader(entityStream);
				ICsvBeanReader requestReader = new CsvBeanReader(reqR,
						CsvPreference.STANDARD_PREFERENCE);) {

			CustomerCampaignInformation customerCCInfo = new CustomerCampaignInformation();
			Integer monthlyAdInventory = null;
			requestReader.read(customerCCInfo,
					new String[] { "monthlyAdInventory" }, onlyfirstLine);
			String untokenizedRow = requestReader.getUntokenizedRow();

			monthlyAdInventory = Integer.parseInt(untokenizedRow);// read(CampaignOptimizerRequest.class,"monthlyAdInventory",processors);

			customerCCInfo.setMonthlyAdInventory(monthlyAdInventory);
			request = new CampaignOptimizerRequest();
			while ((customerCCInfo=requestReader.read(CustomerCampaignInformation.class,
					requestMapping, allProcessors)) != null) {
				request.addCustomerInfo(customerCCInfo);
			}
		}catch(SuperCsvConstraintViolationException ex){
			logger.error(ex.getMessage());
			throw ex;
		}
		return request;
	}

}
