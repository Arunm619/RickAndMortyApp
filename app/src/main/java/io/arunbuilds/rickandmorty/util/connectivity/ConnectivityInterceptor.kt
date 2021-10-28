package io.arunbuilds.rickandmorty.util.connectivity

import android.content.Context
import io.arunbuilds.rickandmorty.util.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

class ConnectivityInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!NetworkUtils.isConnectionOn(context)) {
            throw NoConnectivityException()
        } else if (!NetworkUtils.isInternetAvailable()) {
            throw NoInternetException()
        } else {
            chain.proceed(chain.request())
        }
    }


}

class NoConnectivityException : IOException() {
    override val message: String
        get() =
            "No network available, please check your WiFi or Data connection"
}

class NoInternetException : IOException() {
    override val message: String
        get() =
            "No internet available, please check your connected WIFi or Data"
}



/**
 * Send a ping to google's primary DNS.
 * If successful, that means we have internet.
 */
object DoesNetworkHaveInternet {

    // Make sure to execute this on a background thread.
    fun execute(socketFactory: SocketFactory): Boolean {
        return try{
            Timber.d( "PINGING google.")
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            Timber.d("PING success.")
            true
        }catch (e: IOException){
            Timber.e( "No internet connection. ${e}")
            false
        }
    }
}
