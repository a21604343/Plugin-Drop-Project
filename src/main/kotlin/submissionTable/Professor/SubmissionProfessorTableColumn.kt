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

class SubmissionProfessorTableColumn(submissionListP: List<Submission?>, assignmentID : String) : JFrame() {

    private var data = Array(submissionListP.size) { Array(9) { "" } }
    private var headers = arrayOf("ID do Grupo", "Nome Autores", "Submissões", "Last Sub Date", "Status","Indicadores","Tempo","Relatório Última Submissão","Download Última Submissão")
    private val panel = JPanel(BorderLayout())
    private val frame = JFrame("Submissions By Group")
    private var lastReport: Int = 7
    private var downloadLast: Int = 8
    private val selectSubmission: Int = 2
    private var idGroupSubmissionOpen: String = "0"

    fun getTimeElapsedFromSummary(summary : String) : List<String> {
        var summarySplit = summary.split(", Time elapsed: ")
        return summarySplit
    }

    init {
        var iterator = 0
        for (submission in submissionListP) {
                data[iterator][0] = submission?.idGroup.toString()
            println("id : " + submission?.idGroup)
            println("idG" + submission?.idGroup)
            println("grupo: " + submission?.groupAuthors)
            var prename = submission?.groupAuthors?.split("name=")
            var name = prename?.get(1)?.split(",")
            data[iterator][1] = name?.get(0).toString()
                data[iterator][3] = submission?.submissionDate.toString()
            if (submission != null) {
                data[iterator][4] = submission.status
            }
                data[iterator][5] = getTimeElapsedFromSummary(submission?.summary.toString()).get(0)
                data[iterator][6] = getTimeElapsedFromSummary(submission?.summary.toString()).get(1)
                data[iterator][7] = submission?.report.toString()
                //data[iterator][7] = submission.downloadLast.toString()
          //  }


            iterator++
        }

        val table = object : JTable(data, headers)
        {
           /* override fun isCellEditable(row: Int, col: Int): Boolean {
                return col in 3..8
            }

            */
        }



        fun checkColumnClicked(column : Int){
            when(column){
                0 -> {
                    frame.isVisible = false
                    Globals.listAssignments.reverse()
                }
                2 -> print("tamos ai")
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
        table.columnModel.getColumn(1).preferredWidth = 150
        table.columnModel.getColumn(2).preferredWidth = 110
        table.columnModel.getColumn(3).preferredWidth = 105
        table.columnModel.getColumn(5).preferredWidth = 190
        table.columnModel.getColumn(6).preferredWidth = 70
        table.columnModel.getColumn(7).preferredWidth = 150
        table.columnModel.getColumn(8).preferredWidth = 150

        table.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS
        //table.removeColumn(table.columnModel.getColumn(3))

        /**
         * Show list of submissions
         */

        //var numberSubsByGroup = Globals.hashSubmissionsByAssignment.get(assignmentID)?.size
        table.columnModel.getColumn(selectSubmission).cellRenderer =
            AssignmentTableButtonRenderer("Show Submissions ")
        table.columnModel.getColumn(selectSubmission).cellEditor = SubmissionProfessorTableButtonEditor(JTextField(), "Show Submissions", frame,assignmentID)

        /**
        * Show sub report
        */
        table.columnModel.getColumn(lastReport).cellRenderer =
            AssignmentTableButtonRenderer("Show Submission Report")
        table.columnModel.getColumn(lastReport).cellEditor = SubmissionProfessorTableButtonEditor(JTextField(), "Submission Report", frame,assignmentID)

        /**
        * Download Last
        */
        table.columnModel.getColumn(downloadLast).cellRenderer =
            AssignmentTableButtonRenderer("Download")
        table.columnModel.getColumn(downloadLast).cellEditor = SubmissionProfessorTableButtonEditor(JTextField(), "Download last Submission", frame,assignmentID)


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