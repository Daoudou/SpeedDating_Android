package fr.daoudou.speeddating.Service

import android.util.JsonReader
import android.util.JsonToken
import fr.daoudou.speeddating.Info.DateInfo
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class DateService {

    private val apiUrl = "http://172.21.188.132:3000"
    private val getAllDate = "$apiUrl/dating/"

    fun getAllDate(): List<DateInfo>{
        val url = URL(getAllDate)
        var httpURLConnection : HttpURLConnection? = null
        try {
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connect()
            val code = httpURLConnection.responseCode
            if (code != HttpURLConnection.HTTP_OK)
                return emptyList()
            val inputStream = httpURLConnection.inputStream ?: return emptyList()
            val reader = JsonReader(inputStream.bufferedReader())
            val result = mutableListOf<DateInfo>()
            reader.beginArray()
            while (reader.hasNext()){
                reader.beginObject()
                val dateList = DateInfo("","",null,"","")
                while (reader.hasNext()){
                    when(reader.nextName()){
                        "dateDating" -> if (reader.peek()!= JsonToken.NULL) dateList.date = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        "comment" -> if (reader.peek()!= JsonToken.NULL) dateList.comment = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        "note" -> if (reader.peek()!= JsonToken.NULL) dateList.note = reader.nextInt()
                        else {reader.hasNext(); "Non renseigne"}
                        else -> reader.skipValue()
                    }
                }
                result.add(dateList)
                reader.endObject()
            }
            reader.endArray()
            return result
        }catch (e : IOException){
            return emptyList()
        }finally {
            httpURLConnection?.disconnect()
        }
    }
}