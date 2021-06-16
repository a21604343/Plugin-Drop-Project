package data

import com.squareup.moshi.JsonClass
import java.util.*
import kotlin.collections.HashMap

@JsonClass(generateAdapter = true)
data class Assignment_Professor(
    val id: String,
    var nome: String,
    val language: String,
    var dataEntrega: String?,
    val subs_Grupo: MutableList<Submission_Professor>?,
    var totalGroups: String?,
    var totalSubs: String?,
    var dateLastSubmission: String = "01-01-97",
    var listSubsId: HashMap<String,MutableList<Submission_Professor>> = HashMap(),
    val enunciado: String,
    var info: String, // equivalente olho no DP
    val ativo: String
    //val ativo: Boolean
){
    fun atualizar(){
        if(listSubsId.size != 0){
            this.totalGroups = listSubsId.size.toString()
            var count = 0
            var newestDate = ""
            for(id in listSubsId.values){
                for(sub in id){
                    var dateSplit = this.dateLastSubmission.split("-")
                    var subDateSplit = sub.date.split("-")
                    if(subDateSplit[2] >= dateSplit[2]){
                        this.dateLastSubmission = sub.date
                    }

                }
                count += id.size

            }

            this.totalSubs = count.toString()

        }

    }
}