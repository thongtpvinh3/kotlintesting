package backend.kotlintesting.repo

import backend.kotlintesting.model.Candidate
import backend.kotlintesting.model.CandidateTest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface CandidateTestRepo: JpaRepository<CandidateTest, Int> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE CandidateTest ct SET is_done = 1 WHERE ct.candidateId = :idCandidate AND ct.testId = :idTest")
    fun setTestIsDone(@Param("idTest") idTest:Int, @Param("idCandidate") idCandidate: Int)

    @Query("SELECT candidatetest FROM CandidateTest candidatetest WHERE candidatetest.candidateId = :idCandidate AND candidatetest.testId = :idTest")
    fun findByCandidateIdAndTestId(@Param("idCandidate") idCandidate: Int, @Param("idTest") idTest: Int): CandidateTest

    @Query("SELECT ct FROM CandidateTest ct WHERE ct.candidateId = :idCandidate")
    fun findByCandidateId(@Param("idCandidate") idCandidate: Int): Set<CandidateTest>
}