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
import java.awt.Color
import java.awt.Component
import javax.swing.JButton
import javax.swing.JTable
import javax.swing.table.TableCellRenderer


class AssignmentProfessorTableButtonRenderer(private val labelName: String) : JButton(), TableCellRenderer {

    init {
        isOpaque = true
    }

    override fun getTableCellRendererComponent(
        table: JTable, obj: Any,
        selected: Boolean, focused: Boolean, row: Int, col: Int
    ): Component {
        /*if (isSelected) {
            foreground = Color.BLACK
            background = Color.GRAY
        } else {
            foreground = Color.BLACK
            background = Color.WHITE
        }*/

        if (isSelected && row == Globals.choosenRow && col == Globals.choosenColumn) {
            background = Color.GREEN
            foreground = Color.RED
        } else {
            foreground = Color.BLACK
            background = Color.WHITE
        }

        text = labelName
        return this
    }
}