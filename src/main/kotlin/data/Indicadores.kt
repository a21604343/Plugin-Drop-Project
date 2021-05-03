package data

data class Indicadores(
    var estrutura : Boolean,
    var compilacao : Boolean,
    var checkStyle : Boolean,
    var studentTest: String?,
    var teacherTest: String?,
    var privateTest: String?,
    var execTime: String?,
    var coverage: String
)