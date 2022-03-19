package backend.kotlintesting.repo

import backend.kotlintesting.model.Candidate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CandidateRepository: JpaRepository<Candidate, Int> {

    @Query("SELECT candidate FROM Candidate candidate WHERE candidate.name like %:name%")
    fun findByName(@Param("name") name: String) : List<Candidate>

    @Query("SELECT candidate FROM Candidate candidate WHERE candidate.email like %:email%")
    fun findByEmail(@Param("email") email:String) : List<Candidate>

    @Query("SELECT candidate FROM Candidate candidate WHERE candidate.phone like %:phone%")
    fun findByPhone(@Param("phone") phone:String) : List<Candidate>

//    @Query("SELECT c FROM Candidate c WHERE c.")
//    fun findByTests(idTest: Int): List<Candidate>?


}