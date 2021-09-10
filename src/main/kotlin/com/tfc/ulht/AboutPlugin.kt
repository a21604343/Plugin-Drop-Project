package com.tfc.ulht

/**
 *  Open authors LinkedIn webpage
 */

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import java.net.URI
import javax.swing.Icon
import javax.swing.ImageIcon
import javax.swing.JOptionPane


class AboutPlugin : DumbAware, AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val text = "This plugin is made by a total of two students to another students and future students who" +
                "use the Drop Project website for submissions to help automate the process of submitting" +
               " the projects on the system. The plugin is designed to help teachers see and analize the submission" +
                "made by students, when they can download and automatically open the submission downloaded in another" +
                "window of IntelliJ. They can also see the report of the students submissions, and check their" +
                "participation. This Plugin can also help student submit their projects on the platform without" +
                "needing to create a zip as this will be automated by the plugin.\n\n\n" +
                "Plugin made by: Yash Jahit (v1) - https://github.com/yashjahit-21705201/Plugin-Drop-Project\n" +
                              "  Diogo Casaca (v2) - https://github.com/a21604343/Plugin-Drop-Project"

        JOptionPane.showMessageDialog(
            null,
            text,
            "About",
            JOptionPane.INFORMATION_MESSAGE,
            imageIconCreate()
        )

    }

    fun openURI() {
        val githubLink = "https://github.com/a21604343/Plugin-Drop-Project"
        val url = URI(githubLink)
        try {
            val desktop = java.awt.Desktop.getDesktop()
            desktop.browse(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun imageIconCreate(): Icon? {

        val pluginLogo = ImageIcon(this.javaClass.classLoader.getResource("images/plugin_dp_logo_smaller.png")).image
        val icon = ImageIcon(pluginLogo)

        return icon
    }

}


