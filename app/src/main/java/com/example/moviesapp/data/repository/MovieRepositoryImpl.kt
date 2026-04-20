package com.example.moviesapp.data.repository

import com.example.moviesapp.data.model.api.*
import com.example.moviesapp.domain.mapper.toDomain
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MovieRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor() : MovieRepository {

    override fun getMovies(): List<Movie> = mockMovies.map { it.toDomain() }

    override fun getMovieById(id: String): Movie? = mockMovies.find { it.id == id }?.toDomain()

    companion object {
        private val mockMovies = listOf(
            MovieResponse(
                id = "tt15940132",
                type = "movie",
                title = "War Machine",
                originalTitle = "War Machine",
                image = ImageResponse(
                    url = "https://m.media-amazon.com/images/M/MV5BMmM1ZTc5ZTYtOTM2My00MjBmLWE5NzktYzkyYzdlYWE3ZDAzXkEyXkFqcGc@._V1_.jpg",
                    width = 1013,
                    height = 1500
                ),
                year = 2026,
                runtimeSeconds = 6360,
                genres = listOf("Action", "Sci-Fi", "Thriller"),
                rating = RatingResponse(aggregateRating = 6.4, voteCount = 48762),
                plot = "Follow the final recruits of a grueling special ops boot camp who encounter a mysterious deadly force.",
                directors = listOf(
                    PersonResponse(
                        id = "nm0400850",
                        displayName = "Patrick Hughes",
                        professions = listOf("director", "producer", "writer")
                    )
                ),
                stars = listOf(
                    PersonResponse(id = "nm2024927", displayName = "Alan Ritchson"),
                    PersonResponse(id = "nm0000598", displayName = "Dennis Quaid"),
                    PersonResponse(id = "nm0005246", displayName = "Esai Morales"),
                    PersonResponse(id = "nm2541974", displayName = "Jai Courtney")
                ),
                countries = listOf(
                    CountryResponse(code = "AU", name = "Australia"),
                    CountryResponse(code = "US", name = "United States")
                )
            ),
            MovieResponse(
                id = "tt0111161",
                type = "movie",
                title = "The Shawshank Redemption",
                originalTitle = "The Shawshank Redemption",
                image = ImageResponse(
                    url = "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg"
                ),
                year = 1994,
                runtimeSeconds = 8520,
                genres = listOf("Drama"),
                rating = RatingResponse(aggregateRating = 9.3, voteCount = 2800000),
                plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                directors = listOf(
                    PersonResponse(id = "nm0001104", displayName = "Frank Darabont")
                ),
                stars = listOf(
                    PersonResponse(id = "nm0000209", displayName = "Tim Robbins"),
                    PersonResponse(id = "nm0000151", displayName = "Morgan Freeman"),
                    PersonResponse(id = "nm0348409", displayName = "Bob Gunton")
                ),
                countries = listOf(CountryResponse(code = "US", name = "United States"))
            ),
            MovieResponse(
                id = "tt0068646",
                type = "movie",
                title = "The Godfather",
                originalTitle = "The Godfather",
                image = ImageResponse(
                    url = "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ2NzM@._V1_SX300.jpg"
                ),
                year = 1972,
                runtimeSeconds = 10500,
                genres = listOf("Crime", "Drama"),
                rating = RatingResponse(aggregateRating = 9.2, voteCount = 1900000),
                plot = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant youngest son.",
                directors = listOf(
                    PersonResponse(id = "nm0000338", displayName = "Francis Ford Coppola")
                ),
                stars = listOf(
                    PersonResponse(id = "nm0000008", displayName = "Marlon Brando"),
                    PersonResponse(id = "nm0000199", displayName = "Al Pacino"),
                    PersonResponse(id = "nm0000982", displayName = "James Caan")
                ),
                countries = listOf(CountryResponse(code = "US", name = "United States"))
            ),
            MovieResponse(
                id = "tt0468569",
                type = "movie",
                title = "The Dark Knight",
                originalTitle = "The Dark Knight",
                image = ImageResponse(
                    url = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg"
                ),
                year = 2008,
                runtimeSeconds = 9120,
                genres = listOf("Action", "Crime", "Drama"),
                rating = RatingResponse(aggregateRating = 9.0, voteCount = 2700000),
                plot = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                directors = listOf(
                    PersonResponse(id = "nm0634240", displayName = "Christopher Nolan")
                ),
                stars = listOf(
                    PersonResponse(id = "nm0000288", displayName = "Christian Bale"),
                    PersonResponse(id = "nm0005132", displayName = "Heath Ledger"),
                    PersonResponse(id = "nm0001173", displayName = "Aaron Eckhart")
                ),
                countries = listOf(
                    CountryResponse(code = "US", name = "United States"),
                    CountryResponse(code = "GB", name = "United Kingdom")
                )
            ),
            MovieResponse(
                id = "tt0167260",
                type = "movie",
                title = "The Lord of the Rings: The Return of the King",
                originalTitle = "The Lord of the Rings: The Return of the King",
                image = ImageResponse(
                    url = "https://m.media-amazon.com/images/M/MV5BNzA5ZDNlZWMtN2E0My00ZGRiLWE0OTktZTcxZmM3NjJhZDU0XkEyXkFqcGdeQXVyNzkwMjQ2NzM@._V1_SX300.jpg"
                ),
                year = 2003,
                runtimeSeconds = 12060,
                genres = listOf("Adventure", "Drama", "Fantasy"),
                rating = RatingResponse(aggregateRating = 9.0, voteCount = 1900000),
                plot = "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.",
                directors = listOf(
                    PersonResponse(id = "nm0001392", displayName = "Peter Jackson")
                ),
                stars = listOf(
                    PersonResponse(id = "nm0206359", displayName = "Elijah Wood"),
                    PersonResponse(id = "nm0001557", displayName = "Viggo Mortensen"),
                    PersonResponse(id = "nm0001538", displayName = "Ian McKellen")
                ),
                countries = listOf(
                    CountryResponse(code = "NZ", name = "New Zealand"),
                    CountryResponse(code = "US", name = "United States")
                )
            ),
            MovieResponse(
                id = "tt27497448",
                type = "tvSeries",
                title = "A Knight of the Seven Kingdoms",
                originalTitle = "A Knight of the Seven Kingdoms",
                image = ImageResponse(
                    url = "https://m.media-amazon.com/images/M/MV5BNGUxNDM5OTEtZWRiYi00OWI1LTgxOTctYzljYTJmMjlkY2Y4XkEyXkFqcGc@._V1_.jpg",
                    width = 1536,
                    height = 1920
                ),
                year = 2026,
                runtimeSeconds = 3600,
                genres = listOf("Action", "Adventure", "Drama", "Fantasy"),
                rating = RatingResponse(aggregateRating = 8.8, voteCount = 207590),
                plot = "A century before GOT, Ser Duncan the Tall, and his squire, Egg, wandered through Westeros while the Targaryen dynasty ruled the Iron Throne.",
                directors = listOf(),
                stars = listOf(
                    PersonResponse(id = "nm1111111", displayName = "Peter Claffey"),
                    PersonResponse(id = "nm2222222", displayName = "Dexter Sol Ansell")
                ),
                countries = listOf(CountryResponse(code = "US", name = "United States"))
            )
        )
    }
}