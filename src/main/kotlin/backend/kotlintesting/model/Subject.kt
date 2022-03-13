package backend.kotlintesting.model

import javax.persistence.*

@Entity
@Table(name = "subjects")
data class Subject (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id:Int?,
    @Column
    var name:String?

) : java.io.Serializable
