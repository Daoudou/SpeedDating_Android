package fr.daoudou.speeddating.Service

import android.os.AsyncTask
import android.util.JsonReader
import android.util.JsonToken
import fr.daoudou.speeddating.Info.LinkInfo
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class LinkService : AsyncTask<String, List<*>, String>() {

    private val apiUrl = UserService().getApiIp()
    private val getAllIdInfoWithIdUser = "$apiUrl/linkUser/"

    fun getAll(queryIdUser: String) : List<LinkInfo>{
        val url = URL(String.format("$getAllIdInfoWithIdUser%s","$queryIdUser"))
        var httpURLConnection : HttpURLConnection? = null
        try {
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.connect()
            val code = httpURLConnection.responseCode
            if (code != HttpURLConnection.HTTP_OK)
                return emptyList()
            val inputStream = httpURLConnection.inputStream ?: return emptyList()
            val reader = JsonReader(inputStream.bufferedReader())
            val result = mutableListOf<LinkInfo>()
            reader.beginArray()
            while (reader.hasNext()){
                reader.beginObject()
                val idLinkList = LinkInfo("","","")
                while (reader.hasNext()){
                    when(reader.nextName()){
                        "idInfos" -> if (reader.peek()!= JsonToken.NULL) idLinkList.idInfos = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        else -> reader.skipValue()
                    }
                }
                result.add(idLinkList)
                reader.endObject()
            }
            reader.endArray()
            return result
        }catch (e  :IOException){
            return emptyList()
        }finally {
            httpURLConnection?.disconnect()
        }

    }

    override fun doInBackground(vararg p0: String?): String {
        TODO("Not yet implemented")
    }
}