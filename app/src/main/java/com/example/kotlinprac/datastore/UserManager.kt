package com.example.kotlinprac.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(
    private val dataStore: DataStore<Preferences>
) {
    // key 생성
    /* DataStore<Preferences> 인스턴스에 저장해야 하는 각 값에 대한 키를 정의하려면 해당 키 타입의 함수를 써야 한다
    * 나이는 intPreferencesKey(), 이름 2개는 stringPreferencesKey(), 성별은 booleanPreferencesKey()로 가져온다 */
    companion object {
        val USER_AGE_KEY = intPreferencesKey("USER_AGE")
        val USER_FIRST_NAME_KEY = stringPreferencesKey("USER_FIRST_NAME")
        val USER_LAST_NAME_KEY = stringPreferencesKey("USER_LAST_NAME")
        val USER_GENDER_KEY = booleanPreferencesKey("USER_GENDER")
    }

    // User 데이터 저장
    suspend fun storeUser(
        age: Int,
        frontName: String,
        lastName: String,
        isMale: Boolean
    ) {
        /* edit : 값을 키에 저장하는 함수 */
        dataStore.edit {
            it[USER_AGE_KEY] = age
            it[USER_FIRST_NAME_KEY] = frontName
            it[USER_LAST_NAME_KEY] = lastName
            it[USER_GENDER_KEY] = isMale
        }
    }

    /* Preferences DataStore에서 값을 읽으려면 Flow<Preferences>를 리턴하는 DataStore.data 속성을 써야 하고
    * .map{} 연산자를 쓰면 맞는 키를 환경설정에 넘길 수 있다 */
    // age flow 생성
    val userAgeFlow: Flow<Int?> = dataStore.data.map {
        it[USER_AGE_KEY]
    }

    // frontName flow 생성
    val userFirstNameFlow: Flow<String?> = dataStore.data.map {
        it[USER_FIRST_NAME_KEY]
    }

    // lastName flow 생성
    val userLastNameFlow: Flow<String?> = dataStore.data.map {
        it[USER_LAST_NAME_KEY]
    }

    // gender flow 생성
    val userGenderFlow: Flow<Boolean?> = dataStore.data.map {
        it[USER_GENDER_KEY]
    }

}