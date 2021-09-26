package com.fernandomantoan.chucknorrisapp.data.repository

import com.fernandomantoan.chucknorrisapp.data.remote.ChuckNorrisService
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val chuckNorrisService: ChuckNorrisService
) {
}