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

package com.tfc.ulht.loginComponents

import TextPrompt
import com.tfc.ulht.Users
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.GridLayout
import java.awt.event.ActionListener
import javax.swing.*


class LoginDialog {

    companion object {
        val studentsList = ArrayList<Users>()
    }

    private var GRIDY: Int = 3
    private val nameLabel = JLabel("Name:   ")
    private val usernameLabel = JLabel("Username:   ")
    private val passwordField = JPasswordField()
    private val passwordLabel = JLabel("Password:   ")
    private val addGroupStudents = JButton("Click here to add your group elements")
    private val studentNumberField = mutableListOf<JTextField>()
    private val studentNameField = mutableListOf<JTextField>()
    private val numberField = JTextField()
    private val nameField = JTextField()


    fun assembleDialog(panel: JPanel) {

        val gbPanel = GridBagLayout()
        val gbc = GridBagConstraints()
        var countUsers = 0

        panel.layout = GridLayout(6, 2)

        /**
         * Name
         */
        gbc.gridx = 0; gbc.gridy = 0
        gbc.gridwidth = 1
        gbc.fill = GridBagConstraints.HORIZONTAL
        gbPanel.setConstraints(nameLabel, gbc)
        panel.add(nameLabel)

        studentNameField.add(nameField)
        gbc.gridx = 1; gbc.gridy = 0
        gbc.gridwidth = 3
        gbPanel.setConstraints(studentNameField[countUsers], gbc)
        panel.add(nameField)

        /**
         * Username
         */
        gbc.gridx = 0; gbc.gridy = 1
        gbPanel.setConstraints(usernameLabel, gbc)
        panel.add(usernameLabel)

        studentNumberField.add(numberField)
        gbc.gridx = 1; gbc.gridy = 1
        gbPanel.setConstraints(studentNumberField[countUsers], gbc)
        panel.add(studentNumberField[countUsers])

        /**
         * Password
         */
        gbc.gridx = 0; gbc.gridy = 2
        gbPanel.setConstraints(passwordLabel, gbc)
        panel.add(passwordLabel)

        gbc.gridx = 1; gbc.gridy = 2
        gbPanel.setConstraints(passwordField, gbc)
        panel.add(passwordField)

        /**
         * Add more students
         */
        gbc.gridx = 0; gbc.gridy = 3
        gbPanel.setConstraints(addGroupStudents, gbc)
        panel.add(addGroupStudents)

        /**
         * Empty label after add students button
         */
        val emptyLabel = JLabel()
        panel.add(emptyLabel)


        val actionListener = ActionListener { actionEvent ->
            if (countUsers < 2) {
                countUsers++
                addMoreStudents(panel, gbc, gbPanel, countUsers)
            }
        }

        addGroupStudents.addActionListener(actionListener)

        val opt = arrayOf("Log In", "Cancel")
        UIManager.put("OptionPane.minimumSize", Dimension(panel.width, panel.height))
        val option = JOptionPane.showOptionDialog(
            null, panel, "Log In",
            JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION, null, opt, opt[0]
        )

        /**
         * Check credential
         * */
        if (option == 0) {


            val response = Authentication().checkCredentials(
                studentNumberField[0].text.trim(),
                String(passwordField.password)
            )

            UIManager.put("OptionPane.minimumSize", Dimension(200, 100))

            println("*** " + response)
            if (response && !checkIfCorrectNumber()) {
                JOptionPane.showMessageDialog(null, "Login Successful")
            } else if (!response) {
                JOptionPane.showMessageDialog(
                    null, "Login credentials incorrect!", "Error!",
                    JOptionPane.ERROR_MESSAGE
                )
            }
        }
    }

    private fun addMoreStudents(panel: JPanel, gbc: GridBagConstraints, gbPanel: GridBagLayout, count: Int): JPanel {

        val numberField = JTextField()
        val nameField = JTextField()

        val numberFieldPrompt = TextPrompt(
            "Write your student number here",
            numberField, TextPrompt.Show.FOCUS_LOST
        )
        numberFieldPrompt.changeAlpha(0.5f)

        val nameFieldPrompt = TextPrompt(
            "Write your student name here",
            nameField, TextPrompt.Show.FOCUS_LOST
        )
        nameFieldPrompt.changeAlpha(0.5f)

        studentNumberField.add(numberField)
        studentNameField.add(nameField)

        gbc.gridx = 0; gbc.gridy = GRIDY++
        gbc.fill = GridBagConstraints.HORIZONTAL
        gbPanel.setConstraints(studentNumberField[count], gbc)

        panel.add(studentNumberField[count])

        gbc.gridx = 1; gbc.gridy = GRIDY
        gbc.fill = GridBagConstraints.HORIZONTAL
        gbPanel.setConstraints(studentNameField[count], gbc)
        panel.add(studentNameField[count])

        panel.parent.revalidate()
        return panel
    }

    private fun checkIfCorrectNumber(): Boolean {
        return false
        var index = 0
        for (number in studentNumberField) {
            val addOrNot = number.text.trim().contains('a') && number.text.length == 9

            if (addOrNot) {
                studentsList.add(Users(number.text.trim(), studentNameField[index].text.trim()))
                index++
            } else {
                JOptionPane.showMessageDialog(
                    null,
                    "User ${number.text.trim()} is not in the correct format, please try again",
                    "Error in student number", JOptionPane.ERROR_MESSAGE
                )
                return true
            }
        }

        return false
    }
}