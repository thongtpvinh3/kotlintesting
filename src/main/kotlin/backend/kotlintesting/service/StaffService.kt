package backend.kotlintesting.service

import backend.kotlintesting.model.*
import backend.kotlintesting.repo.*
import backend.kotlintesting.responseException.ResponseObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.text.DecimalFormat
import java.time.LocalDateTime

@Service
public class StaffService(@Autowired private val subRepo: SubjectRepository,@Autowired private val levelRepo: LevelRepository,@Autowired private val candiRepo: CandidateRepository, @Autowired private val questionRepo: QuestionRepo, @Autowired private val staffRepo:StaffRepo,
                            @Autowired private val testRepo: TestRepo, @Autowired private val tempResultRepo: TempResultRepo, @Autowired private val multipleAnswerRepo: MultipleAnswerRepo, @Autowired private val essayAnswerRepo: EssayAnswerRepository) {

    //-------------------------CANDIDATE-----------------------------------------------

    fun findById(idCandidate: Int): Candidate = candiRepo.getById(idCandidate)
    fun findCandidateByName(name: String): List<Candidate>? = candiRepo.findByName(name)
    fun findByPhone(phone:String): List<Candidate>? = candiRepo.findByPhone(phone)
    fun findByEmail(email:String): List<Candidate>? = candiRepo.findByEmail(email)

    fun addCandidate(newCandidate: Candidate): ResponseEntity<ResponseObject> {
        if(candiRepo.findByEmail(newCandidate.email).size == 0 && candiRepo.findByPhone(newCandidate.phone).size == 0)
        return ResponseEntity.ok(
            ResponseObject("OK","Them moi ung vien thanh cong",candiRepo.save(newCandidate))
        )
        else return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
            ResponseObject("FAILED","Email hoac Phone bi trung","")
        )
    }
    fun getAllCandidate(): List<Candidate> = candiRepo.findAll()
    fun deleteCandidate(idCandidate: Int) = candiRepo.deleteById(idCandidate)

    //----------------------TEST---------------------------------

    fun getTestById(idTest: Int): Test = testRepo.getById(idTest)
    fun getAllTest(): List<Test> = testRepo.findAll()
    fun getTestByName(name: String): List<Test>? = testRepo.findByName(name)
    fun getTestByCode(codeTest: String): Test? = testRepo.findByCodeTest(codeTest)
    fun getTestBySubject(idSubject: Int): List<Test>? = testRepo.findBySubject(idSubject)
    fun findByIsDone(done: Int): List<Test>? = testRepo.findByIsDone(done)
    fun findByLevels(level: Int): List<Test>? = testRepo.findByLevel(level)
    fun findByCandidateId(idCandidate: Int): List<Test>? = candiRepo.getById(idCandidate).tests

    fun addTest(newTest: Test): ResponseEntity<ResponseObject> =
        if (!newTest.dates!!.isAfter(LocalDateTime.now()))
            ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(ResponseObject("FAILED","Date khong hop le",""))
        else ResponseEntity.ok(
            ResponseObject("OK","Them thanh cong!",testRepo.save(newTest))
        )

    fun deleteTest(idTest: Int): ResponseEntity<ResponseObject> = ResponseEntity.ok(ResponseObject("OK","Xoa bai test co id = ${idTest}",testRepo.deleteById(idTest)))

    fun updateTest(updateTest: Test): ResponseEntity<ResponseObject> =
        if(!updateTest.dates!!.isAfter(LocalDateTime.now()))
            ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
            ResponseObject("FAILED","Date ko hop le!",""))
        else ResponseEntity.ok(ResponseObject("OK","Update thanh cong",testRepo.save(updateTest)))

    fun addQuestionToTest(idQuestion: Int, idTest: Int): ResponseEntity<ResponseObject> {
        var newQuestion: Question = questionRepo.getById(idQuestion)
        var foundTest: Test = testRepo.getById(idTest)

        if (foundTest.questions!!.size == 0 && newQuestion.level == foundTest.level && newQuestion.subject == foundTest.subject) {
            var newList:MutableList<Question> = foundTest.questions!!
            newList.add(newQuestion)
            foundTest.questions = newList
            return ResponseEntity.ok(ResponseObject("OK","Them thanh cong cau hoi ${idQuestion} cho bai test ${idTest}",testRepo.save(foundTest)))
        }
        else {
            if (newQuestion.level != foundTest.level || newQuestion.subject != foundTest.subject) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    ResponseObject("FAILED","Khong hop Level hoac Subject","")
                )
            }
            if (foundTest.questions!!.contains(newQuestion) == false && newQuestion.level == foundTest.level && newQuestion.subject == foundTest.subject) {
                var newList: MutableList<Question> = foundTest.questions!!
                newList.add(newQuestion)
                foundTest.questions = newList
                return ResponseEntity.ok(ResponseObject("OK","Them thanh cong cau hoi ${idQuestion} cho bai test ${idTest}",testRepo.save(foundTest)))
            } else return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ResponseObject("FAILED!","Cau hoi bi trung!",""))
        }
    }

    fun addTestForCandidate(idTest: Int, idCandidate: Int): ResponseEntity<ResponseObject>? {
        var newTest: Test = testRepo.getById(idTest)
        var foundCandidate: Candidate = candiRepo.getById(idCandidate)

        if(newTest.level != foundCandidate.level) return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ResponseObject("FAILED","Level khong phu hop!",""))
        if(foundCandidate.tests!!.contains(newTest) == false) {
            val newList = foundCandidate.tests
            newList!!.add(newTest)
            foundCandidate.tests = newList
            newTest.candidate = foundCandidate
            testRepo.save(newTest)
            return ResponseEntity.ok(ResponseObject("OK!","Them thanh cong bai test ${idTest} cho ung vien ${idCandidate}",candiRepo.save(foundCandidate)))
        } else return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ResponseObject("FAILED!","Ung vien da co bai test nay!",""))
    }

    fun reviewMCQuestion(idTest: Int): Double {
        var foundTest: Test = testRepo.getById(idTest)
        var thisTestQuestion: MutableList<Question> = foundTest.questions!!
        var idCandidate: Int = foundTest.candidate!!.id
        var result: MutableList<TempResultCandidate> = tempResultRepo.getAnswerOfCandidate(idCandidate,0)
        var count: Int = 0
        var rightResult: Int = 0

        for (q in thisTestQuestion) {
            if (q.type == 0) {
                count++
            }
        }
        for (res in result) {
            if(multipleAnswerRepo.findWithIdAndisTrue(res.idAnswer,res.answer!!.toInt()) != null) {
                rightResult++
            }
        }

        var oneMarkQuestion: Double = 50.0/count
        var df: DecimalFormat = DecimalFormat("#.##")
        var tmp: Double = df.format(oneMarkQuestion).toDouble()

        var lastResult: Double = tmp*rightResult
        foundTest.marks = lastResult
        testRepo.save(foundTest)

        return lastResult
    }

    fun reviewEssay(idTest: Int,idEssay: Int, mark: Double): ResponseEntity<ResponseObject> {
        var foundTest: Test = testRepo.getById(idTest)
        var listQuestion: MutableList<Question> = foundTest.questions!!
        var count: Int = 0
        for (q in listQuestion) {
            if (q.type == 1) count++
        }

        var oneMarkQuestion: Double = 50.0/count
        var df: DecimalFormat = DecimalFormat("#.##")
        var tmp: Double = df.format(oneMarkQuestion).toDouble()

        if (mark>tmp) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ResponseObject("FAILED!","Diem vuot qua max cau hoi (${tmp})",""))
        } else {
            var essayAnswer:EssayAnswer = essayAnswerRepo.getById(idEssay)
            essayAnswer.mark = mark
            var x:Double = foundTest.marks + mark
            foundTest.marks = x
            testRepo.save(foundTest)
            return ResponseEntity.ok(ResponseObject("OK","Set diem thanh cong!",essayAnswerRepo.save(essayAnswer)))
        }
    }

    fun setMark(idCandidate: Int): ResponseEntity<ResponseObject> {
        return null!!
    }

    fun getAllResult(): MutableList<TempResultCandidate> = tempResultRepo.findAll()

    //------------------------QUESTION----------------------------------------------

    fun getAllQuestion(): MutableList<Question> = questionRepo.findAll()
    fun getQuestionById(idQuestion: Int): Question = questionRepo.getById(idQuestion)
    fun getQuestionByType(idType: Int): MutableList<Question> = questionRepo.findByType(idType)!!
    fun getQuestionBySubject(idSubject: Int): MutableList<Question> = questionRepo.findBySubject(idSubject)!!
    fun getQuestionByLevel(idLevel: Int): MutableList<Question> = questionRepo.findByLevel(idLevel)!!
    fun getQuestionByTest(idTest: Int): MutableList<Question> = testRepo.getById(idTest).questions!!
    fun addQuestion(newQuestion: Question): Question = questionRepo.save(newQuestion)
    fun deleteQuestion(idQuestion: Int): ResponseEntity<ResponseObject> = ResponseEntity.ok(ResponseObject("OK!","Xoa thanh cong cau hoi ${idQuestion}",questionRepo.deleteById(idQuestion)))
    fun updateQuestion(updateQuestion: Question): Question = questionRepo.save(updateQuestion)

    fun getAllMcAnswer(): MutableList<MultipleChoiceAnswer> = multipleAnswerRepo.findAll()

    fun addAnswerToQuestion(idAnswer: Int, idQuestion: Int): ResponseEntity<ResponseObject> {
        var foundQuestion: Question = questionRepo.getById(idQuestion)
        if (foundQuestion.type == 0) {
            var answer: MultipleChoiceAnswer = multipleAnswerRepo.getById(idAnswer)
            var newListAnswer: MutableList<MultipleChoiceAnswer> = foundQuestion.multipleChoiceAnswer!!
            newListAnswer.add(answer)
            foundQuestion.multipleChoiceAnswer = newListAnswer
            answer.question = foundQuestion
            return ResponseEntity.ok(ResponseObject("OK!","Them cau tra loi cho cau hoi ${idQuestion}",questionRepo.save(foundQuestion)))
        } else {
            var answer: EssayAnswer = essayAnswerRepo.getById(idAnswer)
            foundQuestion.essayAnswer = answer
            return ResponseEntity.ok(ResponseObject("OK!","Them cau tra loi cho cau hoi ${idQuestion}",questionRepo.save(foundQuestion)))
        }
    }

    fun addMCAnswerForQuestion(idQuestion: Int, newAnswer: MultipleChoiceAnswer): ResponseEntity<ResponseObject> {
        var foundQuestion: Question = questionRepo.getById(idQuestion)
        if (foundQuestion.type == 0) {
            newAnswer.question = foundQuestion
            foundQuestion.multipleChoiceAnswer!!.add(newAnswer)
            return ResponseEntity.ok(ResponseObject("OK!","Them thanh cong dap an cho cau hoi ${idQuestion}",questionRepo.save(foundQuestion)))
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ResponseObject("FAILED!","Cau hoi khong phai loai trac nghiem!",""))

    }

    fun deleteMCAnswer(idAnswer: Int): ResponseEntity<ResponseObject> {
        multipleAnswerRepo.deleteById(idAnswer)
        return ResponseEntity.ok(ResponseObject("OK!","Xoa thanh cong dap an ${idAnswer}",""))
    }

    fun removeMCAnswer(idAnswer: Int): ResponseEntity<ResponseObject> {
        multipleAnswerRepo.getById(idAnswer).question = null
        return ResponseEntity.ok(ResponseObject("OK!","Remove dap an ${idAnswer} ra khoi cau hoi!",multipleAnswerRepo.save(multipleAnswerRepo.getById(idAnswer))))
    }

    fun updateMcAnswer(idAnswer: Int, answer: String, isTrue: Int): MultipleChoiceAnswer {
        var updateAnswer: MultipleChoiceAnswer = multipleAnswerRepo.getById(idAnswer)
        updateAnswer.answer = answer
        updateAnswer.isTrue = isTrue
        return multipleAnswerRepo.save(updateAnswer)
    }

    fun getAllEssayAnswer(): MutableList<EssayAnswer> = essayAnswerRepo.findAll()

    fun addEssayAnsForQuestion(idQuestion: Int, newAnswer: EssayAnswer): ResponseEntity<ResponseObject> {
        var foundQuestion: Question = questionRepo.getById(idQuestion)
        if (foundQuestion.type == 1) {
            newAnswer.question = foundQuestion
            foundQuestion.essayAnswer = newAnswer
            return ResponseEntity.ok(ResponseObject("OK!","Them thanh cong dap an",questionRepo.save(foundQuestion)))
        }
        return ResponseEntity.ok(ResponseObject("OK!","Cau hoi khong thuoc loai tu luan!",""))
    }

    fun updateEssayAnswer(idAnswer: Int, answer: String): EssayAnswer {
        var updateAnswer: EssayAnswer = essayAnswerRepo.getById(idAnswer)
        updateAnswer.answer = answer
        return essayAnswerRepo.save(updateAnswer)
    }

    fun getAllSubject(): MutableList<Subject> = subRepo.findAll()
    fun addSubject(newSubject: Subject): Subject = subRepo.save(newSubject)
    fun deleteSubject(idSubject: Int): ResponseEntity<ResponseObject> {
        subRepo.deleteById(idSubject)
        return ResponseEntity.ok(ResponseObject("OK!","Xoa thanh cong subject ${idSubject}",""))
    }
    fun getAllLevel(): MutableList<Levels> = levelRepo.findAll()
    fun addLevel(newLevels: Levels): Levels = levelRepo.save(newLevels)
    fun deleteLevel(idLevel: Int): ResponseEntity<ResponseObject> {
        levelRepo.deleteById(idLevel)
        return ResponseEntity.ok(ResponseObject("OK!","Xoa thanh cong level ${idLevel}",""))
    }
}