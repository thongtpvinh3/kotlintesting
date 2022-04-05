package backend.kotlintesting.repo

import backend.kotlintesting.model.TempResultCandidate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TempResultRepo: JpaRepository<TempResultCandidate, Int> {

    @Query("SELECT r FROM TempResultCandidate r WHERE r.idCandidate = ?1 AND r.type = ?2 AND  r.idTest = ?3")
    fun getAnswerOfCandidate(idCandidate: Int, type: Int, idTest: Int): MutableList<TempResultCandidate>
}