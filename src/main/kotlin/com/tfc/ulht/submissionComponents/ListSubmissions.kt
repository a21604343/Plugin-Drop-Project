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
    private var firstTime : Boolean = true


    var listaSubsAluno: MutableList<Submission> = mutableListOf<Submission>()
    val listaSubsGroup: MutableList<Submission_Professor> = mutableListOf<Submission_Professor>()
    var indicadoresTeste = Indicadores(true,true,true,"1/5","4/9","3/12","123ms","16%")
    var sub1 = Submission_Professor("1001", "3","Diogo,Tiago",
        "","validated","www.report/24","34",indicadoresTeste, "17/03/21","www",false,"1")
    //val subAluno1 = Submission("300","29-01-2020","3/10 corretos.html","","1")
    var sub2 = Submission_Professor("1002", "4","Bernardo Tavares,Tiago Abreu",
        "","validated","www.report/24","34",indicadoresTeste, "17/03/21","www",false,"1")
    var sub3 = Submission_Professor("1003", "4","Bernardo Tavares,Tiago Abreu",
        "","validated","www.report/24","34",indicadoresTeste, "17/03/21","www",false,"2")


    fun createReport(submissao : Submission) : String{
        var authors = submissao.groupAuthors?.split("name=")
        var isGroup = false
        var auth1 : String
        var auth2 : String
        if(authors.size > 2){
            isGroup = true
            val auths = authors.get(1).split(",")
            val auths2 = authors.get(2).split(",")
             auth1 = auths.get(0)
             auth2 = auths2.get(0)
        }else{
            val auths = authors.get(1).split(",")
            auth1 = auths.get(0)
            auth2 = ""
        }
        println("report-- ${submissao.groupAuthors}")
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
        "<h3> Assignment:  ${submissao.assignmentId}  | Last commit: ${submissao.submissionDate} </h3>" +
        "</head>" +
        "<body>" +
        "<div>"+
        "<h2>Group elements</h2>"+
        "<table >"


        if (isGroup){
            report += "<span>${auth1}</span>"+

                    "<span class=\"label label-primary\"> <b>Submitter</b></span>"+
                    " </td>"+
                    "<td>Student 1</td>"+
                    "</tr>"+
                    "<tr>"+
                    "<td>"+
                    "<span>${auth2}</span>"+
                    "</td"+
                    "<td> Student 2 </td>"+
                    "<tr>"+
                    "</tbody>"+
                    "</table>"
        }else{
            report += "<span>${auth1}</span>"+

                    "<span class=\"label label-primary\"> <b>Submitter</b></span>"+
                    " </td>"+
                    "<td>Student 1</td>"+
                    "</tr>"+
                    "<tr>"+
                    "<td>"
        }

        report += "<h2>Results summary</h2>"+
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
//"<span class=\"label label-success\">${submissao.studentTests}</span>"+
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
                //     "<span class=\"label label-success\">${submissao.teacherTests}</span>"+
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
                // "<span class=\"label label-success\">${submissao.hiddenTests}</span>"+
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
 "<td>Coverage ${submissao.coverage} (only visible to teacher</td>"+
 "</tr>"+
 "<tr>"+
 "<td>Tests run: 2, Failures: 0, Errors: 0, Time elapsed: ${submissao.elapsed} sec</td>"+
 "</tr>"+


" </table>"+
 "<br>"+

" </div>"+
" </body>"+
"  </html>"

 return report

}

fun updateAllReports(listaSubs : List<Submission>){
 for(sub in listaSubs){
     sub.report = createReport(sub)
 }
}




    fun sortByGroups(listSubs : List<Submission>){
        for(sub in listSubs){
            if (Globals.hashSubByGroupId.isEmpty()){
                println("grupo id:" + sub.idGroup)
                Globals.hashSubByGroupId.put(sub.idGroup.toString(), mutableListOf(sub))
            }else{
                if (Globals.hashSubByGroupId.containsKey(sub.idGroup.toString())){
                    Globals.hashSubByGroupId.get(sub.idGroup.toString())?.add(sub)
                }else{
                    Globals.hashSubByGroupId.put(sub.idGroup.toString(), mutableListOf(sub))
                }
            }
        }
    }

    fun createListById() : MutableList<Submission?> {
        var listSubById : MutableList<Submission?> = mutableListOf()
        for (groupId in Globals.hashSubByGroupId.keys){
            var size = Globals.hashSubByGroupId.get(groupId)?.size
            if (size != null) {
                listSubById.add(Globals.hashSubByGroupId.get(groupId)?.get(size-1))
            }
        }
        return listSubById
    }

init {
 if (!Globals.taLigado) {
     listaSubsGroup.add(sub1)
     listaSubsGroup.add(sub2)

     //sub1.report = createReport(sub1)


     if (Globals.user_type == 0) {
         //addSubsToListGlobal(listaSubsGroup)

        // SubmissionProfessorTableColumn(checkID(listaSubsGroup),assignmentId)
     } else {
         SubmissionTableColumn(listaSubsAluno)
     }
 } else {
     println("***** ListSUB" + REQUEST_URL)
 val request = Request.Builder()
     .url("$REQUEST_URL/$assignmentId")
     .build()

 Authentication.httpClient.newCall(request).execute().use { response ->
     submissionList = submissionJsonAdapter.fromJson(response.body()!!.source())!!


 }
     val listTemp : MutableList<Submission> = submissionList as MutableList<Submission>
     Globals.hashSubmissionsByAssignment.put(assignmentId, listTemp)
        Globals.hashSubByGroupId.clear()
     updateAllReports(submissionList)
     if (firstTime){
         Globals.listSubmissionsDP = submissionList
         sortByGroups(submissionList)
         firstTime = false
     }else{
             Globals.hashSubByGroupId.clear()
             sortByGroups(submissionList)
             Globals.listSubmissionsDP = submissionList

     }

     var finalList = createListById()
     submissionList
 showSubmissionList(finalList)

 }
}



private fun showSubmissionList(listSubmissions : List<Submission?>) {

 if(Globals.user_type == 0){
     SubmissionProfessorTableColumn(listSubmissions,assignmentId)
 }else{
     SubmissionTableColumn(submissionList)
 }

}


}