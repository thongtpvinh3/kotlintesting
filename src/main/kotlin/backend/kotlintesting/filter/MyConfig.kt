package backend.kotlintesting.filter

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MyConfig {

    @Bean
    fun loginFilterBean(): FilterRegistrationBean<LoginFilter> {
        val registrationBean: FilterRegistrationBean<LoginFilter> = FilterRegistrationBean()

        registrationBean.filter = LoginFilter()
        registrationBean.addUrlPatterns("/staff/*")

        return registrationBean
    }

    @Bean
    fun joinTestFilterBean(): FilterRegistrationBean<JoinTestFilter> {
        val registrationBean: FilterRegistrationBean<JoinTestFilter> = FilterRegistrationBean()

        registrationBean.filter = JoinTestFilter()
        registrationBean.addUrlPatterns("/candidate/*")

        return  registrationBean
    }
}