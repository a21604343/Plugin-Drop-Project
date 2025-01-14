package com.tfc.ulht

import data.Assignment
import data.Submission

class QuickSort {

        fun partitionData(numSubs: MutableList<String>, Idsubs : MutableList<String>, low: Int, high: Int): Int {
            val pivot: Int = numSubs[high].toInt()
            var i = low - 1 // index of smaller element
            for (j in low until high) {
                if (numSubs[j].toInt() < pivot) {
                    i++
                    val temp: String = numSubs[i]
                    numSubs[i] = numSubs[j]
                    numSubs[j] = temp
                    val temp2: String = Idsubs[i]
                    Idsubs[i] = Idsubs[j]
                    Idsubs[j] = temp2
                }
            }
            val temp: String = numSubs[i + 1]
            numSubs[i + 1] = numSubs[high]
            numSubs[high] = temp
            val temp2: String = Idsubs[i + 1]
            Idsubs[i + 1] = Idsubs[high]
            Idsubs[high] = temp2
            return i + 1
        }

        fun sortData(array: MutableList<String>, array2 : MutableList<String>, low: Int, high: Int): MutableList<String> {
            if (low < high) {
                val part = partitionData(array,array2, low, high)
                sortData(array,array2, low, part - 1)
                sortData(array,array2, part + 1, high)
            }
            return array2
        }




    fun partitionDataID(numSubs: MutableList<Submission>, low: Int, high: Int): Int {
        val pivot: Int = numSubs[high].submissionId.toInt()
        var i = low - 1 // index of smaller element
        for (j in low until high) {
            if (numSubs[j].submissionId.toInt() < pivot) {
                i++
                val temp: Submission = numSubs[i]
                numSubs[i] = numSubs[j]
                numSubs[j] = temp

            }
        }
        val temp: Submission= numSubs[i + 1]
        numSubs[i + 1] = numSubs[high]
        numSubs[high] = temp

        return i + 1
    }

    fun sortDataID(array: MutableList<Submission>, low: Int, high: Int): MutableList<Submission> {
        if (low < high) {
            val part = partitionDataID(array, low, high)
            sortDataID(array, low, part - 1)
            sortDataID(array, part + 1, high)
        }
        return array
    }



    fun partitionDataID2(numSubs: MutableList<Submission>, low: Int, high: Int): Int {
        val pivot: Int = numSubs[high].idGroup.toInt()
        var i = low - 1 // index of smaller element
        for (j in low until high) {
            if (numSubs[j].idGroup.toInt() < pivot) {
                i++
                val temp: Submission = numSubs[i]
                numSubs[i] = numSubs[j]
                numSubs[j] = temp

            }
        }
        val temp: Submission= numSubs[i + 1]
        numSubs[i + 1] = numSubs[high]
        numSubs[high] = temp

        return i + 1
    }

    fun sortDataID2(array: MutableList<Submission>, low: Int, high: Int): MutableList<Submission> {
        if (low < high) {
            val part = partitionDataID2(array, low, high)
            sortDataID2(array, low, part - 1)
            sortDataID2(array, part + 1, high)
        }
        return array
    }

    fun partitionDataAssi(numSubs: MutableList<Assignment>, low: Int, high: Int): Int {
        val pivot: Int = numSubs[high].numSubmissions.toInt()
        var i = low - 1 // index of smaller element
        for (j in low until high) {
            if (numSubs[j].numSubmissions.toInt() > pivot) {
                i++
                val temp: Assignment = numSubs[i]
                numSubs[i] = numSubs[j]
                numSubs[j] = temp

            }
        }
        val temp: Assignment= numSubs[i + 1]
        numSubs[i + 1] = numSubs[high]
        numSubs[high] = temp

        return i + 1
    }

    fun sortDataAssi(array: MutableList<Assignment>, low: Int, high: Int): MutableList<Assignment> {
        if (low < high) {
            val part = partitionDataAssi(array, low, high)
            sortDataAssi(array, low, part - 1)
            sortDataAssi(array, part + 1, high)
        }
        return array
    }

