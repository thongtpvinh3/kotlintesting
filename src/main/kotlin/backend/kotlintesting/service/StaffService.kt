package backend.kotlintesting.service

import backend.kotlintesting.model.*
import backend.kotlintesting.repo.*
import backend.kotlintesting.responseException.ResponseObject
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.text.DecimalFormat


@Service
class StaffService(@Autowired private val levelRepo: LevelRepository,
                   @Autowired private val candiRepo: CandidateRepository,
                   @Autowired private val questionRepo: QuestionRepo,
                   @Autowired private val testRepo: TestRepo,
                   @Autowired private val tempResultRepo: TempResultRepo,
                   @Autowired private val multipleAnswerRepo: MultipleAnswerRepo,
                   @Autowired private val essayAnswerRepo: EssayAnswerRepository,
                   @Autowired private val candidateTestRepo: CandidateTestRepo,
                   @Autowired private val subRepo: SubjectRepository,
                   @Autowired private val staffRepo: StaffRepo
) : UserDetailsService {

    // #################################################################
    // #                             o`
    // #                          _ooOoo_
    // #                         o8888888o
    // #                         88" . "88
    // #                         (| -_- |)
    // #                         O\  =  /O
    // #                      ____/`---'\____
    // #                    .'  \\|     |//  `.
    // #                   /  \\|||  :  |||//  \
    // #                  /  _||||| -:- |||||_  \
    // #                  |   | \\\  -  /'| |   |
    // #                  | \_|  `\`---'//  |_/ |
    // #                  \  .-\__ `-. -'__/-.  /
    // #                ___`. .'  /--.--\  `. .'___
    // #             ."" '<  `.___\_<|>_/___.' _> \"".
    // #            | | :  `- \`. ;`. _/; .'/ /  .' ; |
    // #            \  \ `-.   \_\_`. _.'_/_/  -' _.' /
    // #=============`-.`___`-.__\ \___  /__.-'_.'_.-'=================#
    //                            `=--=-'
    //           _.-/`)
    //          // / / )
    //       .=// / / / )
    //      //`/ / / / /
    //     // /     ` /
    //    ||         /
    //     \\       /
    //      ))    .'
    //     //    /
    //          /

    //-------------------------CANDIDATE-----------------------------------------------

    fun findById(idCandidate: Int) = candiRepo.getById(idCandidate)
    fun findCandidateByName(name: String) = candiRepo.findByName(name)
    fun findByPhone(phone:String) = candiRepo.findByPhone(phone)
    fun findByEmail(email:String) = candiRepo.findByEmail(email)

    fun addCandidate(newCandidate: Candidate) =
        if(candiRepo.findByEmail(newCandidate.email).isEmpty() && candiRepo.findByPhone(newCandidate.phone).isEmpty())
            ResponseEntity.ok(
                ResponseObject("OK","Them moi ung vien thanh cong",candiRepo.save(newCandidate))
            )
        else ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
            ResponseObject("FAILED","Email hoac Phone bi trung","")
        )
    fun getAllCandidate(): List<Candidate> = candiRepo.findAll()
    fun deleteCandidate(idCandidate: Int) = candiRepo.deleteById(idCandidate)

    //----------------------TEST---------------------------------

    fun getTestById(idTest: Int) = testRepo.getById(idTest)
    fun getAllTest(): List<Test> = testRepo.findAll()
    fun getTestByName(name: String) = testRepo.findByName(name)
    fun getTestBySubject(idSubject: Int) = testRepo.findBySubject(idSubject)
    fun findByLevels(level: Int) = testRepo.findByLevel(level)
    fun findByCandidateId(idCandidate: Int) = candiRepo.getById(idCandidate).tests

    fun addTest(newTest: Test) = ResponseEntity.ok(ResponseObject("OK","Them thanh cong!",testRepo.save(newTest)))

    fun deleteTest(idTest: Int) = ResponseEntity.ok(ResponseObject("OK","Xoa bai test co id = $idTest",testRepo.deleteById(idTest)))

    fun updateTest(updateTest: Test) = ResponseEntity.ok(ResponseObject("OK","Update thanh cong",testRepo.save(updateTest)))

    fun addNewQuestion(idTest: Int, newQuestion: Question): Test {
        val foundTest = testRepo.getById(idTest)
        val thisListQuestion = foundTest.questions
        questionRepo.save(newQuestion)
        for (m in newQuestion.multipleChoiceAnswer!!) m.question = newQuestion
        thisListQuestion?.add(newQuestion)
        return testRepo.save(foundTest)
    }

    fun addQuestionToTest(idQuestion: Int, idTest: Int): ResponseEntity<ResponseObject> {
        val newQuestion = questionRepo.getById(idQuestion)
        val foundTest = testRepo.getById(idTest)

        if (foundTest.questions!!.size == 0 && newQuestion.level == foundTest.level && newQuestion.subject == foundTest.subject) {
            val newList = foundTest.questions!!
            newList.add(newQuestion)
            foundTest.questions = newList
            return ResponseEntity.ok(ResponseObject("OK","Them thanh cong cau hoi $idQuestion cho bai test $idTest",testRepo.save(foundTest)))
        }
        else {
            if (newQuestion.level != foundTest.level || newQuestion.subject != foundTest.subject) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    ResponseObject("FAILED","Khong hop Level hoac Subject","")
                )
            }
            return if (!foundTest.questions!!.contains(newQuestion) && newQuestion.level == foundTest.level && newQuestion.subject == foundTest.subject) {
                val newList = foundTest.questions!!
                newList.add(newQuestion)
                foundTest.questions = newList
                ResponseEntity.ok(ResponseObject("OK","Them thanh cong cau hoi $idQuestion cho bai test $idTest",testRepo.save(foundTest)))
            } else ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ResponseObject("FAILED!","Cau hoi bi trung!",""))
        }
    }

    fun addMoreQuestionToTest(idTest: Int, questions: ForAddMoreQuestion): ResponseEntity<ResponseObject> {
        val foundTest = testRepo.getById(idTest)
        var newList = foundTest.questions
        for (q in questions.idQuestion) {
            val foundQuestion = questionRepo.getById(q)
            if (foundQuestion.subject == foundTest.subject && foundQuestion.level == foundTest.level) {
                newList!!.add(foundQuestion)
            } else continue
        }
        return ResponseEntity.ok(ResponseObject("Thanh Cong!","Them thanh cong cac cau hoi cho bai test $idTest",testRepo.save(testRepo.getById(idTest))))
    }

    fun addTestForCandidate(idTest: Int, idCandidate: Int): ResponseEntity<ResponseObject>? {
        val newTest: Test = testRepo.getById(idTest)
        val foundCandidate: Candidate = candiRepo.getById(idCandidate)

        if(newTest.level != foundCandidate.level) return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ResponseObject("FAILED","Level khong phu hop!",""))
        return if(!foundCandidate.tests!!.contains(newTest)) {
            val newList = foundCandidate.tests
            newList!!.add(newTest)
            foundCandidate.tests = newList
            ResponseEntity.ok(ResponseObject("OK!","Them thanh cong bai test $idTest cho ung vien $idCandidate",candiRepo.save(foundCandidate)))
        } else ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ResponseObject("FAILED!","Ung vien da co bai test nay!",""))
    }

    fun reviewMCQuestion(idCandidate: Int): ResponseEntity<ResponseObject> {
        var englishMark = 0.0
        var codingMark = 0.0
        var knowledgeMark = 0.0
        for (test in candiRepo.getById(idCandidate).tests!!) {
            val idTest = test.id
            val thisTestQuestion = testRepo.getById(idTest).questions!!
            val result = tempResultRepo.getAnswerOfCandidate(idCandidate,0, idTest)
            var count = 0
            var rightResult = 0
            for (q in thisTestQuestion) {
                if (q.type == 0) {
                    count++
                }
            }
            for (res in result) {
                if(multipleAnswerRepo.findWithIdAndIsTrue(res.idAnswer!!,res.answer!!.toInt()) != null && res.idCandidate == idCandidate && res.idTest == idTest) {
                    rightResult++
                }
            }
            val oneMarkQuestion: Double = 100.0/count
            val df = DecimalFormat("##.##")
            val tmp = df.format(oneMarkQuestion).toDouble()
            val lastResult = tmp*rightResult
            val candidateTest = candidateTestRepo.findByCandidateIdAndTestId(idCandidate,idTest)
            candidateTest.marks = lastResult
            candidateTestRepo.save(candidateTest)

            when(test.subject) {
                1 -> englishMark = lastResult
                2 -> codingMark = lastResult
                3 -> knowledgeMark = lastResult
            }

            println("\n Tong so cau hoi trong bai test: $count")
            println("\n Diem moi cau: $tmp \n")
            println("\n Tong so cau dung trong bai test: $rightResult")
            println("\n Tong diem cua bai test: $lastResult")
        }
        return ResponseEntity.ok(ResponseObject("OK!","Chấm điểm thành công!","Điểm tiếng anh: $englishMark"+
                                                                                                 " Điểm code: $codingMark+"+
                                                                                                 " Điểm kiến thức chung: $knowledgeMark"))
    }

