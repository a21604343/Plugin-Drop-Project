package submissionTable.Professor


import com.intellij.ide.impl.ProjectUtil
import com.tfc.ulht.FastOpener
import com.tfc.ulht.Globals
import com.tfc.ulht.download.FileDownloader
import com.tfc.ulht.loginComponents.Authentication
import submissionTable.submissionHistory.ListSubmissionsHistory
import java.awt.Component
import java.io.*
import java.net.MalformedURLException
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import javax.swing.*
import kotlin.random.Random


internal class SubmissionProfessorTableButtonEditor(
    txt: JTextField?,
    private val label: String,
    private val frame: JFrame,
    private var assignmentID : String,
    private var assignmentTestsType : String
) : DefaultCellEditor(txt) {
    private var button: JButton = JButton(label)
    private var clicked: Boolean = false

    private var row: Int = 0
    private var column: Int = 0
    private var report: String = ""
    private var idGroup: String = ""
    private var tempFileName : String = ""
    private var baseFolder = ""
    private var reportPosition = 8


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
        when(assignmentTestsType){
            "0" -> this.report = table.model.getValueAt(row, 8).toString()
            "1","3" ->{
                reportPosition = 9
                this.report = table.model.getValueAt(row, 9).toString()}
            "2" -> {
                reportPosition = 10
                this.report = table.model.getValueAt(row, 10).toString()
            }
        }
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
                    ListSubmissionsHistory(assignmentID,idGroup,assignmentTestsType)
                }else{
                  //  ListSubmissions(assignmentId)
                }
            }
            else if (column == reportPosition) {
                val ed1 = JEditorPane("text/html", report)
                ed1.isEditable = false
                val tempFrame = JFrame("Last Submission Report ")
                tempFrame.add(ed1)
                tempFrame.isLocationByPlatform = true
                tempFrame.setSize(850, 1000)
                tempFrame.isVisible = true
            }
            else if (column == reportPosition+1) { // Download LAST
                JOptionPane.showMessageDialog(null, "Preparing to download last submission of group ${this.idGroup}, Sub ID = ${checkLastSubToDownload()}").toString()
                Globals.submissionSelectedToDownload = checkLastSubToDownload()
                downloadSubmissao()
                // var f = FastOpener.adjust(File("C:\\Users\\Diogo Casaca\\testeSubTFC\\exemploProf"))
                var f = FastOpener.adjust(File(baseFolder))
                if(f != null){
                    ProjectUtil.openOrImport(f.getAbsolutePath(), null, true);
                }

            }
        }
        clicked = false
        if(column == reportPosition){
            return report
        }
        return label
    }

    fun checkLastSubToDownload() : String{
        var resultIdSubmission = ""
        var size = Globals.hashSubByGroupId.get(idGroup)?.size
        if (size != null) {
            resultIdSubmission = Globals.hashSubByGroupId.get(idGroup)?.get(size-1)?.submissionId.toString()
        }
        return resultIdSubmission
    }

    fun downloadSubmissao() {
        try {
            //val url = URL("https://github.com/brunompc/aula-15-exceptions/archive/refs/heads/master.zip")
            //val urlToAutenticate = "http://localhost:8080/downloadOriginalProject/46"
             val urlToAutenticate = "http://localhost:8080/downloadMavenProject/" + Globals.submissionSelectedToDownload
            var filename = "DownloadedSubmissionID_" + Globals.submissionSelectedToDownload + "_"
            var nameUnzipped = "unzipped_" + Globals.submissionSelectedToDownload + "_"
            baseFolder = Globals.pathToDownloadAndUnZip + nameUnzipped + rand(0,1000)
            tempFileName = Globals.pathToDownloadAndUnZip + filename + rand(0,1000) + ".zip"
            var ficheiroDestino : OutputStream = FileOutputStream(tempFileName)
            var downloader = FileDownloader(Authentication.httpClient,com.tfc.ulht.download.FileWriter(ficheiroDestino))
            downloader.download(urlToAutenticate)
            //Files.copy(ficheiroDestino, Paths.get(outputFile))
            extractFolder(tempFileName,baseFolder)

            } catch (e: MalformedURLException) {
                println("URL malformed")
                e.printStackTrace()
             } catch (e: java.nio.file.FileAlreadyExistsException) {
                println("O ficheiro de destino já existe")
            } catch (e: FileNotFoundException) {
                println("file not found")
                e.printStackTrace()
            } catch (e: IOException) {
                println("input e output error")
                e.printStackTrace()
            }
    }

    private fun extractFolder(zipFile: String, extractFolder: String) {
        try {
            println("extrac")
            val BUFFER = 2048
            val file = File(zipFile)
            //val fileoutput = FileOutputStream(file)
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

    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        val rand = Random(System.nanoTime())
        return (start..end).random(rand)
    }


    override fun stopCellEditing(): Boolean {
        clicked = false
        return super.stopCellEditing()
    }

}