    fun partitionDataString(numSubs: MutableList<Assignment>, low: Int, high: Int): Int {
        val pivot: Int = numSubs[high].id.get(0).toLowerCase().toInt()
        var i = low - 1 // index of smaller element
        for (j in low until high) {
            if (numSubs[j].id.get(0).toLowerCase().toInt() < pivot) {
                i++
                val temp: Assignment = numSubs[i]
                numSubs[i] = numSubs[j]
                numSubs[j] = temp

            }
            if (numSubs[j].id.get(0).toLowerCase().toInt() == pivot) {
                    var count = 0
                    if(numSubs[j].id.length > numSubs[high].id.length){
                        for(k  in numSubs[high].id){
                            if (numSubs[j].id.get(count).toLowerCase().toInt() >= numSubs[high].id.get(count).toLowerCase().toInt()){
                              if (numSubs[j].id.get(count).toLowerCase().toInt() == numSubs[high].id.get(count).toLowerCase().toInt() && count == numSubs[high].id.length-1){
                                  i++
                                  val temp: Assignment = numSubs[i]
                                  numSubs[i] = numSubs[j]
                                  numSubs[j] = temp
                                  break

                                }
                                count++
                            }else{
                                i++
                                val temp: Assignment = numSubs[i]
                                numSubs[i] = numSubs[j]
                                numSubs[j] = temp
                                break
                            }
                        }
                    }else{
                        for(k  in numSubs[j].id){
                            if (numSubs[j].id.get(count).toLowerCase().toInt() >= numSubs[high].id.get(count).toLowerCase().toInt()){
                                if (numSubs[j].id.get(count).toLowerCase().toInt() == numSubs[high].id.get(count).toLowerCase().toInt() && count == numSubs[j].id.length-1){
                                    i++
                                    val temp: Assignment = numSubs[i]
                                    numSubs[i] = numSubs[j]
                                    numSubs[j] = temp
                                    break

                                }


                                count++
                            }else{
                                i++
                                val temp: Assignment = numSubs[i]
                                numSubs[i] = numSubs[j]
                                numSubs[j] = temp
                                break
                            }
                        }

                    }



            }
        }
        val temp: Assignment= numSubs[i + 1]
        numSubs[i + 1] = numSubs[high]
        numSubs[high] = temp

        return i + 1
    }

    fun sortDataString(array: MutableList<Assignment>, low: Int, high: Int): MutableList<Assignment> {
        if (low < high) {
            val part = partitionDataString(array, low, high)
            sortDataString(array, low, part - 1)
            sortDataString(array, part + 1, high)
        }
        return array
    }

    fun partitionDataNameAssi(numSubs: MutableList<Assignment>, low: Int, high: Int): Int {
        val pivot: Int = numSubs[high].name.get(0).toLowerCase().toInt()
        var i = low - 1 // index of smaller element
        for (j in low until high) {
            if (numSubs[j].name.get(0).toLowerCase().toInt() < pivot) {
                i++
                val temp: Assignment = numSubs[i]
                numSubs[i] = numSubs[j]
                numSubs[j] = temp

            }
            if (numSubs[j].name.get(0).toLowerCase().toInt() == pivot) {
                var count = 0
                if(numSubs[j].name.length > numSubs[high].name.length){
                    for(k  in numSubs[high].name){
                        if (numSubs[j].name.get(count).toLowerCase().toInt() >= numSubs[high].name.get(count).toLowerCase().toInt()){
                            if (numSubs[j].name.get(count).toLowerCase().toInt() == numSubs[high].name.get(count).toLowerCase().toInt() ){

                                if(count == numSubs[high].name.length-1){
                                    break
                                }else{
                                    count++
                                }

                                /*
                                i++
                                val temp: Assignment = numSubs[i]
                                numSubs[i] = numSubs[j]
                                numSubs[j] = temp
                                 */

                            }

                        }else{

                            i++
                            val temp: Assignment = numSubs[i]
                            numSubs[i] = numSubs[j]
                            numSubs[j] = temp


                            break
                        }
                    }
                }else{
                    for(k  in numSubs[j].name){
                        if (numSubs[j].name.get(count).toLowerCase().toInt() >= numSubs[high].name.get(count).toLowerCase().toInt()){
                            if (numSubs[j].name.get(count).toLowerCase().toInt() == numSubs[high].name.get(count).toLowerCase().toInt()  ){
                                if (count == numSubs[j].name.length-1){
                                    i++
                                    val temp: Assignment = numSubs[i]
                                    numSubs[i] = numSubs[j]
                                    numSubs[j] = temp
                                    break
                                }else{
                                    count++
                                }

                            }

                        }else{
                            i++
                            val temp: Assignment = numSubs[i]
                            numSubs[i] = numSubs[j]
                            numSubs[j] = temp
                            break
                        }
                    }

                }
            }
        }
        val temp: Assignment= numSubs[i + 1]
        numSubs[i + 1] = numSubs[high]
        numSubs[high] = temp

        return i + 1
    }

