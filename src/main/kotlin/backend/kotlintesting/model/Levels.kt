package backend.kotlintesting.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "levels")
data class Levels(
    @Id
    @GeneratedValue
    @Column
    var id:Int?,
    @Column
    var name:String?,
    @ManyToMany
    @JsonIgnore
    var candidates: List<Candidate>?,
    @ManyToMany
    @JsonIgnore
    var questions: List<Question>?,
    @ManyToMany
    @JsonIgnore
    var tests: List<Test>?
): java.io.Serializable