package backend.kotlintesting.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import javax.persistence.*

@Entity
@Table(name = "mc_answer")
data class MultipleChoiceAnswer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id:Int?,
    @Column(name = "istrue")
    var isTrue:Int?,
    @Column
    var answer:String?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    var question: Question?
) : java.io.Serializable