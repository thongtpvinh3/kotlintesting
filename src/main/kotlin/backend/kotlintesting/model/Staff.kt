package backend.kotlintesting.model

import javax.persistence.*

@Entity
@Table(name = "staff")
@kotlinx.serialization.Serializable
data class Staff (
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?,
    @Column
    var name:String?,
    @Column
    var username:String?,
    @Column
    var password:String?,
    @Column
    var email:String?,
    @Column
    var department:String? = "Blockchain Technology",
    @Column
    var avatar:String?
) : java.io.Serializable