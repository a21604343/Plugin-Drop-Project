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

package assignmentTable

import com.tfc.ulht.Globals
import data.Submission
import submissionTable.Professor.SubmissionProfessorTableButtonEditor
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import com.tfc.ulht.QuickSort
import submissionTable.submissionHistory.SubmissionHistoryTableColumn


class SubmissionProfessorTableColumn(submissionListP: List<Submission?>, assignmentID : String) : JFrame() {

    //private var data = Array(submissionListP.size) { Array(10) { "" } }
    private var headers = arrayOf("Group ID", "Name Authors", "Submissions","Nr Submissions", "Last Sub Date", "Status","TeacherTests","Time","Last Submission Report","Download Last Submission")
    private var headersWithStudentTests = arrayOf("Group ID", "Name Authors", "Submissions","Nr Submissions", "Last Sub Date", "Status","TeacherTests","StudentTests","Time","Last Submission Report","Download Last Submission")
    private var headersWithHiddenTests = arrayOf("Group ID", "Name Authors", "Submissions","Nr Submissions", "Last Sub Date", "Status","TeacherTests","HiddenTests","Time","Last Submission Report","Download Last Submission")
    private var headersWithStudentAndHiddenTests = arrayOf("Group ID", "Name Authors", "Submissions","Nr Submissions", "Last Sub Date", "Status","TeacherTests","StudentTests","HiddenTests","Time","Last Submission Report","Download Last Submission")
    private val panel = JPanel(BorderLayout())
    private val frame = JFrame("Submissions By Group")
    private val assignmentId = assignmentID
    private var lastReport: Int = 8
    private var downloadLast: Int = 9
    private val selectSubmission: Int = 2
    private var idGroupSubmissionOpen: String = "0"
    private var acceptStudentTests = false
    private var acceptHiddenTests = false
    private var listPerNumberSubs = mutableListOf<String>()
    private var listPerIdSubs = mutableListOf<String>()


    fun getTimeElapsedFromSummary(summary : String) : List<String> {
        var summarySplit = summary.split(", Time elapsed: ")
        return summarySplit
    }

    fun getnumberSubsByGroup(groupID :String) : String {
        println("TESTE NUMEROSUBS : " + Globals.hashSubByGroupId.get(groupID)?.size.toString())
        return  Globals.hashSubByGroupId.get(groupID)?.size.toString()
    }

    fun checkIfAcceptsTests(assiID : String) {
        for (assignment in Globals.listAssignmentsDP){
            if (assignment.id == assiID){
                if (assignment.acceptsStudentTests){
                    acceptStudentTests = true
                }
                println("hiddenVisibility : " + assignment.hiddenTestsVisibility)

                if(assignment.hiddenTestsVisibility == "HIDE_EVERYTHING" || assignment.hiddenTestsVisibility == "SHOW_OK_NOK" || assignment.hiddenTestsVisibility == "SHOW_PROGRESS"
                 ){
                    acceptHiddenTests = true
                }
            }
        }

    }

    fun getMinStudentTests() : String{
        for (assi in Globals.listAssignmentsDP){
            if (assi.id == assignmentId){
                return assi.minStudentTests
            }
        }
        return ""
    }

    fun checkTypeTests () : String{
        if (acceptStudentTests && acceptHiddenTests){
            lastReport = 10
            downloadLast = 11
            return "2"
        }else if (acceptStudentTests && !acceptHiddenTests){
            lastReport = 9
            downloadLast = 10
            return "1"
        }else if(!acceptStudentTests && !acceptHiddenTests){
            return "0"
        }else if(!acceptStudentTests && acceptHiddenTests){
            lastReport = 9
            downloadLast = 10
            return "3"
        }
        return "-1"
    }

    fun inversePerNumSubmissions(){
        listPerNumberSubs.clear()
        listPerIdSubs.clear()
        for ( lista in Globals.hashSubByGroupId.keys){
            listPerNumberSubs.add(Globals.hashSubByGroupId.get(lista)?.size.toString())
            listPerIdSubs.add(lista)
        }
    }

    fun unitListByGroup(lista: List<String>): List<Submission> {
        var finalList = mutableListOf<Submission?>()
        for (idTemp in lista) {
            for (id in Globals.hashSubByGroupId.keys) {

                if (id == idTemp) {
                    var size = Globals.hashSubByGroupId.get(id)?.size
                    if (size != null) {
                        finalList.add(Globals.hashSubByGroupId.get(id)?.get(size - 1))
                    }
                }
            }
        }
        return finalList as List<Submission>

    }

