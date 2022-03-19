package backend.kotlintesting.model

import javax.persistence.*

@Entity
@Table(name = "candidate_test")
data class CandidateTest (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Int,
    @Column(name = "id_candidate")
    var candidateId: Int?,
    @Column(name = "id_test")
    var testId: Int?,
    @Column(name = "marks", columnDefinition = "DOUBLE DEFAULT 0")
    var marks: Double?,
    @Column(name = "is_done", columnDefinition = "INT DEFAULT 0")
    var isDone: Int
) : java.io.Serializable