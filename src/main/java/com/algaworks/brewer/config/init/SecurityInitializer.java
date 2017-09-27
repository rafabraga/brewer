package com.algaworks.brewer.config.init;

import java.util.EnumSet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    /**
     * Para consertar os erros de encoding com a adição do spring security.
     */
    @Override
    protected void beforeSpringSecurityFilterChain(final ServletContext servletContext) {
        // A sessão será guardada em cookies, não mais em url (as vezes o sessionID fica
        // aparecendo na url).
        servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));

        final FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter",
                new CharacterEncodingFilter());
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
    }

}
