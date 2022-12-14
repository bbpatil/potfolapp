/*Copyright (c) 2018 Madona Syombua

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
package com.madonasyombua.growwithgoogleteamproject.ui.intro

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.codemybrainsout.onboarder.AhoyOnboarderActivity
import com.codemybrainsout.onboarder.AhoyOnboarderCard
import com.madonasyombua.growwithgoogleteamproject.R
import java.util.*

/**
 * Created by Ayo on 2/12/2018.
 */
class OnBoardingActivity : AhoyOnboarderActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Creating the cards for the intro
        val card1 = AhoyOnboarderCard("Developers Portfolio", "Welcome! ", R.drawable.logo)
        card1.setBackgroundColor(R.color.black_transparent)
        card1.setTitleColor(R.color.white)
        card1.setDescriptionColor(R.color.grey_200)
        val card2 = AhoyOnboarderCard(
            "About",
            "We want to help developers get a platform to share their projects and showcase their work for free.",
            R.drawable.logo
        )
        card2.setBackgroundColor(R.color.black_transparent)
        card2.setTitleColor(R.color.white)
        card2.setDescriptionColor(R.color.grey_200)
        val card3 = AhoyOnboarderCard(
            "Share Your Work",
            "Thank you for using our platform. Enjoy",
            R.drawable.logo
        )
        card3.setBackgroundColor(R.color.black_transparent)
        card3.setTitleColor(R.color.white)
        card3.setDescriptionColor(R.color.grey_200)
        val pages: MutableList<AhoyOnboarderCard> = ArrayList()
        pages.add(card1)
        pages.add(card2)
        pages.add(card3)
        setOnboardPages(pages)
        setGradientBackground()
        setFinishButtonTitle("Click To Proceed")
        showNavigationControls(true)
        Log.i(TAG, "onCreate: Setting up the cards")
    }

    override fun onFinishButtonPressed() {
        val finishIntent = Intent()
        setResult(Activity.RESULT_OK, finishIntent)
        Log.i(TAG, "onFinishButtonPressed: Closing the onboarding activity")
        finish()
    }

    companion object {
        private const val TAG = "OnBoardingActivity"
    }
}