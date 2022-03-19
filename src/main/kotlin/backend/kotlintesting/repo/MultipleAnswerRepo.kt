package backend.kotlintesting.repo

import backend.kotlintesting.model.MultipleChoiceAnswer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MultipleAnswerRepo: JpaRepository<MultipleChoiceAnswer, Int> {

    @Query("SELECT * FROM mc_answer m WHERE m.id = :idAnswer AND m.istrue = :isTrue", nativeQuery = true)
    fun findWithIdAndisTrue(
        @Param("idAnswer") idAnswer: Int?,
        @Param("isTrue") isTrue: Int?
    ): MultipleChoiceAnswer?
}