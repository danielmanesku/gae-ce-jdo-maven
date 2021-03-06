# gae-ce-jdo-maven
Sample project for GAE (Google App Engine) with Cloud Endpoints, backed by **JDO** and Maven.  
In order to understand this, you should be familiar with [GAE and Cloud Endpoints](https://cloud.google.com/appengine/docs/java/endpoints/).

The code in this repository is based on [GoogleCloudPlatform/appengine-endpoints-helloendpoints-java-maven](https://github.com/GoogleCloudPlatform/appengine-endpoints-helloendpoints-java-maven) with the addition of support for [JDO](https://db.apache.org/jdo/).  
[This commit](https://github.com/danielmanesku/gae-ce-jdo-maven/commit/a1696452251037caf7fd3251baeb6d8640845ecb) adds `SampleJdoClass.java` and `SampleJdoClassEndpoint.java` which is an example of JDO class.

A copy of `README.md` from the original project is provided later on this page. It contains steps on how to get started.  
After you reach step 3 from there, point your browser to
````
   http://localhost:8080/_ah/api/explorer
````
and test SampleJdoClass.

# Original `README.md`

## appengine-endpoints-helloendpoints-java-maven
A "hello world" application for Google Cloud Endpoints in Java.

### Products
- [App Engine][1]

### Language
- [Java][2]

### APIs
- [Google Cloud Endpoints][3]
- [Google App Engine Maven plugin][4]

### Setup Instructions

1. Update the value of `application` in `appengine-web.xml` to the app
   ID you have registered in the App Engine admin console and would
   like to use to host your instance of this sample.

1. Optional step: These sub steps are not required but you need to do
   this if you want to use auth protected API methods.

    1. Update the values in `src/main/java/${packageInPathFormat}/Constants.java`
       to reflect the respective client IDs you have registered in the
       [APIs Console][6].

    1. Update the value of `google.devrel.samples.helloendpoints.CLIENT_ID`
       in `src/main/webapp/base.js` to reflect the web client ID you have
       registered in the [APIs Console][4].

1. Run the application with `mvn appengine:devserver`, and ensure it's
   running by visiting your local server's address (by default
   [localhost:8080][5].)

1. Get the client library with
````
   $ mvn appengine:endpoints_get_client_lib
````
   It will generate a jar file named something like
   `helloworld-v1-1.18.0-rc-SNAPSHOT.jar` under the
   `target/endpoints-client-libs/<api-name>/target` directory of your
   project, as well as install the artifact into your local maven
   repository.

1. Deploy your application to Google App Engine with
````
   $ mvn appengine:update
````
[1]: https://developers.google.com/appengine
[2]: http://java.com/en/
[3]: https://developers.google.com/appengine/docs/java/endpoints/
[4]: https://developers.google.com/appengine/docs/java/tools/maven
[5]: https://localhost:8080/
[6]: https://console.developers.google.com/
