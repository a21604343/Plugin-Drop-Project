package assignmentTable





import java.awt.Component

import java.io.*
import java.net.MalformedURLException
import java.net.URL

import java.nio.file.Files

import java.nio.file.Paths
import java.util.zip.ZipEntry

import javax.net.ssl.HttpsURLConnection
import javax.swing.*

import java.util.*
import java.util.zip.ZipFile


internal class SubmissionTableButtonEditor(
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
               // println(report)
                val ed1 = JEditorPane("text/html", report)
                ed1.isEditable = false
                val tempFrame = JFrame("Report")
                tempFrame.add(ed1)
                tempFrame.isLocationByPlatform = true
                tempFrame.setSize(850, 400)
                tempFrame.isVisible = true
                downloadSubmissao()



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
