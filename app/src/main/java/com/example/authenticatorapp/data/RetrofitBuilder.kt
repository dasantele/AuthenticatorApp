package com.example.authenticatorapp.data

import android.util.Log
import com.example.authenticatorapp.data.api.BackendApi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.prefs.Preferences


class RetrofitBuilder {
    fun createBackendApi():BackendApi{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder()
            .cookieJar(SessionCookieJar())
            .addInterceptor(ReceivedCookiesInterceptor())
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:5002/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(BackendApi::class.java)

    }
}

private class SessionCookieJar : CookieJar {
    private var cookies: List<Cookie>? = null
    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (url.encodedPath.endsWith("login")) {
            this.cookies = ArrayList(cookies)
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return if (!url.encodedPath.endsWith("login") && cookies != null) {
            cookies!!
        } else Collections.emptyList()
    }
}

class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val preferences = setOf<String>()
        for (cookie in preferences) {
            builder.addHeader("Cookie", cookie)
            Log.v(
                "OkHttp",
                "Adding Header: $cookie"
            ) // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }

        return chain.proceed(builder.build())

    }


}

class ReceivedCookiesInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies: HashSet<String> = HashSet()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
        }
        return originalResponse
    }
}