package assignmentTable.Professor

import com.tfc.ulht.Globals
import com.tfc.ulht.assignmentComponents.ListAssignment
import com.tfc.ulht.submissionComponents.ListSubmissions
import com.tfc.ulht.submissionComponents.Professor.ListSubmissions_Professor
import java.awt.Component
import java.awt.Desktop
import javax.swing.*
import javax.swing.event.HyperlinkEvent
import javax.swing.event.HyperlinkListener


internal class AssignmentProfessorTableButtonEditor(
    txt: JTextField?,
    private val label: String,
    private val frame: JFrame
) : DefaultCellEditor(txt) {
    private var button: JButton = JButton(label)
    private var clicked: Boolean = false

    private var row: Int = 0
    private var column: Int = 0
    private var assignmentId: String = ""
    private var assignmentDetails: String = ""

    private var table: JTable? = null

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

        this.assignmentId = table.model.getValueAt(row, 0).toString()
        this.assignmentDetails = table.model.getValueAt(row, 5).toString()
        this.row = row
        this.column = col

        this.table = table

        button.text = label
        clicked = true
        return button
    }

    override fun getCellEditorValue(): Any {
        if (clicked) {
            if (column == 3) {
                if(Globals.user_type == 0){

                    ListSubmissions(assignmentId)
                }else{
                    ListSubmissions(assignmentId)
                }

            }
            else if (column == 5) { // MORE INFO
                val ed1 = JEditorPane("text/html", assignmentDetails) // PREPARE HTML VIEWER
                ed1.isEditable = false
                val tempFrame = JFrame()
                tempFrame.add(ed1)
                tempFrame.isVisible = true
                tempFrame.setSize(600, 600)

                // ABILITY TO CLICK ON HYPERLINK
                ed1.addHyperlinkListener { e ->
                    if (e.eventType == HyperlinkEvent.EventType.ACTIVATED) {
                        // Do something with e.getURL() here
                        if(Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().browse(e.url.toURI())
                        }
                    }
                }
            } else if (column == 6) { // SELECT ASSIGNMENT
                Globals.selectedAssignmentID = assignmentId
                JOptionPane.showMessageDialog(null, "The assignment with ID: $assignmentId was selected")

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
