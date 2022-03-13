package backend.kotlintesting.repo

import backend.kotlintesting.model.Test
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface TestRepo: JpaRepository<Test, Int> {

    fun findBySubject(subject: Int) : List<Test>
    fun findByIsDone(isDone: Int): List<Test>
    fun findByLevel(level: Int): List<Test>?

    @Query("SELECT test FROM Test test WHERE test.name LIKE %:name%")
    fun findByName(@Param("name") name: String): List<Test>?

    @Query("SELECT test FROM Test test WHERE test.codeTest LIKE :code")
    fun findByCodeTest(@Param("code") code: String): Test?
    @Transactional
    @Modifying
    @Query(value = "UPDATE Test test SET is_done = 1 WHERE id = ?1", nativeQuery = true)
    fun setTestIsDone(id: Int)


}