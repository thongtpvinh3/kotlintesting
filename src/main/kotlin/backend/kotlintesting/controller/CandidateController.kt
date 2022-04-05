package backend.kotlintesting.controller

import backend.kotlintesting.model.DisplayCandidate
import backend.kotlintesting.model.TempResultCandidate
import backend.kotlintesting.model.Test
import backend.kotlintesting.responseException.ResponseObject
import backend.kotlintesting.service.CandidateService
import backend.kotlintesting.service.RedisCandidateDoTestCache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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

    @GetMapping("/alltest")
    fun getCandiateTest(req: HttpServletRequest): MutableSet<Test>? {
        val candidate = req.session.getAttribute("candidate") as DisplayCandidate
        return candidate.tests
    }

    @PostMapping("/doingtest/{idQuestion}")
    fun cacheAnswer(req: HttpServletRequest,@PathVariable idQuestion: Int ,@RequestBody tempAns: TempResultCandidate) {
        val session: HttpSession = req.session
        val candidate = session.getAttribute("candidate") as DisplayCandidate
        val tests = candidate.tests
        tempAns.idCandidate = candidate.id
        tempAns.type = candidateService.getTypeQuestion(idQuestion)
        tempAns.answer = "1"
        val timenow = LocalTime.now().toSecondOfDay()
        val testtime = candidate.calculatedTotalTime(tests)
        val timestart = candidate.dates!!.toLocalTime().toSecondOfDay()
        if (timenow-timestart<=testtime) {
                valueCache.saveAnswer(tempAns, idQuestion, candidate.id)
        } else {
            val finalRes: MutableList<TempResultCandidate> = mutableListOf()
            val finalRes1 = valueCache.getHashCachAns("ans")
            for (e in finalRes1.entries) {
                val thisKey = e.key
                val str = thisKey.split(":")
                if(str[1] == "${candidate.id}") {
                    finalRes.add(e.value)
                }
            }
            candidateService.saveAllResult(finalRes)
            candidateService.setIsDone(candidate.id)
            valueCache.deleteCandidateCacheAnswer("ans",candidate.id)
        }
    }

    @PostMapping("/submit")
    fun submitTest(req: HttpServletRequest): ResponseEntity<ResponseObject> {
        val candidate = req.session.getAttribute("candidate") as DisplayCandidate
        candidateService.setIsDone(candidate.id)
        val tempAnswerResult: MutableList<TempResultCandidate> = mutableListOf()
        val tempAnsResult1 = valueCache.getHashCachAns("ans").entries
        for (e in tempAnsResult1) {
            val thisKey = e.key
            val str = thisKey.split(":")
            if(str[1] == "${candidate.id}") {
                tempAnswerResult.add(e.value)
            }
        }
        candidateService.saveAllResult(tempAnswerResult)
        candidateService.setIsDone(candidate.id)
        valueCache.deleteCandidateCacheAnswer("ans",candidate.id)
        return ResponseEntity.ok(ResponseObject("OK!","Nop Bai Thanh Cong!",""))
    }

    @GetMapping("/allcache")
    fun getAllCacheAns(): Map<String, TempResultCandidate> = valueCache.getHashCachAns("ans")

    @PostMapping("/deleteallcache")
    fun deleteAllCache() = valueCache.deleteAll("ans")

    @PostMapping("/logout")
    fun logOut(req: HttpServletRequest): String {
        val session: HttpSession = req.session
        session.setAttribute("candidate",null)
        return "redirect:/testingonline"
    }

}