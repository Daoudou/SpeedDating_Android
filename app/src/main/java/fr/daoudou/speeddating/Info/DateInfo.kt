package fr.daoudou.speeddating.Info

import java.io.Serializable

data class DateInfo(
    var peopleAdd :String,
    var date : String,
    var comment : String? = null,
    var note : String? = null,
    val userId : String? = null,
    val infoPeopleId : String? = null
                    ) : Comparable<DateInfo>, Serializable{

    override fun compareTo(other: DateInfo): Int {
        return date.compareTo(other.date)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DateInfo

        if (peopleAdd != other.peopleAdd) return false
        if (date != other.date) return false
        if (comment != other.comment) return false
        if (note != other.note) return false
        if (userId != other.userId) return false
        if (infoPeopleId != other.infoPeopleId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode() ?: 0
        result = 31 * result + (peopleAdd.hashCode() ?: 0)
        result = 31 * result + (comment?.hashCode() ?: 0)
        result = 31 * result + (note.hashCode() ?: 0)
        result = 31 * result + (userId?.hashCode() ?: 0)
        result = 31 * result + (infoPeopleId?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "date:$date\ncomment:$comment\nnote:$note"
    }

}

