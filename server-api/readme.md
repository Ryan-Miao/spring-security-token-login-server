认证server
==========

## 认证流程
org.springframework.security.web.FilterChainProxy

```
ignoreurl  
->  TokenAuthenticationFilter
->  TokenAuthenticationProvider

```

```


默认的用户密码认证basic认证方案

[restartedMain] o.s.s.web.DefaultSecurityFilterChain     : Creating filter chain: any request, 

[org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@49020e62, 
org.springframework.security.web.context.SecurityContextPersistenceFilter@7fe0e6ac, 
org.springframework.security.web.header.HeaderWriterFilter@3fb7d00d, 
org.springframework.security.web.csrf.CsrfFilter@445479d3, 
org.springframework.security.web.authentication.logout.LogoutFilter@69f822ff, 
org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter@22eb350b, 
org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter@2575835, 
org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter@1a4b1358, 
org.springframework.security.web.authentication.www.BasicAuthenticationFilter@3c4f7cd2, 
org.springframework.security.web.savedrequest.RequestCacheAwareFilter@2c0b28a7, 
org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@3f73d79, 
org.springframework.security.web.authentication.AnonymousAuthenticationFilter@7c52b821, 
org.springframework.security.web.session.SessionManagementFilter@3c107564, 
org.springframework.security.web.access.ExceptionTranslationFilter@5f4611ca, 
org.springframework.security.web.access.intercept.FilterSecurityInterceptor@305a7049]


修改后的自定义token认证方案
[org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@4ad97300, 
org.springframework.security.web.context.SecurityContextPersistenceFilter@6c7e52cc, 
org.springframework.security.web.header.HeaderWriterFilter@65d5fa67,
 org.springframework.web.filter.CorsFilter@592ded13, 
 org.springframework.security.web.authentication.logout.LogoutFilter@33945f92, 
 com.example.serverapi.domain.security.config.TokenAuthenticationFilter@4cb8f568, 
 org.springframework.security.web.savedrequest.RequestCacheAwareFilter@586c7430, 
 org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter@6b869dbe, 
 org.springframework.security.web.authentication.AnonymousAuthenticationFilter@14bbd90d,
 org.springframework.security.web.session.SessionManagementFilter@61332318, 
 org.springframework.security.web.access.ExceptionTranslationFilter@6077702e, 
 org.springframework.security.web.access.intercept.FilterSecurityInterceptor@662a0a8]
```