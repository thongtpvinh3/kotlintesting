package backend.kotlintesting.repo

import backend.kotlintesting.model.TempResultCandidate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TempResultRepo: JpaRepository<TempResultCandidate, Int> {

    @Query(value = "SELECT * FROM result_candidate r WHERE r.id_candidate =:id AND type =:type", nativeQuery = true)
    fun getAnswerOfCandidate(@Param("id") idCandidate:Int,@Param("type") type: Int): MutableList<TempResultCandidate>
}