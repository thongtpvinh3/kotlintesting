package backend.kotlintesting.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.serialization.Contextual
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "test")
data class Test (
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int,
    @Column
    var subject:Int,
    @Column
    var level:Int,
    @Column
    var name: String,
    @Column(name = "date_test")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    var dates:LocalDateTime?,
    @Column(name = "time")
    @DateTimeFormat(pattern = "HH:mm:ss", iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    var times:LocalTime?,
    @Column(name = "code_test", unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var codeTest:String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    var candidate:Candidate?,
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(*[ "hibernateLazyInitializer", "handler" ])
    @JoinTable(name = "test_question",
    joinColumns = [JoinColumn(name = "id_test")], inverseJoinColumns = [JoinColumn(name = "id_question")])
    var questions: Set<Question>?
 ) : java.io.Serializable {
    fun timeToSecond(): Int {
        return LocalTime.of(times!!.hour, times!!.minute, times!!.second).toSecondOfDay()
    }
}