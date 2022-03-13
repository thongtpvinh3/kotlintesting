package backend.kotlintesting.repo

import backend.kotlintesting.model.Question
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepo: JpaRepository<Question, Int> {

    @Query("SELECT question FROM Question question WHERE question.subject = :subject")
    fun findBySubject(@Param("subject") subject: Int): MutableList<Question>?

    @Query("SELECT question FROM Question question WHERE question.level = :level")
    fun findByLevel(@Param("level") level: Int): MutableList<Question>?

    @Query("SELECT q.type FROM question q WHERE id=:id", nativeQuery = true)
    fun getTypeById(@Param("id") idQuestion: Int): Int

    @Query("SELECT question FROM Question question WHERE question.type = :type")
    fun findByType(@Param("type") type: Int): MutableList<Question>?

}