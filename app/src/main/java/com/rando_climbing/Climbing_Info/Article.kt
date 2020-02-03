package com.rando_climbing.Climbing_Info

import java.io.Serializable

data class Article (
        var ID: Int?,
        var CreatedAt: String,
        var UpdatedAt: String,
        var DeletedAt: String?,
        var category: String,
        var title: String,
        var link: String,
        var pub_date: String,
        var site_name: String,
        var image: String
) : Serializable