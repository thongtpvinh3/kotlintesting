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
    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "candidate_test", joinColumns = [JoinColumn(name = "id_candidate")], inverseJoinColumns = [JoinColumn(name = "id_test")])
    @JsonIgnoreProperties(*["hibernateLazyInitializer","handler"])
    var tests: MutableSet<Test>?,
    @Column
    var avatar:String?
) : java.io.Serializable