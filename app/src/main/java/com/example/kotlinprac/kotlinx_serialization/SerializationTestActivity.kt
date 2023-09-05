package com.example.kotlinprac.kotlinx_serialization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinprac.BaseActivity
import com.example.kotlinprac.R
import com.example.kotlinprac.databinding.ActivitySerializationTestBinding
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import timber.log.Timber

class SerializationTestActivity :
    BaseActivity<ActivitySerializationTestBinding>(R.layout.activity_serialization_test) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val json = Json {
            prettyPrint = true
        }

        // Object -> JSON
        val employee = Employee("John Doe", 30)
        val jsonString = json.encodeToString(Employee.serializer(), employee)
        Timber.e("## jsonString : $jsonString")

        // JSON -> Object
        val jsonStringInput = """
            {
              "name": "Jane Doe",
              "age": 25
            }
        """.trimIndent()
        val employeeObj = json.decodeFromString(Employee.serializer(), jsonStringInput)
        Timber.e("## employeeObj : $employeeObj")
    }
}

@Serializable
data class Employee(
    val name: String,
    val age: Int
)