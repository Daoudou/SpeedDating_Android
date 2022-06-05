package fr.daoudou.speeddating.Info

import java.io.Serializable

data class LinkInfo(var id: String? = null,
                    var idUser :String,
                    var idInfos  :String,
                    ) : Comparable<LinkInfo>, Serializable {

    override fun compareTo(other: LinkInfo): Int {
        return idUser.compareTo(other.idUser)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LinkInfo

        if (id != other.id) return false
        if (idUser != other.idUser) return false
        if (idInfos != other.idInfos) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + idUser.hashCode()
        result = 31 * result + idInfos.hashCode()
        return result
    }

    override fun toString(): String {
        return "$idInfos\n"
    }

}