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
    var candidates: Set<Candidate>?,
    @ManyToMany
    @JsonIgnore
    var questions: Set<Question>?,
    @ManyToMany
    @JsonIgnore
    var tests: Set<Test>?
): java.io.Serializable