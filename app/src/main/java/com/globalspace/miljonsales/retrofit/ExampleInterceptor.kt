package com.globalspace.miljonsales.retrofit
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ExampleInterceptor @Inject constructor() : Interceptor {
    private var mScheme: String? = null
    private var mHost: String? = null
    private var seg : List<String>? = null

    fun setInterceptor(url: String?) {
        val httpUrl = HttpUrl.parse(url)
        mScheme = httpUrl!!.scheme()
        mHost = httpUrl.host()
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var original: Request = chain.request()

        // If new Base URL is properly formatted than replace with old one
        if (mScheme != null && mHost != null) {
            val newUrl: HttpUrl = original.url().newBuilder()
                .scheme(mScheme)
                .host(mHost)
                .build()
            original = original.newBuilder()
                .url(newUrl)
                .build()
        }
        return chain.proceed(original)
    }


       /* lateinit var sInterceptor: ExampleInterceptor
        @Inject
        fun get(): ExampleInterceptor {
            if (sInterceptor == null) {
                sInterceptor = ExampleInterceptor()
            }
            return sInterceptor
        }*/

}