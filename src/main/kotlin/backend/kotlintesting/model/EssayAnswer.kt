package backend.kotlintesting.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import javax.persistence.*

@Entity
@Table(name = "e_question")
data class EssayAnswer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Int?,
    @Column
    var answer:String?,
    @Column
    var mark: Double?,
    @OneToOne
    @JsonIgnore
    var question: Question?

): java.io.Serializable