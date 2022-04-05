package backend.kotlintesting.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class UploadFileService {

    private val storageFolder: Path = Paths.get("uploads")

    init {
        try {
            Files.createDirectories(storageFolder)
        } catch (e: Exception) {
            throw RuntimeException("K the tao thu muc!", e)
        }
    }

    @Throws(java.lang.Exception::class)
    fun upload(file: MultipartFile) {
        try {
            val destinationFilePath = storageFolder.resolve(Paths.get(file.originalFilename)).normalize()
                .toAbsolutePath()
            if (destinationFilePath.parent != storageFolder.toAbsolutePath()) {
                throw RuntimeException("Luu file ngoai!!")
            }
            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream,
                    destinationFilePath,
                    StandardCopyOption.REPLACE_EXISTING
                )
            }
        } catch (e: java.lang.Exception) {
            throw RuntimeException("ko the luu file!", e)
        }
    }
}