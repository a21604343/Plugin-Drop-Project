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

package submissionTable.submissionHistory

import assignmentTable.SubmissionProfessorTableColumn
import com.tfc.ulht.Globals
import com.tfc.ulht.QuickSort
import data.Submission
import data.Submission_Professor
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class SubmissionHistoryTableColumn(submissionListP: List<Submission>?, assignmentID : String, assingmentTestsType : String) : JFrame() {

    //private var data = Array(submissionListP!!.size) { Array(10) { "" } }
    private var headers = arrayOf("ID Submission", "Submission Date","Status","Final","TeacherTests","Time","Report","Mark as Final","Download Submission")
    private var headersWithStudentTests = arrayOf("ID Submission", "Submission Date","Status","Final","TeacherTests","StudentTests","Time","Report","Mark as Final","Download Submission")
    private var headersWithHiddenTests = arrayOf("ID Submission", "Submission Date","Status","Final","TeacherTests","HiddenTests","Time","Report","Mark as Final","Download Submission")
    private var headersWithStudentAndHiddenTests = arrayOf("ID Submission", "Submission Date","Status","Final","TeacherTests","StudentTests","HiddenTests","Time","Report","Mark as Final","Download Submission")
    private val panel = JPanel(BorderLayout())
    private val frame = JFrame("Submissions History")
    private val assignemntID = assignmentID
    private var reportSub: Int = 6
    private var markAsFinal: Int = 7
    private var download: Int = 8
    private var acceptStudentTests = false

    private var idGroupSubmissionOpen: String = "0"


    fun getTimeElapsedFromSummary(summary : String) : List<String> {
        var summarySplit = summary.split(", Time elapsed: ")
        return summarySplit
    }
    fun getMinStudentTests() : String{
        for (assi in Globals.listAssignmentsDP){
            if (assi.id == assignemntID){
                println("MinStudentTests: " + assi.minStudentTests)
            }
                return assi.minStudentTests
            }
        return ""
        }




    init {
        lateinit var table : JTable

        when( assingmentTestsType){
            "0" -> {
                var data = Array(submissionListP!!.size) { Array(9) { "" } }
                table = object : JTable(data, headers) {
                    override fun isCellEditable(row: Int, col: Int): Boolean {
                        return col in 6..8
                    }
                }
                var iterator = 0
                if (submissionListP != null) {
                    for (submission in submissionListP) {

                        data[iterator][0] = submission.submissionId.toString()
                        data[iterator][1] = submission.submissionDate.toString()
                        data[iterator][2] = submission.status.toString()
                        data[iterator][3] = submission.markedAsFinal.toString()

                        println("TEACHER TESTS : " + submission.teacherTests.toString())
                        println("sTUDENT TESTS : " + submission.studentTests.toString())
                        println("hidden TESTS : " + submission.hiddenTests.toString())

                        var summaryTemp = getTimeElapsedFromSummary(submission?.teacherTests.toString())
                        if(summaryTemp.size > 1){
                            data[iterator][4] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(0)
                            data[iterator][5] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(1)
                        }else{
                            data[iterator][4] = submission?.structureErrors.toString()
                            data[iterator][5] = ""
                        }
                        data[iterator][6] = submission.report.toString()
                        if (submission.coverage == null){
                            submission.coverage = 0
                        }

                        iterator++
                    }
                }
            }
            "1" -> {
                reportSub = 7
                markAsFinal= 8
                download = 9
                var data = Array(submissionListP!!.size) { Array(10) { "" } }
                table = object : JTable(data, headersWithStudentTests) {
                    override fun isCellEditable(row: Int, col: Int): Boolean {
                        return col in 7..9
                    }
                }
                var iterator = 0
                if (submissionListP != null) {
                    for (submission in submissionListP) {

                        data[iterator][0] = submission.submissionId.toString()
                        data[iterator][1] = submission.submissionDate.toString()
                        data[iterator][2] = submission.status.toString()
                        data[iterator][3] = submission.markedAsFinal.toString()

                        println("TEACHER TESTS : " + submission.teacherTests.toString())
                        println("sTUDENT TESTS : " + submission.studentTests.toString())
                        println("hidden TESTS : " + submission.hiddenTests.toString())

                        var summaryTemp = getTimeElapsedFromSummary(submission?.teacherTests.toString())
                        if(summaryTemp.size > 1){
                            data[iterator][4] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(0)
                            data[iterator][6] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(1)
                        }else{
                            data[iterator][4] = submission?.structureErrors.toString()
                            data[iterator][6] = ""
                        }
                        if(submission?.studentTests == null){
                            data[iterator][5] = "The submission doesn't include unit tests. Minimum of " + getMinStudentTests() + "tests."
                        }else{
                            data[iterator][5] = submission?.studentTests.toString()
                        }
                        data[iterator][7] = submission.report.toString()
                        if (submission.coverage == null){
                            submission.coverage = 0
                        }

                        iterator++
                    }
                }
            }
            "2" -> {
                reportSub = 8
                markAsFinal= 9
                download = 10
                var data = Array(submissionListP!!.size) { Array(11) { "" } }
                table = object : JTable(data, headersWithStudentAndHiddenTests) {
                    override fun isCellEditable(row: Int, col: Int): Boolean {
                        return col in 8..10
                    }
                }
                var iterator = 0
                if (submissionListP != null) {
                    for (submission in submissionListP) {

                        data[iterator][0] = submission.submissionId.toString()
                        data[iterator][1] = submission.submissionDate.toString()
                        data[iterator][2] = submission.status.toString()
                        data[iterator][3] = submission.markedAsFinal.toString()

                        println("TEACHER TESTS : " + submission.teacherTests.toString())
                        println("sTUDENT TESTS : " + submission.studentTests.toString())
                        println("hidden TESTS : " + submission.hiddenTests.toString())

                        var summaryTemp = getTimeElapsedFromSummary(submission?.teacherTests.toString())
                        if(summaryTemp.size > 1){
                            data[iterator][4] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(0)
                            data[iterator][7] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(1)
                        }else{
                            data[iterator][4] = submission?.structureErrors.toString()
                            data[iterator][7] = ""
                        }
                        if(submission.studentTests == null){
                            data[iterator][5] = "The submission doesn't include unit tests. Minimum of " + getMinStudentTests() + "tests."
                        }else{
                            data[iterator][5] = submission.studentTests.toString()
                        }

                        data[iterator][6] = submission.hiddenTests.toString()
                        data[iterator][8] = submission.report.toString()
                        if (submission.coverage == null){
                            submission.coverage = 0
                        }

                        iterator++
                    }
                }
            }
            "3" -> {
                reportSub = 7
                markAsFinal= 8
                download = 9
                var data = Array(submissionListP!!.size) { Array(10) { "" } }
                table = object : JTable(data, headersWithHiddenTests) {
                    override fun isCellEditable(row: Int, col: Int): Boolean {
                        return col in 7..9
                    }
                }
                var iterator = 0
                if (submissionListP != null) {
                    for (submission in submissionListP) {

                        data[iterator][0] = submission.submissionId.toString()
                        data[iterator][1] = submission.submissionDate.toString()
                        data[iterator][2] = submission.status.toString()
                        data[iterator][3] = submission.markedAsFinal.toString()

                        println("TEACHER TESTS : " + submission.teacherTests.toString())
                        println("sTUDENT TESTS : " + submission.studentTests.toString())
                        println("hidden TESTS : " + submission.hiddenTests.toString())

                        var summaryTemp = getTimeElapsedFromSummary(submission?.teacherTests.toString())
                        if(summaryTemp.size > 1){
                            data[iterator][4] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(0)
                            data[iterator][6] = getTimeElapsedFromSummary(submission?.teacherTests.toString()).get(1)
                        }else{
                            data[iterator][4] = submission?.structureErrors.toString()
                            data[iterator][6] = ""
                        }

                            data[iterator][5] = submission.hiddenTests.toString()

                        data[iterator][7] = submission.report.toString()
                        if (submission.coverage == null){
                            submission.coverage = 0
                        }

                        iterator++
                    }
                }
            }

        }

        fun checkColumnClicked(column : Int){
            when(column){
                0 ->{
                    frame.isVisible = false
                    var sortedID = QuickSort()
                    var listaTemp = sortedID.sortDataID(submissionListP as MutableList<Submission>,0,submissionListP.size.minus(1))

                    SubmissionHistoryTableColumn(listaTemp, assignmentID,assingmentTestsType)
                }
                1 -> {
                    frame.isVisible = false
                    var listTemp = submissionListP?.reversed()
                    if (listTemp != null) {
                        SubmissionHistoryTableColumn(listTemp, assignmentID,assingmentTestsType)
                    }
                }
                2 -> print("tamos ai")
                else -> "Erro"
            }
        }
        table.getTableHeader().addMouseListener(object : MouseAdapter(){

            override fun mouseClicked (event : MouseEvent){
                var point : Point = event.getPoint()
                var column : Int = table.columnAtPoint(point)
                print(column)
                checkColumnClicked(column)
            }
        })



        table.rowHeight = 50
        table.columnModel.getColumn(0).preferredWidth = 40
        table.columnModel.getColumn(1).preferredWidth = 70
        table.columnModel.getColumn(2).preferredWidth = 40
        table.columnModel.getColumn(3).preferredWidth = 30
        table.columnModel.getColumn(4).preferredWidth = 140
        table.columnModel.getColumn(5).preferredWidth = 40
        table.columnModel.getColumn(6).preferredWidth = 40
        table.columnModel.getColumn(7).preferredWidth = 40

        table.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS
        //table.removeColumn(table.columnModel.getColumn(3))


        /**
         * Show report
         */
        table.columnModel.getColumn(reportSub).cellRenderer =
            SubmissionHistoryTableButtonRenderer("Report")
        table.columnModel.getColumn(reportSub).cellEditor = SubmissionHistoryTableButtonEditor(JTextField(), "Report", frame,assingmentTestsType)

        /**
         * Show report
         */
        table.columnModel.getColumn(markAsFinal).cellRenderer =
            SubmissionHistoryTableButtonRenderer("Mark as Final")
        table.columnModel.getColumn(markAsFinal).cellEditor = SubmissionHistoryTableButtonEditor(JTextField(), "Final", frame,assingmentTestsType)


        /**
         * Download
         */
        table.columnModel.getColumn(download).cellRenderer =
            SubmissionHistoryTableButtonRenderer("Download")
        table.columnModel.getColumn(download).cellEditor = SubmissionHistoryTableButtonEditor(JTextField(), "Download", frame,assingmentTestsType)


        val scrollPane = JScrollPane(table)
        scrollPane.preferredSize = Dimension(1500, 400)

        frame.isLocationByPlatform = true
        panel.add(scrollPane, BorderLayout.CENTER)
        frame.contentPane.preferredSize = Dimension(1500, 400)
        frame.contentPane.add(panel)

        frame.pack()
        frame.isVisible = true
    }
}