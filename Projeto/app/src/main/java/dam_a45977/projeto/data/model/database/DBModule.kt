package dam_a45977.projeto.data.model.database

import android.content.Context
import dam_a45977.projeto.data.model.repositories.UserRepository

class DBModule(private val context: Context) {

//    val userRepository : UserRepository
//    val userDBManager : UserDatabase
    companion object {
        // For Singleton instantiation
        @Volatile private var instance : DBModule ? = null
        fun getInstance (context : Context): DBModule {
            if ( instance != null ) return instance !!
            synchronized ( this ) {
                return DBModule(context)
            }
            return instance!!
        }
    }

    init {
//        userDBManager = UserDatabase.getInstance(context)
//        userRepository = UserRepository(userDBManager.userDao())
    }
}