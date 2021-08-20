package com.tfc.ulht

class Configurations {


    fun switchToTeacher(){
        Globals.user_type = 0
    }
    fun switchToStudent(){
        Globals.user_type = 1
    }

    fun pathWindowsUnziped(userName : String) : String{
        return "C:\\Users\\${userName}\\testeSubTFC\\unzipTESTE"
    }

    fun pathWindowsZiped(userName : String) : String{
        return "C:\\Users\\${userName}\\testeSubTFC\\file_name.zip"
    }

}