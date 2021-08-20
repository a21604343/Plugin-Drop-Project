package com.tfc.ulht.assignmentComponents

import assignmentTable.AssignmentTableColumn
import assignmentTable.Professor.AssignmentProfessorTableColumn
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tfc.ulht.Globals
import data.Assignment
import data.Assignment_Professor
import data.Submission_Professor
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.HashMap
import okhttp3.Request
import javax.swing.JOptionPane
import com.tfc.ulht.loginComponents.Authentication
import data.Submission


class ListAssignment : AnAction() {

    val type: Type = Types.newParameterizedType(
        List::class.java,
        Assignment::class.java
    )


    private val REQUEST_URL = "${Globals.REQUEST_URL}/api/v1/teacherAssignmentList"///"${Globals.REQUEST_URL}/api/v1/assignmentList"
    private val REQUEST_URL_STUDENT = "${Globals.REQUEST_URL}/api/v1/assignmentList"
    private var assignmentList = listOf<Assignment>()
    private var assignmentListProfessor = listOf<Assignment_Professor>()
    private val moshi = Moshi.Builder().build()

    private val assignmentJsonAdapter: JsonAdapter<List<Assignment>> = moshi.adapter(type)
    

    //________________________________________________________________/////


    val listaAssignments_P: MutableList<Assignment> = mutableListOf<Assignment>()
    val listaSubsGrupo: MutableList<Submission_Professor> = mutableListOf<Submission_Professor>()
    var dicSubs : HashMap<String,MutableList<Submission_Professor>> = HashMap()

   //val assi1 = Assignment("1","aedFinal","java","29-01-2021",3,"24","",true)
    //val assi2 = Assignment_Professor("2","LP1Final","java","29-01-2021",listaSubsGrupo,"3","24",
    //"",dicSubs,"ex2.html","detalhes","sim")
    //val assi2 = Assignment_Professor("2","Java","10-02-2021",listaSubsGrupo,"ex2.html","não")

    val listaAssignments_A: MutableList<Assignment> = mutableListOf<Assignment>()
    //val assiAluno1 = Assignment("1","Java","30-03-21","enunciado.html")
    //val assiAluno2 = Assignment("2","Java","15-04-21","enunciado2.html")

    fun addListGlobal(listaOriginal : List<Assignment>){
        var existe = false
        for(assi in listaOriginal){
            for(assiGlobal in Globals.listAssignments){
                if(assi.id == assiGlobal.id){
                    existe = true
                }
            }
            if(!existe){
                Globals.listAssignments.add(assi)
                existe = false
            }
        }
    }

     fun addAssiHash(listaAssi : List<Assignment>){
         for(assi in listaAssi){
             if(!(Globals.hashAssiSub.containsKey(assi))){
                    var listaSubs : List<Submission> = emptyList()
                    Globals.hashAssiSub.put(assi,listaSubs)
             }
         }
     }

    fun updateLastSubDate(){
        for(assi in Globals.listAssignments){

        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        if (!Globals.taLigado) {

            //listaAssignments_A.add(assiAluno1)
            //listaAssignments_A.add(assiAluno2)
           // listaAssignments_P.add(assi1)

            //listaAssignments_P.add(assi2)
            addListGlobal(listaAssignments_P)
            if (Globals.user_type == 0) {
                AssignmentProfessorTableColumn(Globals.listAssignments)
            } else {
                AssignmentTableColumn(listaAssignments_A)
            }
        } else {
        if (Authentication.alreadyLoggedIn) {

            if(Globals.user_type == 0){
                val request = Request.Builder()
                    .url(REQUEST_URL)
                    .build()
                Authentication.httpClient.newCall(request).execute().use { response ->
                    assignmentList = assignmentJsonAdapter.fromJson(response.body()!!.source())!!
                    Globals.listAssignmentsDP = assignmentList
                }
            }else{
                val request = Request.Builder()
                    .url(REQUEST_URL_STUDENT)
                    .build()
                Authentication.httpClient.newCall(request).execute().use { response ->
                    assignmentList = assignmentJsonAdapter.fromJson(response.body()!!.source())!!
                    Globals.listAssignmentsDP = assignmentList
                }
            }
            showAssingmentTable()

        } else {
            JOptionPane.showMessageDialog(null, "You need to login first!", "Login First", JOptionPane.WARNING_MESSAGE)
        }

        }
    }

    private fun showAssingmentTable() {
        // TODO: Create submissions dialog
        if(Globals.user_type == 0){
            AssignmentProfessorTableColumn(assignmentList)
        }else{
            AssignmentTableColumn(assignmentList)
        }

    }



}