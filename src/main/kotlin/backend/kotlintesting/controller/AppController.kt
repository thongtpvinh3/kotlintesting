package backend.kotlintesting.controller

import backend.kotlintesting.service.AppService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
public class AppController(private val appService: AppService) {

    @GetMapping("/testingonline")
    fun toWebPage(): String = "homepage"

    @GetMapping("/login")
    fun toStaffLogin(): String = "login"


    @PostMapping("/jointest/{idCandidate}")
    fun joinWithTestCode(@PathVariable("idCandidate") idCandidate: Int, req:HttpServletRequest): String? {
        if (appService.joinByIdCandidate(idCandidate)) {
            val session: HttpSession = req.getSession()
            session.setAttribute("candidate", appService.getJoinCandidate(idCandidate))
            return "redirect:/candidate/testpage"
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
            "login"
        }
    }

}