//    fun reviewEssay(idTest: Int,idCandidate: Int,idEssay: Int, mark: Double): ResponseEntity<ResponseObject> {
//        val foundTest: Test = testRepo.getById(idTest)
//        val listQuestion: MutableList<Question> = foundTest.questions!!
//        var candidateTest: CandidateTest = candidateTestRepo.findByCandidateIdAndTestId(idTest,idCandidate)
//        var count: Int = 0
//        for (q in listQuestion) {
//            if (q.type == 1) count++
//        }
//
//        val oneMarkQuestion: Double = 100.0/count
//        val df: DecimalFormat = DecimalFormat("#.##")
//        val tmp: Double = df.format(oneMarkQuestion).toDouble()
//
//        if (mark>tmp) {
//            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ResponseObject("FAILED!","Diem vuot qua max cau hoi (${tmp})",""))
//        } else {
//            val essayAnswer:EssayAnswer = essayAnswerRepo.getById(idEssay)
////            essayAnswer.mark = mark
////            var x:Double = foundTest.marks + mark
////            foundTest.marks = x
//            testRepo.save(foundTest)
//            return ResponseEntity.ok(ResponseObject("OK","Set diem thanh cong!",essayAnswerRepo.save(essayAnswer)))
//        }
//    }

    fun setMark(idCandidate: Int): ResponseEntity<ResponseObject> {
        val foundCandidate: Candidate = candiRepo.getById(idCandidate)
        for (ct in candidateTestRepo.findByCandidateId(idCandidate)) {
            when(testRepo.getById(ct.testId!!).subject) {
                1 -> foundCandidate.englishMark = ct.marks
                2 -> foundCandidate.codingMark = ct.marks
                3 -> foundCandidate.knowledgeMark = ct.marks
            }
        }
        return ResponseEntity.ok(ResponseObject("OK!","Set diem thanh cong cho ung vien ${foundCandidate.name}",candiRepo.save(foundCandidate)))
    }

    fun getAllResult(): List<TempResultCandidate> = tempResultRepo.findAll()

    //------------------------QUESTION----------------------------------------------

    fun getAllQuestion(): List<Question> = questionRepo.findAll()
    fun getQuestionById(idQuestion: Int) = questionRepo.getById(idQuestion)
    fun getQuestionByType(idType: Int) = questionRepo.findByType(idType)!!
    fun getQuestionBySubject(idSubject: Int) = questionRepo.findBySubject(idSubject)!!
    fun getQuestionByLevel(idLevel: Int) = questionRepo.findByLevel(idLevel)!!
    fun getQuestionByTest(idTest: Int) = testRepo.getById(idTest).questions!!
    fun addQuestion(newQuestion: Question) = questionRepo.save(newQuestion)
    fun deleteQuestion(idQuestion: Int) = ResponseEntity.ok(ResponseObject("OK!","Xoa thanh cong cau hoi $idQuestion",questionRepo.deleteById(idQuestion)))
    fun updateQuestion(updateQuestion: Question) = questionRepo.save(updateQuestion)

    fun getAllMcAnswer(): List<MultipleChoiceAnswer> = multipleAnswerRepo.findAll()

    fun addAnswerToQuestion(idAnswer: Int, idQuestion: Int): ResponseEntity<ResponseObject> {
        val foundQuestion: Question = questionRepo.getById(idQuestion)
        return if (foundQuestion.type == 0) {
            val answer: MultipleChoiceAnswer = multipleAnswerRepo.getById(idAnswer)
            val newListAnswer: MutableList<MultipleChoiceAnswer> = foundQuestion.multipleChoiceAnswer!!
            newListAnswer.add(answer)
            foundQuestion.multipleChoiceAnswer = newListAnswer
            answer.question = foundQuestion
            ResponseEntity.ok(ResponseObject("OK!","Them cau tra loi cho cau hoi $idQuestion",questionRepo.save(foundQuestion)))
        } else {
            val answer: EssayAnswer = essayAnswerRepo.getById(idAnswer)
            foundQuestion.essayAnswer = answer
            ResponseEntity.ok(ResponseObject("OK!","Them cau tra loi cho cau hoi $idQuestion",questionRepo.save(foundQuestion)))
        }
    }

    fun addMCAnswerForQuestion(idQuestion: Int, newAnswer: MultipleChoiceAnswer): ResponseEntity<ResponseObject> {
        val foundQuestion: Question = questionRepo.getById(idQuestion)
        if (foundQuestion.type == 0) {
            newAnswer.question = foundQuestion
            val newList: MutableList<MultipleChoiceAnswer>? = foundQuestion.multipleChoiceAnswer
            newList!!.add(newAnswer)
            return ResponseEntity.ok(ResponseObject("OK!","Them thanh cong dap an cho cau hoi $idQuestion",questionRepo.save(foundQuestion)))
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(ResponseObject("FAILED!","Cau hoi khong phai loai trac nghiem!",""))
    }

    fun removeQuestionFromTest(idQuestion: Int, idTest: Int): ResponseEntity<ResponseObject> {
        val newList = testRepo.getById(idTest).questions
        newList!!.remove(questionRepo.getById(idQuestion))
        return ResponseEntity.ok(ResponseObject("OK!","Xóa câu hỏi $idQuestion khỏi bài Test $idTest",testRepo.save(testRepo.getById(idTest))))
    }

    fun deleteMCAnswer(idAnswer: Int): ResponseEntity<ResponseObject> {
        multipleAnswerRepo.deleteById(idAnswer)
        return ResponseEntity.ok(ResponseObject("OK!","Xoa thanh cong dap an $idAnswer",""))
    }

    fun removeMCAnswer(idAnswer: Int): ResponseEntity<ResponseObject> {
        multipleAnswerRepo.getById(idAnswer).question = null
        return ResponseEntity.ok(ResponseObject("OK!","Remove dap an $idAnswer ra khoi cau hoi!",multipleAnswerRepo.save(multipleAnswerRepo.getById(idAnswer))))
    }

    fun updateMcAnswer(idAnswer: Int, answer: String, isTrue: Int): MultipleChoiceAnswer {
        val updateAnswer: MultipleChoiceAnswer = multipleAnswerRepo.getById(idAnswer)
        updateAnswer.answer = answer
        updateAnswer.isTrue = isTrue
        return multipleAnswerRepo.save(updateAnswer)
    }

    fun getAllEssayAnswer(): List<EssayAnswer> = essayAnswerRepo.findAll()

    fun addEssayAnsForQuestion(idQuestion: Int, newAnswer: EssayAnswer): ResponseEntity<ResponseObject> {
        val foundQuestion: Question = questionRepo.getById(idQuestion)
        if (foundQuestion.type == 1) {
            newAnswer.question = foundQuestion
            foundQuestion.essayAnswer = newAnswer
            return ResponseEntity.ok(ResponseObject("OK!","Them thanh cong dap an",questionRepo.save(foundQuestion)))
        }
        return ResponseEntity.ok(ResponseObject("OK!","Cau hoi khong thuoc loai tu luan!",""))
    }

    fun updateEssayAnswer(idAnswer: Int, answer: String): EssayAnswer {
        val updateAnswer: EssayAnswer = essayAnswerRepo.getById(idAnswer)
        updateAnswer.answer = answer
        return essayAnswerRepo.save(updateAnswer)
    }

    fun deleteEssay(idAnswer: Int): ResponseEntity<ResponseObject> {
        essayAnswerRepo.deleteById(idAnswer)
        return ResponseEntity.ok(ResponseObject("OK!","Xoa thanh cong dap an $idAnswer",""))
    }

    fun getAllSubject(): List<Subject> = subRepo.findAll()
    fun addSubject(newSubject: Subject) = subRepo.save(newSubject)
    fun deleteSubject(idSubject: Int): ResponseEntity<ResponseObject> {
        subRepo.deleteById(idSubject)
        return ResponseEntity.ok(ResponseObject("OK!","Xoa thanh cong subject $idSubject",""))
    }
    fun getAllLevel(): List<Levels> = levelRepo.findAll()
    fun addLevel(newLevels: Levels) = levelRepo.save(newLevels)
    fun deleteLevel(idLevel: Int): ResponseEntity<ResponseObject> {
        levelRepo.deleteById(idLevel)
        return ResponseEntity.ok(ResponseObject("OK!","Xoa thanh cong level $idLevel",""))
    }

    fun findByTests(idTest: Int) = testRepo.getById(idTest).candidates

    @Throws(IOException::class)
    fun addQuestionInXlsFile(xlsFilePath: String): MutableList<Question>? {
        val COLUMN_INDEX_CONTENT = 1
        val COLUMN_INDEX_ANSWER_A = 2
        val COLUMN_INDEX_ANSWER_B = 3
        val COLUMN_INDEX_ANSWER_C = 4
        val COLUMN_INDEX_ANSWER_D = 5
        val COLUMN_INDEX_TRUE = 6
        var subject = 1
        val presentList = questionRepo.findAll()
        val listQuestions: MutableList<Question> = ArrayList()
        val file = FileInputStream(xlsFilePath)
        val wb = getWorkbook(file, xlsFilePath)
        val sheet: Iterator<Sheet> = wb.iterator()
        val formatter = DataFormatter()
        // sheet
        while (sheet.hasNext()) {
            val row: Iterator<Row> = sheet.next().iterator()
            // row
            while (row.hasNext()) {
                val nextRow: Row = row.next()
                if (nextRow.getRowNum() == 0) {
                    continue
                }
                val cellIterator: Iterator<Cell> = nextRow.cellIterator()
                val newQuestion = Question()
                val m: MutableList<MultipleChoiceAnswer> = mutableListOf()
                val A = MultipleChoiceAnswer()
                val B = MultipleChoiceAnswer()
                val C = MultipleChoiceAnswer()
                val D = MultipleChoiceAnswer()
                newQuestion.type = 0
                newQuestion.subject = subject
                newQuestion.level = 1
                // cell
                while (cellIterator.hasNext()) {
                    val cell = cellIterator.next()
                    if (cell.columnIndex == 0) {
                        continue
                    } else {
                        when (cell.columnIndex) {
                            COLUMN_INDEX_CONTENT -> {
                                newQuestion.content = cell.stringCellValue
                                continue
                            }
                            COLUMN_INDEX_ANSWER_A -> {
                                A.answer = formatter.formatCellValue(cell)
                                continue
                            }
                            COLUMN_INDEX_ANSWER_B -> {
                                B.answer = formatter.formatCellValue(cell)
                                continue
                            }
                            COLUMN_INDEX_ANSWER_C -> {
                                C.answer = formatter.formatCellValue(cell)
                                continue
                            }
                            COLUMN_INDEX_ANSWER_D -> {
                                D.answer = formatter.formatCellValue(cell)
                                continue
                            }
                            COLUMN_INDEX_TRUE -> {
                                if (cell.stringCellValue.equals("A")) {
                                    A.isTrue = 1
                                    B.isTrue = 0
                                    C.isTrue = 0
                                    D.isTrue = 0
                                }
                                if (cell.stringCellValue.equals("B")) {
                                    A.isTrue = 0
                                    B.isTrue = 1
                                    C.isTrue = 0
                                    D.isTrue = 0
                                }
                                if (cell.stringCellValue.equals("C")) {
                                    A.isTrue = 0
                                    B.isTrue = 0
                                    C.isTrue = 1
                                    D.isTrue = 0
                                }
                                if (cell.stringCellValue.equals("D")) {
                                    A.isTrue = 0
                                    B.isTrue = 0
                                    C.isTrue = 0
                                    D.isTrue = 1
                                }
                            }
                        }
                        m.add(A)
                        m.add(B)
                        m.add(C)
                        m.add(D)
                    }
                    println("\n\nxong 1 hang\n\n")
                    println("${presentList.contains(newQuestion)}\n")
                    if (!presentList.contains(newQuestion)) {
                        questionRepo.save(newQuestion)
                        for (ans in m) {
                            ans.question = newQuestion
                        }
                        multipleAnswerRepo.saveAll(m)
                        listQuestions.add(newQuestion)
                    }
                }
            }
            subject++
            continue
        }
        wb.close()
        file.close()
        return listQuestions
    }

    @Throws(IOException::class)
    private fun getWorkbook(inputStream: InputStream, excelFilePath: String): Workbook {
        val workbook: Workbook?
        workbook = if (excelFilePath.endsWith("xlsx")) {
            XSSFWorkbook(inputStream)
        } else {
            throw IllegalArgumentException("The specified file is not Excel file")
        }
        return workbook
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val staff: Staff = staffRepo.findByUsername(username)
        return User(staff.username,staff.password, mutableListOf())
    }
}