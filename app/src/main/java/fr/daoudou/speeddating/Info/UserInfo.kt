package fr.daoudou.speeddating.Info

import java.io.Serializable

data class UserInfo(
    var id: String? = null,
    var pseudo: String,
    var email: String? = null,
    var password:String? = null,
    var roles: String? = null) : Comparable<UserInfo>,Serializable{




    override fun compareTo(other: UserInfo): Int {
        return pseudo.compareTo(other.pseudo)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserInfo

        if (pseudo != other.pseudo) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (roles != other.roles) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pseudo.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + roles.hashCode()
        return result
    }

    override fun toString(): String {
        return "pseudo : $pseudo\nemail : $email'\n"
    }

}


