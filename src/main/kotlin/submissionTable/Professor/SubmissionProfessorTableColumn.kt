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

class SubmissionProfessorTableColumn(submissionListP: List<Submission>, assignmentID : String) : JFrame() {

    private var data = Array(submissionListP.size) { Array(8) { "" } }
    private var headers = arrayOf("ID do Grupo", "Nome Autores", "Submissões", "Last Sub Date", "Status","Indicadores","Relatório Última Sub","Download Última Submissão")
    private val panel = JPanel(BorderLayout())
    private val frame = JFrame("Submissions By Group")
    private var lastReport: Int = 6
    private var downloadLast: Int = 7
    private val selectSubmission: Int = 2
    private var idGroupSubmissionOpen: String = "0"

    init {
        var iterator = 0
        for (submission in submissionListP) {
           // if(submission.assignmentId == idAssi){
                data[iterator][0] = submission.idGroup.toString()
            println("id : " + submission.idGroup)
            println("idG" + submission.idGroup)
            println("grupo: " + submission.groupAuthors)
            var prename = submission.groupAuthors.split("name=")
            var name = prename.get(1).split(",")
            data[iterator][1] = name.get(0).toString()
                data[iterator][3] = submission.submissionDate.toString()
                data[iterator][4] = submission.status
                data[iterator][5] = submission.coverage.toString()
                data[iterator][6] = submission.report.toString()
                //data[iterator][7] = submission.downloadLast.toString()
          //  }


            iterator++
        }

        val table = JTable(data, headers)

        fun checkColumnClicked(column : Int){
            when(column){
                0 -> {
                    frame.isVisible = false
                    Globals.listAssignments.reverse()
                    // AssignmentProfessorTableColumn(Globals.listAssignments)
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
        table.rowHeight = 50
        table.columnModel.getColumn(0).preferredWidth = 75
        table.columnModel.getColumn(1).preferredWidth = 185
        table.columnModel.getColumn(2).preferredWidth = 135
        table.columnModel.getColumn(1).preferredWidth = 150
        table.columnModel.getColumn(2).preferredWidth = 150

        table.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS
        //table.removeColumn(table.columnModel.getColumn(3))

        /**
         * Show list of submissions
         */
        table.columnModel.getColumn(selectSubmission).cellRenderer =
            AssignmentTableButtonRenderer("Show Submission ")
        table.columnModel.getColumn(selectSubmission).cellEditor = SubmissionProfessorTableButtonEditor(JTextField(), "Show Submission", frame,assignmentID)

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
        table.columnModel.getColumn(downloadLast).cellEditor = SubmissionProfessorTableButtonEditor(JTextField(), "Download last SubmissionA", frame,assignmentID)


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