package mamer.eg.bootstrapper;

import mamer.eg.config.CampaignOptimizerConfig;
import mamer.eg.messages.provider.CsvMappingProvider;
import mamer.eg.resource.CampaignOptimizerResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * Hello world!
 *
 */
public class CampaignOptimizerApplication extends
		Application<CampaignOptimizerConfig> {
	public static void main(String[] args) throws Exception {
		new CampaignOptimizerApplication().run(args);
	}

	@Override
	public void run(CampaignOptimizerConfig config, Environment env)
			throws Exception {
		env.jersey().register(new CampaignOptimizerResource());
		env.jersey().register(CsvMappingProvider.class);
	}
}
