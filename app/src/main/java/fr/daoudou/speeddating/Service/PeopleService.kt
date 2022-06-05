package fr.daoudou.speeddating.Service

import android.util.JsonReader
import android.util.JsonToken
import fr.daoudou.speeddating.Info.PeopleInfos
import fr.daoudou.speeddating.Security.ResponseCode
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import fr.daoudou.speeddating.Service.UserService

class PeopleService {

    private val apiUrl = UserService().getApiIp()
    private val getAllInfos = "$apiUrl/infos/infos"
    private val createInfoApirUrl = "$apiUrl/infos/infosAdd/"

    fun getAllInfos() : List<PeopleInfos>{
        val url = URL(getAllInfos)
        var httpURLConnection : HttpURLConnection? = null
        try {
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connect()
            val code = httpURLConnection.responseCode
            if (code != HttpURLConnection.HTTP_OK)
                return emptyList()
            val inputStream = httpURLConnection.inputStream ?: return emptyList()
            val reader = JsonReader(inputStream.bufferedReader())
            val result = mutableListOf<PeopleInfos>()
            reader.beginArray()
            while (reader.hasNext()){
                reader.beginObject()
                val peopleList = PeopleInfos("","","","")
                while (reader.hasNext()){
                    when(reader.nextName()){
                        "firstName"-> if (reader.peek()!= JsonToken.NULL) peopleList.firstName = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        "lastName" -> if (reader.peek()!= JsonToken.NULL) peopleList.lastName = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        "sexe" -> if (reader.peek()!= JsonToken.NULL) peopleList.sexe = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        "birthdate" -> if (reader.peek()!= JsonToken.NULL) peopleList.birthdate = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        else -> reader.skipValue()
                    }
                }
                result.add(peopleList)
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

    fun getAllInfosName() : List<PeopleInfos>{
        val url = URL(getAllInfos)
        var httpURLConnection : HttpURLConnection? = null
        try {
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connect()
            val code = httpURLConnection.responseCode
            if (code != HttpURLConnection.HTTP_OK)
                return emptyList()
            val inputStream = httpURLConnection.inputStream ?: return emptyList()
            val reader = JsonReader(inputStream.bufferedReader())
            val result = mutableListOf<PeopleInfos>()
            reader.beginArray()
            while (reader.hasNext()){
                reader.beginObject()
                val peopleList = PeopleInfos("","","","")
                while (reader.hasNext()){
                    when(reader.nextName()){
                        "firstName"-> if (reader.peek()!= JsonToken.NULL) peopleList.firstName = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        "lastName" -> if (reader.peek()!= JsonToken.NULL) peopleList.lastName = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        else -> reader.skipValue()
                    }
                }
                result.add(peopleList)
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

    fun createUserInfos(queryfirstName:String, querylastName: String, querysexe: String, querybirthdate: String,queryIdUser : String): ResponseCode.StatusCode {
        val url = URL(String.format("$createInfoApirUrl%s/%s/%s/%s/%s","$queryfirstName","$querylastName","$querysexe","$querybirthdate","$queryIdUser"))
        var httpURLConnection : HttpURLConnection? = null
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
}