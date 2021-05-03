package data

import com.squareup.moshi.JsonClass
import java.util.*



@JsonClass(generateAdapter = true)
data class Submission_Professor (
    val submissionId: String,
    val GroupId: String,
    val authorsName: String?,
    var date: Date,
    var status: String,
    var report: String?,
    var tempo: String?,
    var indicares: Indicadores,
    var dateLastSubGroupsExemplo: String?,
    val downloadLast: String?,
    var isFinal: Boolean,
    val assignmentId: String
)