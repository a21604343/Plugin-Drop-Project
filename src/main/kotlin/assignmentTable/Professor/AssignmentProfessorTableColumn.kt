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

package assignmentTable.Professor

import com.tfc.ulht.Globals
import data.Assignment
import data.Assignment_Professor
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.util.*
import javax.swing.*

class AssignmentProfessorTableColumn(assignmentList: List<Assignment>) : JFrame() {

    private var data = Array(assignmentList.size) { Array(8) { "" } }
    private var headers = arrayOf("Assignment ID", "Nome", "Last Submission Date","Submissoes", "Detalhes","","Arquivado")
    private val panel = JPanel(BorderLayout())
    private val frame = JFrame("Available Assignments")
    private val listSubmissionsButton: Int = 3
    private val infoButton: Int = 4
    private val selectAssignmentButton: Int = 5
    private var subsTotal : String = "0"
    private var subsGroups: String = "0"


    init {
        for ((iterator, assignment) in assignmentList.withIndex()) {
            data[iterator][0] = assignment.id
            data[iterator][1] = assignment.name

            if (!assignment.date.isNullOrEmpty()) {
                data[iterator][2] = assignment.lastSubmissionDate.toString()
            } else {
                data[iterator][2] = "No Submissions Yet"
            }

            data[iterator][4] = assignment.html
            subsTotal = assignment.numSubmissions.toString()
            //subsGroups = assignment.totalGroups.toString()
            data[iterator][6] = assignment.active.toString()
        }

        val table = object : JTable(data, headers) {
            override fun isCellEditable(row: Int, col: Int): Boolean {
                return col in 3..5
            }
        }
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
        table.columnModel.getColumn(6).preferredWidth = 75
        table.rowHeight = 30
        //table.removeColumn(table.columnModel.getColumn(3))


        /**
         * Show list of submissionsGroup
         */
        table.columnModel.getColumn(listSubmissionsButton).cellRenderer =
            AssignmentProfessorTableButtonRenderer("SubmissionsGroup")
            table.columnModel.getColumn(listSubmissionsButton).cellEditor = AssignmentProfessorTableButtonEditor(JTextField(), "Submissions By Group", frame)

        /**
         * Open more info
         */
        table.columnModel.getColumn(infoButton).cellRenderer =
            AssignmentProfessorTableButtonRenderer("Assignment Details")
        table.columnModel.getColumn(infoButton).cellEditor = AssignmentProfessorTableButtonEditor(JTextField(), "Assignment Details", frame)

        /**
         * Select assignment for upload
         */
        table.columnModel.getColumn(selectAssignmentButton).cellRenderer =
            AssignmentProfessorTableButtonRenderer("Select assignment")
        table.columnModel.getColumn(selectAssignmentButton).cellEditor = AssignmentProfessorTableButtonEditor(JTextField(), "Select assignment", frame)

        val scrollPane = JScrollPane(table)
        scrollPane.preferredSize = Dimension(1000, 400)
        frame.isLocationByPlatform = true
        panel.add(scrollPane, BorderLayout.CENTER)
        frame.preferredSize = Dimension(1000, 400)
        frame.contentPane.add(panel)

        frame.pack()
        frame.isVisible = true
    }
}