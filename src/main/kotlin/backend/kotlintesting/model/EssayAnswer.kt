package backend.kotlintesting.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "e_answer")
data class EssayAnswer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Int?,
    @Column
    var answer:String? = "",
    @OneToOne
    @JoinColumn(name = "id_question")
//    @Cascade(CascadeType.ALL)
    @JsonIgnore
    var question: Question? = null

): java.io.Serializable