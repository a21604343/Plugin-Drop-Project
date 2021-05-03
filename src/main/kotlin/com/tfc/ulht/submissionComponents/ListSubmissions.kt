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

import assignmentTable.SubmissionProfessorTableColumn
import assignmentTable.SubmissionTableColumn


import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tfc.ulht.Globals
import com.tfc.ulht.loginComponents.Authentication
import data.Indicadores
import java.lang.reflect.Type
import data.Submission
import data.Submission_Professor

import java.util.*
import okhttp3.Request

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
    private val submissionProfessorJsonAdapter: JsonAdapter<List<Submission_Professor>> = moshi.adapter(type)
    private val submissionJsonAdapter: JsonAdapter<List<Submission>> = moshi.adapter(type)


    var listaSubsAluno: MutableList<Submission> = mutableListOf<Submission>()
    val listaSubsGroup: MutableList<Submission_Professor> = mutableListOf<Submission_Professor>()
    var indicadoresTeste = Indicadores(true,true,true,"1/5","4/9","3/12","123ms","16%")
    var sub1 = Submission_Professor("1001", "3","Diogo,Tiago",
        Date(2021,4,12,3,2,1),"validated","www.report/24","34",indicadoresTeste, "17/03/21","www",false,"1")
    //val subAluno1 = Submission("300","29-01-2020","3/10 corretos.html","","1")
    var sub2 = Submission_Professor("1002", "4","Bernardo Tavares,Tiago Abreu",
        Date(2021,4,12,3,2,1),"validated","www.report/24","34",indicadoresTeste, "17/03/21","www",false,"1")
    var sub3 = Submission_Professor("1003", "4","Bernardo Tavares,Tiago Abreu",
        Date(2021,4,12,3,2,1),"validated","www.report/24","34",indicadoresTeste, "17/03/21","www",false,"2")


    fun createReport(submissao : Submission_Professor) : String{
        var authors = submissao.authorsName?.split(",")

        var report = "<html>" +
        "<head>" +
       " <title>Drop Project - Build report</title>" +
        "<style>"+
        "table, th, td {"+
        "border: 1px solid black;"+
        "padding: 5px"+
        "}"+
         "</style"+
        "<H1 class=\"page-header\"> Build report for submission </H1>" +
        "<h3> Assignment:  ${submissao.assignmentId}  | Last commit: ${submissao.date} </h3>" +
        "</head>" +
        "<body>" +
        "<div>"+
        "<h2>Group elements</h2>"+
        "<table >"+
        "<tbody>"+
        "<tr>"+
        "<td>"+
                "<span>${authors?.get(0)}</span>"+
        "<span class=\"label label-primary\"> <b>Submitter</b></span>"+
       " </td>"+
        "<td>Student 1</td>"+
        "</tr>"+
        "<tr>"+
        "<td>"+
        "<span>${authors?.get(1)}</span>"+
        "</td"+
        "<td> Student 2 </td>"+
        "<tr>"+
        "</tbody>"+
        "</table>"+
        "<h2>Results summary</h2>"+
        "<table>"+
        "<tbody>"+
        "<tr>"+
        "<td>"+
        "<span>Project Structure</span>"+
        "</td>"+
        "<td>"+
        "<!--<img src=\"../img/if_sign-check_299110.png\"> -->"+


        "</td>"+

       " </tr>"+
        "<tr>"+
        "<td>"+
        "<span>Compilation</span>"+

        "</td>"+
        "<td>"+
        "<!--<img src=\"../img/if_sign-check_299110.png\">-->"+

        "</td>"+

        "</tr>"+
        "<tr>"+
        "<td>"+
        "<span>Code Quality (Checkstyle)</span>"+

        "</td>"+
        "<td>"+
        "<!--<img src=\"../img/if_sign-check_299110.png\"> -->"+
        "<br>"+
        "</td>"+
        "</tr>"+
        "<tr>"+
        "<td>"+
        "<span>Student Unit Tests</span>"+
        "<br>"+
        "</td>"+
        "<td>"+
        "<h4 style=\"margin-top: 3px\">"+
        "<span class=\"label label-success\">${submissao.indicares.studentTest}</span>"+
        "</h4>"+
        "</td>"+
        "</tr>"+
                "<tr>"+
                "<td>"+
                "<span>Teacher Unit Tests</span>"+
                "<br>"+
                "</td>"+
                "<td>"+
                "<h4 style=\"margin-top: 3px\">"+
                "<span class=\"label label-success\">${submissao.indicares.teacherTest}</span>"+
                "</h4>"+
                "</td>"+
                "</tr>"+
                "<tr>"+
                "<td>"+
                "<span>Teacher Hidden Unit Tests</span>"+
                "<br>"+
                "</td>"+
                "<td>"+
                "<h4 style=\"margin-top: 3px\">"+
                "<span class=\"label label-success\">${submissao.indicares.privateTest}</span>"+
                "</h4>"+
                "</td>"+
                "</tr>"+
        "</tbody>"+
        "</table>"+
        "<br>"+
       " <!--<table class=\"table table-bordered\" -->"+
        "<!--<thead>-->"+
        "<!--<tr>-->"+
        "<!--</tr>-->"+

        "<!--</thead>-->"+
        "<!--<tbody>-->"+
        "<!--</tr>-->"+
       " <!--</tbody>-->"+
        "<!--</table>-->"+
        "<br>"+
        "<table class=\"table table-bordered\">"+
        "<thead>"+
        "<tr>"+
        "<th>JUnit Summary (Teacher Tests)</th>"+
        "</tr>"+
        "</thead>"+
        "<tbody>"+
        "<tr>"+
        "<td>Coverage ${submissao.indicares.coverage} (only visible to teacher</td>"+
        "</tr>"+
        "<tr>"+
        "<td>Tests run: 2, Failures: 0, Errors: 0, Time elapsed: ${submissao.indicares.execTime} sec</td>"+
        "</tr>"+

     "   </tbody>"+
       " </table>"+
        "<br>"+

       " </div>"+
       " </body>"+
      "  </html>"

        return report

    }

    fun updateAllReports(listaSubs : MutableList<Submission_Professor>){
        for(sub in listaSubs){
            sub.report = createReport(sub)
        }
    }

    fun checkID(listaSubs : MutableList<Submission_Professor>) : MutableList<Submission_Professor>{
        var listaFinal : MutableList<Submission_Professor> = mutableListOf()
        for(sub in listaSubs){
            if (sub.assignmentId == assignmentId){
                listaFinal.add(sub)
            }
        }
        return listaFinal

    }

    fun addSubsToListGlobal (listaSubsOriginal : MutableList<Submission_Professor>){
        for (subOriginal in listaSubsOriginal){
            for(assiGlobal in Globals.listAssignments){
                if(subOriginal.assignmentId == assiGlobal.id){
                    for (subs in assiGlobal.listSubsId.keys){
                        if (subs == subOriginal.GroupId){
                            var existe = false
                            for (sub in assiGlobal.listSubsId.get(subOriginal.GroupId)!!){
                                if (sub.submissionId == subOriginal.submissionId){
                                  existe = true
                                }
                            }
                            if (!existe){

                                assiGlobal.listSubsId.get(subOriginal.GroupId)?.add(subOriginal)
                                existe = false
                            }
                        }else{ // não existe key com aquele IDgroup
                            var listTemp : MutableList<Submission_Professor> = mutableListOf<Submission_Professor>()
                            listTemp.add(subOriginal)

                            assiGlobal.listSubsId.put(subOriginal.GroupId,listTemp)
                        }
                    }

                    var listTemp : MutableList<Submission_Professor> = mutableListOf<Submission_Professor>()
                    listTemp.add(subOriginal)
                    assiGlobal.listSubsId.put(subOriginal.GroupId,listTemp)

                }
            }
        }

    }

    init {
        if (!Globals.taLigado) {
            listaSubsGroup.add(sub1)
            listaSubsGroup.add(sub2)
            updateAllReports(listaSubsGroup)
            //sub1.report = createReport(sub1)

            //listaSubsAluno.add(subAluno1)
            if (Globals.user_type == 0) {
                addSubsToListGlobal(listaSubsGroup)

                SubmissionProfessorTableColumn(checkID(listaSubsGroup),assignmentId)
            } else {
                SubmissionTableColumn(listaSubsAluno)
            }
        } else {
        val request = Request.Builder()
            .url("$REQUEST_URL/$assignmentId")
            .build()

        Authentication.httpClient.newCall(request).execute().use { response ->
            submissionList = submissionJsonAdapter.fromJson(response.body()!!.source())!!
        }

        submissionList
        showSubmissionList()

        }
    }



    private fun showSubmissionList() {
        SubmissionTableColumn(submissionList)
    }


}