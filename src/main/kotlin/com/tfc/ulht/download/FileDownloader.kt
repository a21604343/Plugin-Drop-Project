package com.tfc.ulht.download

import com.google.common.net.HttpHeaders.CONTENT_LENGTH
import okhttp3.OkHttpClient
import okhttp3.Request

import java.io.IOException
import java.util.*


class FileDownloader(private val client: OkHttpClient, writer: FileWriter) : AutoCloseable {
    private val writer: FileWriter
    @Throws(IOException::class)
    fun download(url: String?): Long {
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val responseBody = response.body() ?: throw IllegalStateException("Response doesn't contain a file")
        val length = Objects.requireNonNull(response.header(CONTENT_LENGTH, "1"))!!
            .toDouble()
        return writer.write(responseBody.byteStream(), length)
    }

    @Throws(Exception::class)
    override fun close() {
        writer.close()
    }

    init {
        this.writer = writer
    }
}