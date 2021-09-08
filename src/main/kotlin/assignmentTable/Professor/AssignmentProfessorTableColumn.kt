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

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tfc.ulht.Globals
import com.tfc.ulht.QuickSort
import com.tfc.ulht.loginComponents.Authentication
import data.Assignment
import data.Submission
import okhttp3.Request
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.awt.Point
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.lang.reflect.Type
import java.util.*
import javax.swing.*
import javax.swing.table.DefaultTableCellRenderer


class AssignmentProfessorTableColumn(assignmentList: List<Assignment>) : JFrame() {

    private var data = Array(assignmentList.size) { Array(9) { "" } }
    private var headers = arrayOf("Assignment ID", "Name", "Last Submission \n Date","Submissions By Group","Nr Submissions","Assignemnt Details","","Active?")
    private val panel = JPanel(BorderLayout())
    private val frame = JFrame("Available Assignments")
    private val listSubmissionsButton: Int = 3
    private val infoButton: Int = 5
    private val selectAssignmentButton: Int = 6
    private var subsTotal : String = "0"
    private var subsGroups: String = "0"
    lateinit private var imagem1 : ImageIcon


    init {

        fun testRequestForUpdateNumSubmissions (assiID : String, typeReturn : String) : String{
            var type: java.lang.reflect.Type = Types.newParameterizedType(
                List::class.java,
                Submission::class.java
            )
             val REQUEST_URL = "${Globals.REQUEST_URL}/api/v1/submissionsList"
             var submissionList = listOf<Submission>()
             val moshi = Moshi.Builder().build()
             val submissionJsonAdapter: JsonAdapter<List<Submission>> = moshi.adapter(type)
            val request = Request.Builder()
                .url("$REQUEST_URL/$assiID")
                .build()

            Authentication.httpClient.newCall(request).execute().use { response ->
                submissionList = submissionJsonAdapter.fromJson(response.body()!!.source())!!


            }
            if (typeReturn.equals("2")){
                return submissionList.size.toString()
            }else{
                if(submissionList.size != 0){
                    return submissionList.get(submissionList.size-1).submissionDate.toString()
                }else{
                    return "No submissions Yet"
                }

            }


        }


        for ((iterator, assignment) in assignmentList.withIndex()) {
            data[iterator][0] = assignment.id
            data[iterator][1] = assignment.name
            val idString : String = assignment.id
            data[iterator][2] = testRequestForUpdateNumSubmissions(assignment.id,"1")
            /*
            if(Globals.hashSubmissionsByAssignment.get(idString)?.size == 0){
                data[iterator][2] = "No Submissions Yet"
            }else{
                var size = Globals.hashSubmissionsByAssignment.get(idString)?.size
                if (size != null) {

                    data[iterator][2] = Globals.hashSubmissionsByAssignment.get(idString)?.get(size-1)?.submissionDate.toString()
                }
            }

             */
            var numberSubsByGroup = Globals.hashSubmissionsByAssignment.get(assignment.id)?.size
            if(numberSubsByGroup == null){
                numberSubsByGroup = 0
            }
            //data[iterator][4] = numberSubsByGroup.toString()
            data[iterator][4] = testRequestForUpdateNumSubmissions(assignment.id,"2")
            data[iterator][5] = assignment.html
            subsTotal = assignment.numSubmissions.toString()
            if(assignment.active){
                data[iterator][7] = "yes"
            }else{
                data[iterator][7] = "no"
            }
        }


        val table = object : JTable(data, headers) {

            override fun isCellEditable(row: Int, col: Int): Boolean {
                return col in 3..6
            }
        }
        // tamanho headers
        table.tableHeader.setSize(75,50)
        class txtIcon(var txt: String, var imageIcon: ImageIcon)

        class iconRenderer : DefaultTableCellRenderer() {
            override fun getTableCellRendererComponent(
                table: JTable,
                obj: Any, isSelected: Boolean, hasFocus: Boolean, row: Int,
                column: Int
            ): Component {
                val i: txtIcon = obj as txtIcon
                if (obj === i) {
                    icon = i.imageIcon
                    text = i.txt
                }
                border = UIManager.getBorder("TableHeader.cellBorder")
                horizontalAlignment = CENTER
                return this
            }
        }

        fun SetIcon( table : JTable,  col_index : Int,  icon : ImageIcon, name : String){
            table.tableHeader.columnModel.getColumn(col_index).headerRenderer
            (iconRenderer());
            table.columnModel.getColumn(col_index).headerValue = txtIcon(name, icon);
        }
        //var imageOne = ImageIcon("https://tenor.com/view/down-arrow-symbols-joypixels-arrow-down-cardinal-gif-17524105")

        imagem1 = ImageIcon("C:\\Users\\Diogo Casaca\\Pictures\\down-arrow-symbols.gif")
        println()

        SetIcon(table,0,imagem1,"")






        fun checkColumnClicked(column : Int){
            when(column){
                0 -> {
                    frame.isVisible = false
                    var sortedIdAssignment = QuickSort()
                    var sortedIdAssi = sortedIdAssignment.sortDataString(assignmentList as MutableList<Assignment>,0,assignmentList.size-1)
                   AssignmentProfessorTableColumn(sortedIdAssi)
                }
                1 -> {
                    var arrayTeste = mutableListOf<Assignment>()
                    arrayTeste.add(assignmentList.get(0))
                    arrayTeste.add(assignmentList.get(1))
                    frame.isVisible = false
                    var sortedNameAssignment = QuickSort()
                    var sortedNameAssi = sortedNameAssignment.sortDataNameAssi(assignmentList as MutableList<Assignment>,0,assignmentList.size-1)
                    //var sortedNameAssi = sortedNameAssignment.sortDataNameAssi(arrayTeste,0,arrayTeste.size-1)
                    AssignmentProfessorTableColumn(sortedNameAssi)

                }
                4 -> {
                    frame.isVisible = false
                    var sortedAssi = QuickSort()
                    var listaFinal = sortedAssi.sortDataAssi(assignmentList as MutableList<Assignment>,0,assignmentList.size.minus(1))
                    AssignmentProfessorTableColumn(listaFinal)
                }

                else -> "Erro"
            }
        }





        frame.getContentPane().add(table.tableHeader)
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
       //table.tableHeader.setSize(75,50)

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
        table.columnModel.getColumn(infoButton).cellEditor = AssignmentProfessorTableButtonEditor(JTextField(), "Details", frame)

        /**
         * Select assignment for upload
         */
        table.columnModel.getColumn(selectAssignmentButton).cellRenderer =
            AssignmentProfessorTableButtonRenderer("Select")
        table.columnModel.getColumn(selectAssignmentButton).cellEditor = AssignmentProfessorTableButtonEditor(JTextField(), "Select", frame)

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


