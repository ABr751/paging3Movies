package com.training.pagingsample.data.model

import com.google.gson.annotations.SerializedName

class MovieResponse {
    var page: Page? = null
}

class Page {
    var title: String? = null

    @SerializedName("total-content-items")
    var totalContentItems: String? = null

    @SerializedName("page-num")
    var pageNum: String? = null

    @SerializedName("page-size")
    var pageSize: String? = null

    @SerializedName("content-items")
    var contentItems: ContentItems? = null
}

class ContentItems {
    var content: List<Content>? = null
}

class Content {
    var name: String? = null

    @SerializedName("poster-image")
    var posterImage: String? = null
}