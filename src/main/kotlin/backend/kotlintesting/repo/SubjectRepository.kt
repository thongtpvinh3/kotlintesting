package backend.kotlintesting.repo

import backend.kotlintesting.model.Subject
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface SubjectRepository: JpaRepository<Subject,Int> {
}