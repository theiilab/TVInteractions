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
                "The King's Man",
                "Red Notice",

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
                "20th Century",
                "Universal Pictures",
                "Studio Zero",
                "Studio One",
                "Studio Two",
                "Studio Three",
                "Studio Four"
        };
        String category[] = {
                "Action",
                "Action",
                "Category Zero",
                "Category One",
                "Category Two",
                "Category Three",
                "Category Four"
        };
        String videoUrl[] = {
                "https://streamable.com/l/ksya4v/mp4.mp4",
                "https://streamable.com/l/46vm7y/mp4-high.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose.mp4"
        };
        String bgImageUrl[] = {
                "https://static1.colliderimages.com/wordpress/wp-content/uploads/2021/12/kingsman-3.jpg",
                "https://occ-0-34-1555.1.nflxso.net/dnm/api/v6/6AYY37jfdO6hpXcMjf9Yu5cnmO0/AAAABVkcDtE1_UaJBGFQUfxakjzhYdT1L4kso24uZS0ASl_faURPBSGzY_Mxbx1KEcNbZr3ZqCatG-0zi2k3L4oBXenznQrrROKJQqdu.jpg?r=590",

                "https://roost.nbcuni.com/bin/viewasset.html/content/dam/Peacock/Landing-Pages/campaign/p1/badguys/BADGUYS-seo-m.jpg/_jcr_content/renditions/original.JPEG",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/pdfCr8W0wBCpdjbZXSxnKhZtosP.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/wcKFYIiVDvRURrzglV9kGu7fpfY.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/9bColKVEAaWfmOLiLZoUhdYfevy.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/3VQj6m0I6gkuRaljmhNZl0XR3by.jpg"
        };
        String cardImageUrl[] = {
                "https://flxt.tmsimg.com/assets/p10672282_p_v8_ad.jpg",
                "https://cinemagic.ir/upload/image/movie/2021/11/red-notice-2021.jpg",

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
                            category[index % n],
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
            String category,
            String videoUrl,
            String cardImageUrl,
            String backgroundImageUrl) {
        Movie movie = new Movie();
//        movie.setId(count++);
        movie.setId(id);
        movie.setTitle(title);
        movie.setDescription(description);
        movie.setStudio(studio);
        movie.setCategory(category);
        movie.setCardImageUrl(cardImageUrl);
        movie.setBackgroundImageUrl(backgroundImageUrl);
        movie.setVideoUrl(videoUrl);

//        Map<String, List<String>> xRayItemForMovie = xRayItems.get((id.intValue()));
        Map<String, List<String>> xRayItemForMovie = xRayItems.get(0);
        List<String> types = xRayItemForMovie.get("type");
        List<String> names = xRayItemForMovie.get("name");
        List<String> images = xRayItemForMovie.get("image");
        List<String> descriptions = xRayItemForMovie.get("description");
        List<String> merchandises = xRayItemForMovie.get("merchandise");

        List<XRayItem> items = new ArrayList<>();
        for (int i = 0; i < names.size(); ++i) {
            XRayItem item = new XRayItem(id, i, types.get(i), names.get(i), images.get(i), descriptions.get(i), merchandises.get(i));
            items.add(item);
        }
        movie.setXRayItems(items);

        return movie;
    }

    private static List<Map<String, List<String>>> setUpXRayItems() {
        List<Map<String, List<String>>> listOfItems = new ArrayList<>();
        List<String> xRayName = new ArrayList<>();
        List<List<String>> xRayImageUrl = new ArrayList<>();
        List<List<String>> xRayLinks = new ArrayList<>();

        // The king's Man
        String xRay1Type[] = {
                "0",
                "0",
                "1",
                "1",
                "1",
                "1",
                "1"
        };
        String xRay1Name[] = {
                "Taron Egerton",
                "Sofia Boutella",
                "Cutler and Gross Glasses",
                "Savile Row Suits",
                "Oxford Brogues Shoes",
                "Drake's Tie",
                "Tag Heuer Watch",
        };
        String xRay1ImageUrl[] = {
                "https://flxt.tmsimg.com/assets/765421_v9_bd.jpg",
                "https://flxt.tmsimg.com/assets/327103_v9_bb.jpg",
                "https://cdn-images.farfetch-contents.com/11/88/16/03/11881603_9297567_1000.jpg",
                "https://savilerowco.com/media/catalog/product/cache/dcdc46754122207d7695de0b04975a01/n/a/navy-wool-blend-tailored-suit-msuit337nav_model_1.jpg",
                "https://images.squarespace-cdn.com/content/v1/5f4e4231f7c05656d0e2ef40/1641562901123-LL3V6VRARJAZPYKSHIJO/Shoe+5+Front.jpg?format=2500w",
                "https://cdn.shopify.com/s/files/1/0649/6603/3653/products/FRE80R-02053-011-1_1080x.jpg?v=1665494255",
                "https://www.tagheuer.com/on/demandware.static/-/Sites-tagheuer-master/default/dw5044426c/TAG_Heuer_Connected_/SBR8A10.BT6259/SBR8A10.BT6259_0913.png?impolicy=resize&width=1328&height=996",

        };
        String xRay1Description[] = {
                "Taron Egerton;• Born in United Kingdom\n• Age: 33 years old\n• Height: 5′ 9″\n• Nationality: British;Taron Egerton is a Welsh actor. He is the recipient of a Golden Globe Award, and has received nominations for a Grammy Award and two British Academy Film Awards.\n\nHe gained recognition for his starring role as a spy recruit in the action comedy film Kingsman: The Secret Service (2014) and its sequel Kingsman: The Golden Circle (2017). ",
                "Sofia Boutella;• Born in Algeria\n• Age: 40 (1982)\n• Height: 5′ 5″\n• Nationality: Algerian, French;Sofia Boutella is an Algerian actress, model, and dancer.",
                "Cutler and Gross GR03 Square Glasses;$439;• Lens Width: 50mm\n• Bridge Width: 18mm\n• Temple Length: 145mm\n\nThis optical frame is packed with considered design details and technical elements which elevate both its form and function. Angular exterior bevelling, coupled with a high nose bridge reflect the stylistic inspirations for this frame, whilst weight-saving interior milling maximises wearability and comfort.",
                "Navy Wool-Blend Tailored Suit;$311;• 43% Virgin Wool\n• 53% Polyester\n• 4% Lycra\n• Weight: 280 Gms\n\nDurable and lightweight, this two-piece navy suit is ideal for business trips abroad. Designed on Savile Row, it has contrast burgundy lining and features elastane within the fabric composition – allowing any creases to drop out easily.",
                "Edward in Black Calf with Rubber Sole Shoes;£575;• Leather linings\n• Comes with Dust bags\n• Handmade in England\n\nThese black calf Oxford semi-brogues featuring brogue detail on cap, vamp & top of quarters is a essential business shoe. Flawlessly hand-finished in England in a round-toe shape, this slick pair will put you in good stead for years to come.",
                "Navy and Blue Stripe Repp Silk Tie;$225;• 100% Silk\n• Handmade in London, England\n• 8cm x 147cm\n\nA striped repp silk tie is one of the most enduring pieces of neckwear available, redolent of earlier periods of style, while retaining a distinctly modern feel.",
                "Tag Heuer Connected Modular 45 Watch;$2,050;• Calibre E4 - 45 mm\n\nStriking the perfect blend between innovation and high-end watchmaking, this TAG Heuer Connected Watch reveals an elegant 45mm steel case with sharp and sporty lugs, and integrated steel chronograph pushers. A new dimension where luxury meets performance."
        };
        String xRay1Merchandise[] = {
                "",
                "",
                "amazon target walmart",
                "amazon target walmart",
                "amazon target walmart",
                "amazon target walmart",
                "amazon bestbuy costco"
        };

        Map<String, List<String>> entry1 = new HashMap<>();
        entry1.put("type", Arrays.asList(xRay1Type));
        entry1.put("name", Arrays.asList(xRay1Name));
        entry1.put("image", Arrays.asList(xRay1ImageUrl));
        entry1.put("description", Arrays.asList(xRay1Description));
        entry1.put("merchandise", Arrays.asList(xRay1Merchandise));
        listOfItems.add(entry1);

        // Red Notice
        String xRay2Type[] = {
                "0",
                "0",
                "1",
                "1",
                "1",
                "1",
                "1"
        };
        String xRay2Name[] = {
                "Taron Egerton",
                "Sofia Boutella",
                "utler and Gross GR03 Square",
                "Emma Watson",
                "Tom Cruise",
                "Emma Watson",
                "Emma Watson",
        };
        String xRay2ImageUrl[] = {
                "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcRezb3QSPGhLptNSXoqUpKeVofpNCTLPXOG9n9o3Z2bnMp80f2AimK17SPKLa2PPkqsYkqIUAHfDgZFTs0",
                "https://popularnetworth.com/wp-content/uploads/2021/05/6495d05033eb2029300f4a6fe5151952.jpg",
                "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcRezb3QSPGhLptNSXoqUpKeVofpNCTLPXOG9n9o3Z2bnMp80f2AimK17SPKLa2PPkqsYkqIUAHfDgZFTs0",
                "https://popularnetworth.com/wp-content/uploads/2021/05/6495d05033eb2029300f4a6fe5151952.jpg",
                "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcRezb3QSPGhLptNSXoqUpKeVofpNCTLPXOG9n9o3Z2bnMp80f2AimK17SPKLa2PPkqsYkqIUAHfDgZFTs0",
                "https://popularnetworth.com/wp-content/uploads/2021/05/6495d05033eb2029300f4a6fe5151952.jpg",
                "https://popularnetworth.com/wp-content/uploads/2021/05/6495d05033eb2029300f4a6fe5151952.jpg",

        };
        String xRay2Description[] = {
                "",
                "",
                "",
                "",
                "",
                "",
                ""
        };
        Map<String, List<String>> entry2 = new HashMap<>();
        entry2.put("type", Arrays.asList(xRay2Type));
        entry2.put("name", Arrays.asList(xRay2Name));
        entry2.put("image", Arrays.asList(xRay2ImageUrl));
        entry2.put("description", Arrays.asList(xRay2Description));
        listOfItems.add(entry1);

        // random x-ray for placeholders
        for (int i = 0; i < 5; ++i) {
            String xRay21Name[] = {
                    "Tom Cruise",
                    "Emma Watson",
            };
            String xRay21ImageUrl[] = {
                    "https://t1.gstatic.com/licensed-image?q=tbn:ANd9GcRezb3QSPGhLptNSXoqUpKeVofpNCTLPXOG9n9o3Z2bnMp80f2AimK17SPKLa2PPkqsYkqIUAHfDgZFTs0",
                    "https://popularnetworth.com/wp-content/uploads/2021/05/6495d05033eb2029300f4a6fe5151952.jpg",
            };
            String xRay21Description[] = {
                    "",
                    ""
            };
            Map<String, List<String>> entry21 = new HashMap<>();
            entry21.put("name", Arrays.asList(xRay21Name));
            entry21.put("image", Arrays.asList(xRay21ImageUrl));
            entry21.put("description", Arrays.asList(xRay21Description));
            listOfItems.add(entry21);
        }
        return listOfItems;
    }
}