    fun sortDataNameAssi(array: MutableList<Assignment>, low: Int, high: Int): MutableList<Assignment> {
        if (low < high) {
            val part = partitionDataNameAssi(array, low, high)
            sortDataNameAssi(array, low, part - 1)
            sortDataNameAssi(array, part + 1, high)
        }
        return array
    }


    fun partitionDataLanguage(numSubs: MutableList<Assignment>, low: Int, high: Int): Int { // sort by Assignment Language for student
        val pivot: Int = numSubs[high].language.get(0).toLowerCase().toInt()
        var i = low - 1 // index of smaller element
        for (j in low until high) {
            if (numSubs[j].language.get(0).toLowerCase().toInt() < pivot) {
                i++
                val temp: Assignment = numSubs[i]
                numSubs[i] = numSubs[j]
                numSubs[j] = temp

            }
            if (numSubs[j].language.get(0).toLowerCase().toInt() == pivot) {
                var count = 0
                if(numSubs[j].language.length > numSubs[high].language.length){
                    for(k  in numSubs[high].language){
                        if (numSubs[j].language.get(count).toLowerCase().toInt() >= numSubs[high].language.get(count).toLowerCase().toInt()){
                            if (numSubs[j].language.get(count).toLowerCase().toInt() == numSubs[high].language.get(count).toLowerCase().toInt() ){

                                if(count == numSubs[high].language.length-1){
                                    break
                                }else{
                                    count++
                                }

                                /*
                                i++
                                val temp: Assignment = numSubs[i]
                                numSubs[i] = numSubs[j]
                                numSubs[j] = temp
                                 */

                            }

                        }else{

                            i++
                            val temp: Assignment = numSubs[i]
                            numSubs[i] = numSubs[j]
                            numSubs[j] = temp


                            break
                        }
                    }
                }else{
                    for(k  in numSubs[j].language){
                        if (numSubs[j].language.get(count).toLowerCase().toInt() >= numSubs[high].language.get(count).toLowerCase().toInt()){
                            if (numSubs[j].language.get(count).toLowerCase().toInt() == numSubs[high].language.get(count).toLowerCase().toInt()  ){
                                if (count == numSubs[j].language.length-1){
                                    i++
                                    val temp: Assignment = numSubs[i]
                                    numSubs[i] = numSubs[j]
                                    numSubs[j] = temp
                                    break
                                }else{
                                    count++
                                }

                            }

                        }else{
                            i++
                            val temp: Assignment = numSubs[i]
                            numSubs[i] = numSubs[j]
                            numSubs[j] = temp
                            break
                        }
                    }

                }
            }
        }
        val temp: Assignment= numSubs[i + 1]
        numSubs[i + 1] = numSubs[high]
        numSubs[high] = temp

        return i + 1
    }

    fun sortDataLanguage(array: MutableList<Assignment>, low: Int, high: Int): MutableList<Assignment> {
        if (low < high) {
            val part = partitionDataLanguage(array, low, high)
            sortDataLanguage(array, low, part - 1)
            sortDataLanguage(array, part + 1, high)
        }
        return array
    }





}