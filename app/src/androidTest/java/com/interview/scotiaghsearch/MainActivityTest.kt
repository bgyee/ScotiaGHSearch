package com.interview.scotiaghsearch

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasTextColor
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.interview.scotiaghsearch.ui.MainActivity
import com.interview.scotiaghsearch.ui.list.RepoListAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun checkActionBar() {
        launchActivity<MainActivity>().use {
            onView(allOf(
                withText("Take Home"),
                ViewMatchers.isDescendantOfA(withId(R.id.toolbar))
            )).check(matches(isDisplayed()))
                .check(matches(hasTextColor(R.color.white)))
        }
    }

    @Test
    fun loadRepoListAndCheckItIsClickable() {
        launchActivity<MainActivity>().use {
            onView(
                withId(R.id.search_edit_text)
            ).perform(replaceText("octocat"))
            onView(withId(R.id.search_button)).perform(click())

            Thread.sleep(2000)

            onView(withId(R.id.repo_list_view)).perform(
                actionOnItemAtPosition<RepoListAdapter.RepoViewHolder>(
                    0,
                    click()
                )
            )

            onView(withId(R.id.total_forks_count_view)).check(matches(isDisplayed()))
        }
    }
}