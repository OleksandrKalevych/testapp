package com.ajax.ajaxtestassignment.api.contacts

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiContactResponse(val results: List<ApiContact>?)