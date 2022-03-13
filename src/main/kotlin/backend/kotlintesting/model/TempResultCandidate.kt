package backend.kotlintesting.model

import javax.persistence.*

@Entity
@Table(name = "result_candidate")
data class TempResultCandidate (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        var id:Int?,
        @Column(name = "id_answer")
        var idAnswer: Int?,
        @Column
        var type: Int?,
        @Column
        var answer:String?,
        @Column(name = "id_candidate")
        var idCandidate: Int?
) : java.io.Serializable