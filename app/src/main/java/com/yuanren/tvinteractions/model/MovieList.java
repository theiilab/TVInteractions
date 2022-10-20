package com.yuanren.tvinteractions.model;

import java.util.ArrayList;
import java.util.List;

public final class MovieList {
    public static final String MOVIE_CATEGORY[] = {
            "Dramas",
            "Romance",
            "Comedies",
            "Action",
            "Horror",
            "Sci-Fi",
    };

    private static List<Movie> list;
    private static long count = 0;

    public static List<Movie> getList() {
//        if (list == null) {
//            list = setupMovies();
//        }
        return list;
    }

    public static List<Movie> setupMovies(int length) {
        list = new ArrayList<>();
        String title[] = {
                "Bad Guys",
                "The Lord Of The Rings",
                "Doctor Strange III",
                "Groot's First Steps",
                "Luck"
        };

        String description = "Fusce id nisi turpis. Praesent viverra bibendum semper. "
                + "Donec tristique, orci sed semper lacinia, quam erat rhoncus massa, non congue tellus est "
                + "quis tellus. Sed mollis orci venenatis quam scelerisque accumsan. Curabitur a massa sit "
                + "amet mi accumsan mollis sed et magna. Vivamus sed aliquam risus. Nulla eget dolor in elit "
                + "facilisis mattis. Ut aliquet luctus lacus. Phasellus nec commodo erat. Praesent tempus id "
                + "lectus ac scelerisque. Maecenas pretium cursus lectus id volutpat.";
        String studio[] = {
                "Studio Zero", "Studio One", "Studio Two", "Studio Three", "Studio Four"
        };
        String videoUrl[] = {
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose.mp4"
        };
        String bgImageUrl[] = {
//                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/bg.jpg",
                "https://roost.nbcuni.com/bin/viewasset.html/content/dam/Peacock/Landing-Pages/campaign/p1/badguys/BADGUYS-seo-m.jpg/_jcr_content/renditions/original.JPEG",
//                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/bg.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/pdfCr8W0wBCpdjbZXSxnKhZtosP.jpg",
//                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/bg.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/wcKFYIiVDvRURrzglV9kGu7fpfY.jpg",
//                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/bg.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/9bColKVEAaWfmOLiLZoUhdYfevy.jpg",
//                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose/bg.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/3VQj6m0I6gkuRaljmhNZl0XR3by.jpg"
        };
        String cardImageUrl[] = {
//                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
                "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/81e8eaf4-42df-45fe-8eb9-f7596ba98246/df0ekt5-005cce1b-568a-4f4e-bdc3-a4879be52c8a.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzgxZThlYWY0LTQyZGYtNDVmZS04ZWI5LWY3NTk2YmE5ODI0NlwvZGYwZWt0NS0wMDVjY2UxYi01NjhhLTRmNGUtYmRjMy1hNDg3OWJlNTJjOGEuanBnIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.U-5Y35kKoLSzjlhoUWLpBQOi2fZtcA9erkGynkC-VlI",
//                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/suyNxglk17Cpk8rCM2kZgqKdftk.jpg",
//                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg",
//                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/1ifiV9ZJD4tC3tRYcnLIzH0meaB.jpg",
//                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose/card.jpg"
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/1HOYvwGFioUFL58UVvDRG6beEDm.jpg"
        };

        int n = title.length;
        for (int index = 0; index < length; ++index) {
            list.add(
                    buildMovieInfo(
                            (long) index,
                            title[index % n],
                            description,
                            studio[index % n],
                            videoUrl[index % n],
                            cardImageUrl[index % n],
                            bgImageUrl[index % n]));
        }

        return list;
    }

    private static Movie buildMovieInfo(
            Long id,
            String title,
            String description,
            String studio,
            String videoUrl,
            String cardImageUrl,
            String backgroundImageUrl) {
        Movie movie = new Movie();
//        movie.setId(count++);
        movie.setId(id);
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setStudio(studio);
        movie.setCardImageUrl(cardImageUrl);
        movie.setBackgroundImageUrl(backgroundImageUrl);
        movie.setVideoUrl(videoUrl);
        return movie;
    }
}