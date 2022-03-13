package backend.kotlintesting.service

import backend.kotlintesting.model.TempResultCandidate
import backend.kotlintesting.repo.CandidateRepository
import backend.kotlintesting.repo.TempResultRepo
import backend.kotlintesting.repo.TestRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CandidateService(@Autowired private val candiRepo: CandidateRepository,
@Autowired private val tempResultRepo: TempResultRepo,@Autowired private val testRepo: TestRepo) {

    fun saveAllResult(finalRes: MutableList<TempResultCandidate>) = tempResultRepo.saveAll(finalRes)

    fun setTestIsDone(id: Int) = testRepo.setTestIsDone(id)


}