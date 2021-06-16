package submissionTable.submissionHistory

import com.intellij.ide.impl.ProjectUtil
import com.tfc.ulht.FastOpener
import com.tfc.ulht.Globals
import com.tfc.ulht.submissionComponents.ListSubmissions
import okhttp3.Request
import sun.net.www.http.HttpClient
import java.awt.Component
import javax.swing.*
import sun.net.www.http.HttpCaptureOutputStream
import java.awt.Desktop
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.FileChannel
import java.nio.channels.ReadableByteChannel
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import javax.net.ssl.HttpsURLConnection


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
                var f = FastOpener.adjust(File("C:\\Users\\Diogo Casaca\\testeSubTFC\\exemploProf"))
                if(f != null){
                    ProjectUtil.openOrImport(f.getAbsolutePath(), null, true);
                }


            }
        }
        clicked = false
        return label
    }

    fun downloadSubmissao() {
        try {
            val url = URL("https://github.com/brunompc/aula-15-exceptions/archive/refs/heads/master.zip")
            val con = url.openConnection() as HttpsURLConnection
            val baseFolder = "C:\\Users\\Diogo Casaca\\testeSubTFC\\unzipTESTE"
            val outputFile = "C:\\Users\\Diogo Casaca\\testeSubTFC\\file_name3.zip"
            con.inputStream.use { stream -> Files.copy(stream, Paths.get(outputFile)) }
            extractFolder(outputFile,baseFolder)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: java.nio.file.FileAlreadyExistsException) {
            println("O ficheiro de destino já existe")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun extractFolder(zipFile: String, extractFolder: String) {
        try {
            val BUFFER = 2048
            val file = File(zipFile)
            val zip = ZipFile(file)
            File(extractFolder).mkdir()
            val zipFileEntries: Enumeration<*> = zip.entries()

            // Process each entry
            while (zipFileEntries.hasMoreElements()) {
                // grab a zip file entry
                val entry = zipFileEntries.nextElement() as ZipEntry
                val currentEntry = entry.name
                val destFile = File(extractFolder, currentEntry)
                //destFile = new File(newPath, destFile.getName());
                val destinationParent = destFile.parentFile

                // create the parent directory structure if needed
                destinationParent.mkdirs()
                if (!entry.isDirectory) {
                    val `is` = BufferedInputStream(zip.getInputStream(entry))
                    var currentByte: Int
                    // establish buffer for writing file
                    val data = ByteArray(BUFFER)

                    // write the current file to disk
                    val fos = FileOutputStream(destFile)
                    val dest = BufferedOutputStream(
                        fos,
                        BUFFER
                    )

                    // read and write until last byte is encountered
                    while (`is`.read(data, 0, BUFFER).also { currentByte = it } != -1) {
                        dest.write(data, 0, currentByte)
                    }
                    dest.flush()
                    dest.close()
                    `is`.close()
                }
            }
        } catch (e: java.lang.Exception) {
            println("ERROR: " + e.message)
        }
    }


    override fun stopCellEditing(): Boolean {
        clicked = false
        return super.stopCellEditing()
    }

}
