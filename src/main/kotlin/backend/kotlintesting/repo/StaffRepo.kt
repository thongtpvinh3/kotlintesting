package backend.kotlintesting.repo

import backend.kotlintesting.model.Staff
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface StaffRepo:JpaRepository<Staff, Int> {

    @Query("SELECT staff FROM Staff staff WHERE staff.username = :username AND staff.password = :password")
    fun findByUsernameAndPassword(@Param("username") username:String, @Param("password") password: String): Staff?

}