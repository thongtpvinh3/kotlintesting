package backend.kotlintesting

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VoiApplication

fun main(args: Array<String>) {
	runApplication<VoiApplication>(*args)
}
