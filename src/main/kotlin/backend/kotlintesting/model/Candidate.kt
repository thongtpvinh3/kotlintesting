package backend.kotlintesting.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.random.Random

@Entity
@Table(name = "candidate")
data class Candidate (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id:Int,
    @Column
    var avatar:String? = "",
    @Column
    var name:String = "",
    @Column
    var level: Int? = -1,
    @Column(unique = true)
    var phone:String = "",
    @Column(unique = true)
    var email:String = "",
    @Column
    var position: String = "Blockchain Developer",
    @Column(name = "is_done")
    var isDone: Int = 0,
    @Column(name = "date_test", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    var dates: LocalDateTime? = LocalDateTime.now(),
    @Column(name = "english_mark")
    var englishMark: Double? = 0.0,
    @Column(name = "coding_mark")
    var codingMark: Double? = 0.0,
    @Column(name = "knowledge_mark")
    var knowledgeMark: Double? = 0.0,
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "candidate_test", joinColumns = [JoinColumn(name = "id_candidate")], inverseJoinColumns = [JoinColumn(name = "id_test")])
    @JsonIgnoreProperties(*["hibernateLazyInitializer","handler"])
    var tests: MutableSet<Test>? = null
) : java.io.Serializable {

    fun calculatedTotalTime(tests: MutableSet<Test>?): Int {
        var time = 0
        for (i in this.tests!!) {
            time+=i.timeToSecond()
        }
        return time
    }

    fun generatedId(count: Int): String {
        val r = Random
        val x: Int = r.nextInt(1000,9999)
        val prefix = "CD"
        val newCount = count + 10
        val suffix = x.toString()+newCount.toString()
        return "$prefix$suffix"
    }
}
