package data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Assignment_Professor(
    val id: String,
    val language: String,
    val date: String?,
    val subs_Grupo: MutableList<Submission_Professor>,
    val enunciado: String,
    val ativo: String
    //val ativo: Boolean
)