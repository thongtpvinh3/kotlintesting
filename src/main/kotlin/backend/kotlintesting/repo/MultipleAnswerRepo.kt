package backend.kotlintesting.repo

import backend.kotlintesting.model.MultipleChoiceAnswer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MultipleAnswerRepo: JpaRepository<MultipleChoiceAnswer, Int> {

    @Query("SELECT m FROM MultipleChoiceAnswer m WHERE m.id = ?1 AND m.isTrue = ?2")
    fun findWithIdAndIsTrue(idAnswer: Int, isTrue: Int): MultipleChoiceAnswer?
}