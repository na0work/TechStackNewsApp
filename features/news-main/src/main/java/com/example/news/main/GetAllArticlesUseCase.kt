package com.example.news.main

import com.example.news.data.ArticleRepository
import com.example.news.data.models.Article
import kotlinx.coroutines.flow.Flow

class GetAllArticlesUseCase(private val repository: ArticleRepository) {

    operator suspend fun invoke(): Flow<Article>{
        return repository.getAll()
    }

}