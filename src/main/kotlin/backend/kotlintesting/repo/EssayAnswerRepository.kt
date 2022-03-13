package backend.kotlintesting.repo

import backend.kotlintesting.model.EssayAnswer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EssayAnswerRepository: JpaRepository<EssayAnswer, Int> {

}