package backend.kotlintesting.filter

import backend.kotlintesting.model.Staff
import backend.kotlintesting.model.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class JoinTestFilter: Filter {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val req: HttpServletRequest = p0 as HttpServletRequest
        val resp: HttpServletResponse = p1 as HttpServletResponse
        var logger: Logger = LoggerFactory.getLogger(JoinTestFilter::class.java)

        val session: HttpSession = req.session
        if (session.getAttribute("test") == null) {
            logger.info("\nNo test in session!!!!")
            return resp.sendRedirect(req.contextPath + "/testingonline")
        } else {
            logger.info("\nThanh cong!!")
        }
        p2?.doFilter(req,resp)
    }

    override fun destroy() {
    }

}