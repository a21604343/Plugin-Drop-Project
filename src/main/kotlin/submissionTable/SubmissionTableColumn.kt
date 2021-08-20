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

import data.Submission
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*

class SubmissionTableColumn(submissionList: List<Submission>) : JFrame() {

    private var data = Array(submissionList.size) { Array(5) { "" } }
    private var headers = arrayOf("Submission ID", "Submission Date", "Summary", "", "")
    private val panel = JPanel(BorderLayout())
    private val frame = JFrame("Submissions")
    private val selectSubmission: Int = 3

    init {
        var iterator = 0
        for (submission in submissionList) {
            data[iterator][0] = submission.submissionId.toString()
            data[iterator][1] = submission.submissionDate.toString()
            data[iterator][2] = submission.teacherTests.toString()
            data[iterator][3] = submission.report.toString()

            iterator++
        }

        val table = JTable(data, headers)
        table.rowHeight = 50
        table.columnModel.getColumn(0).preferredWidth = 75
        table.columnModel.getColumn(1).preferredWidth = 150
        table.columnModel.getColumn(2).preferredWidth = 300

        table.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS
        table.removeColumn(table.columnModel.getColumn(3))

        /**
         * Show list of submissions
         */
        table.columnModel.getColumn(selectSubmission).cellRenderer =
            AssignmentTableButtonRenderer("Show Submission Report")
        table.columnModel.getColumn(selectSubmission).cellEditor = SubmissionTableButtonEditor(JTextField(), "Show Submission Report", frame)


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