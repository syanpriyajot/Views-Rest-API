package com.syan.rest.cli;

import com.google.common.base.Optional;
import com.syan.rest.RealcallerConfiguration;
import com.syan.rest.core.Template;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.inf.Subparser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RenderCommand extends ConfiguredCommand<RealcallerConfiguration> {

	/** Reference for the logger object. **/
	private static final Logger log = LoggerFactory
			.getLogger(RenderCommand.class);

	public RenderCommand() {
		super("render", "Render the template data to console");
	}

	@Override
	public void configure(Subparser subparser) {
		super.configure(subparser);
		subparser.addArgument("-i", "--include-default")
				.action(Arguments.storeTrue()).dest("include-default")
				.help("Also render the template with the default name");
		subparser.addArgument("names").nargs("*");
	}

	@Override
	protected void run(Bootstrap<RealcallerConfiguration> bootstrap,
			Namespace namespace, RealcallerConfiguration configuration)
			throws Exception {
		final Template template = configuration.buildTemplate();

		if (namespace.getBoolean("include-default")) {
			log.info("DEFAULT => {}",
					template.render(Optional.<String> absent()));
		}

		for (String name : namespace.<String> getList("names")) {
			for (int i = 0; i < 1000; i++) {
				log.info("{} => {}", name, template.render(Optional.of(name)));
				Thread.sleep(1000);
			}
		}
	}
}