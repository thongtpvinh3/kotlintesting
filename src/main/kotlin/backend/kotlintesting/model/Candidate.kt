package backend.kotlintesting.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
@Table(name = "candidate")
data class Candidate (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id:Int,
    @Column
    var name:String = "",
    @Column
    var level: Int = 0,
    @Column
    var phone:String,
    @Column
    var email:String,
    @Column(name = "english_mark")
    var englishMark: Double? = 0.0,
    @Column(name = "coding_mark")
    var codingMark: Double? = 0.0,
    @Column(name = "knowledge_mark")
    var knowledgeMark: Double? = 0.0,
    @OneToMany(mappedBy = "candidate")
    @Cascade(CascadeType.ALL)
    @JsonIgnoreProperties(*["hibernateLazyInitializer","handler"])
    var tests: MutableList<Test>?,
    @Column
    var avatar:String?
) : java.io.Serializable