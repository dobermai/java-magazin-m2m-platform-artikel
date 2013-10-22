package de.dobermai.rest;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dominik Obermaier
 */
public class JerseyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final HashSet<Class<?>> classes = new HashSet<Class<?>>();

        classes.add(Webservice.class);
        return classes;
    }
}
