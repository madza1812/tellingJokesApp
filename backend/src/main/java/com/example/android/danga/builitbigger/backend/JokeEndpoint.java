/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.android.danga.builitbigger.backend;

import com.example.android.danga.Joke;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "jokeApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.builitbigger.danga.android.example.com",
    ownerName = "backend.builitbigger.danga.android.example.com",
    packagePath=""
  )
)
public class JokeEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "getJoke")
    public JokeBean getJoke() {
        JokeBean response = new JokeBean();
        response.setData(new Joke().getRandomJokeFromChuckNorrisDatabase());

        return response;
    }

}
