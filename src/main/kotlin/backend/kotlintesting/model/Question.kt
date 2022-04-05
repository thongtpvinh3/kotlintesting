package backend.kotlintesting.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
@Table(name = "question")
data class Question (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id:Int? = null,
    @Column
    var type:Int? = null,
    @Column
    var subject:Int? = null,
    @Column
    var content:String? = null,
    @Column
    var level:Int? = null,
    @Column
    var image:String? = null,
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    @JoinTable(name = "test_question", joinColumns = [JoinColumn(name = "id_question")], inverseJoinColumns = [JoinColumn(name = "id_test")])
    var tests: MutableSet<Test>? = null,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "question", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    var multipleChoiceAnswer: MutableList<MultipleChoiceAnswer>? = null,
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "question", cascade = [CascadeType.ALL])
    @JsonIgnoreProperties("hibernateLazyInitializer", "handler")
    var essayAnswer: EssayAnswer? = null,
): java.io.Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Question

        if (subject != other.subject) return false
        if (content != other.content) return false
        if (level != other.level) return false

        return true
    }

    override fun hashCode(): Int {
        var result = subject ?: 0
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + (level ?: 0)
        return result
    }
}