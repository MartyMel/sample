package net.wmfs.coalesce.aa.access;

import java.util.ArrayList;
import java.util.List;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import net.wmfs.security.auth.ActiveDirectoryUserDetailsMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Value("${net.wmfs.cas.client.contextRootUrl}")
	private String contextRootUrl;
	
	@Value("${net.wmfs.cas.server.secureUrl}")
	private String secureUrl;
	
	@Value("${net.wmfs.cas.server.rootUrl}")
	private String rootUrl;
	
	@Value("${net.wmfs.cas.server.id}")
	private String casServerId;
	
	@Value("${net.wmfs.ldap.server}")
	private String ldapServer;
	
	@Value("${net.wmfs.ldap.port}")
	private String ldapPort;
	
	@Value("${net.wmfs.ldap.baseDN}")
	private String ldapBaseDN;
	
	@Value("${net.wmfs.ldap.userDN}")
	private String adminAccountUserDN;
	
	@Value("${net.wmfs.ldap.password}")
	private String adminAccountPassword;
	
	@Value("${net.wmfs.ldap.userSearchBase}")
	private String ldapUserSearchBase;
	
	@Value("${net.wmfs.ldap.userSearchFilter}")
	private String ldapUserSearchFilter;
	
	@Value("${net.wmfs.ldap.groupSearchBase}")
	private String ldapGroupSearchBase;
	
	
	// CAS CONFIGURATION
	
	// Define auth user info acquisition source, password check rule, etc
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.authenticationProvider(casAuthenticationProvider());
	}
	
	// Define security policy
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint())
			.and().logout().logoutSuccessUrl(rootUrl + "/logout").invalidateHttpSession(true).permitAll()
			.and().authorizeRequests().anyRequest().authenticated()
			.and()
				.addFilter(casAuthenticationFilter())
				.addFilterBefore(casLogoutFilter(), LogoutFilter.class)
				.addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class);
	}

	@Bean
	public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
		CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
		casAuthenticationEntryPoint.setLoginUrl(secureUrl + "/login");
		casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
		return casAuthenticationEntryPoint;
	}
	
	@Bean
	public ServiceProperties serviceProperties() {
		ServiceProperties serviceProperties = new ServiceProperties();
		serviceProperties.setService(contextRootUrl + "/login/cas");
		serviceProperties.setSendRenew(false);
		serviceProperties.setAuthenticateAllArtifacts(true);
		return serviceProperties;
	}
	
	@Bean
	public CasAuthenticationFilter casAuthenticationFilter() {
		CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
		casAuthenticationFilter.setAuthenticationManager(myAuthenticationManager());
		return casAuthenticationFilter;
	}
	
	@Bean
	public AuthenticationManager myAuthenticationManager() {
		List<AuthenticationProvider> providers = new ArrayList<>();
		providers.add(casAuthenticationProvider());
		return new ProviderManager(providers);
	}
	
	@Bean
	public CasAuthenticationProvider casAuthenticationProvider() {
		CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
		casAuthenticationProvider.setAuthenticationUserDetailsService(
				new UserDetailsByNameServiceWrapper<CasAssertionAuthenticationToken>(userDetailsService()));
		casAuthenticationProvider.setServiceProperties(serviceProperties());
		casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
		casAuthenticationProvider.setKey(casServerId);
		return casAuthenticationProvider;
	}

	@Bean
    public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
        return new Cas20ServiceTicketValidator(rootUrl);
    }
	
	@Bean
	public SingleSignOutFilter singleSignOutFilter() {
		SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
		singleSignOutFilter.setCasServerUrlPrefix(secureUrl);
		singleSignOutFilter.setIgnoreInitConfiguration(true);
		return singleSignOutFilter;
	}
	
	@Bean
	public LogoutFilter casLogoutFilter() {
		LogoutFilter logoutFilter = new LogoutFilter(contextRootUrl + "/logout", 
				new SecurityContextLogoutHandler());
		logoutFilter.setFilterProcessesUrl("/logout/cas");
		return logoutFilter;
	}
	
	
	
	// LDAP Configuration
	
	@Bean
	public LdapContextSource contextSource() {
		DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(
				"ldap://" + ldapServer + ":" + ldapPort + "/" + ldapBaseDN);
		contextSource.setUrl("ldap://" + ldapServer + ":" + ldapPort + "/");
		contextSource.setBase(ldapBaseDN);
		contextSource.setPooled(true);
		contextSource.setUserDn(adminAccountUserDN);
		contextSource.setPassword(adminAccountPassword);
		contextSource.setReferral("follow");
		return contextSource;
	}
	
	@Bean
	public LdapUserSearch userSearch() {
		return new FilterBasedLdapUserSearch(
				ldapUserSearchBase, ldapUserSearchFilter, contextSource());
	}
	
	@Bean
	public LdapAuthoritiesPopulator authoritiesPopulator() {
		DefaultLdapAuthoritiesPopulator authoritiesPopulator = new DefaultLdapAuthoritiesPopulator(
				contextSource(), ldapGroupSearchBase);
		authoritiesPopulator.setGroupRoleAttribute("cn");
		authoritiesPopulator.setSearchSubtree(true);
		authoritiesPopulator.setRolePrefix("ROLE_");
		authoritiesPopulator.setDefaultRole("ROLE_USER");
		authoritiesPopulator.setConvertToUpperCase(true);
		return authoritiesPopulator;
	}
	
	@Bean
	public UserDetailsContextMapper userDetailsMapper() {
		return new ActiveDirectoryUserDetailsMapper();
	}
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		LdapUserDetailsService userDetailsService = new LdapUserDetailsService(
				userSearch(), authoritiesPopulator());
		userDetailsService.setUserDetailsMapper(userDetailsMapper());
		return userDetailsService;
	}
}
