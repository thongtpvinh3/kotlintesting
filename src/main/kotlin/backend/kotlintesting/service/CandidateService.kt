package backend.kotlintesting.service

import backend.kotlintesting.model.TempResultCandidate
import backend.kotlintesting.model.Test
import backend.kotlintesting.repo.CandidateRepository
import backend.kotlintesting.repo.CandidateTestRepo
import backend.kotlintesting.repo.TempResultRepo
import backend.kotlintesting.repo.TestRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CandidateService(@Autowired private val candiRepo: CandidateRepository,
@Autowired private val tempResultRepo: TempResultRepo,@Autowired private val testRepo: TestRepo, @Autowired private val candidateTestRepo: CandidateTestRepo) {

    fun saveAllResult(finalRes: MutableList<TempResultCandidate>) = tempResultRepo.saveAll(finalRes)
    fun getTestById(idTest: Int): Test = testRepo.getById(idTest)
    fun setTestIsDone(idTest: Int, idCandidate: Int) = candidateTestRepo.setTestIsDone(idTest,idCandidate)

//    fun setTestIsDone(idTest: Int,idCandidate: Int ) = testRepo.setTestIsDone(idTest, idCandidate)


}