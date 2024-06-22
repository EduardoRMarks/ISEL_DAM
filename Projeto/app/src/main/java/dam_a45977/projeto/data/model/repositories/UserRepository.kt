package dam_a45977.projeto.data.model.repositories

import dam_a45977.projeto.data.model.dao.UserDao

class UserRepository (private val userDao: UserDao) {

    fun getUserByEmail(email: String): String {
        return userDao.getUserByEmail(email)
    }

    fun insertUser(email: String, password: String, firstName: String, lastName: String) {
        userDao.insertUser(email, password, firstName, lastName)
    }
}