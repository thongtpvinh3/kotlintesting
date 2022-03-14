package backend.kotlintesting.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class LoginFilter : Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val req: HttpServletRequest = p0 as HttpServletRequest
        val resp: HttpServletResponse = p1 as HttpServletResponse
        var logger: Logger = LoggerFactory.getLogger(LoginFilter::class.java)

        val session: HttpSession = req.session
        if (session.getAttribute("staff") == null) {
            logger.info("No staff in session!!!!")
            return resp.sendRedirect(req.contextPath+"/login")
        } else {
            logger.info("Thanh cong!!")
        }
        p2?.doFilter(req,resp)
    }

    override fun destroy() {
    }
}