package fr.daoudou.speeddating.Info

import java.io.Serializable

data class PeopleInfos(
    var firstName : String,
    var lastName : String,
    var sexe : String,
    var birthdate : String
                       ) : Comparable<PeopleInfos>, Serializable{

    override fun compareTo(other: PeopleInfos): Int {
        return firstName.compareTo(other.firstName)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PeopleInfos

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (sexe != other.sexe) return false
        if (birthdate != other.birthdate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode() ?: 0
        result = 31 * result + (lastName.hashCode() ?: 0)
        result = 31 * result + (sexe.hashCode() ?: 0)
        result = 31 * result + (birthdate.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "firstName:$firstName\nlastName:$lastName\nsexe:$sexe\nbirthdate:$birthdate"
    }

    fun toStringNameLastName():String{
        return "firstName:$firstName\nlastName:$lastName"
    }

}