package backend.kotlintesting.service

import backend.kotlintesting.model.TempResultCandidate
import backend.kotlintesting.repo.EssayAnswerRepository
import backend.kotlintesting.repo.MultipleAnswerRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisCandidateDoTestCache(@Autowired private val multipleAnswerRepo: MultipleAnswerRepo,
                                @Autowired private val essayAnswerRepo: EssayAnswerRepository,
                                private var valueOps: ValueOperations<String, Any>,
                                private var hashOps: HashOperations<String,Int,Any>) {

    fun RedisCandidateDoTestCache(redisTemplate: RedisTemplate<String, Any>) {
        valueOps = redisTemplate.opsForValue()
        hashOps = redisTemplate.opsForHash()
    }

    fun saveMultipleAnswer(temp: TempResultCandidate) {
        try {
            var idQuestion: Int? = multipleAnswerRepo.getById(temp.idAnswer!!).question!!.id
            hashOps.put("ans",idQuestion!!,temp)
        } catch (e: Exception) {
            println("Ko tim thay cau tra loi")
        }
    }

    fun saveEssayAnswer(temp: TempResultCandidate) {
        try {
            var idQuestion: Int? = essayAnswerRepo.getById(temp.idAnswer!!).question!!.id
            hashOps.put("ans",idQuestion!!,temp)
        } catch (e: Exception) {
            println("Ko tim thay cau tra loi")
        }
    }

    fun getHashCachAns(key: String) = hashOps.entries(key)
    fun delete(key: String) {
        hashOps.operations.delete(key)
    }

    fun cache(key:String,data: Any) {
        valueOps.set(key,data,2,TimeUnit.HOURS)
    }

    fun getCachedValue(key: String): Any? = valueOps.get(key)

    fun deleteCachedValue(key: String) {
        valueOps.operations.delete(key)
    }
}