    init {
        lateinit var table : JTable
        checkIfAcceptsTests(assignmentID)
        println("checkTests : " + checkTypeTests())
        var colEditable = arrayOf(2,8,9)
        when(checkTypeTests() ){


            "0" -> {
                var data = Array(submissionListP.size) { Array(10) { "" } }
                table = object : JTable(data, headers) {
                    override fun isCellEditable(row: Int, col: Int): Boolean {
                        return col in colEditable
                    }
                }
                var iterator  = 0
                for (submission in submissionListP) {
                    data[iterator][0] = submission?.idGroup.toString()
                    println("id : " + submission?.idGroup)
                    println("idG" + submission?.idGroup)
                    println("grupo: " + submission?.groupAuthors)
                    var prename = submission?.groupAuthors?.split("name=")
                    var name = prename?.get(1)?.split(",")
                    data[iterator][1] = name?.get(0).toString()
                    data[iterator][3] = getnumberSubsByGroup(submission?.idGroup.toString())
                    data[iterator][4] = submission?.submissionDate.toString()
                    if (submission != null) {
                        data[iterator][5] = submission.status
                    }
                    var summaryTemp = getTimeElapsedFromSummary(submission?.teacherTests.toString())
                    if (summaryTemp.size > 1) {
                        data[iterator][6] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(0)
                        data[iterator][7] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(1)
                        println("STRUCTUREERROS : " + submission?.structureErrors)
                    } else {
                        data[iterator][6] = submission?.structureErrors.toString()
                        data[iterator][7] = ""
                    }

                    data[iterator][8] = submission?.report.toString()

                    // Arranjar solução para mostar numero de subs por grupo, problema: atualiza a coluna toda de uma só vez

                    iterator++
                }
            }
            "1" -> {
                colEditable = arrayOf(2,9,10)
                var data = Array(submissionListP.size) { Array(11) { "" } }
                table = object : JTable(data, headersWithStudentTests) {
                    override fun isCellEditable(row: Int, col: Int): Boolean {
                        return col in colEditable
                    }
                }
                var iterator  = 0
             for (submission in submissionListP) {
                data[iterator][0] = submission?.idGroup.toString()
                println("id : " + submission?.idGroup)
                println("idG" + submission?.idGroup)
                println("grupo: " + submission?.groupAuthors)
                var prename = submission?.groupAuthors?.split("name=")
                var name = prename?.get(1)?.split(",")
                data[iterator][1] = name?.get(0).toString()
                data[iterator][3] = getnumberSubsByGroup(submission?.idGroup.toString())
                data[iterator][4] = submission?.submissionDate.toString()
                if (submission != null) {
                    data[iterator][5] = submission.status
                }
                var summaryTemp = getTimeElapsedFromSummary(submission?.teacherTests.toString())
                if(summaryTemp.size > 1){
                    data[iterator][6] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(0)

                    data[iterator][8] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(1)
                    println("STRUCTUREERROS : " + submission?.structureErrors)
                }else{
                    data[iterator][6] = submission?.structureErrors.toString()
                    data[iterator][8] = ""
                }
                 if(submission?.studentTests == null){
                     data[iterator][7] = "The submission doesn't include unit tests. Minimum of " + getMinStudentTests() + "tests."
                 }else{
                     data[iterator][7] = submission?.studentTests.toString()
                 }


                data[iterator][9] = submission?.report.toString()

                // Arranjar solução para mostar numero de subs por grupo, problema: atualiza a coluna toda de uma só vez
                println("ELAPSED TIME : " + submission?.elapsed)
                iterator++
            }
            }
            "2" ->{
                colEditable = arrayOf(2,10,11)
                var data = Array(submissionListP.size) { Array(12) { "" } }
                table = object : JTable(data, headersWithStudentAndHiddenTests) {
                    override fun isCellEditable(row: Int, col: Int): Boolean {
                        return col in colEditable
                    }
                }
                var iterator  = 0
                for (submission in submissionListP) {
                data[iterator][0] = submission?.idGroup.toString()
                println("id : " + submission?.idGroup)
                println("idG" + submission?.idGroup)
                println("grupo: " + submission?.groupAuthors)
                var prename = submission?.groupAuthors?.split("name=")
                var name = prename?.get(1)?.split(",")
                data[iterator][1] = name?.get(0).toString()
                data[iterator][3] = getnumberSubsByGroup(submission?.idGroup.toString())
                data[iterator][4] = submission?.submissionDate.toString()
                if (submission != null) {
                    data[iterator][5] = submission.status
                }
                var summaryTemp = getTimeElapsedFromSummary(submission?.teacherTests.toString())
                if(summaryTemp.size > 1){
                    data[iterator][6] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(0)

                    data[iterator][9] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(1)
                    println("STRUCTUREERROS : " + submission?.structureErrors)
                }else{
                    data[iterator][6] = submission?.structureErrors.toString()
                    data[iterator][9] = ""
                }
                    if(submission?.studentTests == null){
                        data[iterator][7] = "The submission doesn't include unit tests. Minimum of " + getMinStudentTests() + "tests."
                    }else{
                        data[iterator][7] = submission.studentTests.toString()
                    }
                data[iterator][8] = submission?.hiddenTests.toString()

                data[iterator][10] = submission?.report.toString()

                // Arranjar solução para mostar numero de subs por grupo, problema: atualiza a coluna toda de uma só vez

                iterator++
            }}
            "3" -> {
                colEditable = arrayOf(2,9,10)
                var data = Array(submissionListP.size) { Array(11) { "" } }
                table = object : JTable(data, headersWithHiddenTests) {
                    override fun isCellEditable(row: Int, col: Int): Boolean {
                        return col in colEditable
                    }
                }
                var iterator  = 0
                for (submission in submissionListP) {
                    data[iterator][0] = submission?.idGroup.toString()
                    println("id : " + submission?.idGroup)
                    println("idG" + submission?.idGroup)
                    println("grupo: " + submission?.groupAuthors)
                    var prename = submission?.groupAuthors?.split("name=")
                    var name = prename?.get(1)?.split(",")
                    data[iterator][1] = name?.get(0).toString()
                    data[iterator][3] = getnumberSubsByGroup(submission?.idGroup.toString())
                    data[iterator][4] = submission?.submissionDate.toString()
                    if (submission != null) {
                        data[iterator][5] = submission.status
                    }
                    var summaryTemp = getTimeElapsedFromSummary(submission?.teacherTests.toString())
                    if(summaryTemp.size > 1){
                        data[iterator][6] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(0)

                        data[iterator][8] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(1)
                        println("STRUCTUREERROS : " + submission?.structureErrors)
                    }else{
                        data[iterator][6] = submission?.structureErrors.toString()
                        data[iterator][8] = ""
                    }

                        data[iterator][7] = submission?.hiddenTests.toString()



                    data[iterator][9] = submission?.report.toString()

                    // Arranjar solução para mostar numero de subs por grupo, problema: atualiza a coluna toda de uma só vez
                    println("ELAPSED TIME : " + submission?.elapsed)
                    iterator++
                }
            }
        }

        fun checkColumnClicked(column : Int){
            when(column){
                0 -> {
                    frame.isVisible = false
                    var sortedID2 = QuickSort()
                    var listaTemp = sortedID2.sortDataID(submissionListP as MutableList<Submission>,0,submissionListP.size.minus(1))
                    SubmissionProfessorTableColumn(listaTemp, assignmentID)
                }
                3 -> {
                    frame.isVisible = false
                inversePerNumSubmissions()
                var sortedList = QuickSort()
                    var lista = sortedList.sortData(listPerNumberSubs,listPerIdSubs,0,listPerNumberSubs.size.minus(1))

                    when(Globals.sortedOnce){
                        "1" -> {
                            lista.reverse()
                            Globals.sortedOnce = "2"
                            SubmissionProfessorTableColumn(unitListByGroup(lista),assignmentID)
                        }
                        "0" -> {
                            Globals.sortedOnce = "1"
                            SubmissionProfessorTableColumn(unitListByGroup(lista),assignmentID)
                        }
                        "2" -> {
                            Globals.sortedOnce = "1"
                            SubmissionProfessorTableColumn(unitListByGroup(lista),assignmentID)
                        }
                    }
                }
                else -> "Erro"
            }
        }

        frame.getContentPane().add(table.getTableHeader())
        table.getTableHeader().addMouseListener(object : MouseAdapter(){

            override fun mouseClicked (event : MouseEvent){
                var point : Point = event.getPoint()
                var column : Int = table.columnAtPoint(point)
                print(column)
                checkColumnClicked(column)
            }
        })
        table.rowHeight = 45
        table.columnModel.getColumn(0).preferredWidth = 75
        table.columnModel.getColumn(1).preferredWidth = 135
        table.columnModel.getColumn(2).preferredWidth = 120
        table.columnModel.getColumn(3).preferredWidth = 105
        table.columnModel.getColumn(4).preferredWidth = 105
        table.columnModel.getColumn(6).preferredWidth = 190
        table.columnModel.getColumn(7).preferredWidth = 70
        table.columnModel.getColumn(8).preferredWidth = 150
        table.columnModel.getColumn(9).preferredWidth = 150

        table.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS

        /**
         * Show list of submissions

         */
        table.columnModel.getColumn(selectSubmission).cellRenderer =
            AssignmentTableButtonRenderer("Show")
        table.columnModel.getColumn(selectSubmission).cellEditor = SubmissionProfessorTableButtonEditor(JTextField(), "Show Submissions", frame,assignmentID,checkTypeTests())

        /**
        * Show sub report
        */
        table.columnModel.getColumn(lastReport).cellRenderer =
            AssignmentTableButtonRenderer("Show")
        table.columnModel.getColumn(lastReport).cellEditor = SubmissionProfessorTableButtonEditor(JTextField(), "Submission Report", frame,assignmentID,checkTypeTests())
        /**
        * Download Last
        */
        table.columnModel.getColumn(downloadLast).cellRenderer =
            AssignmentTableButtonRenderer("Download")
        table.columnModel.getColumn(downloadLast).cellEditor = SubmissionProfessorTableButtonEditor(JTextField(), "Download last Submission", frame,assignmentID,checkTypeTests())


        val scrollPane = JScrollPane(table)
        scrollPane.preferredSize = Dimension(1750, 400)

        frame.isLocationByPlatform = true
        panel.add(scrollPane, BorderLayout.CENTER)
        frame.contentPane.preferredSize = Dimension(1750, 400)
        frame.contentPane.add(panel)

        frame.pack()
        frame.isVisible = true
    }
}