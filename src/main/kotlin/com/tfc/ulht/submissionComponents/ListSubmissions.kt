/*-
 * Plugin Drop Project
 * 
 * Copyright (C) 2019 Yash Jahit
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tfc.ulht.submissionComponents

import assignmentTable.SubmissionTableColumn
import submissionTable.Professor.SubmissionProfessorTableColumn

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tfc.ulht.Globals
import java.lang.reflect.Type
import data.Submission
import data.Submission_Professor

class ListSubmissions(val assignmentId: String) {

    companion object {
        var selectedSubmission: String = ""
    }

    var type: Type = Types.newParameterizedType(
        List::class.java,
        Submission::class.java
    )

    private val REQUEST_URL = "${Globals.REQUEST_URL}/api/v1/submissionsList"
    private var submissionList = listOf<Submission>()
    private val moshi = Moshi.Builder().build()
    private val submissionJsonAdapter: JsonAdapter<List<Submission>> = moshi.adapter(type)
    val listaSubsGrupo: MutableList<Submission_Professor> = mutableListOf<Submission_Professor>()
    val sub1 = Submission_Professor("1001","101","Diogo Casaca, Pedro Teodoro","3/10 corretos.html","clicavel","1")

    val listaSubsAluno: MutableList<Submission> = mutableListOf<Submission>()
    val subAluno1 = Submission("300","29-01-2020","3/10 corretos.html","","1")

    init {
        listaSubsGrupo.add(sub1)
        listaSubsAluno.add(subAluno1)
        if (Globals.user_type == 0){
            SubmissionProfessorTableColumn(listaSubsGrupo)
        }else{
            SubmissionTableColumn(listaSubsAluno)
        }



        /* Comentado para testes sem DropProject
        val request = Request.Builder()
            .url("$REQUEST_URL/$assignmentId")
            .build()

        Authentication.httpClient.newCall(request).execute().use { response ->
            submissionList = submissionJsonAdapter.fromJson(response.body()!!.source())!!
        }

        submissionList
        showSubmissionList()
        */
    }



    private fun showSubmissionList() {
        SubmissionTableColumn(submissionList)
    }


}