package com.serviceindeed.yike.yikemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * OAuth2相关配置：使用默认的DefaultTokenServices，token保存到数据，以及token超时时间设置
 * Author: 		Colin Feng
 * Created On: 	2017年12月15日
 */
@Configuration
public class OAuth2ServerConfiguration {

    private static final String RESOURCE_ID = "ifk";
    private static final String CLIENT_ID = "ifk_site";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID);
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().authenticated();
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        @Autowired
        @Qualifier("dataSource")
        private DataSource dataSource;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        @Qualifier("userDetailsService")
        private UserDetailsService userDetailsService;

        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource);
        }


        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            // @formatter:off
			endpoints
				.tokenStore(tokenStore())
				.authenticationManager(this.authenticationManager)
				.userDetailsService(userDetailsService);
			// @formatter:on
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // @formatter:off
			clients
				.inMemory()
					.withClient(CLIENT_ID)
						.authorizedGrantTypes("password", "refresh_token")
						.scopes("read", "write", "trust")
						.resourceIds(RESOURCE_ID)
						.secret("secret").accessTokenValiditySeconds(60*60*24*7);
			// @formatter:on
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(tokenStore());
            return tokenServices;
        }

    }
}
