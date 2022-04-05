package backend.kotlintesting.repo

import backend.kotlintesting.model.CandidateTest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CandidateTestRepo: JpaRepository<CandidateTest, Int> {

    @Query("SELECT ct FROM CandidateTest ct WHERE ct.candidateId = :idCandidate AND ct.testId = :idTest")
    fun findByCandidateIdAndTestId(@Param("idCandidate") idCandidate: Int, @Param("idTest") idTest: Int): CandidateTest

    @Query("SELECT ct FROM CandidateTest ct WHERE ct.candidateId = :idCandidate")
    fun findByCandidateId(@Param("idCandidate") idCandidate: Int): Set<CandidateTest>
}