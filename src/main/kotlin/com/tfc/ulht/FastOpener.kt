package com.tfc.ulht

import com.intellij.ide.impl.ProjectUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.UnsupportedFlavorException
import java.io.*



class FastOpener : AnAction() {
    private val projectFromClipboard: File?
        private get() {
            val f = fileFromClipboard ?: return null
            return adjust(f)
        }// fall through// ignore
    // abandon the processing if it's taking too long
// ignore// fall through

    // if this is Unix, try xclip
    // one would expect that this is unnecessary, but somehow the above scheme
    // doesn't catch the contents xclip put into the clipboard.
    // fall through
    private val fileFromClipboard: File?
        private get() {
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            try {
                if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                    val data = clipboard.getData(DataFlavor.stringFlavor).toString()
                    val f = File(data.trim { it <= ' ' })
                    if (f.exists()) return f
                }
                if (clipboard.isDataFlavorAvailable(DataFlavor.javaFileListFlavor)) {
                    val data = clipboard.getData(DataFlavor.javaFileListFlavor) as List<File>
                    if (!data.isEmpty()) return data[0]
                }
            } catch (e: UnsupportedFlavorException) {
                // fall through
            } catch (e: IOException) {
                // fall through
            }

            // if this is Unix, try xclip
            // one would expect that this is unnecessary, but somehow the above scheme
            // doesn't catch the contents xclip put into the clipboard.
            if (File.separatorChar == '/') {
                try {
                    val result = arrayOfNulls<File>(1)
                    val t: Thread = object : Thread() {
                        override fun run() {
                            var p: Process? = null
                            try {
                                val buf = ByteArrayOutputStream()
                                val b = ProcessBuilder("xclip", "-o", "-selection", "clipboard")
                                b.redirectErrorStream(true)
                                p = b.start()
                                p.outputStream.close()
                                copyStream(p.inputStream, buf)
                                p.waitFor()
                                result[0] = File(String(buf.toByteArray()).trim { it <= ' ' })
                            } catch (e: IOException) {
                                // ignore
                                p?.destroy()
                            } catch (e: InterruptedException) {
                                // ignore
                                p?.destroy()
                            }
                        }
                    }
                    t.start()
                    t.join(1000)
                    t.interrupt() // abandon the processing if it's taking too long
                    if (result[0] != null && result[0]!!.exists()) return result[0]
                } catch (e: InterruptedException) {
                    // fall through
                }
            }
            return null
        }

    @Throws(IOException::class)
    private fun copyStream(i: InputStream, o: OutputStream) {
        val buf = ByteArray(1024)
        var len: Int
        while (i.read(buf).also { len = it } >= 0) o.write(buf, 0, len)
        i.close()
        o.close()
    }

    override fun update(event: AnActionEvent) {
        val p = event.presentation
        val f = projectFromClipboard
        if (f != null) {
            p.isEnabled = true
            p.text = "Open Project _Fast [.../" + f.parentFile.name + '/' + f.name + "]"
        } else {
            p.isEnabled = false
            p.text = "Open Project _Fast"
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        try {
            val f = projectFromClipboard
            if (f != null) ProjectUtil.openOrImport(f.absolutePath, null, true)
        } catch (x: Exception) {
            Messages.showErrorDialog(x.message, "Error!")
        }
    }

    companion object {
        fun adjust(f: File): File? {
            println("llll" + "tou aqui1")
            val n = f.name
            if (n.endsWith(".ipr") || n == "pom.xml") return f // project file name is selected
            if (f.isDirectory) {
                // directory is selected. Find the project file
                val children = f.listFiles()
                for (child in children) {
                    if (child.name.endsWith(".ipr")) return child
                    if (child.name == ".idea") return f
                }
                // next attempt is to find POM
                for (child in children) {
                    if (child.name == "pom.xml") return child
                }
            }

            // not a project file
            return null
        }
    }
}