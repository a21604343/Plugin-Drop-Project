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

import com.jetbrains.rd.util.use
import com.tfc.ulht.Globals
import okhttp3.*
import java.io.IOException

class Authentication {

    companion object {
        var httpClient = OkHttpClient()
        var alreadyLoggedIn = false
    }

    var serverResponse: Boolean = false

    fun checkCredentials(username: String, password: String, firstRun: Boolean = false): Boolean {

        httpClient = OkHttpClient.Builder()
            .authenticator(object : Authenticator {
                @Throws(IOException::class)
                override fun authenticate(route: Route?, response: Response): Request? {
                    if (response.request().header("Authorization") != null) {
                        return null // Give up, we've already attempted to authenticate.
                    }

                    return response.request().newBuilder()
                        .header("Authorization", Credentials.basic(username, password))
                        .build()
                }
            })
            .build()

        val request = Request.Builder()
            .url(Globals.REQUEST_URL)
            .build()

        httpClient.newCall(request).execute().use { response ->
            serverResponse = response.isSuccessful
            println("**** " + response)
        }


        /*httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                //serverResponse = false
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    serverResponse = response.isSuccessful
                }
            }
        })*/

        println(serverResponse)

        if (serverResponse && !firstRun) {
            CredentialsController().encryptPassword(username, password)
            alreadyLoggedIn = true
        }

        return serverResponse
    }

}