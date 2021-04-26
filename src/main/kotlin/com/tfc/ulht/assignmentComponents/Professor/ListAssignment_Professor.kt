package com.tfc.ulht.assignmentComponents.Professor

import assignmentTable.AssignmentTableColumn
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tfc.ulht.Globals
import com.tfc.ulht.loginComponents.Authentication
import data.Assignment
import data.Assignment_Professor
import okhttp3.Request
import java.lang.reflect.Type
import javax.swing.JOptionPane


class ListAssignment_Professor : AnAction() {

    val type: Type = Types.newParameterizedType(
        List::class.java,
        Assignment_Professor::class.java
    )

    private val REQUEST_URL = "${Globals.REQUEST_URL}/api/v1/assignmentList"
    private var assignmentList = listOf<Assignment_Professor>()
    private val moshi = Moshi.Builder().build()
    private val assignmentJsonAdapter: JsonAdapter<List<Assignment_Professor>> = moshi.adapter(type)

    val listaAssignments_P: MutableList<Assignment_Professor> = mutableListOf<Assignment_Professor>()

   // val assi1 = Assignment_Professor("1","Java","29-01-2021","ex1.html",true)


    override fun actionPerformed(e: AnActionEvent) {
        //listaAssignments_P.add(assi1)
        /*AssignmentTableColumn(assignmentList)*/
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
        //AssignmentTableColumn(assignmentList)
    }



}