package backend.kotlintesting.securityConfig

import backend.kotlintesting.service.StaffService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val staffService: StaffService) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(staffService)
    }
}


// #################################################################
// #                             _`
// #                          _ooOoo_
// #                         o8888888o
// #                         88" . "88
// #                         (| -_- |)
// #                         O\  =  /O
// #                      ____/`---'\____
// #                    .'  \\|     |//  `.
// #                   /  \\|||  :  |||//  \
// #                  /  _||||| -:- |||||_  \
// #                  |   | \\\  -  /'| |   |
// #                  | \_|  `\`---'//  |_/ |
// #                  \  .-\__ `-. -'__/-.  /
// #                ___`. .'  /--.--\  `. .'___
// #             ."" '<  `.___\_<|>_/___.' _> \"".
// #            | | :  `- \`. ;`. _/; .'/ /  .' ; |
// #            \  \ `-.   \_\_`. _.'_/_/  -' _.' /
// #=============`-.`___`-.__\ \___  /__.-'_.'_.-'=================#
//                            `=--=-'
//           _.-/`)
//          // / / )
//       .=// / / / )
//      //`/ / / / /
//     // /     ` /
//    ||         /
//     \\       /
//      ))    .'
//     //    /
//          /