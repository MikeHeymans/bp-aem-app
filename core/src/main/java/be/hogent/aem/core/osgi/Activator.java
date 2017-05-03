package be.hogent.aem.core.osgi;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
    private static final Logger LOG = LoggerFactory.getLogger("osgi.Activator");

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        LOG.debug("Activating bundle.");
        bundleContext.registerService("com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider", new JacksonJsonProvider(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        LOG.debug("Deactivating bundle.");
    }
}
