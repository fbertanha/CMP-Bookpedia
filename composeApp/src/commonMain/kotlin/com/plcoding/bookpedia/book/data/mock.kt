package com.plcoding.bookpedia.book.data

import com.plcoding.bookpedia.book.domain.model.Book

val mockBooks = listOf(
    Book(
        id = "1",
        title = "Kotlin In Action",
        imageUrl = "https://covers.openlibrary.org/b/id/14815558-L.jpg",
        authors = listOf("Dmitry Jemerov", "Svetlana Isakova"),
        description = "Comprehensive guide to Kotlin programming.",
        languages = listOf(
            "Eng",
            "Spa",
            "Fre",
            "Ger",
            "Ita",
            "Por",
            "Rus",
            "Chi",
            "Jap",
            "Kor",
            "Ara",
            "Hin",
            "Tur",
            "Swe",
            "Gre"
        ),
        firstPublishYear = "2017",
        averageRating = 4.5,
        ratingCount = 2500,
        numPages = 360,
        numEditions = 3
    ),
    Book(
        id = "2",
        title = "Clean Code",
        imageUrl = "https://covers.openlibrary.org/b/id/14815558-L.jpg",
        authors = listOf("Robert C. Martin"),
        description = "A Handbook of Agile Software Craftsmanship.",
        languages = listOf("English", "Spanish"),
        firstPublishYear = "2008",
        averageRating = 4.7,
        ratingCount = 19500,
        numPages = 464,
        numEditions = 5
    ),
    Book(
        id = "3",
        title = "Atomic Habits",
        imageUrl = "https://covers.openlibrary.org/b/id/14815558-L.jpg",
        authors = listOf("James Clear"),
        description = "An Easy & Proven Way to Build Good Habits & Break Bad Ones.",
        languages = listOf("English", "German", "French"),
        firstPublishYear = "2018",
        averageRating = 4.8,
        ratingCount = 22000,
        numPages = 320,
        numEditions = 7
    ),
    Book(
        id = "4",
        title = "Effective Java",
        imageUrl = "https://covers.openlibrary.org/b/id/14815558-L.jpg",
        authors = listOf("Joshua Bloch"),
        description = "Best practices for the Java platform.",
        languages = listOf("English"),
        firstPublishYear = "2001",
        averageRating = 4.6,
        ratingCount = 15000,
        numPages = 416,
        numEditions = 3
    ),
    Book(
        id = "5",
        title = "The Pragmatic Programmer",
        imageUrl = "https://covers.openlibrary.org/b/id/14815558-L.jpg",
        authors = listOf("Andrew Hunt", "David Thomas"),
        description = "Your Journey to Mastery.",
        languages = listOf("English", "Chinese"),
        firstPublishYear = "1999",
        averageRating = 4.7,
        ratingCount = 18000,
        numPages = 352,
        numEditions = 4
    )
)

val mockFavorites = listOf(

    Book(
        id = "3",
        title = "Atomic Habits",
        imageUrl = "https://covers.openlibrary.org/b/id/14815558-L.jpg",
        authors = listOf("James Clear"),
        description = "An Easy & Proven Way to Build Good Habits & Break Bad Ones.",
        languages = listOf("English", "German", "French"),
        firstPublishYear = "2018",
        averageRating = 4.8,
        ratingCount = 22000,
        numPages = 320,
        numEditions = 7
    ),
    Book(
        id = "5",
        title = "The Pragmatic Programmer",
        imageUrl = "https://covers.openlibrary.org/b/id/14815558-L.jpg",
        authors = listOf("Andrew Hunt", "David Thomas"),
        description = "Your Journey to Mastery.",
        languages = listOf("English", "Chinese"),
        firstPublishYear = "1999",
        averageRating = 4.7,
        ratingCount = 18000,
        numPages = 352,
        numEditions = 4
    )
)