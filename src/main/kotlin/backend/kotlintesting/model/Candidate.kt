package backend.kotlintesting.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
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
    @Column(name = "english_mark", columnDefinition = "DOUBLE DEFAULT 0")
    var englishMark: Double?,
    @Column(name = "coding_mark", columnDefinition = "DOUBLE DEFAULT 0")
    var codingMark: Double?,
    @Column(name = "knowledge_mark", columnDefinition = "DOUBLE DEFAULT 0")
    var knowledgeMark: Double?,
    @OneToMany(mappedBy = "candidate")
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonIgnoreProperties(*["hibernateLazyInitializer","handler"])
    var tests: Set<Test>?,
    @Column
    var avatar:String?
) : java.io.Serializable