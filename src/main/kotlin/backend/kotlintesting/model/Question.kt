package backend.kotlintesting.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Entity
@Table(name = "question")
data class Question (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id:Int?,
    @Column
    var type:Int?,
    @Column
    var subject:Int?,
    @Column
    var content:String?,
    @Column
    var level:Int?,
    @Column
    var image:String?,
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    @JoinTable(name = "test_question", joinColumns = [JoinColumn(name = "id_question")], inverseJoinColumns = [JoinColumn(name = "id_test")])
    var tests: MutableSet<Test>?,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "question", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnoreProperties(*["hibernateLazyInitializer", "handler"])
    var multipleChoiceAnswer: MutableList<MultipleChoiceAnswer>?,
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "question", cascade = [CascadeType.ALL])
    @JsonIgnoreProperties(*["hibernateLazyInitializer", "handler"])
    var essayAnswer: EssayAnswer?

): java.io.Serializable