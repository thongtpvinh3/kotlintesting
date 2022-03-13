package backend.kotlintesting.filter

import backend.kotlintesting.model.Staff
import backend.kotlintesting.model.Test
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

        val session: HttpSession = req.session
        val test: Test = session.getAttribute("test") as Test
        if (test == null) {
            resp.sendRedirect(req.contextPath + "/testingonline")
            return
        } else {
            println("thanh cong!")
        }
        p2?.doFilter(req,resp)
    }

    override fun destroy() {
        super.destroy()
    }

}