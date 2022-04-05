package backend.kotlintesting.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "candidate")
data class DisplayCandidate(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id:Int,
    @Column
    var name:String,
    @Column
    var avatar:String? = "",
    @Column
    var level: Int,
    @Column
    var phone:String,
    @Column
    var email:String,
    @Column(name = "is_done")
    var isDone: Int = 0,
    @Column(name = "date_test", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    var dates: LocalDateTime? = LocalDateTime.of(1999,12,14,0,0,0),
    @Column(name = "english_mark")
    var englishMark: Double? = 0.0,
    @Column(name = "coding_mark")
    var codingMark: Double? = 0.0,
    @Column(name = "knowledge_mark")
    var knowledgeMark: Double? = 0.0,
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "candidate_test", joinColumns = [JoinColumn(name = "id_candidate")], inverseJoinColumns = [JoinColumn(name = "id_test")])
    @JsonIgnore
    var tests: MutableSet<Test>? = mutableSetOf()
): java.io.Serializable {
    fun calculatedTotalTime(tests: MutableSet<Test>?): Int {
        var time = 0
        for (i in this.tests!!) time+=i.timeToSecond()
        return time
    }
}
