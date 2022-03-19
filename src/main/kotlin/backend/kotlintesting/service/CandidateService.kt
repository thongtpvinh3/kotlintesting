package backend.kotlintesting.service

import backend.kotlintesting.model.CandidateTest
import backend.kotlintesting.model.TempResultCandidate
import backend.kotlintesting.model.Test
import backend.kotlintesting.repo.CandidateRepository
import backend.kotlintesting.repo.CandidateTestRepo
import backend.kotlintesting.repo.TempResultRepo
import backend.kotlintesting.repo.TestRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class CandidateService(@Autowired private val candiRepo: CandidateRepository,
@Autowired private val tempResultRepo: TempResultRepo,@Autowired private val testRepo: TestRepo, @Autowired private val candidateTestRepo: CandidateTestRepo) {

    fun saveAllResult(finalRes: MutableList<TempResultCandidate>) = tempResultRepo.saveAll(finalRes)
    fun getTestById(idTest: Int): Test = testRepo.getById(idTest)
    fun setTestIsDone(idTest: Int, idCandidate: Int) = candidateTestRepo.setTestIsDone(idTest,idCandidate)

    fun joinThisTest(idTest: Int, idCandidate: Int): Test? {
        var thisTest: Test = testRepo.getById(idTest)
        if (thisTest != null) {
            val idTest: Int = thisTest.id
            var candidateTest: CandidateTest = candidateTestRepo.findByCandidateIdAndTestId(idCandidate, idTest)
            val timeNow = LocalDateTime.now().toLocalTime().toSecondOfDay()
            val timeTest: Int = thisTest.timeToSecond()
            val timeStart: Int = thisTest.dates!!.toLocalTime().toSecondOfDay()

            if (candidateTest.isDone == 0) {
                if (thisTest.dates!!.toLocalDate().equals(LocalDate.now()) == false) {
                    println("Chua den ngay hoac da qua ngay test")
                    return null
                }
                if (thisTest.dates!!.toLocalTime().isAfter(LocalTime.now())) {
                    println("Chua den gio")
                    return null
                }
                if (timeNow - timeStart > timeTest) {
                    println("Da het thoi gian lam bai")
                    return null
                }
                return thisTest
            } else {
                println("Bai Thi Da Lam xong")
                return null
            }
        }
        return null
    }
//    fun setTestIsDone(idTest: Int,idCandidate: Int ) = testRepo.setTestIsDone(idTest, idCandidate)
}