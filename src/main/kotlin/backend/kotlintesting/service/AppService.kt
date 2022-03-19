package backend.kotlintesting.service

import backend.kotlintesting.model.Candidate
import backend.kotlintesting.model.Staff
import backend.kotlintesting.model.Test
import backend.kotlintesting.repo.CandidateRepository
import backend.kotlintesting.repo.StaffRepo
import backend.kotlintesting.repo.TestRepo
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class AppService(val staffRepo: StaffRepo, val candidateRepo: CandidateRepository, val testRepo: TestRepo) {

    fun findByUsernameAndPassword(username: String,password: String): Staff? = staffRepo.findByUsernameAndPassword(username,password)
    fun login(username:String,password:String) : Boolean {
        if(staffRepo.findByUsernameAndPassword(username,password)!=null) {
            println("Dang nhap thanh cong")
            println(staffRepo.findByUsernameAndPassword(username,password))
            return true
        }
        return false
    }

//    fun joinByTestCode(code: String?): Boolean {
//        val findTest: Test? = testRepo.findByCodeTest(code!!)
//        println(findTest)
//        if (findTest != null) {
//            val timeNow = LocalDateTime.now().toLocalTime().toSecondOfDay()
//            val timeTest: Int = findTest.timeToSecond()
//            val timeStart: Int = findTest.dates!!.toLocalTime().toSecondOfDay()
//            return if (findTest.isDone == 0) {
//                if (findTest.dates!!.toLocalDate().equals(LocalDate.now()) === false) {
//                    println("Chua den ngay hoac da qua ngay test")
//                    return false
//                }
//                if (findTest.dates!!.toLocalTime().isAfter(LocalTime.now())) {
//                    println("Chua den gio")
//                    return false
//                }
//                if (timeNow - timeStart > timeTest) {
//                    println("Da het thoi gian lam bai")
//                    return false
//                }
//                return true
//            } else {
//                println("Bai Thi Da Lam xong")
//                return false
//            }
//        }
//        return false
//    }

    fun getJoinCandidate(idCandidate: Int) :Candidate? = candidateRepo.getById(idCandidate)
    fun joinByIdCandidate(idCandidate: Int): Boolean {
        if (candidateRepo.getById(idCandidate)!=null) {
            return true
        }
        return false
    }

}