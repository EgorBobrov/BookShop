package bookshop.configuration;



import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import bookshop.aspect.LoggingAspect;
import bookshop.converter.RoleToUserProfileConverter;


@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "bookshop, com.epamjuniors.bookshop.bookshop_dao")
public class AppConfig extends WebMvcConfigurerAdapter{


	@Autowired
	RoleToUserProfileConverter roleToUserProfileConverter;


	/*
	 * ViewResolvers add suffixes and prefixes to JSP files names.
	 * Suffixes == path to the files, prefixes == file extension.
	 */
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}

	/*
	 * ResourceHandlers to point to the locations of our static resources 
	 * CSS, pictures and Javascript files
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
		registry.addResourceHandler("/img/**").addResourceLocations("/img/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
	}

	/*
	 * Configuring the Converter
	 * We need it to convert string values of User Roles to UserProfile types
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(roleToUserProfileConverter);
	}


	/*
	 * MessageSource is used to lookup 
	 * messages in internationalized property files
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	/*
	 * Registering beans which allow locale support and change
	 */
	 @Bean(name = "localeResolver")
	 public LocaleResolver sessionLocaleResolver(){
	     SessionLocaleResolver localeResolver=new SessionLocaleResolver();
	     localeResolver.setDefaultLocale(new Locale("en"));
	      
	     return localeResolver;
	 }  
	 
	 @Bean
	 public LocaleChangeInterceptor localeChangeInterceptor(){
	     LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
	     localeChangeInterceptor.setParamName("language");
	     return localeChangeInterceptor;
	 }	
	 
	 public void addInterceptors(InterceptorRegistry registry) {
	     registry.addInterceptor(localeChangeInterceptor());
	 }
	 
	 /* Required when handling '.' in @PathVariables which otherwise ignore everything after last '.' in @PathVaidables argument.
	 * It's a known bug in Spring [https://jira.spring.io/browse/SPR-6164], still present in Spring 4.
	 * This is a workaround for this issue.
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer matcher) {
		matcher.setUseRegisteredSuffixPatternMatch(true);
	}
	
	/*
	 * Adding our aspect class to the configuration
	 */
	 @Bean
	 public LoggingAspect loggingAspect(){
		 LoggingAspect loggingAspect = new LoggingAspect();
		 return loggingAspect;
	 }	

}