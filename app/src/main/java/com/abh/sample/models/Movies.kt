package com.abh.sample.models

data class Movies(
	val page: Int,
	val results: List<ResultItems>,
	val total_pages: Int,
	val total_results: Int
)
