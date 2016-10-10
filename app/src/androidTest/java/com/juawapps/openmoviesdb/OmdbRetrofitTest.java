package com.juawapps.openmoviesdb;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.juawapps.openmoviesdb.data.MovieItemResponse;
import com.juawapps.openmoviesdb.data.MovieListItem;
import com.juawapps.openmoviesdb.data.MovieListResponse;
import com.juawapps.openmoviesdb.data.OmdbClient;
import com.juawapps.openmoviesdb.data.OmdbHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class OmdbRetrofitTest {

    Context mContext;

    @Before
    public void setup() {
        mContext =  InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void getOmdbClient() throws Exception {


        OmdbClient client = OmdbClient.getInstance();

        assertNotNull("OmdbClient not instantiated correctly", client);
        assertNotNull("Retrofit not instantiated correctly", client.getRetrofit());
    }

    @Test
    public void callOmdbHelperQueryMoviesList() throws Exception {

        String smallString = "xy";
        String mediumString = "Star";
        String twoWordsString = "South+Park";
        String noMatch = "fortytwo";

        OmdbHelper omdbHelper = new OmdbHelper();

        assertNotNull("Query error", getMovies(omdbHelper, smallString));
        assertNotNull("Query error", getMovies(omdbHelper, mediumString));
        assertNotNull("Query error", getMovies(omdbHelper, twoWordsString));
        assertNull("Query returned an unexpected result", getMovies(omdbHelper, noMatch));

    }

    @Test
    public void callOmdbHelperQueryMoviesDetails() throws Exception {

        String validId = "tt0372784";
        String invalidId = "tt0000000";


        OmdbHelper omdbHelper = new OmdbHelper();

        assertEquals("Query error", "True", getMovie(omdbHelper, validId).getResponse());
        assertEquals("Query error", "False", getMovie(omdbHelper, invalidId).getResponse());
    }


    private static List<MovieListItem> getMovies (OmdbHelper omdbHelper, String titleString) {

        //Query to get tags
        List<MovieListResponse> moviesListList =
                testObservable(omdbHelper.getMoviesByTitle(titleString));

        assertNotEquals("Connection error", moviesListList.size(), 0);

        MovieListResponse movieListResponse = moviesListList.get(0);

        return movieListResponse.getResults();
    }

    private static MovieItemResponse getMovie (OmdbHelper omdbHelper, String movieId) {

        //Query to get tags
        List<MovieItemResponse> moviesItemList =
                testObservable(omdbHelper.getMovieDetailsById(movieId));

        assertNotEquals("Connection error", moviesItemList.size(), 0);

        MovieItemResponse movieItemResponse = moviesItemList.get(0);

        return movieItemResponse;
    }


    private static <T> List<T> testObservable (Observable<T> tasksObs) {

        TestSubscriber<T> testSubscriberTasksAll = new TestSubscriber<>();
        tasksObs.subscribe(testSubscriberTasksAll);
        //Running the observer
        testSubscriberTasksAll.assertNoErrors();
        return testSubscriberTasksAll.getOnNextEvents();
    }
}
