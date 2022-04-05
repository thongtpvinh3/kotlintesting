package backend.kotlintesting.repo

import backend.kotlintesting.model.DisplayCandidate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface CandiDisplayRepo: JpaRepository<DisplayCandidate, Int> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE Candidate c SET isDone = 1 WHERE c.id = :idCandidate")
    fun setIsDone(@Param("idCandidate") idCandidate: Int)
}