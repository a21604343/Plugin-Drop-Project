package submissionTable.Professor

import com.tfc.ulht.assignmentComponents.ListAssignment
import java.awt.Component
import java.awt.Desktop
import javax.swing.*
import javax.swing.event.HyperlinkEvent
import javax.swing.event.HyperlinkListener


internal class SubmissionProfessorTableButtonEditor(
    txt: JTextField?,
    private val label: String,
    private val frame: JFrame
) : DefaultCellEditor(txt) {
    private var button: JButton = JButton(label)
    private var clicked: Boolean = false

    private var row: Int = 0
    private var column: Int = 0
    private var report: String = ""

    init {
        button.isOpaque = true
        button.addActionListener { fireEditingStopped() }
    }

    override fun getTableCellEditorComponent(
        table: JTable, obj: Any,
        selected: Boolean, row: Int, col: Int
    ): Component {
        if (selected) {
            button.foreground = table.selectionForeground
            button.background = table.selectionBackground
        } else {
            button.foreground = table.foreground
            button.background = table.background
        }

        this.report = table.model.getValueAt(row, 3).toString()
        this.row = row
        this.column = col

        button.text = label
        clicked = true
        return button
    }

    override fun getCellEditorValue(): Any {
        if (clicked) {
            if (column == 3) {
                println("Entrei")
                println(report)
                val ed1 = JEditorPane("text", report)
                ed1.isEditable = false
                val tempFrame = JFrame("Errors Report")
                tempFrame.add(ed1)
                tempFrame.isLocationByPlatform = true
                tempFrame.setSize(850, 400)
                tempFrame.isVisible = true
            }
        }
        clicked = false
        return label
    }

    override fun stopCellEditing(): Boolean {
        clicked = false
        return super.stopCellEditing()
    }

}
