package backend.kotlintesting.controller

import backend.kotlintesting.service.AppService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
class AppController(private val appService: AppService) {

    @PostMapping("/jointest/{idCandidate}")
    fun joinWithTestCode(@PathVariable("idCandidate") idCandidate: Int, req:HttpServletRequest): Any? {
        if (appService.getJoinCandidate(idCandidate)!=null) {
            val session: HttpSession = req.session
            val candidate = appService.getJoinCandidate(idCandidate)
            if (candidate.isDone == 1) return "Bạn đã làm xong bài test"
            session.setAttribute("candidate", candidate)
            return candidate
        }
        return "redirect:/testingonline"
    }

    @PostMapping("/checklogin")
    fun checkLogin(@RequestParam username: String, @RequestParam password: String, req: HttpServletRequest): String {
        val checkLogin: Boolean = appService.login(username, password)
        println(checkLogin)
        return if (checkLogin) {
            val session = req.session
            session.setAttribute("staff", appService.findByUsernameAndPassword(username, password))
            "redirect:/staff/home"
        } else {
            "redirect:/login"
        }
    }

}