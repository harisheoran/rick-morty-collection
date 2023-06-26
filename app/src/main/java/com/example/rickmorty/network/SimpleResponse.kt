package com.example.rickmorty.network

import retrofit2.Response

data class SimpleResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: Exception?
) {
    /*
    In Kotlin, a companion object is a special construct that allows you to define properties and functions within a class that can be accessed
    directly on the class itself, without needing to create an instance of the class.

    - functions and properties defined within the companion object can be accessed using the class name directly/
    - The companion object is like a singleton within the class. It is created only once, similar to a static block in Java, and the members
     within the companion object can be accessed globally without creating an instance of the enclosing class
     */

    // these functions are helper funstions to create SimpleResponse Object easily.
    companion object {
        fun <T> success(data: Response<T>): SimpleResponse<T> {
            return SimpleResponse(
                status = Status.Success,
                data = data,
                exception = null
            )
        }

        fun <T> failure(exception: Exception?): SimpleResponse<T> {
            return SimpleResponse(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }
    }

    /*
    a sealed class is a special kind of abstract class that restricts the inheritance of its subclasses.
    It provides a limited set of subclasses that can be defined within the same file where the sealed class is
    declared.
     */

    // Like an enum
    sealed class Status {
        object Success : Status()
        object Failure : Status()
    }

    val failed: Boolean
        get() = this.status == Status.Failure

    val isSucceed: Boolean
        get() = !failed && this.data?.isSuccessful == true

    val body: T
        get() = this.data!!.body()!!
}