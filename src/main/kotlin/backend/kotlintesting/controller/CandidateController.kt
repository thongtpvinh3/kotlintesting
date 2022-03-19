package backend.kotlintesting.controller

import backend.kotlintesting.model.Candidate
import backend.kotlintesting.model.TempResultCandidate
import backend.kotlintesting.model.Test
import backend.kotlintesting.repo.CandidateTestRepo
import backend.kotlintesting.repo.TestRepo
import backend.kotlintesting.responseException.ResponseObject
import backend.kotlintesting.service.CandidateService
import backend.kotlintesting.service.RedisCandidateDoTestCache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@RestController
@RequestMapping("/candidate")
class CandidateController(@Autowired private val candidateService: CandidateService,
                          private var valueCache: RedisCandidateDoTestCache) {

    fun CandidateController(valueCache: RedisCandidateDoTestCache) {
        this.valueCache = valueCache
    }

    @GetMapping("/testpage")
    fun toTestPage() = "testpage"

    @GetMapping("/test")
    @ResponseBody
    fun getAllCandidateTest(req : HttpServletRequest): MutableSet<Test>? {
        val candidate: Candidate = req.session.getAttribute("candidate") as Candidate
        return candidate.tests
    }

    @GetMapping("/test/{idTest}/{idCandidate}")
    @ResponseBody
    fun toThisTestView(@PathVariable("idTest") idTest: Int,@PathVariable("idCandidate") idCandidate: Int, req: HttpServletRequest): Test? {
        val session: HttpSession = req.session
        var thisTest: Test = candidateService.getTestById(idTest)
        session.setAttribute("test",thisTest)
        return candidateService.joinThisTest(idTest,idCandidate)
    }

    @PostMapping("/doingtest")
    fun cacheAnswer(req: HttpServletRequest, @RequestBody tempAns: TempResultCandidate) {
        val session: HttpSession = req.session
        val thisTest: Test = session.getAttribute("test") as Test
        val thisCandidate: Candidate = session.getAttribute("candidate") as Candidate
        valueCache.cache("testtime", LocalTime.now().toSecondOfDay())
        val timenow = valueCache.getCachedValue("testtime") as Int
        val testtime: Int = thisTest.timeToSecond()
        val timestart: Int = thisTest.dates!!.toLocalTime().toSecondOfDay()

        if (timenow-timestart<=testtime) {
            if (tempAns.type == 0) {
                valueCache.saveMultipleAnswer(tempAns)
            } else {
                valueCache.saveEssayAnswer(tempAns)
            }
        } else {
            val finalRes: MutableList<TempResultCandidate> = mutableListOf()
            val finalRes1: Map<Int, TempResultCandidate> = valueCache.getHashCachAns("ans")

            for (e in finalRes1.entries) {
                finalRes.add(e.value)
            }
            candidateService.saveAllResult(finalRes)
            candidateService.setTestIsDone(thisTest.id,thisCandidate.id)
            valueCache.delete("ans")
        }
    }

    @PostMapping("/submit/{idTest}")
    fun submitTest(@PathVariable("idTest") idTest: Int, req: HttpServletRequest): ResponseEntity<ResponseObject> {
        var candidate: Candidate = req.session.getAttribute("candidate") as Candidate
        val idCandidate: Int = candidate.id
        candidateService.setTestIsDone(idTest,idCandidate)
        val tempAnswerResult: MutableList<TempResultCandidate> = mutableListOf()
        val tempAnsResult1: Map<Int, TempResultCandidate> = valueCache.getHashCachAns("ans") as Map<Int, TempResultCandidate>
        for (e in tempAnsResult1.entries) {
            tempAnswerResult.add(e.value)
        }
        candidateService.saveAllResult(tempAnswerResult)
        req.session.setAttribute("test",null)
        valueCache.delete("ans")

        return ResponseEntity.ok(ResponseObject("OK!","Nop Bai Thanh Cong!",""))
    }

    @PostMapping("/logout")
    fun logOut(req: HttpServletRequest): String {
        val session: HttpSession = req.session
//        session.setAttribute("test",null)
        session.setAttribute("candidate",null)
        return "redirect:/testingonline"
    }

}