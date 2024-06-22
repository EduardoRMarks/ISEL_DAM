package dam_a45977.projeto.data.model.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT email FROM user WHERE email = :email")
    fun getUserByEmail(email: String): String

    @Query("INSERT INTO user (email, password, first_name, last_name) VALUES (:email, :password, :firstName, :lastName)")
    fun insertUser(email: String, password: String, firstName: String, lastName: String)
}