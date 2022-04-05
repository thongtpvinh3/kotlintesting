package backend.kotlintesting.repo

import backend.kotlintesting.model.Levels
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LevelRepository: JpaRepository<Levels, Int>