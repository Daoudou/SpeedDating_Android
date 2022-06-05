package fr.daoudou.speeddating.Service

import android.util.JsonReader
import android.util.JsonToken
import com.auth0.android.jwt.JWT
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import fr.daoudou.speeddating.Info.UserInfo
import fr.daoudou.speeddating.Security.ResponseCode
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

var tokenPrincip : String = "";
var tokenIdDecode : String ="";

class UserService {

    private val apiUrl = "http://172.30.66.54:3000"
    private val getAllUserApiUrl = "$apiUrl/users/"
    private val createUserApirUrl = "$apiUrl/users/create/"
    private val loginUserApiUrl = "$apiUrl/users/login/"
    private val idUserApiUrl = "$apiUrl/users/usersId/"

    fun getToken(): String {
        return tokenIdDecode
    }

    fun getApiIp() : String {
        return apiUrl
    }

    fun getAllUser(): List<UserInfo> {
        val url = URL(getAllUserApiUrl)
        var httpURLConnection : HttpURLConnection? = null
        try {
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connect()
            val code = httpURLConnection.responseCode
            if (code != HttpURLConnection.HTTP_OK)
                return emptyList();
            val inputStream = httpURLConnection.inputStream ?: return emptyList()
            val reader = JsonReader(inputStream.bufferedReader())
            val result = mutableListOf<UserInfo>()
            reader.beginArray()
            while(reader.hasNext()){
                reader.beginObject()
                val userList = UserInfo("","","","","")
                while (reader.hasNext()){
                    when(reader.nextName()){
                        "id" ->if(reader.peek()!= JsonToken.NULL) userList.id = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        "pseudo" ->if(reader.peek()!= JsonToken.NULL) userList.pseudo = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        "email" ->if(reader.peek()!= JsonToken.NULL) userList.email = reader.nextString()
                        else {reader.hasNext(); "Non renseigne"}
                        else -> reader.skipValue()
                    }
                }
                result.add(userList)
                reader.endObject()
            }
            reader.endArray()
            return result
        } catch (e: IOException) {
            return emptyList()
        } finally {
            httpURLConnection?.disconnect()
        }
    }

    fun createUser(queryPseudo : String, queryEmail: String, queryPassword : String): ResponseCode.StatusCode {
        val url = URL(String.format("$createUserApirUrl%s/%s/%s", "$queryPseudo","$queryEmail","$queryPassword"))
        var httpURLConnection : HttpURLConnection? = null
        try {
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.connect()
            val code =httpURLConnection.responseCode
            if (code != HttpURLConnection.HTTP_CREATED)
                return ResponseCode.StatusCode.BadRequest
        }catch (e : IOException){
        } finally {
            httpURLConnection?.disconnect()
        }
        return ResponseCode.StatusCode.Created
    }


     fun loginUser(queryEmail: String, queryPassword: String): ResponseCode.StatusCode{
        val url = URL(String.format("$loginUserApiUrl%s/%s","$queryEmail","$queryPassword"))
        var httpURLConnection : HttpURLConnection? = null
        try {
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.setRequestProperty("Content-Typr","application/json")
            httpURLConnection.setRequestProperty("Accept","application/json")
            httpURLConnection.setRequestProperty ("Authorization", tokenPrincip);
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = true
            httpURLConnection.connect()
            val code = httpURLConnection.responseCode
            if (code != HttpURLConnection.HTTP_OK)
                return ResponseCode.StatusCode.BadRequest
            val response = httpURLConnection.inputStream.bufferedReader().use { it.readText() }
                val gson = GsonBuilder().setPrettyPrinting().create()
                val prettyJson = gson.toJson(JsonParser.parseString(response))
            tokenPrincip = prettyJson
            val tokenDecode = JWT(tokenPrincip)
            val subscriptionMetaDATA = tokenDecode.getClaim("id")
            tokenIdDecode = subscriptionMetaDATA.asString().toString()
        }catch (e : IOException){
        }finally {
            httpURLConnection?.disconnect()
        }
        return ResponseCode.StatusCode.OK
    }

    fun getUserById(): List<UserInfo>{


        val url = URL(String.format("$idUserApiUrl%s", tokenIdDecode))
        var httpURLConnection : HttpURLConnection? = null
        try {
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.connect()
            val code = httpURLConnection.responseCode
            if (code != HttpURLConnection.HTTP_OK)
                return emptyList()
            val result = mutableListOf<UserInfo>()
            val inputStream = httpURLConnection.inputStream ?: return emptyList()
            val reader = JsonReader(inputStream.bufferedReader())
            reader.beginObject()
            val userList = UserInfo(null,"",null,null,null)
            while(reader.hasNext()){
                when(reader.nextName()){
                    "pseudo" ->if(reader.peek()!= JsonToken.NULL) userList.pseudo = reader.nextString()
                    else {reader.hasNext(); "Non renseigne"}
                    else -> reader.skipValue()
                }
            }
            result.add(userList)
            reader.endObject()
            return result
        }catch (e : IOException){
            return emptyList()
        }finally {
            httpURLConnection?.disconnect()
        }
    }
}

private fun <E> MutableList<E>.sort() {
    TODO("Not yet implemented")
}
