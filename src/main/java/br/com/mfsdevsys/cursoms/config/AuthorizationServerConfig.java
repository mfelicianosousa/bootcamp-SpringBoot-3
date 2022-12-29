package br.com.mfsdevsys.cursoms.config;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter ;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private BCryptPasswordEncoder passwordEncoder ; // @Autowired
    private JwtAccessTokenConverter accessTokenConverter; // @Autowired
    private JwtTokenStore tokenStore; // @Autowired
    private AuthenticationManager authenticationManager; // @Autowired
    public AuthorizationServerConfig(BCryptPasswordEncoder passwordEncoder,
                                     JwtAccessTokenConverter accessTokenConverter,
                                     JwtTokenStore tokenStore,
                                     AuthenticationManager authenticationManager){
          this.passwordEncoder = passwordEncoder;
          this.accessTokenConverter=accessTokenConverter;
          this.tokenStore = tokenStore;
          this.authenticationManager = authenticationManager;
    }
     @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permiteALL()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("mars-shopping")
                .secret(passwordEncoder.encode("mars-shopping123"))
                .scopes("read","write")
                .authorizedGrantTypes("password")
                .accessTokenValiditySeconds(86400);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter);
    }
}
