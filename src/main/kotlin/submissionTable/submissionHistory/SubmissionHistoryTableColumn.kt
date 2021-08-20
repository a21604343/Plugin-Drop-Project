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

import data.Submission
import data.Submission_Professor
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*

class SubmissionHistoryTableColumn(submissionListP: List<Submission>?) : JFrame() {

    private var data = Array(submissionListP!!.size) { Array(9) { "" } }
    private var headers = arrayOf("ID Submission", "Submission Date","Status","Final","Indicators","Time","Report","Mark as Final","Download Submission")
    private val panel = JPanel(BorderLayout())
    private val frame = JFrame("Submissions History")
    private var reportSub: Int = 6
    private var markAsFinal: Int = 7
    private var download: Int = 8

    private var idGroupSubmissionOpen: String = "0"


    fun getTimeElapsedFromSummary(summary : String) : List<String> {
        var summarySplit = summary.split(", Time elapsed: ")
        return summarySplit
    }

    init {
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
                //data[iterator][7] = submission.isFinal.toString()
                // data[iterator][8] = submission.downloadLast.toString()

                iterator++
            }
        }
        /*
        if (submissionListP != null) {
            for (submission in submissionListP) {
                data[iterator][0] = submission.submissionId.toString()
                data[iterator][1] = submission.submissionDate.toString()
                data[iterator][2] = submission.status.toString()
                data[iterator][3] = submission.markedAsFinal.toString()

                var summaryTemp = getTimeElapsedFromSummary(submission?.summary.toString())
                if(summaryTemp.size > 1){
                    data[iterator][4] = getTimeElapsedFromSummary(submission?.summary.toString()).get(0)
                    data[iterator][5] = getTimeElapsedFromSummary(submission?.summary.toString()).get(1)
                }else{
                    // data[iterator][6] = getTimeElapsedFromSummary(submission?.summary.toString()).get(0)
                    data[iterator][4] = submission?.structureErrors.toString()
                    data[iterator][5] = ""
                }
                data[iterator][6] = submission.report.toString()

                if (submission.coverage == null){
                    submission.coverage = 0
                }
                //data[iterator][7] = submission.isFinal.toString()
               // data[iterator][8] = submission.downloadLast.toString()

                iterator++
            }
        }

         */

        val table = object : JTable(data, headers) {
            override fun isCellEditable(row: Int, col: Int): Boolean {
                return col in 6..8
            }
        }
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
        table.columnModel.getColumn(reportSub).cellEditor = SubmissionHistoryTableButtonEditor(JTextField(), "Report", frame)

        /**
         * Show report
         */
        table.columnModel.getColumn(markAsFinal).cellRenderer =
            SubmissionHistoryTableButtonRenderer("Mark as Final")
        table.columnModel.getColumn(markAsFinal).cellEditor = SubmissionHistoryTableButtonEditor(JTextField(), "Final", frame)


        /**
         * Download
         */
        table.columnModel.getColumn(download).cellRenderer =
            SubmissionHistoryTableButtonRenderer("Download")
        table.columnModel.getColumn(download).cellEditor = SubmissionHistoryTableButtonEditor(JTextField(), "Download", frame)


        val scrollPane = JScrollPane(table)
        scrollPane.preferredSize = Dimension(1000, 400)

        frame.isLocationByPlatform = true
        panel.add(scrollPane, BorderLayout.CENTER)
        frame.contentPane.preferredSize = Dimension(1000, 400)
        frame.contentPane.add(panel)

        frame.pack()
        frame.isVisible = true
    }
}