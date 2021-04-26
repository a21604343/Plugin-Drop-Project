package com.tfc.ulht.assignmentComponents

import assignmentTable.AssignmentTableColumn
import assignmentTable.Professor.AssignmentProfessorTableColumn
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tfc.ulht.Globals
import data.Assignment
import data.Assignment_Professor
import data.Submission_Professor
import java.lang.reflect.Type


class ListAssignment : AnAction() {

    val type: Type = Types.newParameterizedType(
        List::class.java,
        Assignment::class.java
    )

    private val REQUEST_URL = "${Globals.REQUEST_URL}/api/v1/assignmentList"
    private var assignmentList = listOf<Assignment>()
    private var assignmentListProfessor = listOf<Assignment_Professor>()
    private val moshi = Moshi.Builder().build()
    private val assignmentJsonAdapter: JsonAdapter<List<Assignment>> = moshi.adapter(type)
    //________________________________________________________________/////


    val listaAssignments_P: MutableList<Assignment_Professor> = mutableListOf<Assignment_Professor>()
    val listaSubsGrupo: MutableList<Submission_Professor> = mutableListOf<Submission_Professor>()

    val assi1 = Assignment_Professor("1","Java","29-01-2021",listaSubsGrupo,"ex1.html","sim")
    val assi2 = Assignment_Professor("2","Java","10-02-2021",listaSubsGrupo,"ex2.html","não")


    val listaAssignments_A: MutableList<Assignment> = mutableListOf<Assignment>()


    val assiAluno1 = Assignment("1","Java","30-03-21","enunciado.html")
    val assiAluno2 = Assignment("2","Java","15-04-21","enunciado2.html")



    override fun actionPerformed(e: AnActionEvent) {

        listaAssignments_A.add(assiAluno1)
        listaAssignments_A.add(assiAluno2)
        listaAssignments_P.add(assi1)
        listaAssignments_P.add(assi2)
        if (Globals.user_type == 0){
            AssignmentProfessorTableColumn(listaAssignments_P)
        }else{
            AssignmentTableColumn(listaAssignments_A)
        }



        /*
        if (Authentication.alreadyLoggedIn) {
            val request = Request.Builder()
                .url(REQUEST_URL)
                .build()

            Authentication.httpClient.newCall(request).execute().use { response ->
                assignmentList = assignmentJsonAdapter.fromJson(response.body()!!.source())!!

            }
            showAssingmentTable()

        } else {
            JOptionPane.showMessageDialog(null, "You need to login first!", "Login First", JOptionPane.WARNING_MESSAGE)
        }
        */
    }

    private fun showAssingmentTable() {
        // TODO: Create submissions dialog
        AssignmentTableColumn(assignmentList)
    }



}