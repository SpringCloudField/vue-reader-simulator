package net.opentrends.vue.simulator.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

import org.mockito.Mockito;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.opentrends.vue.simulator.exception.CustomRestExceptionHandler;

public abstract class BaseResourceDocumentedTest {
	
	protected RestDocumentationResultHandler documentationHandler;
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;
    protected FormattingConversionService conversionService;

    public void configureForDocumentation(RestDocumentationContextProvider restDocumentation, Object... controllers) {
        this.objectMapper = new ObjectMapper();

        this.documentationHandler = document("{method-name}",
        		Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
        		Preprocessors.preprocessResponse(Preprocessors.prettyPrint()));
        
        Authentication auth = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(auth);
		when(auth.getName()).thenReturn("mockedUserName");
		
		SecurityContextHolder.setContext(securityContext);
		//Reconfigure the view resolver in test
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB_INF/views");
		resolver.setSuffix(".jsp");

        this.mockMvc = MockMvcBuilders.standaloneSetup(controllers)
                .setConversionService(conversionService)
                .apply(documentationConfiguration(restDocumentation))
                .setControllerAdvice(new CustomRestExceptionHandler())
                .alwaysDo(this.documentationHandler)
                .setViewResolvers(resolver)
                .build();
    }
}