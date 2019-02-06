# DD Mock

An API mocking library for Android.

## Pre-requisite

App must be using:
* Retrofit and OkHttp for networking
* AndroidX

Support library support coming soon


## Getting started

1. In your `build.gradle` file:

```groovy
debugImplementation 'com.github.nf1993.ddmock:ddmock:[version]'
releaseImplementation 'com.github.nf1993.ddmock:ddmock-no-op:[version]'
```

1. In your `Application` class:

```java
class ExampleApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    DDMock.install(this)
    // Other app init code...
  }
}
```

2. Add `MockInterceptor` to OkHttpClient

```java
val clientBuilder = OkHttpClient().newBuilder()
  .addInterceptor(MockInterceptor())
  // Other configurations...
  .build()
```


## Mock API files

* All API mock files are stored under __/assets/mockfiles__ and are mapped based on the __endpoint path__ and __HTTP method__.
  * e.g. login mock response file for endpoint __POST__ BASE_URL/__mobile-api/v1/auth/login__ will be stored under __mobile-api/v1/auth/login/post__
* The mock files need to be JSON files
* There can be more than one mock file stored under each endpoint path
  * By default, the first file listed (alphabetically ordered) under each endpoint path is selected as the mock response

## FAQs

* Can we only mock some endpoints, while the rest still calls the actual API?
  * Yes. If there are no mock files to return as a response, the app will call the actual API configured in RetrofitBuilder