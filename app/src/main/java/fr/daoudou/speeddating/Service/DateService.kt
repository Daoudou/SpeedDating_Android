package fr.daoudou.speeddating.Service

import android.util.JsonReader
import android.util.JsonToken
import fr.daoudou.speeddating.Info.DateInfo
import fr.daoudou.speeddating.Security.ResponseCode
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class DateService {

    private val apiUrl = UserService().getApiIp()
    private val getAllDate = "$apiUrl/dating/"
    private val createDate = "$apiUrl/dating/datingAdd/"
    private val getDateByUser = "$apiUrl/dating/userDateId/"
    private val deleteDate = "$apiUrl/dating/deleteDate/"
    private val updateDate = "$apiUrl/dating/"

    fun getAllDateByUser(queryIdUser :String): List<DateInfo>{
        val url = URL(String.format("$getDateByUser%s","$queryIdUser"))
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
                val dateList = DateInfo("","","",null,"","")
                while (reader.hasNext()){
                    when(reader.nextName()){
                        "id" -> if (reader.peek()!= JsonToken.NULL) dateList.id = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        "dateDating" -> if (reader.peek()!= JsonToken.NULL) dateList.date = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        "peopleAdding" -> if (reader.peek()!= JsonToken.NULL) dateList.peopleAdd = reader.nextString()
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

    fun createDatingByUser(queryPeople : String,
                           queryDatingDate : String,
                           queryComment :String,
                           queryNote :String,
                           queryIdInfo :String,
                           queryIdUser : String
                           ) : ResponseCode.StatusCode{

        val url = URL(String.format("$createDate%s/%s/%s/%s/%s/%s",
                                    "$queryPeople",
                                    "$queryDatingDate",
                                    "$queryComment",
                                    "$queryNote",
                                    "$queryIdInfo",
                                    "$queryIdUser"))

        var httpURLConnection  :HttpURLConnection? = null
        try {
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.connect()
            val code =httpURLConnection.responseCode
            if (code != HttpURLConnection.HTTP_CREATED)
                return ResponseCode.StatusCode.BadRequest
        }catch (e : IOException){
            println(e)
        }finally {
            httpURLConnection?.disconnect()
        }
        return ResponseCode.StatusCode.Created
    }

    fun deleteDate(queryIdDate : String): ResponseCode.StatusCode {
        val url = URL(String.format("$deleteDate%s","$queryIdDate"))
        var httpURLConnection  :HttpURLConnection? = null
        try {
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "DELETE"
            httpURLConnection.connect()
            val code =httpURLConnection.responseCode
            if (code != HttpURLConnection.HTTP_OK)
                return ResponseCode.StatusCode.BadRequest
        }catch (e : IOException){
            println(e)
        }finally {
            httpURLConnection?.disconnect()
        }
        return ResponseCode.StatusCode.Created
    }

    fun updateDate(queryIdDate: String,queryDateDating : String, queryNote : Int, queryComment : String) : ResponseCode.StatusCode{
        val url = URL(String.format("$updateDate%s/%s/%s/%s","$queryIdDate","$queryDateDating","$queryNote","$queryComment"))
        var httpURLConnection  :HttpURLConnection? = null
        try {
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "PUT"
            httpURLConnection.connect()
            val code =httpURLConnection.responseCode
            if (code != HttpURLConnection.HTTP_OK)
                return ResponseCode.StatusCode.BadRequest
        }catch (e : IOException){
            println(e)
        }finally {
            httpURLConnection?.disconnect()
        }
        return ResponseCode.StatusCode.Created
    }
}