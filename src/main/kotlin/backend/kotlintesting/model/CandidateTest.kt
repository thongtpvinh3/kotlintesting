package backend.kotlintesting.model

import javax.persistence.*

@Entity
@Table(name = "candidate_test")
@kotlinx.serialization.Serializable
data class CandidateTest (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Int,
    @Column(name = "id_candidate")
    var candidateId: Int? = 0,
    @Column(name = "id_test")
    var testId: Int? = 0,
    @Column(name = "marks")
    var marks: Double = 0.0
)