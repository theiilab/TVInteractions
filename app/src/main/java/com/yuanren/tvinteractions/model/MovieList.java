package com.yuanren.tvinteractions.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public final class MovieList {
    public static final String MOVIE_CATEGORY[] = {
            "Dramas",
            "Romance",
            "Comedies",
            "Action",
            "Horror",
            "Sci-Fi",
            "Adventure",
            "Fantasy",
            "Musicals",
            "Mysteries"
    };

    private static List<Movie> list;
    private static List<Map<String, List<String>>> xRayItems;
    private static long count = 0;

    public static List<Movie> getList() {
//        if (list == null) {
//            list = setupMovies();
//        }
        return list;
    }

    public static Movie findBy(int id) {
        if (list == null) {
            return null;
        }

        Movie movie = null;
        for (int i = 0; i < list.size(); ++i) {
            if (id == list.get(i).getId()) {
                return list.get(i);
            }
        }
        return null;
    }

    public static List<Movie> setupMovies(int length) {
        list = new ArrayList<>();
        String title[] = {
                "Inception",
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
                "https://streamable.com/l/qc8dio/mp4.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose.mp4"
        };
        String bgImageUrl[] = {
                "https://roost.nbcuni.com/bin/viewasset.html/content/dam/Peacock/Landing-Pages/campaign/p1/badguys/BADGUYS-seo-m.jpg/_jcr_content/renditions/original.JPEG",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/pdfCr8W0wBCpdjbZXSxnKhZtosP.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/wcKFYIiVDvRURrzglV9kGu7fpfY.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/9bColKVEAaWfmOLiLZoUhdYfevy.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/3VQj6m0I6gkuRaljmhNZl0XR3by.jpg"
        };
        String cardImageUrl[] = {
                "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/81e8eaf4-42df-45fe-8eb9-f7596ba98246/df0ekt5-005cce1b-568a-4f4e-bdc3-a4879be52c8a.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzgxZThlYWY0LTQyZGYtNDVmZS04ZWI5LWY3NTk2YmE5ODI0NlwvZGYwZWt0NS0wMDVjY2UxYi01NjhhLTRmNGUtYmRjMy1hNDg3OWJlNTJjOGEuanBnIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.U-5Y35kKoLSzjlhoUWLpBQOi2fZtcA9erkGynkC-VlI",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/suyNxglk17Cpk8rCM2kZgqKdftk.jpg",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/9Gtg2DzBhmYamXBS1hKAhiwbBKS.jpg",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/1ifiV9ZJD4tC3tRYcnLIzH0meaB.jpg",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/1HOYvwGFioUFL58UVvDRG6beEDm.jpg"
        };

        xRayItems = setUpXRayItems();

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

//        Map<String, List<String>> xRayItemForMovie = xRayItems.get((id.intValue()));
        Map<String, List<String>> xRayItemForMovie = xRayItems.get(0);
        List<String> names = xRayItemForMovie.get("name");
        List<String> images = xRayItemForMovie.get("image");
        List<String> links = xRayItemForMovie.get("link");

        List<XRayItem> items = new ArrayList<>();
        for (int i = 0; i < names.size(); ++i) {
            XRayItem item = new XRayItem(names.get(i), images.get(i), links.get(i));
            items.add(item);
        }
        movie.setxRayItems(items);

        return movie;
    }

    private static List<Map<String, List<String>>> setUpXRayItems() {
        List<Map<String, List<String>>> listOfItems = new ArrayList<>();
        List<String> xRayName = new ArrayList<>();
        List<List<String>> xRayImageUrl = new ArrayList<>();
        List<List<String>> xRayLinks = new ArrayList<>();

        String xRay1Name[] = {
                "Tom Cruise",
                "Emma Watson"
        };
        String xRay1ImageUrl[] = {
                "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcRezb3QSPGhLptNSXoqUpKeVofpNCTLPXOG9n9o3Z2bnMp80f2AimK17SPKLa2PPkqsYkqIUAHfDgZFTs0",
                "https://popularnetworth.com/wp-content/uploads/2021/05/6495d05033eb2029300f4a6fe5151952.jpg",

        };
        String xRay1Link[] = {
                "",
                ""
        };
        Map<String, List<String>> entry1 = new HashMap<>();
        entry1.put("name", Arrays.asList(xRay1Name));
        entry1.put("image", Arrays.asList(xRay1ImageUrl));
        entry1.put("link", Arrays.asList(xRay1Link));
        listOfItems.add(entry1);

        return listOfItems;
    }
}