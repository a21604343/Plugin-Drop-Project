package data

import com.squareup.moshi.JsonClass
import java.util.*



@JsonClass(generateAdapter = true)
data class Submission_Professor (
    val submissionId: String,
    val GroupId: String?,
    val authorsName: String?,
    val lastReport: String?,
    val downloadLast: String?,
    val assignmentId: String
)