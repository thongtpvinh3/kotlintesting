package backend.kotlintesting

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["backend.kotlintesting"])
class VoiApplication

fun main(args: Array<String>) {
	runApplication<VoiApplication>(*args)
}
