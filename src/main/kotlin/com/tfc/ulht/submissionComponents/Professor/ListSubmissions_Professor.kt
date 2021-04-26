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

package com.tfc.ulht.submissionComponents.Professor

import assignmentTable.SubmissionTableColumn
import submissionTable.Professor.SubmissionProfessorTableColumn
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tfc.ulht.Globals
import com.tfc.ulht.loginComponents.Authentication
import java.lang.reflect.Type
import data.Submission
import data.Submission_Professor
import okhttp3.Request

class ListSubmissions_Professor(val assignmentId: String) {

    companion object {
        var selectedSubmission: String = ""
    }

    var type: Type = Types.newParameterizedType(
        List::class.java,
        Submission_Professor::class.java
    )

    private val REQUEST_URL = "${Globals.REQUEST_URL}/api/v1/submissionsList"
    private var submissionList_P = listOf<Submission_Professor>()
    private val moshi = Moshi.Builder().build()
    private val submissionJsonAdapter: JsonAdapter<List<Submission_Professor>> = moshi.adapter(type)

    private var listSubmissoes_P: MutableList<Submission_Professor> = mutableListOf<Submission_Professor>()

    var sub1 = Submission_Professor("10", "3","Diogo,Tiago","www.report/24","www.report/24", "34")
    var sub2 = Submission_Professor("11", "6","Diogo,Afonso","www.report/32","www.report/32", "34")
    var sub3 = Submission_Professor("12", "6","Diogo,Afonso","www.report/33","www.report/33", "34")
    var sub4 = Submission_Professor("13", "13","João,Diana","www.report/45","www.report/45", "34")

    init {
        listSubmissoes_P.add(sub1)
        listSubmissoes_P.add(sub2)
        listSubmissoes_P.add(sub3)
        listSubmissoes_P.add(sub4)
        SubmissionProfessorTableColumn(listSubmissoes_P)

    }
    /*
    init {
        val request = Request.Builder()
            .url("$REQUEST_URL/$assignmentId")
            .build()

        Authentication.httpClient.newCall(request).execute().use { response ->
            submissionList_P = submissionJsonAdapter.fromJson(response.body()!!.source())!!
        }

        submissionList_P
        showSubmissionList()
    }
       */
    private fun showSubmissionList() {
        //SubmissionTableColumn(submissionList_P)
        SubmissionProfessorTableColumn(listSubmissoes_P)
    }


}