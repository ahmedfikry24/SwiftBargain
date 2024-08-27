package com.example.swiftbargain.ui.reviews.nav_type

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.example.swiftbargain.navigation.ReviewsParam
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ReviewNavType : NavType<ReviewsParam>(false) {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun get(bundle: Bundle, key: String): ReviewsParam {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, ReviewsParam::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        } ?: ReviewsParam(listOf())
    }

    override fun parseValue(value: String): ReviewsParam {
        return Json.decodeFromString<ReviewsParam>(value)
    }

    override fun serializeAsValue(value: ReviewsParam): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: ReviewsParam) {
        bundle.putParcelable(key, value)
    }
}
