package com.dd

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.github.com"

interface GithubService {
	@GET("/repos/{owner}/{repo}/branches")
	fun getBranches(@Path("owner") owner: String, @Path("repo") repo: String): Call<List<Branch>>
}

data class Branch(val name: String)

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Create OkHttp client with mock interceptor
		val okHttpClient = OkHttpClient().newBuilder().addInterceptor(MockInterceptor()).build()

		// Create simple Retrofit with points to Github API
		val retrofit = Retrofit.Builder()
				.baseUrl(BASE_URL)
				.client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create())
				.build()

		// Create Github API interface
		val github = retrofit.create(GithubService::class.java)

		// Create a call for looking up DDMock branches
		val call = github.getBranches("nf1993", "ddmock")

		// Fetch and print a list of all DDMock branches
		call.enqueue(object : Callback<List<Branch>> {
			override fun onFailure(call: Call<List<Branch>>, t: Throwable) {
				Toast.makeText(this@MainActivity, "Failed!", Toast.LENGTH_LONG).show()
			}

			override fun onResponse(call: Call<List<Branch>>, response: Response<List<Branch>>) {
				Toast.makeText(this@MainActivity, "Success!", Toast.LENGTH_LONG).show()
				response.body()?.let { branches ->
					for (branch in branches) {
						println(branch.name)
					}
				}
			}
		})
	}
}
