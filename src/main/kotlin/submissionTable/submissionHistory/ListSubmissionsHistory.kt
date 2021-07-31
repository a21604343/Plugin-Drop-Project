package submissionTable.submissionHistory

import assignmentTable.SubmissionProfessorTableColumn
import assignmentTable.SubmissionTableColumn
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tfc.ulht.Globals
import com.tfc.ulht.loginComponents.Authentication
import data.Indicadores
import data.Submission
import data.Submission_Professor
import okhttp3.Request
import java.lang.reflect.Type


class ListSubmissionsHistory(assignmentId : String, GroupId: String) {
        companion object {
            var selectedSubmission: String = ""
        }
        var type: Type = Types.newParameterizedType(
            List::class.java,
            Submission::class.java
        )
        //http://localhost:8080/submissions/?assignmentId=sampleJavaProject&groupId=44
        private val REQUEST_URL = "${Globals.REQUEST_URL}/submissions/?assignmentId=${assignmentId}&groupId=${GroupId}"
        private var submissionList = listOf<Submission>()
        private val moshi = Moshi.Builder().build()
        private val submissionProfessorJsonAdapter: JsonAdapter<List<Submission_Professor>> = moshi.adapter(type)
        private val submissionJsonAdapter: JsonAdapter<List<Submission>> = moshi.adapter(type)



        init {

                println("***** " + REQUEST_URL)
                val request = Request.Builder()
                    .url(REQUEST_URL)
                    .build()

                Authentication.httpClient.newCall(request).execute().use { response ->
                   // submissionList = submissionJsonAdapter.fromJson(response.body()!!.source())!!
                }



                submissionList = Globals.hashSubByGroupId.get(GroupId) as List<Submission>
                showSubmissionList()


        }



        private fun showSubmissionList() {

            if(Globals.user_type == 0){
                SubmissionHistoryTableColumn(submissionList)
            }else{
                SubmissionTableColumn(submissionList)
            }

        }


    }
