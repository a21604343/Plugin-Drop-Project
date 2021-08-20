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

    private var data = Array(assignmentList.size) { Array(9) { "" } }
    private var headers = arrayOf("Assignment ID", "Name", "Last Submission Date","Submissions By Group","Nr Submissions","Assignemnt Details","","Active?")
    private val panel = JPanel(BorderLayout())
    private val frame = JFrame("Available Assignments")
    private val listSubmissionsButton: Int = 3
    private val infoButton: Int = 5
    private val selectAssignmentButton: Int = 6
    private var subsTotal : String = "0"
    private var subsGroups: String = "0"


    init {


        for ((iterator, assignment) in assignmentList.withIndex()) {
            data[iterator][0] = assignment.id
            data[iterator][1] = assignment.name
            val idString : String = assignment.id
            if(Globals.hashSubmissionsByAssignment.get(idString)?.size == 0){
                data[iterator][2] = "No Submissions Yet"
            }else{
                var size = Globals.hashSubmissionsByAssignment.get(idString)?.size
                if (size != null) {
                    println("subdate :" + Globals.hashSubmissionsByAssignment.get(idString)?.get(size-1)?.submissionDate.toString())
                    data[iterator][2] = Globals.hashSubmissionsByAssignment.get(idString)?.get(size-1)?.submissionDate.toString()
                }
            }
            /*
            if (!assignment.lastSubmissionDate.isNullOrEmpty()) {
                data[iterator][2] = assignment.lastSubmissionDate.toString()
            } else {
                data[iterator][2] = "No Submissions Yet"
            }
            */
            var numberSubsByGroup = Globals.hashSubmissionsByAssignment.get(assignment.id)?.size


            if(numberSubsByGroup == null){
                numberSubsByGroup = 0
            }
            data[iterator][4] = numberSubsByGroup.toString()
            println("HTML CHECK : " + assignment.html)
            data[iterator][5] = assignment.html
            subsTotal = assignment.numSubmissions.toString()
            data[iterator][7] = assignment.active.toString()
        }
         // Arranjar solução para mostar numero de subs por assignment, problema: atualiza a coluna toda de uma só vez
            /**
             * Show list of submissionsGroup

            var numberSubsByGroup1 = Globals.hashSubmissionsByAssignment.get(assignment.id)?.size


            if(numberSubsByGroup1 == null){
                numberSubsByGroup1 = 0
            }
            table.columnModel.getColumn(listSubmissionsButton).cellRenderer =
                AssignmentProfessorTableButtonRenderer("${numberSubsByGroup1}")
            table.columnModel.getColumn(listSubmissionsButton).cellEditor = AssignmentProfessorTableButtonEditor(JTextField(), "Submissions By Group", frame)
            */



        val table = object : JTable(data, headers) {
            override fun isCellEditable(row: Int, col: Int): Boolean {
                return col in 3..6
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
            AssignmentProfessorTableButtonRenderer("Show")
            table.columnModel.getColumn(listSubmissionsButton).cellEditor = AssignmentProfessorTableButtonEditor(JTextField(), "Submissions By Group", frame)


        /**
         * Open more info
         */
        table.columnModel.getColumn(infoButton).cellRenderer =
            AssignmentProfessorTableButtonRenderer("Show")
        table.columnModel.getColumn(infoButton).cellEditor = AssignmentProfessorTableButtonEditor(JTextField(), "Assignment Details", frame)

        /**
         * Select assignment for upload
         */
        table.columnModel.getColumn(selectAssignmentButton).cellRenderer =
            AssignmentProfessorTableButtonRenderer("Select assignment")
        table.columnModel.getColumn(selectAssignmentButton).cellEditor = AssignmentProfessorTableButtonEditor(JTextField(), "Select assignment", frame)

        val scrollPane = JScrollPane(table)
        scrollPane.preferredSize = Dimension(1200, 400)
        frame.isLocationByPlatform = true
        panel.add(scrollPane, BorderLayout.CENTER)
        frame.preferredSize = Dimension(1200, 400)
        frame.contentPane.add(panel)

        frame.pack()
        frame.isVisible = true
    }
}