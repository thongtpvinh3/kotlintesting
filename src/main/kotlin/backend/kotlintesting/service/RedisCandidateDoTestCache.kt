package backend.kotlintesting.service

import backend.kotlintesting.model.TempResultCandidate
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisCandidateDoTestCache(redisTemplate: RedisTemplate<String, Any>) {

    private val hashOps: HashOperations<String, String, TempResultCandidate>

    init {
        hashOps = redisTemplate.opsForHash()
    }

    fun saveAnswer(temp: TempResultCandidate, idQuestion: Int, idCandidate: Int) {
        try {
            val keyGen = "$idQuestion:$idCandidate"
            println(keyGen)
            hashOps.put("ans",keyGen,temp)
            println(idQuestion)
        } catch (e: Exception)  {
            println("Ko tim thay cau tra loi")
        }
    }

    fun deleteCandidateCacheAnswer(key: String, idCandidate: Int) {
        val cacheAns = hashOps.entries(key)
        val toRemove: MutableMap<String, TempResultCandidate> = mutableMapOf()
        for (e in cacheAns) {
            val thisKey = e.key
            val str = thisKey.split(":")
            if(str[1] == "$idCandidate") {
                toRemove[thisKey] = e.value
            }
        }
        cacheAns.entries.removeAll(toRemove.entries)
        hashOps.operations.delete(key)
        hashOps.putAll("ans",cacheAns)
    }

    fun getHashCachAns(key: String): Map<String, TempResultCandidate> = hashOps.entries(key)
    fun deleteAll(key: String) {
        hashOps.operations.delete(key)
    }
}