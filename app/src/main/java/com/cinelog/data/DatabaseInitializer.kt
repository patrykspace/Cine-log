package com.cinelog.data

import com.cinelog.viewmodel.MovieListViewModel

object DatabaseInitializer {
    fun seedDatabase(viewModel: MovieListViewModel) {
        val seedMovies = listOf(
            // ── Sci-Fi ──────────────────────────────────────────────────────────
            MovieEntity(
                title = "The Matrix",
                year = 1999, genre = "Sci-Fi", rating = 8.7,
                director = "Lana & Lilly Wachowski",
                synopsis = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                poster  = "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg",
                backdrop = "",
                watched = true, toWatch = false, favorite = true, runtime = "136 min",
                userRating = 10, review = "Arcydzieło! Klimat i stylizacja."
            ),
            MovieEntity(
                title = "Interstellar",
                year = 2014, genre = "Sci-Fi", rating = 8.7,
                director = "Christopher Nolan",
                synopsis = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                poster  = "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
                backdrop = "",
                watched = true, toWatch = false, favorite = false, runtime = "169 min",
                userRating = 9, review = "Muzyka, efekty, kosmos."
            ),
            MovieEntity(
                title = "Inception",
                year = 2010, genre = "Sci-Fi", rating = 8.8,
                director = "Christopher Nolan",
                synopsis = "A thief who steals corporate secrets through dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
                poster  = "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
                backdrop = "https://image.tmdb.org/t/p/w1280/s3TBrRGB1iav7gFOCNx3H31MoES.jpg",
                watched = false, toWatch = true, favorite = false, runtime = "148 min"
            ),
            MovieEntity(
                title = "Blade Runner 2049",
                year = 2017, genre = "Sci-Fi", rating = 8.0,
                director = "Denis Villeneuve",
                synopsis = "Young Blade Runner K's discovery of a long-buried secret leads him on a quest to find Rick Deckard, a former Blade Runner who's been missing for 30 years.",
                poster  = "https://image.tmdb.org/t/p/w500/gajva2L0rPYkEWjzgFlBXCAVBE5.jpg",
                backdrop = "",
                watched = false, toWatch = false, favorite = true, runtime = "164 min"
            ),
            MovieEntity(
                title = "Dune",
                year = 2021, genre = "Sci-Fi", rating = 8.0,
                director = "Denis Villeneuve",
                synopsis = "Feature adaptation of Frank Herbert's science fiction novel about the son of a noble family entrusted with the protection of the most valuable asset in the galaxy.",
                poster  = "https://image.tmdb.org/t/p/w500/d5NXSklXo0qyIYkgV94XAgMIckC.jpg",
                backdrop = "",
                watched = false, toWatch = false, runtime = "155 min"
            ),

            // ── Crime / Thriller ─────────────────────────────────────────────────
            MovieEntity(
                title = "Pulp Fiction",
                year = 1994, genre = "Crime", rating = 8.9,
                director = "Quentin Tarantino",
                synopsis = "The lives of two mob hitmen, a boxer, a gangster and his wife intertwine in four tales of violence and redemption.",
                poster  = "https://image.tmdb.org/t/p/w500/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg",
                backdrop = "",
                watched = true, toWatch = false, favorite = true, runtime = "154 min",
                userRating = 8, review = "Nietuzinkowy montaż i genialne dialogi."
            ),
            MovieEntity(
                title = "The Godfather",
                year = 1972, genre = "Crime", rating = 9.2,
                director = "Francis Ford Coppola",
                synopsis = "The aging patriarch of an organized crime dynasty transfers control of his empire to his reluctant son.",
                poster  = "https://m.media-amazon.com/images/M/MV5BNGEwYjgwOGQtYjg5ZS00Njc1LTk2ZGEtM2QwZWQ2NjdhZTE5XkEyXkFqcGc@._V1_.jpg",
                backdrop = "",
                watched = false, toWatch = false, runtime = "175 min"
            ),
            MovieEntity(
                title = "No Country for Old Men",
                year = 2007, genre = "Crime", rating = 8.1,
                director = "Coen Brothers",
                synopsis = "Violence and mayhem ensue after a hunter stumbles upon a drug deal gone wrong and more than two million dollars in cash near the Rio Grande.",
                poster  = "https://images.squarespace-cdn.com/content/v1/5e02ce8fbe14ca5d06039aca/1596384669439-PFPIFUCMCGI38C2JD9KD/ncfom+1.jpg?format=750w",
                backdrop = "",
                watched = false, toWatch = true, favorite = false, runtime = "122 min"
            ),
            MovieEntity(
                title = "Parasite",
                year = 2019, genre = "Thriller", rating = 8.5,
                director = "Bong Joon Ho",
                synopsis = "Greed and class discrimination threaten the newly formed symbiotic relationship between the wealthy Park family and the destitute Kim clan.",
                poster  = "https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg",
                backdrop = "",
                watched = false, toWatch = true, favorite = true, runtime = "132 min"
            ),
            MovieEntity(
                title = "Gone Girl",
                year = 2014, genre = "Thriller", rating = 7.8,
                director = "David Fincher",
                synopsis = "With his wife's disappearance having become the focus of an intense media circus, a man sees the spotlight turned on him.",
                poster  = "https://play-lh.googleusercontent.com/IIz8p1DWmLveduLGKd69zRWG7xssvOjYzLMEbzrDo-N5sgfi_ZxZu1l7TbTgkLsi014zcoPrDZQVr3g7H4s",
                backdrop = "", 
                watched = true, toWatch = false, favorite = true, runtime = "149 min"
            ),

            // ── Drama ─────────────────────────────────────────────────────────────
            MovieEntity(
                title = "The Shawshank Redemption",
                year = 1994, genre = "Drama", rating = 9.3,
                director = "Frank Darabont",
                synopsis = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                poster  = "https://image.tmdb.org/t/p/w500/lyQBXzOQSuE59IsHyhrp0qIiPAz.jpg",
                backdrop = "https://image.tmdb.org/t/p/w1280/kXfqcdQKsToO0OUXHcrrNCHDBzO.jpg",
                watched = true, toWatch = false, favorite = true, runtime = "142 min",
                userRating = 10, review = "Zasłużony klasyk!"
            ),
            MovieEntity(
                title = "Forrest Gump",
                year = 1994, genre = "Drama", rating = 8.8,
                director = "Robert Zemeckis",
                synopsis = "The presidencies of Kennedy and Johnson, Vietnam War, and other historical events unfold through the eyes of an Alabama man with an extraordinary life.",
                poster  = "https://image.tmdb.org/t/p/w500/arw2vcBveWOVZr6pxd9XTd1TdQa.jpg",
                backdrop = "",
                watched = true, toWatch = false, favorite = false, runtime = "142 min",
                trailerUrl = "res/forrest_gump_trailer"
            ),
            MovieEntity(
                title = "Fight Club",
                year = 1999, genre = "Drama", rating = 8.8,
                director = "David Fincher",
                synopsis = "An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into something much, much more.",
                poster  = "https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
                backdrop = "",
                watched = false, toWatch = false, runtime = "139 min"
            ),
            MovieEntity(
                title = "Joker",
                year = 2019, genre = "Drama", rating = 8.4,
                director = "Todd Phillips",
                synopsis = "A mentally troubled stand-up comedian embarks on a downward spiral of revolution and bloody crime in Gotham City.",
                poster  = "https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
                backdrop = "",
                watched = false, toWatch = false, runtime = "122 min"
            ),

            // ── Action / Adventure ───────────────────────────────────────────────
            MovieEntity(
                title = "The Dark Knight",
                year = 2008, genre = "Action", rating = 9.0,
                director = "Christopher Nolan",
                synopsis = "When the menace known as the Joker wreaks havoc on Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                poster  = "https://play-lh.googleusercontent.com/m6LAGUVG2BURUJ1ziMQFtYzWadIcuV6WHMBwhf5qO3ujN8EtIp94J99YEYaR0BfiH7fa",
                backdrop = "",
                watched = true, toWatch = false, favorite = true, runtime = "152 min",
                userRating = 9, review = "Najlepszy Joker i fenomenalna akcja."
            ),
            MovieEntity(
                title = "Gladiator",
                year = 2000, genre = "Action", rating = 8.5,
                director = "Ridley Scott",
                synopsis = "A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.",
                poster  = "https://image.tmdb.org/t/p/w500/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg",
                backdrop = "",
                watched = false, toWatch = false, runtime = "155 min"
            ),
            MovieEntity(
                title = "Mad Max: Fury Road",
                year = 2015, genre = "Action", rating = 8.1,
                director = "George Miller",
                synopsis = "In a post-apocalyptic wasteland, a woman rebels against a tyrannical ruler in search for her homeland with the aid of a group of female prisoners.",
                poster  = "https://image.tmdb.org/t/p/w500/8tZYtuWezp8JbcsvHYO0O46tFbo.jpg",
                backdrop = "",
                watched = false, toWatch = false, runtime = "120 min"
            ),
            MovieEntity(
                title = "John Wick",
                year = 2014, genre = "Action", rating = 7.4,
                director = "Chad Stahelski",
                synopsis = "An ex-hitman comes out of retirement to track down the gangsters that killed his dog and stole his car.",
                poster  = "https://image.tmdb.org/t/p/w500/fZPSd91yGE9fCcCe6OoQr6E3Bev.jpg",
                backdrop = "",
                watched = false, toWatch = true, favorite = false, runtime = "101 min"
            ),

            // ── Biography / History ──────────────────────────────────────────────
            MovieEntity(
                title = "The Wolf of Wall Street",
                year = 2013, genre = "Biography", rating = 8.2,
                director = "Martin Scorsese",
                synopsis = "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime and corruption.",
                poster  = "https://image.tmdb.org/t/p/w500/34m2tygAYBGqA9MXKhRDtzYd4MR.jpg",
                backdrop = "",
                watched = false, toWatch = false, runtime = "180 min"
            ),
            MovieEntity(
                title = "Oppenheimer",
                year = 2023, genre = "Biography", rating = 8.3,
                director = "Christopher Nolan",
                synopsis = "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb during World War II.",
                poster  = "https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
                backdrop = "https://image.tmdb.org/t/p/w1280/rLb2cwF3Pazuxaj0sRXQ037tGI1.jpg",
                watched = false, toWatch = false, runtime = "180 min"
            ),

            // ── Animation ────────────────────────────────────────────────────────
            MovieEntity(
                title = "Spirited Away",
                year = 2001, genre = "Animation", rating = 8.6,
                director = "Hayao Miyazaki",
                synopsis = "During her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods, witches, and spirits.",
                poster  = "https://image.tmdb.org/t/p/w500/39wmItIWsg5sZMyRUHLkWBcuVCM.jpg",
                backdrop = "",
                watched = true, toWatch = false, favorite = true, runtime = "125 min",
                userRating = 8, review = "Magiczna przygoda, rewelacyjna animacja."
            ),
            MovieEntity(
                title = "Spider-Man: Into the Spider-Verse",
                year = 2018, genre = "Animation", rating = 8.4,
                director = "Bob Persichetti, Peter Ramsey",
                synopsis = "Teen Miles Morales becomes Spider-Man of his reality and must team with counterparts from other dimensions to stop a threat to all realities.",
                poster  = "https://image.tmdb.org/t/p/w500/iiZZdoQBEYBv6id8su7ImL0oCbD.jpg",
                backdrop = "",
                watched = false, toWatch = false, favorite = true, runtime = "117 min"
            ),

            // ── Comedy ────────────────────────────────────────────────────────────
            MovieEntity(
                title = "The Grand Budapest Hotel",
                year = 2014, genre = "Comedy", rating = 8.1,
                director = "Wes Anderson",
                synopsis = "A writer encounters the owner of a decaying European hotel and hears the story of its legendary concierge, who befriended a young lobby boy.",
                poster  = "https://image.tmdb.org/t/p/w500/eWdyYQreja6JGCzqHWXpWHDrrPo.jpg",
                backdrop = "",
                watched = false, toWatch = false, runtime = "99 min"
            ),

            // ── Horror ────────────────────────────────────────────────────────────
            MovieEntity(
                title = "Get Out",
                year = 2017, genre = "Horror", rating = 7.7,
                director = "Jordan Peele",
                synopsis = "A young African-American visits his white girlfriend's parents for the weekend, where his uneasiness about their reception of him proves justified.",
                poster  = "https://image.tmdb.org/t/p/w500/tFXcEccSQMf3lfhfXKSU9iRBpa3.jpg",
                backdrop = "",
                watched = false, toWatch = false, runtime = "104 min"
            ),

            // ── Romance / Drama ───────────────────────────────────────────────────
            MovieEntity(
                title = "La La Land",
                year = 2016, genre = "Romance", rating = 8.0,
                director = "Damien Chazelle",
                synopsis = "A jazz musician and an aspiring actress fall in love while pursuing their dreams in Los Angeles.",
                poster  = "https://image.tmdb.org/t/p/w500/uDO8zWDhfWwoFdKS4fzkUJt0Rf0.jpg",
                backdrop = "",
                watched = false, toWatch = false, runtime = "128 min"
            )
        )

        seedMovies.forEach { viewModel.insert(it) }
    }
}
