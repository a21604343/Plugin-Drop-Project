package submissionTable.Professor

import com.tfc.ulht.Globals
import com.tfc.ulht.assignmentComponents.ListAssignment
import com.tfc.ulht.submissionComponents.ListSubmissions
import com.tfc.ulht.submissionComponents.Professor.ListSubmissions_Professor
import data.Submission_Professor
import submissionTable.submissionHistory.SubmissionHistoryTableButtonEditor
import submissionTable.submissionHistory.SubmissionHistoryTableColumn
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
    private var idGroup: String=""


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
        this.idGroup = table.model.getValueAt(row,0).toString()
        this.report = table.model.getValueAt(row, 6).toString()
        this.row = row
        this.column = col

        button.text = label
        clicked = true
        return button
    }

    override fun getCellEditorValue(): Any {
        if (clicked) {
            if (column == 2) {
                if(Globals.user_type == 0){
                       SubmissionHistoryTableColumn(findListById(idGroup))
                    //ListSubmissions_Professor(assignmentId) // troquei assingmentId por "1" efeitos de teste
                }else{
                  //  ListSubmissions(assignmentId)
                }

            }
            else if (column == 6) {
                val ed1 = JEditorPane("text/html", report)
                ed1.isEditable = false
                val tempFrame = JFrame("Last Submission Report ")
                tempFrame.add(ed1)
                tempFrame.isLocationByPlatform = true
                tempFrame.setSize(850, 750)
                tempFrame.isVisible = true
            }
            else if (column == 7) { // Download LAST
                for(assiGlobal in Globals.listAssignments){
                    for(assiIDGroup in assiGlobal.listSubsId.values){

                    }
                  //  if(id.id)

                }
                Globals.submissionSelectedToDownload =
                    JOptionPane.showMessageDialog(null, "Preparando o download da ultima submissão do Grupo X").toString()
                //frame.dispatchEvent(WindowEvent(frame, WindowEvent.WINDOW_CLOSING))
                /*Globals.choosenColumn = column
                Globals.choosenRow = row*/

            }
        }
        clicked = false
        return label
    }

    fun findListById(idGroup: String): MutableList<Submission_Professor>? {
        for(assiGlobal in Globals.listAssignments){
            for(subs in assiGlobal.listSubsId.keys){
                if(subs == idGroup){
                    return assiGlobal.listSubsId.get(idGroup)                }
            }
        }
        return null
    }

    override fun stopCellEditing(): Boolean {
        clicked = false
        return super.stopCellEditing()
    }

}
