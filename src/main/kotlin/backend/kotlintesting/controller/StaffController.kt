package backend.kotlintesting.controller

import backend.kotlintesting.model.*
import backend.kotlintesting.service.StaffService
import backend.kotlintesting.service.UploadFileService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.nio.file.Paths
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession


@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
@RequestMapping("/staff")
class StaffController(val staffService: StaffService,
                      val uploadService: UploadFileService) {

    @GetMapping("/home")
    fun toStaffHome(): String = "staffhome"

    @PostMapping("/logout")
    fun logOut(req: HttpServletRequest): String {
        val session: HttpSession = req.session
        session.setAttribute("staff",null)
        return "redirect:/testingonline"
    }

//-------------------------------CANDIDATE------------------------------------------------------------------

    @GetMapping("/candidate/{idCandidate}")
    fun getCandidateById(@PathVariable("idCandidate") idCandidate: Int) = staffService.findById(idCandidate)

    @GetMapping("/candidate/phone/{phone}")
    fun findByPhone(@PathVariable("phone") phone: String) = staffService.findByPhone(phone);

    @GetMapping("/candidate/name/{name}")
    fun findByName(@PathVariable("name") name: String) = staffService.findCandidateByName(name)

    @GetMapping("/candidate/email/{email}")
    fun findByEmail(@PathVariable("email") email: String) = staffService.findByEmail(email)

    @GetMapping("/candidate/test/{idTest}")
    fun findByTest(@PathVariable("idTest") idTest: Int) = staffService.findByTests(idTest)
    @GetMapping("/candidate")
    fun allCandidate() = staffService.getAllCandidate()

    @PostMapping("/addcandidate")
    fun addCandidate(@RequestBody candidate: Candidate) = staffService.addCandidate(candidate)

    @DeleteMapping("/deletecandidate/{idCandidate}")
    fun deleteCandidate(@PathVariable("idCandidate") idCandidate: Int) = staffService.deleteCandidate(idCandidate)

    //----------------------------TEST-------------------------------------

    @GetMapping("/testview")
    fun toTestView() = "testview"

    @GetMapping("/test")
    fun getAllTest() = staffService.getAllTest()

    @GetMapping("/test/{idTest}")
    fun getTestById(@PathVariable("idTest") idTest: Int) = staffService.getTestById(idTest)

    @GetMapping("/test/name/{name}")
    fun getTestByName(@PathVariable("name") name: String) = staffService.getTestByName(name)

//    @GetMapping("/test/code/{codeTest}")
//    fun getTestByCode(@PathVariable("codeTest") codeTest: String) = staffService.getTestByCode(codeTest)

    @GetMapping("/test/subject/{idSubject}")
    fun getTestBySubject(@PathVariable("idSubject") idSubject: Int) = staffService.getTestBySubject(idSubject)

//    @GetMapping("/test/done/{isDone}")
//    @ResponseBody
//    fun getTestByIsDone(@PathVariable("isDone") isDone: Int): List<Test>? = staffService.findByIsDone(isDone)

    @GetMapping("/test/level/{level}")
    fun getTestByLevel(@PathVariable("level") level: Int) = staffService.findByLevels(level)

    @GetMapping("/test/candidate/{idCandidate}")
    fun getTestByCandidateId(@PathVariable("idCandidate") idCandidate: Int) = staffService.findByCandidateId(idCandidate)

    @PostMapping("/addtest")
    fun addTest(@RequestBody newTest: Test) = staffService.addTest(newTest)

    @DeleteMapping("/deletetest/{idTest}")
    fun deleteTest(@PathVariable("idTest") idTest: Int) = staffService.deleteTest(idTest)

    @PutMapping("/updatetest")
    fun updateTest(@RequestBody updateTest: Test) = staffService.updateTest(updateTest)

    @PutMapping("/addquestion/{idQuestion}/{idTest}")
    fun addQuestionToTest(@PathVariable("idQuestion") idQuestion: Int,@PathVariable("idTest") idTest: Int) = staffService.addQuestionToTest(idQuestion, idTest)

    @PutMapping("/addquestion/{idTest}")
    fun addMoreQuestionToTest(@PathVariable("idTest") idTest: Int,@RequestBody questions: ForAddMoreQuestion) = staffService.addMoreQuestionToTest(idTest,questions)

    @PutMapping("/addnewquestion/{idTest}")
    fun addNewQuestionTotest(@PathVariable("idTest") idTest: Int, @RequestBody newQuestion: Question) = staffService.addNewQuestion(idTest,newQuestion)

    @PutMapping("/removequestion/{idQuestion}/{idTest}")
    fun removeQuestionFromTest(@PathVariable("idQuestion") idQuestion: Int, @PathVariable("idTest") idTest: Int) = staffService.removeQuestionFromTest(idQuestion, idTest)

    @PutMapping("/addtestforcandidate/{idTest}/{idCandidate}")
    fun addTestForCandidate(@PathVariable("idTest") idTest: Int, @PathVariable("idCandidate") idCandidate: Int) = staffService.addTestForCandidate(idTest,idCandidate)

    @PutMapping("/reviewmcanswer/{idCandidate}")
    fun reviewMCAnswer(@PathVariable("idCandidate") idCandidate: Int) = staffService.reviewMCQuestion(idCandidate)

//    @PutMapping("/reviewessay/{idTest}/{idEssay}/{mark}")
//    fun reviewEssay(@PathVariable("idTest") idTest: Int,@PathVariable("idEssay") idEssay: Int,@PathVariable("mark") mark:Double): ResponseEntity<ResponseObject> = staffService.reviewEssay(idTest,idEssay,mark)

    @PutMapping("/setmark/{idCandidate}")
    fun setMark(@PathVariable("idCandidate") idCandidate: Int) = staffService.setMark(idCandidate)

    @GetMapping("/result")
    fun getAllResult() = staffService.getAllResult()

    //-------------------------QUESTION--------------------------------------------------

    @GetMapping("/question")
    fun getAllQuestion() = staffService.getAllQuestion()

    @GetMapping("/question/{idQuestion}")
    fun getQuestionById(@PathVariable("idQuestion") idQuestion: Int) = staffService.getQuestionById(idQuestion)

    @GetMapping("/question/type/{idType}")
    fun getQuestionByType(@PathVariable("idType") idType: Int) = staffService.getQuestionByType(idType)

    @GetMapping("/question/subject/{idSubject}")
    fun getQuestionBySubject(@PathVariable("idSubject") idSubject: Int) = staffService.getQuestionBySubject(idSubject)

    @GetMapping("/question/level/{idLevel}")
    fun getQuestionByLevel(@PathVariable("idLevel") idLevel: Int) = staffService.getQuestionByLevel(idLevel)

    @GetMapping("/question/test/{idTest}")
    fun getQuestionByTest(@PathVariable("idTest") idTest: Int) = staffService.getQuestionByTest(idTest)

    @PostMapping("/addquestion")
    fun addQuestion(@RequestBody newQuestion: Question) = staffService.addQuestion(newQuestion)

    @DeleteMapping("/deletequestion/{idQuestion}")
    fun deleteQuestion(@PathVariable("idQuestion") idQuestion: Int) = staffService.deleteQuestion(idQuestion)

    @PutMapping("/updatequestion")
    fun updateQuestion(@RequestBody updateQuestion: Question) = staffService.updateQuestion(updateQuestion)

    //----------------------------------ANSWER--------------------------------------------------------

    @GetMapping("/mcanswer")
    fun getAllMcAnswer() = staffService.getAllMcAnswer()

    @PutMapping("/updatemcanswer/{idAnswer}")
    fun updateMcAnswer(@PathVariable("idAnswer") idAnswer: Int, @RequestParam("answer") answer: String,@RequestParam("istrue") isTrue: Int) = staffService.updateMcAnswer(idAnswer,answer,isTrue)

    @PutMapping("/addanswer/{idAnswer}/{idQuestion}")
    fun addMcAnswer(@PathVariable("idAnswer") idAnswer: Int, @PathVariable("idQuestion") idQuestion: Int) = staffService.addAnswerToQuestion(idAnswer,idQuestion)

    @PutMapping("/addmcanswer/question/{idQuestion}")
    fun addNewMCAnswer(@PathVariable("idQuestion") idQuestion: Int, @RequestBody newAnswer: MultipleChoiceAnswer) = staffService.addMCAnswerForQuestion(idQuestion,newAnswer)

    @DeleteMapping("/deletemcanswer/{idAnswer}")
    fun deleteMCAnswer(@PathVariable("idAnswer") idAnswer: Int) = staffService.deleteMCAnswer(idAnswer)

    @PutMapping("/removemcanswer/{idAnswer}")
    fun removeMCAnswer(@PathVariable("idAnswer") idAnswer: Int) = staffService.removeMCAnswer(idAnswer)

    @GetMapping("/eanswer")
    fun getAllEssayAnswer() = staffService.getAllEssayAnswer()

    @PostMapping("/addeanswer/question/{idQuestion}")
    fun addEssayAnswer(@PathVariable("idQuestion") idQuestion: Int, @RequestBody newAnswer: EssayAnswer) = staffService.addEssayAnsForQuestion(idQuestion,newAnswer)

    @PutMapping("/updateessayanswer/{idAnswer}")
    fun updateEssayAnswer(@PathVariable("idAnswer") idAnswer: Int, @RequestParam("answer") answer: String) = staffService.updateEssayAnswer(idAnswer,answer)

    @DeleteMapping("/delete/essay/{idAnswer}")
    fun deleteEssayAnswer(@PathVariable("idAnswer") idAnswer: Int) = staffService.deleteEssay(idAnswer)

    //-----------------------SUBJECT,LEVEL----------------------------------------------------------

    @GetMapping("/subject")
    fun getAllSubject() = staffService.getAllSubject()

    @PostMapping("/addsubject")
    fun addSubject(@RequestBody newSubject: Subject) = staffService.addSubject(newSubject)

    @DeleteMapping("/deletesubject/{idSubject}")
    fun deleteSubject(@PathVariable("idSubject") idSubject: Int) = staffService.deleteSubject(idSubject)

    @GetMapping("/level")
    fun getAllLevel() = staffService.getAllLevel()

    @PostMapping("/addlevel")
    fun addLevel(@RequestBody newLevels: Levels) = staffService.addLevel(newLevels)

    @DeleteMapping("/deletelevel/{idLevel}")
    fun deleteLevel(@PathVariable("idLevel") idLevel: Int) = staffService.deleteLevel(idLevel)

//----------------------------------Other------------------------------------------------------------

    @PutMapping("/importdata")
    fun importData(@RequestParam("file") file: MultipartFile): MutableList<Question>? {
        println(file.originalFilename)
        val storageFolder: Path = Paths.get("uploads")
        uploadService.upload(file)
        val originalName = file.originalFilename
        val filePath = storageFolder.resolve(Paths.get(originalName)).normalize().toAbsolutePath()
        return staffService.addQuestionInXlsFile(filePath.toString())
    }
}