package com.example.moviesapp.data.repository

import com.example.moviesapp.data.model.*

class MovieRepository {
    
    fun getMovies(): List<Movie> = mockMovies
    
    fun getMovieById(id: String): Movie? = mockMovies.find { it.id == id }
    
    companion object {
        val mockMovies = listOf(
            Movie(
                id = "tt15940132",
                type = "movie",
                title = "War Machine",
                originalTitle = "War Machine",
                image = Image(
                    url = "https://m.media-amazon.com/images/M/MV5BMmM1ZTc5ZTYtOTM2My00MjBmLWE5NzktYzkyYzdlYWE3ZDAzXkEyXkFqcGc@._V1_.jpg",
                    width = 1013,
                    height = 1500
                ),
                year = 2026,
                runtimeSeconds = 6360,
                genres = listOf("Action", "Sci-Fi", "Thriller"),
                rating = Rating(aggregateRating = 6.4, voteCount = 48762),
                plot = "Follow the final recruits of a grueling special ops boot camp who encounter a mysterious deadly force.",
                directors = listOf(
                    Person(
                        id = "nm0400850",
                        displayName = "Patrick Hughes",
                        professions = listOf("director", "producer", "writer")
                    )
                ),
                stars = listOf(
                    Person(id = "nm2024927", displayName = "Alan Ritchson"),
                    Person(id = "nm0000598", displayName = "Dennis Quaid"),
                    Person(id = "nm0005246", displayName = "Esai Morales"),
                    Person(id = "nm2541974", displayName = "Jai Courtney")
                ),
                countries = listOf(
                    Country(code = "AU", name = "Australia"),
                    Country(code = "US", name = "United States")
                )
            ),
            Movie(
                id = "tt8599532",
                type = "tvSeries",
                title = "Young Sherlock",
                originalTitle = "Young Sherlock",
                image = Image(
                    url = "https://m.media-amazon.com/images/M/MV5BMGY1NGJkOTAtM2RmNC00NWI3LWJkY2EtYjU1YmY5OGFjN2RjXkEyXkFqcGc@._V1_.jpg",
                    width = 1080,
                    height = 1350
                ),
                year = 2026,
                runtimeSeconds = 3000,
                genres = listOf("Action", "Adventure", "Mystery"),
                rating = Rating(aggregateRating = 7.6, voteCount = 11759),
                plot = "A disgraced, young Sherlock Holmes finds himself wrapped up in a murder case that threatens his liberty. His first ever case unravels a globe-trotting conspiracy, culminating in an explosive showdown that changes his life forever.",
                directors = listOf(),
                stars = listOf(
                    Person(id = "nm1234567", displayName = "Hero Fiennes Tiffin"),
                    Person(id = "nm7654321", displayName = "Natalia Dyer")
                ),
                countries = listOf(Country(code = "GB", name = "United Kingdom"))
            ),
            Movie(
                id = "tt30851137",
                type = "movie",
                title = "The Bride!",
                originalTitle = "The Bride!",
                image = Image(
                    url = "https://m.media-amazon.com/images/M/MV5BM2VmMDVlNzgtNThhZC00ZGMwLTg4MmEtZTUzNmRiYTkxYzUyXkEyXkFqcGc@._V1_.jpg",
                    width = 2764,
                    height = 4096
                ),
                year = 2026,
                runtimeSeconds = 7560,
                genres = listOf("Drama", "Horror", "Romance", "Sci-Fi"),
                rating = Rating(aggregateRating = 5.9, voteCount = 12593),
                plot = "In 1930s Chicago, Frankenstein asks Dr. Euphronius to help create a companion. They give life to a murdered woman as the Bride, sparking romance, police interest, and radical social change.",
                directors = listOf(
                    Person(id = "nm0000112", displayName = "Maggie Gyllenhaal")
                ),
                stars = listOf(
                    Person(id = "nm0000234", displayName = "Christian Bale"),
                    Person(id = "nm0000654", displayName = "Penélope Cruz"),
                    Person(id = "nm0000429", displayName = "Jessie Buckley")
                ),
                countries = listOf(Country(code = "US", name = "United States"))
            ),
            Movie(
                id = "tt27497448",
                type = "tvSeries",
                title = "A Knight of the Seven Kingdoms",
                originalTitle = "A Knight of the Seven Kingdoms",
                image = Image(
                    url = "https://m.media-amazon.com/images/M/MV5BNGUxNDM5OTEtZWRiYi00OWI1LTgxOTctYzljYTJmMjlkY2Y4XkEyXkFqcGc@._V1_.jpg",
                    width = 1536,
                    height = 1920
                ),
                year = 2026,
                runtimeSeconds = 3600,
                genres = listOf("Action", "Adventure", "Drama", "Fantasy"),
                rating = Rating(aggregateRating = 8.8, voteCount = 207590),
                plot = "A century before GOT, Ser Duncan the Tall, and his squire, Egg, wandered through Westeros while the Targaryen dynasty ruled the Iron Throne, and dragons were still remembered. Great destinies and enemies await the incomparable friends.",
                directors = listOf(),
                stars = listOf(
                    Person(id = "nm1111111", displayName = "Peter Claffey"),
                    Person(id = "nm2222222", displayName = "Dexter Sol Ansell")
                ),
                countries = listOf(Country(code = "US", name = "United States"))
            ),
            Movie(
                id = "tt0111161",
                type = "movie",
                title = "The Shawshank Redemption",
                originalTitle = "The Shawshank Redemption",
                image = Image(
                    url = "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg"
                ),
                year = 1994,
                runtimeSeconds = 8520,
                genres = listOf("Drama"),
                rating = Rating(aggregateRating = 9.3, voteCount = 2800000),
                plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                directors = listOf(
                    Person(id = "nm0001104", displayName = "Frank Darabont")
                ),
                stars = listOf(
                    Person(id = "nm0000209", displayName = "Tim Robbins"),
                    Person(id = "nm0000151", displayName = "Morgan Freeman"),
                    Person(id = "nm0348409", displayName = "Bob Gunton")
                ),
                countries = listOf(Country(code = "US", name = "United States"))
            ),
            Movie(
                id = "tt0068646",
                type = "movie",
                title = "The Godfather",
                originalTitle = "The Godfather",
                image = Image(
                    url = "https://m.media-amazon.com/images/M/MV5BM2MyNjYxNmUtYTAwNi00MTYxLWJmNWYtYzZlODY3ZTk3OTFlXkEyXkFqcGdeQXVyNzkwMjQ2NzM@._V1_SX300.jpg"
                ),
                year = 1972,
                runtimeSeconds = 10500,
                genres = listOf("Crime", "Drama"),
                rating = Rating(aggregateRating = 9.2, voteCount = 1900000),
                plot = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant youngest son.",
                directors = listOf(
                    Person(id = "nm0000338", displayName = "Francis Ford Coppola")
                ),
                stars = listOf(
                    Person(id = "nm0000008", displayName = "Marlon Brando"),
                    Person(id = "nm0000199", displayName = "Al Pacino"),
                    Person(id = "nm0000982", displayName = "James Caan")
                ),
                countries = listOf(Country(code = "US", name = "United States"))
            ),
            Movie(
                id = "tt0468569",
                type = "movie",
                title = "The Dark Knight",
                originalTitle = "The Dark Knight",
                image = Image(
                    url = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_SX300.jpg"
                ),
                year = 2008,
                runtimeSeconds = 9120,
                genres = listOf("Action", "Crime", "Drama"),
                rating = Rating(aggregateRating = 9.0, voteCount = 2700000),
                plot = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                directors = listOf(
                    Person(id = "nm0634240", displayName = "Christopher Nolan")
                ),
                stars = listOf(
                    Person(id = "nm0000288", displayName = "Christian Bale"),
                    Person(id = "nm0005132", displayName = "Heath Ledger"),
                    Person(id = "nm0001173", displayName = "Aaron Eckhart")
                ),
                countries = listOf(
                    Country(code = "US", name = "United States"),
                    Country(code = "GB", name = "United Kingdom")
                )
            ),
            Movie(
                id = "tt0167260",
                type = "movie",
                title = "The Lord of the Rings: The Return of the King",
                originalTitle = "The Lord of the Rings: The Return of the King",
                image = Image(
                    url = "https://m.media-amazon.com/images/M/MV5BNzA5ZDNlZWMtN2E0My00ZGRiLWE0OTktZTcxZmM3NjJhZDU0XkEyXkFqcGdeQXVyNzkwMjQ2NzM@._V1_SX300.jpg"
                ),
                year = 2003,
                runtimeSeconds = 12060,
                genres = listOf("Adventure", "Drama", "Fantasy"),
                rating = Rating(aggregateRating = 9.0, voteCount = 1900000),
                plot = "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.",
                directors = listOf(
                    Person(id = "nm0001392", displayName = "Peter Jackson")
                ),
                stars = listOf(
                    Person(id = "nm0206359", displayName = "Elijah Wood"),
                    Person(id = "nm0001557", displayName = "Viggo Mortensen"),
                    Person(id = "nm0001538", displayName = "Ian McKellen")
                ),
                countries = listOf(
                    Country(code = "NZ", name = "New Zealand"),
                    Country(code = "US", name = "United States")
                )
            )
        )
    }
}
