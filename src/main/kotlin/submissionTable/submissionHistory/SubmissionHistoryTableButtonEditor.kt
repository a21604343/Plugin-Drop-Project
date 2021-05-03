package submissionTable.submissionHistory

import com.tfc.ulht.Globals
import com.tfc.ulht.submissionComponents.ListSubmissions
import okhttp3.Request
import sun.net.www.http.HttpClient
import java.awt.Component
import javax.swing.*
import sun.net.www.http.HttpCaptureOutputStream
import java.io.FileOutputStream
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.FileChannel
import java.nio.channels.ReadableByteChannel


internal class SubmissionHistoryTableButtonEditor(
    txt: JTextField?,
    private val label: String,
    private val frame: JFrame
) : DefaultCellEditor(txt) {
    private var button: JButton = JButton(label)
    private var clicked: Boolean = false

    private var row: Int = 0
    private var column: Int = 0
    private var submissionReport: String = ""
    private var submissionId: String=""

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
        this.submissionId = table.model.getValueAt(row,0).toString()
        this.submissionReport = table.model.getValueAt(row, 6).toString()
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
                    // resolver problema com o submissionID

                    //ListSubmissions_Professor(assignmentId) // troquei assingmentId por "1" efeitos de teste
                }else{
                  //  ListSubmissions(assignmentId)
                }

            }
            else if (column == 6) {
                val ed1 = JEditorPane("text/html", submissionReport)
                ed1.isEditable = false
                val tempFrame = JFrame("Submission Report")
                tempFrame.add(ed1)
                tempFrame.isLocationByPlatform = true
                tempFrame.setSize(850, 750)
                tempFrame.isVisible = true
            }
            else if (column == 7){
                Globals.submissionSelectedAsFinal = submissionId
                JOptionPane.showMessageDialog(null, "A submissão de ID: $submissionId  foi marcada como FINAL").toString()

            }
            else if (column == 8) { // Download
                Globals.submissionSelectedToDownload = submissionId

                //Tratar Download Submissão
                    JOptionPane.showMessageDialog(null, "Preparando o download da ultima submissão do Grupo X").toString()
                //frame.dispatchEvent(WindowEvent(frame, WindowEvent.WINDOW_CLOSING))
                /*Globals.choosenColumn = column
                Globals.choosenRow = row*/

                downloadSubmissao()

            }
        }
        clicked = false
        return label
    }

    fun downloadSubmissao(){
        var readChannel : ReadableByteChannel = Channels.newChannel(URL("http://teste.com").openStream())
        var fileOS : FileOutputStream = FileOutputStream("/Users/username/Documents/file_name.txt")
        var writeChannel : FileChannel = fileOS.channel
        writeChannel.transferFrom(readChannel,0,Long.MAX_VALUE)
    }

    override fun stopCellEditing(): Boolean {
        clicked = false
        return super.stopCellEditing()
    }

}
