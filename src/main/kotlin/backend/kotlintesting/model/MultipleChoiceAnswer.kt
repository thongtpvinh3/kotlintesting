package backend.kotlintesting.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "mc_answer")
data class MultipleChoiceAnswer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id:Int? = -1,
    @Column(name = "istrue")
    var isTrue:Int = 0,
    @Column
    var answer:String? = "",
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    var question: Question? = null
) : java.io.Serializable