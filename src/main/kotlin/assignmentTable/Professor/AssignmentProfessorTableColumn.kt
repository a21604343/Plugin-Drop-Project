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

import data.Assignment_Professor
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*

class AssignmentProfessorTableColumn(assignmentList: List<Assignment_Professor>) : JFrame() {

    private var data = Array(assignmentList.size) { Array(8) { "" } }
    private var headers = arrayOf("Assignment ID", "Language", "Due Date","Submissoes", "Group subs","Enunciado", "","Arquivado")
    private val panel = JPanel(BorderLayout())
    private val frame = JFrame("Available Assignments")
    private val listAlunosSubmissions: Int = 4
    private val listSubmissionsButton: Int = 3
    private val infoButton: Int = 5
    private val selectAssignmentButton: Int = 6

    init {
        for ((iterator, assignment) in assignmentList.withIndex()) {
            data[iterator][0] = assignment.id
            data[iterator][1] = assignment.language

            if (!assignment.date.isNullOrEmpty()) {
                data[iterator][2] = assignment.date.toString()
            } else {
                data[iterator][2] = "No due date"
            }

            data[iterator][3] = assignment.enunciado
        }

        val table = object : JTable(data, headers) {
            override fun isCellEditable(row: Int, col: Int): Boolean {
                return col in 3..6
            }
        }

        table.rowHeight = 30
        //table.removeColumn(table.columnModel.getColumn(3))

        /**
         * Show list of submissions
         */
        table.columnModel.getColumn(listSubmissionsButton).cellRenderer =
            AssignmentProfessorTableButtonRenderer("Submissions Made")
        table.columnModel.getColumn(listSubmissionsButton).cellEditor = AssignmentProfessorTableButtonEditor(JTextField(), "Submissions Made", frame)

        /**
         * Show list of submissionsGroup
         */
        table.columnModel.getColumn(listAlunosSubmissions).cellRenderer =
            AssignmentProfessorTableButtonRenderer("SubmissionsGroup")
        table.columnModel.getColumn(listAlunosSubmissions).cellEditor = AssignmentProfessorTableButtonEditor(JTextField(), "Submissions By Group", frame)

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