package plugin;

import com.dcsquare.hivemq.spi.HiveMQPluginModule;
import com.dcsquare.hivemq.spi.PluginEntryPoint;
import com.dcsquare.hivemq.spi.plugin.meta.Information;
import com.google.inject.Provider;
import org.apache.commons.configuration.AbstractConfiguration;

import static com.dcsquare.hivemq.spi.config.Configurations.noConfigurationNeeded;


@Information(
        name = "HiveMQ Message Persistence Plugin",
        author = "Dominik Obermaier",
        version = "1.0-SNAPSHOT",
        description = "This plugin is for demonstration purposes only and is part of an " +
                "article in the German Java Magazin.")
public class MessagePersistencePluginModule extends HiveMQPluginModule {


    @Override
    public Provider<Iterable<? extends AbstractConfiguration>> getConfigurations() {
        return noConfigurationNeeded();
    }

    @Override
    protected void configurePlugin() {
        //Nothing to do here
    }

    @Override
    protected Class<? extends PluginEntryPoint> entryPointClass() {
        return MessagePersistencePlugin.class;
    }
}
