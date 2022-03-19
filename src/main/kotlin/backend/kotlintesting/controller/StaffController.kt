package backend.kotlintesting.controller

import backend.kotlintesting.model.*
import backend.kotlintesting.responseException.ResponseObject
import backend.kotlintesting.service.StaffService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
@RequestMapping("/staff")
class StaffController(
    val staffService: StaffService
) {
    @GetMapping("/home")
    fun toStaffHome(): String = "staffhome"

    @PostMapping("/logout")
    fun logOut(req: HttpServletRequest): String {
        var session: HttpSession = req.session
        session.setAttribute("staff",null)
        return "redirect:/testingonline"
    }

    @GetMapping("/candidate/{idCandidate}")
    fun getCandidateById(@PathVariable("idCandidate") idCandidate: Int): Candidate = staffService.findById(idCandidate)

    @GetMapping("/candidate/phone/{phone}")
    fun findByPhone(@PathVariable("phone") phone: String): List<Candidate>? = staffService.findByPhone(phone);

    @GetMapping("/candidate/name/{name}")
    fun findByName(@PathVariable("name") name: String): List<Candidate>? = staffService.findCandidateByName(name)

    @GetMapping("/candidate/email/{email}")
    fun findByEmail(@PathVariable("email") email: String): List<Candidate>? = staffService.findByEmail(email)

    @GetMapping("/candidate")
    @ResponseBody
    fun allCandidate(): List<Candidate> = staffService.getAllCandidate()

    @PostMapping("/addcandidate")
    fun addCandidate(@RequestBody candidate: Candidate): ResponseEntity<ResponseObject> = staffService.addCandidate(candidate)

    @DeleteMapping("/deletecandidate/{idCandidate}")
    fun deleteCandidate(@PathVariable("idCandidate") idCandidate: Int) = staffService.deleteCandidate(idCandidate)

    //----------------------------TEST-------------------------------------

    @GetMapping("/testview")
    fun toTestView(): String = "testview"

    @GetMapping("/test")
    @ResponseBody
    fun getAllTest(): List<Test> = staffService.getAllTest()

    @GetMapping("/test/{idTest}")
    @ResponseBody
    fun getTestById(@PathVariable("idTest") idTest: Int): Test = staffService.getTestById(idTest)

    @GetMapping("/test/name/{name}")
    @ResponseBody
    fun getTestByName(@PathVariable("name") name: String) : List<Test>? = staffService.getTestByName(name)

    @GetMapping("/test/code/{codeTest}")
    @ResponseBody
    fun getTestByCode(@PathVariable("codeTest") codeTest: String): Test? = staffService.getTestByCode(codeTest)

    @GetMapping("/test/subject/{idSubject}")
    @ResponseBody
    fun getTestBySubject(@PathVariable("idSubject") idSubject: Int): List<Test>? = staffService.getTestBySubject(idSubject)

//    @GetMapping("/test/done/{isDone}")
//    @ResponseBody
//    fun getTestByIsDone(@PathVariable("isDone") isDone: Int): List<Test>? = staffService.findByIsDone(isDone)

    @GetMapping("/test/level/{level}")
    @ResponseBody
    fun getTestByLevel(@PathVariable("level") level: Int): List<Test>? = staffService.findByLevels(level)

    @GetMapping("/test/candidate/{idCandidate}")
    @ResponseBody
    fun getTestByCandidateId(@PathVariable("idCandidate") idCandidate: Int): Set<Test>? = staffService.findByCandidateId(idCandidate)

    @PostMapping("/addtest")
    fun addTest(@RequestBody newTest: Test): ResponseEntity<ResponseObject> = staffService.addTest(newTest)

    @DeleteMapping("/deletetest/{idTest}")
    fun deleteTest(@PathVariable("idTest") idTest: Int): ResponseEntity<ResponseObject> = staffService.deleteTest(idTest)

    @PutMapping("/updatetest")
    fun updateTest(@RequestBody updateTest: Test): ResponseEntity<ResponseObject> = staffService.updateTest(updateTest)

    @PutMapping("/addquestion/{idQuestion}/{idTest}")
    fun addQuestionToTest(@PathVariable("idQuestion") idQuestion: Int,@PathVariable("idTest") idTest: Int): ResponseEntity<ResponseObject> = staffService.addQuestionToTest(idQuestion, idTest)

    @PutMapping("/addtestforcandidate/{idTest}/{idCandidate}")
    fun addTestForCandidate(@PathVariable("idTest") idTest: Int, @PathVariable("idCandidate") idCandidate: Int): ResponseEntity<ResponseObject>? = staffService.addTestForCandidate(idTest,idCandidate)

    @PutMapping("/reviewmcanswer/{idTest}/{idCandidate}")
    fun reviewMCAnswer(@PathVariable("idTest") idTest: Int, @PathVariable("idCandidate") idCandidate: Int): Double = staffService.reviewMCQuestion(idTest,idCandidate)

//    @PutMapping("/reviewessay/{idTest}/{idEssay}/{mark}")
//    fun reviewEssay(@PathVariable("idTest") idTest: Int,@PathVariable("idEssay") idEssay: Int,@PathVariable("mark") mark:Double): ResponseEntity<ResponseObject> = staffService.reviewEssay(idTest,idEssay,mark)

    @PutMapping("/setmark/{idCandidate}")
    fun setMark(@PathVariable("idCandidate") idCandidate: Int): ResponseEntity<ResponseObject> = staffService.setMark(idCandidate)

    @GetMapping("/result")
    @ResponseBody
    fun getAllResult(): MutableList<TempResultCandidate> = staffService.getAllResult()

    //-------------------------QUESTION--------------------------------------------------

    @GetMapping("/question")
    @ResponseBody
    fun getAllQuestion(): MutableList<Question> = staffService.getAllQuestion()

    @GetMapping("/question/{idQuestion}")
    @ResponseBody
    fun getQuestionById(@PathVariable("idQuestion") idQuestion: Int): Question = staffService.getQuestionById(idQuestion)

    @GetMapping("/question/type/{idType}")
    @ResponseBody
    fun getQuestionByType(@PathVariable("idType") idType: Int): MutableList<Question> = staffService.getQuestionByType(idType)

    @GetMapping("/question/subject/{idSubject}")
    @ResponseBody
    fun getQuestionBySubject(@PathVariable("idSubject") idSubject: Int): MutableList<Question> = staffService.getQuestionBySubject(idSubject)

    @GetMapping("/question/level/{idLevel}")
    @ResponseBody
    fun getQuestionByLevel(@PathVariable("idLevel") idLevel: Int): MutableList<Question> = staffService.getQuestionByLevel(idLevel)

    @GetMapping("/question/test/{idTest}")
    @ResponseBody
    fun getQuestionByTest(@PathVariable("idTest") idTest: Int): Set<Question> = staffService.getQuestionByTest(idTest)

    @PostMapping("/addquestion")
    @ResponseBody
    fun addQuestion(@RequestBody newQuestion: Question): Question = staffService.addQuestion(newQuestion)

    @DeleteMapping("/deletequestion/{idQuestion}")
    fun deleteQuestion(@PathVariable("idQuestion") idQuestion: Int): ResponseEntity<ResponseObject> = staffService.deleteQuestion(idQuestion)

    @PutMapping("/updatequestion")
    @ResponseBody
    fun updateQuestion(@RequestBody updateQuestion: Question): Question = staffService.updateQuestion(updateQuestion)

    //----------------------------------ANSWER--------------------------------------------------------

    @GetMapping("/mcanswer")
    @ResponseBody
    fun getAllMcAnswer(): MutableList<MultipleChoiceAnswer> = staffService.getAllMcAnswer()

    @PutMapping("/updatemcanswer/{idAnswer}")
    @ResponseBody
    fun updateMcAnswer(@PathVariable("idAnswer") idAnswer: Int, @RequestParam("answer") answer: String,@RequestParam("istrue") isTrue: Int): MultipleChoiceAnswer = staffService.updateMcAnswer(idAnswer,answer,isTrue)

    @PutMapping("/addanswer/{idAnswer}/{idQuestion}")
    fun addMcAnswer(@PathVariable("idAnswer") idAnswer: Int, @PathVariable("idQuestion") idQuestion: Int): ResponseEntity<ResponseObject> = staffService.addAnswerToQuestion(idAnswer,idQuestion)

    @PutMapping("/addmcanswer/question/{idQuestion}")
    fun addNewMCAnswer(@PathVariable("idQuestion") idQuestion: Int, @RequestBody newAnswer: MultipleChoiceAnswer): ResponseEntity<ResponseObject> = staffService.addMCAnswerForQuestion(idQuestion,newAnswer)

    @DeleteMapping("/deletemcanswer/{idAnswer}")
    fun deleteMCAnswer(@PathVariable("idAnswer") idAnswer: Int): ResponseEntity<ResponseObject> = staffService.deleteMCAnswer(idAnswer)

    @PutMapping("/removemcanswer/{idAnswer}")
    fun removeMCAnswer(@PathVariable("idAnswer") idAnswer: Int): ResponseEntity<ResponseObject> = staffService.removeMCAnswer(idAnswer)

    @GetMapping("/eanswer")
    @ResponseBody
    fun getAllEssayAnswer(): MutableList<EssayAnswer> = staffService.getAllEssayAnswer()

    @PostMapping("/addeanswer/question/{idQuestion}")
    fun addEssayAnswer(@PathVariable("idQuestion") idQuestion: Int, @RequestBody newAnswer: EssayAnswer): ResponseEntity<ResponseObject> = staffService.addEssayAnsForQuestion(idQuestion,newAnswer)

    @PutMapping("/updateessayanswer/{idAnswer}")
    @ResponseBody
    fun updateEssayAnswer(@PathVariable("idAnswer") idAnswer: Int, @RequestParam("answer") answer: String): EssayAnswer = staffService.updateEssayAnswer(idAnswer,answer)

    @DeleteMapping("/delete/essay/{idAnswer}")
    fun deleteEssayAnswer(@PathVariable("idAnswer") idAnswer: Int): ResponseEntity<ResponseObject> = staffService.deleteEssay(idAnswer)
    //-----------------------SUBJECT,LEVEL----------------------------------------------------------

    @GetMapping("/subject")
    @ResponseBody
    fun getAllSubject(): MutableList<Subject> = staffService.getAllSubject()

    @PostMapping("/addsubject")
    @ResponseBody
    fun addSubject(@RequestBody newSubject: Subject): Subject = staffService.addSubject(newSubject)

    @DeleteMapping("/deletesubject/{idSubject}")
    fun deleteSubject(@PathVariable("idSubject") idSubject: Int): ResponseEntity<ResponseObject> = staffService.deleteSubject(idSubject)

    @GetMapping("/level")
    @ResponseBody
    fun getAllLevel(): MutableList<Levels> = staffService.getAllLevel()

    @PostMapping("/addlevel")
    @ResponseBody
    fun addLevel(@RequestBody newLevels: Levels): Levels = staffService.addLevel(newLevels)

    @DeleteMapping("/deletelevel/{idLevel}")
    fun deleteLevel(@PathVariable("idLevel") idLevel: Int): ResponseEntity<ResponseObject> = staffService.deleteLevel(idLevel)


}