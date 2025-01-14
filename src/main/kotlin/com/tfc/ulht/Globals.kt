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

package com.tfc.ulht

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.tfc.ulht.statusBarWidget.PluginStatusWidget
import data.Assignment
import data.Assignment_Professor
import data.Submission

class Globals {

    companion object {
        val REQUEST_URL =  "http://127.0.0.1:8080" // "https://plugindropproject.herokuapp.com"
        val PLUGIN_ID = PluginStatusWidget::class.java.name

        var choosenRow: Int = 0
        var choosenColumn: Int = 0

        //Tipos de Acesso
        var user_type = 0
        var taLigado = true

        var selectedAssignmentID: String = ""
        var submissionSelectedAsFinal = "" // criar hashmap, key sendo uma classe (id assignement, id group) valor submissão selecionada como final
        var submissionSelectedToDownload = ""


        //Config
        var pathToDownloadAndUnZip = "C:\\Users\\Diogo Casaca\\testeSubTFC\\"


        // Armazenar e manipular dados
        var listAssignments : MutableList<Assignment> = ArrayList()
        var hashSubmissionsByAssignment : HashMap<String,MutableList<Submission>> = hashMapOf() // chave id Assignment
        var listAssignmentsDP : List<Assignment> = ArrayList()
        var listSubmissionsDP : List<Submission> = ArrayList()
        var hashSubByGroupId : HashMap<String,MutableList<Submission>> = hashMapOf() // chave GroupID
        var hashAssiSub : HashMap<Assignment,List<Submission>> = HashMap()
        var sortedOnce : String = "0" // 0 = initial mode / 1 = Decrescente / 2 = Crescente
        var sortedNumSub = "0"

        var listaToDownload = mutableListOf<String>()
    }

}