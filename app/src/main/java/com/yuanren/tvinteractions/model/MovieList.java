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

    private static  final String KEY_TYPE = "type";
    private static  final String KEY_NAME = "name";
    private static  final String KEY_IMAGE = "image";
    private static  final String KEY_DESCRIPTION = "description";
    private static  final String KEY_MERCHANDISE = "merchandise";


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
                "Jumanji",
                "Uncharted",
                "The Devil Wears Prada",

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
                "Columbia Pictures",
                "Columbia Pictures",
                "20th Century Fox",

                "Studio Zero",
                "Studio One",
                "Studio Two",
                "Studio Three",
                "Studio Four"
        };
        String category[] = {
                MOVIE_CATEGORY[3],
                MOVIE_CATEGORY[3],
                MOVIE_CATEGORY[6],
                MOVIE_CATEGORY[6],
                MOVIE_CATEGORY[2],

                "Category Zero",
                "Category One",
                "Category Two",
                "Category Three",
                "Category Four"
        };
        String videoUrl[] = {
                "https://streamable.com/l/ksya4v/mp4-high.mp4",
                "https://streamable.com/l/46vm7y/mp4-high.mp4",
                "https://streamable.com/l/efmzs7/mp4-high.mp4",
                "https://streamable.com/l/xuehud/mp4-high.mp4",
                "https://streamable.com/l/60r3my/mp4-high.mp4",

                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose.mp4"
        };
        String bgImageUrl[] = {
                "https://static1.colliderimages.com/wordpress/wp-content/uploads/2021/12/kingsman-3.jpg",
                "https://occ-0-34-1555.1.nflxso.net/dnm/api/v6/6AYY37jfdO6hpXcMjf9Yu5cnmO0/AAAABVkcDtE1_UaJBGFQUfxakjzhYdT1L4kso24uZS0ASl_faURPBSGzY_Mxbx1KEcNbZr3ZqCatG-0zi2k3L4oBXenznQrrROKJQqdu.jpg?r=590",
                "https://imageio.forbes.com/blogs-images/scottmendelson/files/2017/12/1511829518078_215329_cops_21-1200x588.jpg?format=jpg&width=1200",
                "https://www.whats-on-netflix.com/wp-content/uploads/2022/08/best-new-movies-on-netflix-this-week-august-5th-2022.webp",
                "https://prod-ripcut-delivery.disney-plus.net/v1/variant/disney/998E2A4163BD5059543900BC44B16BF2F46009A87A16886F46E1323A39CA167E/scale?width=1200&aspectRatio=1.78&format=jpeg",

                "https://thumb.canalplus.pro/http/unsafe/2560x1440/filters:quality(80)/img-hapi.canalplus.pro:80/ServiceImage/ImageID/108431902",
                "https://static1.colliderimages.com/wordpress/wp-content/uploads/2022/10/Rings-of-Power.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/wcKFYIiVDvRURrzglV9kGu7fpfY.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/9bColKVEAaWfmOLiLZoUhdYfevy.jpg",
                "https://www.themoviedb.org/t/p/w1066_and_h600_bestv2/3VQj6m0I6gkuRaljmhNZl0XR3by.jpg"
        };
        String cardImageUrl[] = {
                "https://flxt.tmsimg.com/assets/p10672282_p_v8_ad.jpg",
                "https://cinemagic.ir/upload/image/movie/2021/11/red-notice-2021.jpg",
                "https://occ-0-622-299.1.nflxso.net/dnm/api/v6/evlCitJPPCVCry0BZlEFb5-QjKc/AAAABUd8TVjXGgyMY5xgsqWKbpbKx4XgKm5vXsFRSWUhoYE52kJvEcjqFjfIuAGnjh5NWWLUlfG_mMyALnqZOW67muP5zYH4WGte6YGj.jpg",
                "https://www.waukeepubliclibrary.org/sites/default/files/Event%20Images/Adult%20Events/MV5BMWEwNjhkYzYtNjgzYy00YTY2LThjYWYtYzViMGJkZTI4Y2MyXkEyXkFqcGdeQXVyNTM0OTY1OQ%40%40._V1_FMjpg_UX1000_.jpg",
                "https://m.media-amazon.com/images/M/MV5BZjQ3ZTIzOTItMGNjNC00MWRmLWJlMGEtMjJmMDM5ZDIzZGM3XkEyXkFqcGdeQXVyMTkzODUwNzk@._V1_.jpg",

                "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/81e8eaf4-42df-45fe-8eb9-f7596ba98246/df0ekt5-005cce1b-568a-4f4e-bdc3-a4879be52c8a.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzgxZThlYWY0LTQyZGYtNDVmZS04ZWI5LWY3NTk2YmE5ODI0NlwvZGYwZWt0NS0wMDVjY2UxYi01NjhhLTRmNGUtYmRjMy1hNDg3OWJlNTJjOGEuanBnIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.U-5Y35kKoLSzjlhoUWLpBQOi2fZtcA9erkGynkC-VlI",
                "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgWYFYyopTx6Ltjw_L84nqjkEMjc9_cylZgXQNq1xBHmujrjTbKog8pl_cPhotXVj1D1clWla7AImkDVATweq4AB0qk8DcgMvsaoSInPBvecwQxolSGT7lz8pgAUajuLJC2X-zr2r1z0UvKxVa-4GnLUb4WNXFjXRfuZalMo8Sn4mDefdMhGU4OjQlu/w1200-h630-p-k-no-nu/Rings%20of%20Power.jpg",
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

        Map<String, List<String>> xRayItemForMovie = xRayItems.get((id.intValue() % 8));
//        Map<String, List<String>> xRayItemForMovie = xRayItems.get(0);
        List<String> types = xRayItemForMovie.get(KEY_TYPE);
        List<String> names = xRayItemForMovie.get(KEY_NAME);
        List<String> images = xRayItemForMovie.get(KEY_IMAGE);
        List<String> descriptions = xRayItemForMovie.get(KEY_DESCRIPTION);
        List<String> merchandises = xRayItemForMovie.get(KEY_MERCHANDISE);

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
                "Taron Egerton;• Born in United Kingdom\n• Age: 33 (1989)\n• Height: 5′ 9″\n• Nationality: British;Taron Egerton is a Welsh actor. He is the recipient of a Golden Globe Award, and has received nominations for a Grammy Award and two British Academy Film Awards.\n\nHe gained recognition for his starring role as a spy recruit in the action comedy film Kingsman: The Secret Service (2014) and its sequel Kingsman: The Golden Circle (2017). ",
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
        entry1.put(KEY_TYPE, Arrays.asList(xRay1Type));
        entry1.put(KEY_NAME, Arrays.asList(xRay1Name));
        entry1.put(KEY_IMAGE, Arrays.asList(xRay1ImageUrl));
        entry1.put(KEY_DESCRIPTION, Arrays.asList(xRay1Description));
        entry1.put(KEY_MERCHANDISE, Arrays.asList(xRay1Merchandise));
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
                "Dwayne Johnson",
                "Ryan Reynolds",
                "Brunello Cucinelli Blazer",
                "Suunto Core Watch",
                "iPhone SE",
                "iPad mini 2",
                "Gitzo Camera Tripod",
        };
        String xRay2ImageUrl[] = {
                "https://flxt.tmsimg.com/assets/235135_v9_bb.jpg",
                "https://t2.gstatic.com/licensed-image?q=tbn:ANd9GcRDIJ-PkZ_U6WOLo7fm-UVwSZbyFb8fu5TVmvvErBzsxb_TmMMdJEyOMBT53C7zqxvuqDATtVONl_l5zPM",
                "https://cdn-images.farfetch-contents.com/18/64/56/76/18645676_41150734_1000.jpg",
                "https://www.suunto.com/globalassets/productimages/suunto-core-alu-deep-black/suunto-core-alu-deep-black-3946.png?height=1100&format=jpg&bgcolor=f6f6f6",
                "https://m.media-amazon.com/images/I/61TOWf11+jL.jpg",
                "https://bestphone.ru/upload/resize_cache/iblock/0dc/1200_1200_140cd750bba9870f18aada2478b24840a/p0zihiqy743eb8fc2aomrmdju08572er.jpg",
                "https://m.media-amazon.com/images/I/41qqMswbp4L._AC_SX679_.jpg"
        };
        String xRay2Description[] = {
                "Dwayne Johnson;• Born in Hayward, CA, American\n• Age: 50 (1972)\n• Height: 6′ 5″\n• Nationality: American;Dwayne Douglas Johnson, also known by his ring name The Rock, is an American actor and former professional wrestler.",
                "Ryan Reynolds;• Born in Vancouver, Canada\n• Age: 46 (1976)\n• Height: 6′ 2″\n• Nationality: American, Canadian;Ryan Rodney Reynolds began his career starring in the Canadian teen soap opera Hillside, and had minor roles before landing the lead role on the sitcom Two Guys and a Girl between 1998 and 2001.",
                "Brunello Cucinelli Silk-Lapel Single-Breasted Llazer;$4,995;• Outer: Cotton 100%\n• Lining: Cupro 100%\n• Dry Clean Only\n\nMade in Italy.",
                "Suunto Core Alu Deep Black;$229;• Measurements: 49.1 x 49.1 x 14.5 mm\n• Weight: 79 g\n• Bezel material: Aluminum\n• Glass material: Mineral crystal\n\nSuunto Core Premium combines modern design with the essential outdoor functions. Choose rigid natural stainless steel with sapphire crystal glass for added durability, or the elegant aluminum cases for less weight on the wrist.",
                "iPhone SE; $479; • Size: 128 GB\n• Color: Midnight\n• Display:4.7-inch Retina HD display with True Tone\n• Height:138.4 mm\n• Width: 67.3 mm\n• Depth: 7.3 mm\n• Weight: 144 g",
                "iPad Mini 2;• $649; • Size: 256 GB\n• Color: Space Grey\n• Display: 8.3” Liquid Retina display² True Tone\n• Height: 7.87 inches (200 mm)\n• Width: 134.7 mm\n• Depth: .5 mm\n• Weight: 331 g",
                "Gitzo Camera Tripod;$1616.88;• Dimensions: 30.9 x 7.6 x 6.6 inches\n• Weight: 5.8 pounds\n\nThe Gitzo Carbon Fiber Tripod with  3-Way Fluid Head Kit is a 4-section support with carbon fiber eXact tubing-constructed legs. The twist leg locks unlock with a quarter turn, enabling you to extend the legs along with the rapid center column"
        };
        String xRay2Merchandise[] = {
                "",
                "",
                "amazon target walmart",
                "amazon target walmart",
                "apple amazon bestbuy",
                "apple amazon bestbuy",
                "amazon bestbuy costco"
        };
        Map<String, List<String>> entry2 = new HashMap<>();
        entry2.put(KEY_TYPE, Arrays.asList(xRay2Type));
        entry2.put(KEY_NAME, Arrays.asList(xRay2Name));
        entry2.put(KEY_IMAGE, Arrays.asList(xRay2ImageUrl));
        entry2.put(KEY_DESCRIPTION, Arrays.asList(xRay2Description));
        entry2.put(KEY_MERCHANDISE, Arrays.asList(xRay2Merchandise));
        listOfItems.add(entry2);




        // Jumanji
        String xRay3Type[] = {
                "0",
                "0",
                "0",
                "1",
                "1",
                "1",
                "1",
                "1"
        };
        String xRay3Name[] = {
                "Dwayne Johnson",
                "Kevin Hart",
                "Karen Gillan",
                "Timberland Mill Shirt ",
                "Riparo Motorsports Half Finger Gloves",
                "Army Camo Multicam Pants",
                "Tru-Spec Men's Gen-ii Adjustable Boonie",
                "Fitted Short Sleeve Crop Top",
        };
        String xRay3ImageUrl[] = {
                "https://flxt.tmsimg.com/assets/235135_v9_bb.jpg",
                "https://assets.fxnetworks.com/cms-next/production/cms/2021/05/26/web_crew_kevinhart_dave_570x698.jpg",
                "https://static.tvtropes.org/pmwiki/pub/images/dw_kg.png",
                "https://m.media-amazon.com/images/I/713Gok5YfvL._AC_UY879_.jpg",
                "https://m.media-amazon.com/images/I/81QqXeRio+L._AC_SX679_.jpg",
                "https://m.media-amazon.com/images/I/718UGtRaAlL._AC_UY879_.jpg",
                "https://m.media-amazon.com/images/I/91ELqVs25HL._AC_UX679_.jpg",
                "https://m.media-amazon.com/images/I/610b7cSuRaL._AC_UY879_.jpg"
        };
        String xRay3Description[] = {
                "Dwayne Johnson;• Born in Hayward, CA, American\n• Age: 50 (1972)\n• Height: 6′ 5″\n• Nationality: American;Dwayne Douglas Johnson, also known by his ring name The Rock, is an American actor and former professional wrestler.",
                "Kevin Hart;• Born in Philadelphia, PA, American\n• Age: 43 (1979)\n• Height: 5′ 2″\n• Nationality: American;Kevin Darnell Hart is an American comedian and actor. Originally known as a stand-up comedian, he has since starred in Hollywood films and on TV. He has also released several well-received comedy albums.",
                "Karen Gillan;• Born in Inverness, United Kingdom\n• Age: 35 (1987)\n• Height: 5′ 11″\n• Nationality:British, Scottish;Karen Sheila Gillan is a Scottish actress. She gained recognition for her work in British film and television, particularly for playing Amy Pond, a primary companion to the Eleventh Doctor in the science fiction series Doctor Who, for which she received several awards and nominations.",
                "Timberland Mill River Linen Cargo SS Shirt ;$90;• Cotton: 100%\n• Machine Wash\n\nThis durable short-sleeve work shirt comes standard with built-in stretch, so whatever you're doing, it's easier to do.",
                "Riparo Motorsports Half Finger Leather Gloves;$41.5;• Material: Leather\n• Color: Black\n• Sport Type: Cycling\n\nThese genuine leather gloves have the quality and the features you would expect from a pair of quality driving gloves , including soft supple leather and a snap closure on the wrist strap.",
                "Army Camo Multicam Pants; 99.99; • 50% cotton\n• 50% nylon\n• Machine Wash\n\nThere are some core features include: double layer seat /knee areas for durability ,a rugged metal YKK zipper is used for the fly, knees are slightly articulated, and a simple velcro strap system in built into the waist to allow for size adjustments.",
                "Tru-Spec Men's Gen-ii Adjustable Boonie;• $16.5;• 100% Cotton\n• Hand Wash Only\n\nThese stylish hats that meet US military specs are constructed from 65/35 poly-cotton rip-stop with an flexible drawstring chin strap with cardlock for an easy fit",
                "Fitted Short Sleeve Crop Top;$17.99;• 63% Rayon\n• 33% Polyester\n• 4% Spandex\n• Hand Wash Only\n\nThis round neck short sleeve crop top will have you feeling wild for it. Keep it trendy and pair it with our flowy ankle length maxi skirt or for a more casual look wear it with our favorite high waisted skinny jeans."
        };
        String xRay3Merchandise[] = {
                "",
                "",
                "",
                "amazon target walmart",
                "amazon target walmart",
                "amazon target walmart",
                "amazon target walmart",
                "amazon target walmart"
        };
        Map<String, List<String>> entry3 = new HashMap<>();
        entry3.put(KEY_TYPE, Arrays.asList(xRay3Type));
        entry3.put(KEY_NAME, Arrays.asList(xRay3Name));
        entry3.put(KEY_IMAGE, Arrays.asList(xRay3ImageUrl));
        entry3.put(KEY_DESCRIPTION, Arrays.asList(xRay3Description));
        entry3.put(KEY_MERCHANDISE, Arrays.asList(xRay3Merchandise));
        listOfItems.add(entry3);




        // Uncharted
        String xRay4Type[] = {
                "0",
                "0",
                "0",
                "1",
                "1",
                "1",
                "1",
                "1"
        };
        String xRay4Name[] = {
                "Dwayne Johnson",
                "Ryan Reynolds",
                "Mark Wahlberg",
                "Neck Ruffle Fit and Flare Dress",
                "Vegan Leather Moto Jacket",
                "The Boulevard Bomber",
                "Relax Fit Men's Jeans",
                "Uncharted Grey Hoodie"
        };
        String xRay4ImageUrl[] = {
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_bnTTg0RPTPRfk3n8lHBWB_oJS5SyPYSRTb0ffkdFcdXGNwh-rR7hwqwbykz90m2LWrI&usqp=CAU",
                "https://flxt.tmsimg.com/assets/609607_v9_bb.jpg",
                "https://flxt.tmsimg.com/assets/43431_v9_ba.jpg",
                "https://images.express.com/is/image/expressfashion/0094_07827535_2073_a001?cache=on&wid=960&fmt=jpeg&qlt=85,1&resmode=sharp2&op_usm=1,1,5,0&defaultImage=Photo-Coming-Soon",
                "https://i5.walmartimages.com/asr/07e91197-7e60-4e48-bbeb-510cb2ac420d.bc276ee358e5d9372ac695090fb73a38.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF",
                "https://bonobos-prod-s3.imgix.net/products/286106/original/JACKET_BOMBER-JACKET_BOT01535N1233G_1.jpg?1675112807=&auto=format&fit=clip&cs=srgb&w=768&q=75",
                "https://n.nordstrommedia.com/id/sr3/815b0f7a-6a5c-42be-b9b7-f19ca5653451.jpeg?crop=pad&pad_color=FFF&format=jpeg&w=780&h=1196&dpr=2",
                "https://res.cloudinary.com/teepublic/image/private/s--CXeMIyX5--/t_Resized%20Artwork/c_crop,x_10,y_10/c_fit,w_465/c_crop,g_north_west,h_620,w_465,x_0,y_0/g_north_west,u_upload:v1446840653:production:blanks:f6q1psnlmvhpoighmph1,x_-391,y_-276/b_rgb:eeeeee/c_limit,f_auto,h_630,q_90,w_630/v1603477063/production/designs/15352737_0.jpg"
        };
        String xRay4Description[] = {
                "Tom Holland ;• Born in Kingston upon Thames, United Kingdom\n• Age: 26 (1996)\n• Height: 5′ 8″\n• Nationality: British;Thomas Stanley Holland is an English actor. His accolades include a British Academy Film Award, three Saturn Awards, a Guinness World Record and an appearance on the Forbes 30 Under 30 Europe list. Some publications have called him one of the most popular actors of his generation.",
                "Ryan Reynolds;• Born in San Diego, CA, American\n• Age: 27 (1995)\n• Height: 5′ 8″\n• Nationality: American;Sophia Taylor Ramsey Ali is an American actress. She is best known for her work in the MTV romantic comedy series Faking It, the ABC medical drama series Grey's Anatomy, and The Wilds.",
                "Mark Wahlberg;• Born in Boston, MA, American\n• Age: 51 (1971)\n• Height: 5′ 8″\n• Nationality: American;Mark Robert Michael Wahlberg, former stage name Marky Mark, is an American actor, businessman, and former rapper. His work as a leading man spans the comedy, drama, and action genres.",
                "Neck Ruffle Fit and Flare Dress;$128;• Polyester/Rayon 100%\n• Length: Regular 36, Petite 34\n\nThis one features fun ruffle details and a flattering fit and flare style. This piece perfectly pairs with heels or booties for your next event.",
                "Vegan Leather Moto Jacket;$128;• Machine wash cold, tumble dry low\n\nWater resistant outer shell for extra protection against inclement weather.",
                "The Boulevard Bomber; $89; • 60% Cotton, 40% Nylon\n• Dry clean only\n\nClassic bomber style with rib collar, cuff and hem | Single welt hand pockets with snap | Water repellent | Fully lined.",
                "Relax Fit Men's Jeans;• $69.5; • 1% elastane (Lycra®), 100% cotton\n• Zip fly\n• 5-pocket styling\n• Machine wash cold\n\nA comfortable classic, introduced in 1985 and loved ever since. After the '70s were over, things got more relaxed, including Levi's® jeans. This pair features extra room throughout with a slight, tailor-like taper at the leg.",
                "Uncharted Grey Hoodie;$39;• Color: Vintage Heather\n• Style: Classic Hoodie\n• Cotton/Poly fleece blend\n\nSuper warm and cozy fleece lining with an adjustable hood and banded cuffs to keep in the heat."
        };
        String xRay4Merchandise[] = {
                "",
                "",
                "",
                "amazon target walmart",
                "amazon costco walmart",
                "amazon target walmart",
                "amazon costco walmart",
                "amazon target walmart"
        };
        Map<String, List<String>> entry4 = new HashMap<>();
        entry4.put(KEY_TYPE, Arrays.asList(xRay4Type));
        entry4.put(KEY_NAME, Arrays.asList(xRay4Name));
        entry4.put(KEY_IMAGE, Arrays.asList(xRay4ImageUrl));
        entry4.put(KEY_DESCRIPTION, Arrays.asList(xRay4Description));
        entry4.put(KEY_MERCHANDISE, Arrays.asList(xRay4Merchandise));
        listOfItems.add(entry4);




        // The Devil Wears Prada
        String xRay5Type[] = {
                "0",
                "0",
                "1",
                "1",
                "1",
                "1",
                "1"
        };
        String xRay5Name[] = {
                "Anne Hathaway",
                "Meryl Streep",
                "Green Leopard Coat",
                "Double Breasted Coat",
                "Wool Alpaca Mohair Coat",
                "Black Wool Trench Coat",
                "Shiny Leather Coat"
        };
        String xRay5ImageUrl[] = {
                "https://cdn.prod.www.spiegel.de/images/bbb54fd1-0001-0004-0000-000000455757_w1200_r1_fpx47.84_fpy44.99.jpg",
                "data:image/jpeg;base64,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AAYAAAAAAAEAMgAAAAEAWgAAAAYAAAAAAAEANQAAAAEALQAAAAYAAAAAAAE4QklNA/gAAAAAAHAAAP////////////////////////////8D6AAAAAD/////////////////////////////A+gAAAAA/////////////////////////////wPoAAAAAP////////////////////////////8D6AAAOEJJTQQGAAAAAAAHAAQAAQABAQA4QklNBAgAAAAAABAAAAABAAACQAAAAkAAAAAAOEJJTQQKAAAAAAABAAA4QklNBAwAAAAAHjQAAAABAAAAiwAAAKAAAAGkAAEGgAAAHhgAGAAB/9j/4gxYSUNDX1BST0ZJTEUAAQEAAAxITGlubwIQAABtbnRyUkdCIFhZWiAHzgACAAkABgAxAABhY3NwTVNGVAAAAABJRUMgc1JHQgAAAAAAAAAAAAAAAAAA9tYAAQAAAADTLUhQICAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABFjcHJ0AAABUAAAADNkZXNjAAABhAAAAGx3dHB0AAAB8AAAABRia3B0AAACBAAAABRyWFlaAAACGAAAABRnWFlaAAACLAAAABRiWFlaAAACQAAAABRkbW5kAAACVAAAAHBkbWRkAAACxAAAAIh2dWVkAAADTAAAAIZ2aWV3AAAD1AAAACRsdW1pAAAD+AAAABRtZWFzAAAEDAAAACR0ZWNoAAAEMAAAAAxyVFJDAAAEPAAACAxnVFJDAAAEPAAACAxiVFJDAAAEPAAACAx0ZXh0AAAAAENvcHlyaWdodCAoYykgMTk5OCBIZXdsZXR0LVBhY2thcmQgQ29tcGFueQAAZGVzYwAAAAAAAAASc1JHQiBJRUM2MTk2Ni0yLjEAAAAAAAAAAAAAABJzUkdCIElFQzYxOTY2LTIuMQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAWFlaIAAAAAAAAPNRAAEAAAABFsxYWVogAAAAAAAAAAAAAAAAAAAAAFhZWiAAAAAAAABvogAAOPUAAAOQWFlaIAAAAAAAAGKZAAC3hQAAGNpYWVogAAAAAAAAJKAAAA+EAAC2z2Rlc2MAAAAAAAAAFklF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bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjUtYzAyMSA3OS4xNTU3NzIsIDIwMTQvMDEvMTMtMTk6NDQ6MDAiPjxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+PHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiI+PGRjOnRpdGxlIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyI+ODM5OTAyOTU8L2RjOnRpdGxlPjxkYzpjcmVhdG9yIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyI+R3JlZ2cgRGVHdWlyZTwvZGM6Y3JlYXRvcj48ZGM6cmlnaHRzIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyI+MjAxNSBHcmVnZyBEZUd1aXJlPC9kYzpyaWdodHM+PGRjOnN1YmplY3QgeG1sbnM6ZGM9Imh0dHA6Ly9wdXJsLm9yZy9kYy9lbGVtZW50cy8xLjEvIj48cmRmOkJhZz48cmRmOmxpPkNlbGVicml0aWVzPC9yZGY6bGk+PHJkZjpsaT5Bd2FyZDwvcmRmOmxpPjxyZGY6bGk+QXJyaXZhbDwvcmRmOmxpPjxyZGY6bGk+MjAxNTwvcmRmOmxpPjxyZGY6bGk+ODd0aCBBbm51YWwgQWNhZGVteSBBd2FyZHM8L3JkZjpsaT48cmRmOmxpPlJlZCBDYXJwZXQgRXZlbnQ8L3JkZjpsaT48cmRmOmxpPkhvbGx5d29vZCBhbmQgSGlnaGxhbmQgQ2VudGVyPC9yZGY6bGk+PHJkZjpsaT50b3BpY3M8L3JkZjpsaT48cmRmOmxpPnRvcGl4PC9yZGY6bGk+PHJkZjpsaT5iZXN0b2Y8L3JkZjpsaT48cmRmOmxpPnRvcHBpY3M8L3JkZjpsaT48cmRmOmxpPnRvcHBpeDwvcmRmOmxpPjwvcmRmOkJhZz48L2RjOnN1YmplY3Q+PGRjOmRlc2NyaXB0aW9uIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyI+SE9MTFlXT09ELCBDQSAtIEZFQlJVQVJZIDIyOiBBY3RyZXNzIE1lcnlsIFN0cmVlcCBhcnJpdmVzIGF0IHRoZSA4N3RoIEFubnVhbCBBY2FkZW15IEF3YXJkcyBhdCBIb2xseXdvb2QgJmFtcDsgSGlnaGxhbmQgQ2VudGVyIG9uIEZlYnJ1YXJ5IDIyLCAyMDE1IGluIEhvbGx5d29vZCwgQ2FsaWZvcm5pYS4gIChQaG90byBieSBHcmVnZyBEZUd1aXJlL1dpcmVJbWFnZSk8L2RjOmRlc2NyaXB0aW9uPjwvcmRmOkRlc2NyaXB0aW9uPjxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiPjxwaG90b3Nob3A6QXV0aG9yc1Bvc2l0aW9uIHhtbG5zOnBob3Rvc2hvcD0iaHR0cDovL25zLmFkb2JlLmNvbS9waG90b3Nob3AvMS4wLyI+Q29udHJpYnV0b3I8L3Bob3Rvc2hvcDpBdXRob3JzUG9zaXRpb24+PHBob3Rvc2hvcDpDcmVkaXQgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIj5XaXJlSW1hZ2U8L3Bob3Rvc2hvcDpDcmVkaXQ+PHBob3Rvc2hvcDpTb3VyY2UgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIj5XaXJlSW1hZ2U8L3Bob3Rvc2hvcDpTb3VyY2U+PHBob3Rvc2hvcDpDaXR5IHhtbG5zOnBob3Rvc2hvcD0iaHR0cDovL25zLmFkb2JlLmNvbS9waG90b3Nob3AvMS4wLyI+SG9sbHl3b29kPC9waG90b3Nob3A6Q2l0eT48cGhvdG9zaG9wOlN0YXRlIHhtbG5zOnBob3Rvc2hvcD0iaHR0cDovL25zLmFkb2JlLmNvbS9waG90b3Nob3AvMS4wLyI+Q0E8L3Bob3Rvc2hvcDpTdGF0ZT48cGhvdG9zaG9wOkNvdW50cnkgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIj5Vbml0ZWQgU3RhdGVzPC9waG90b3Nob3A6Q291bnRyeT48cGhvdG9zaG9wOkRhdGVDcmVhdGVkIHhtbG5zOnBob3Rvc2hvcD0iaHR0cDovL25zLmFkb2JlLmNvbS9waG90b3Nob3AvMS4wLyI+MjAxNS0wMi0yMiAwMDowMDowMC4wPC9waG90b3Nob3A6RGF0ZUNyZWF0ZWQ+PHBob3Rvc2hvcDpDYXRlZ29yeSB4bWxuczpwaG90b3Nob3A9Imh0dHA6Ly9ucy5hZG9iZS5jb20vcGhvdG9zaG9wLzEuMC8iPkU8L3Bob3Rvc2hvcDpDYXRlZ29yeT48cGhvdG9zaG9wOlN1cHBsZW1lbnRhbENhdGVnb3JpZXMgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIj48cmRmOkJhZz48cmRmOmxpPkFDRTwvcmRmOmxpPjxyZGY6bGk+Q0VMPC9yZGY6bGk+PHJkZjpsaT5FTlQ8L3JkZjpsaT48cmRmOmxpPlRFTDwvcmRmOmxpPjxyZGY6bGk+QVdEPC9yZGY6bGk+PC9yZGY6QmFnPjwvcGhvdG9zaG9wOlN1cHBsZW1lbnRhbENhdGVnb3JpZXM+PHBob3Rvc2hvcDpIZWFkbGluZSB4bWxuczpwaG90b3Nob3A9Imh0dHA6Ly9ucy5hZG9iZS5jb20vcGhvdG9zaG9wLzEuMC8iPjg3dGggQW5udWFsIEFjYWRlbXkgQXdhcmRzIC0gQXJyaXZhbHM8L3Bob3Rvc2hvcDpIZWFkbGluZT48cGhvdG9zaG9wOkNhcHRpb25Xcml0ZXIgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIj5nZDwvcGhvdG9zaG9wOkNhcHRpb25Xcml0ZXI+PHBob3Rvc2hvcDpJbnN0cnVjdGlvbnMgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIi8+PHBob3Rvc2hvcDpUcmFuc21pc3Npb25SZWZlcmVuY2UgeG1sbnM6cGhvdG9zaG9wPSJodHRwOi8vbnMuYWRvYmUuY29tL3Bob3Rvc2hvcC8xLjAvIj41Mzc2MDUyMDc8L3Bob3Rvc2hvcDpUcmFuc21pc3Npb25SZWZlcmVuY2U+PHBob3Rvc2hvcDpVcmdlbmN5IHhtbG5zOnBob3Rvc2hvcD0iaHR0cDovL25zLmFkb2JlLmNvbS9waG90b3Nob3AvMS4wLyIvPjwvcmRmOkRlc2NyaXB0aW9uPjxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiPjxJcHRjNHhtcENvcmU6TG9jYXRpb24geG1sbnM6SXB0YzR4bXBDb3JlPSJodHRwOi8vaXB0Yy5vcmcvc3RkL0lwdGM0eG1wQ29yZS8xLjAveG1sbnMvIi8+PElwdGM0eG1wQ29yZTpDb3VudHJ5Q29kZSB4bWxuczpJcHRjNHhtcENvcmU9Imh0dHA6Ly9pcHRjLm9yZy9zdGQvSXB0YzR4bXBDb3JlLzEuMC94bWxucy8iPlVTQTwvSXB0YzR4bXBDb3JlOkNvdW50cnlDb2RlPjwvcmRmOkRlc2NyaXB0aW9uPjxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiPjxJcHRjNHhtcEV4dDpQZXJzb25JbkltYWdlIHhtbG5zOklwdGM0eG1wRXh0PSJodHRwOi8vaXB0Yy5vcmcvc3RkL0lwdGM0eG1wRXh0LzIwMDgtMDItMjkvIj48cmRmOkJhZz48cmRmOmxpPk1lcnlsIFN0cmVlcDwvcmRmOmxpPjwvcmRmOkJhZz48L0lwdGM0eG1wRXh0OlBlcnNvbkluSW1hZ2U+PC9yZGY6RGVzY3JpcHRpb24+PHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiI+PEdldHR5SW1hZ2VzR0lGVDpDb21wb3NpdGlvbiB4bWxuczpHZXR0eUltYWdlc0dJRlQ9Imh0dHA6Ly94bXAuZ2V0dHlpbWFnZXMuY29tL2dpZnQvMS4wLyI+PHJkZjpCYWc+PHJkZjpsaT5IZWFkc2hvdDwvcmRmOmxpPjxyZGY6bGk+RXllIENvbnRhY3Q8L3JkZjpsaT48cmRmOmxpPlBvc2VkPC9yZGY6bGk+PC9yZGY6QmFnPjwvR2V0dHlJbWFnZXNHSUZUOkNvbXBvc2l0aW9uPjxHZXR0eUltYWdlc0dJRlQ6SW1hZ2VSYW5rIHhtbG5zOkdldHR5SW1hZ2VzR0lGVD0iaHR0cDovL3htcC5nZXR0eWltYWdlcy5jb20vZ2lmdC8xLjAvIj4xPC9HZXR0eUltYWdlc0dJRlQ6SW1hZ2VSYW5rPjxHZXR0eUltYWdlc0dJRlQ6UGVyc29uYWxpdHkgeG1sbnM6R2V0dHlJbWFnZXNHSUZUPSJodHRwOi8veG1wLmdldHR5aW1hZ2VzLmNvbS9naWZ0LzEuMC8iPjxyZGY6QmFnPjxyZGY6bGk+TWVyeWwgU3RyZWVwPC9yZGY6bGk+PC9yZGY6QmFnPjwvR2V0dHlJbWFnZXNHSUZUOlBlcnNvbmFsaXR5PjxHZXR0eUltYWdlc0dJRlQ6QXNzZXRJRCB4bWxuczpHZXR0eUltYWdlc0dJRlQ9Imh0dHA6Ly94bXAuZ2V0dHlpbWFnZXMuY29tL2dpZnQvMS4wLyI+NDY0MTk2NzYyPC9HZXR0eUltYWdlc0dJRlQ6QXNzZXRJRD48R2V0dHlJbWFnZXNHSUZUOk9yaWdpbmFsQ3JlYXRlRGF0ZVRpbWUgeG1sbnM6R2V0dHlJbWFnZXNHSUZUPSJodHRwOi8veG1wLmdldHR5aW1hZ2VzLmNvbS9naWZ0LzEuMC8iPjIwMTUtMDItMjJUMTg6MTc6MzMtMDg6MDA8L0dldHR5SW1hZ2VzR0lGVDpPcmlnaW5hbENyZWF0ZURhdGVUaW1lPjxHZXR0eUltYWdlc0dJRlQ6Q2FtZXJhTWFrZU1vZGVsIHhtbG5zOkdldHR5SW1hZ2VzR0lGVD0iaHR0cDovL3htcC5nZXR0eWltYWdlcy5jb20vZ2lmdC8xLjAvIj5OSUtPTiBEMzwvR2V0dHlJbWFnZXNHSUZUOkNhbWVyYU1ha2VNb2RlbD48L3JkZjpEZXNjcmlwdGlvbj48L3JkZjpSREY+PC94OnhtcG1ldGE+/9sAhAAJBgcIBwYJCAcICgoJCw0WDw0MDA0bFBUQFiAdIiIgHR8fJCg0LCQmMScfHy09LTE1Nzo6OiMrP0Q/OEM0OTo3AQoKCg0MDRoPDxo3JR8lNzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzc3Nzf/wAARCAE+AT4DASIAAhEBAxEB/8QAHAAAAgMBAQEBAAAAAAAAAAAAAgMBBAUGAAcI/8QAPRAAAQQBAwIDBgMGBQQDAQAAAQACAxEE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",
                "https://cdn.shopify.com/s/files/1/2505/5036/products/SCV021181-1_1024x1024.jpg?v=1663818425",
                "https://i5.walmartimages.com/asr/4f25fa98-c283-4513-a130-b97df4752a85_1.4ad01b93abbd8d344027a00cc31ce67c.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF",
                "https://b2c-media.maxmara.com/sys-master/m0/MM/2022/2/5016042306/023/s3master/5016042306023-a-tempera_normal.jpg#product",
                "https://www.fanjackets.com/wp-content/uploads/2022/03/Womens-long-wool-coat-double-breasted-black.jpg",
                "https://bottega-veneta.dam.kering.com/m/743220d2534df4b0/Large-713813VKLC02164_A.jpg?v=2"
        };
        String xRay5Description[] = {
                "Anne Hathaway;• Born in New York, NY, American\n• Age: 40 (1982)\n• Height: 5′ 8″\n• Nationality: American;Anne Jacqueline Hathaway is an American actress. The recipient of various accolades, including an Academy Award, a Golden Globe Award, and a Primetime Emmy Award, she was among the world's highest-paid actresses in 2015.",
                "Meryl Streep;• Born in Summit, NJ, American\n• Age: 73 (1949)\n• Height: 5′ 6″\n• Nationality: American;Mary Louise Streep is an American actress. Often described as \"the best actress of her generation\", Streep is particularly known for her versatility and accent adaptability.",
                "Green 1950S Leopard Patchwork Button Coat;$99.99;• Polyester 100%\n• Length: Knee-Length\n• No Stretch\n\nThe 1950s vintage green button coat is to tribute to the movie The Devil Wears Prado. It can keep your retro feminine charm in cold winter! It's a coat that can change between two collar styles. You can button up all the leopard buttons and the unique leopard neckline that wraps around your neck for warmth and elegance.",
                "Double Breasted Slant Pockets Coat;$82.99;• Polyester 100%\n• Button closure\n• Machine wash cold\n\nTake on chilly winter with this stylish midi length coat with its chic stand collar design and double breasted details.",
                "Wool Alpaca Mohair Coat;$871; • 48% wool, 22% alpaca, 15% nylon, 15% mohair\n• Length: 125 cm\n• Do not wash, do not bleach\n\nWool, alpaca and mohair coat featuring wide lapels, kimono sleeves and welt pockets. The central rear vent and waist tie belt enhance the garment’s flared fit.",
                "Black Wool Trench Coat;$179; • 100% Wool Blend\n• Front: Double Breasted Button Closure\n• Collar: Wide Lapel Style\n• Pockets: One Inside Pocket\n• Handwash only\n\nAn elegant addition to your fall wardrobe, this coat is crafted from a blend of premium quality wool. This coat can be worn to work, brunch, or a night out.",
                "Shiny Leather Coat;$6900;• 100% Lambskin\n• Half lined\n• Regular fit\n\nCrafted in buttery lambskin, this coat takes styling directions from the classic trench — but with added femininity"
        };
        String xRay5Merchandise[] = {
                "",
                "",
                "amazon target walmart",
                "amazon costco walmart",
                "amazon target walmart",
                "amazon costco walmart",
                "amazon target walmart"
        };
        Map<String, List<String>> entry5 = new HashMap<>();
        entry5.put(KEY_TYPE, Arrays.asList(xRay5Type));
        entry5.put(KEY_NAME, Arrays.asList(xRay5Name));
        entry5.put(KEY_IMAGE, Arrays.asList(xRay5ImageUrl));
        entry5.put(KEY_DESCRIPTION, Arrays.asList(xRay5Description));
        entry5.put(KEY_MERCHANDISE, Arrays.asList(xRay5Merchandise));
        listOfItems.add(entry5);




        // random x-ray for placeholders
        for (int i = 0; i < 5; ++i) {
            String xRay21Type[] = {
                "0",
                "0",
            };
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
            String xRay21Merchandise[] = {
                "",
                ""
            };
            Map<String, List<String>> entry21 = new HashMap<>();
            entry21.put(KEY_TYPE, Arrays.asList(xRay21Type));
            entry21.put(KEY_NAME, Arrays.asList(xRay21Name));
            entry21.put(KEY_IMAGE, Arrays.asList(xRay21ImageUrl));
            entry21.put(KEY_DESCRIPTION, Arrays.asList(xRay21Description));
            entry21.put(KEY_MERCHANDISE, Arrays.asList(xRay21Merchandise));
            listOfItems.add(entry21);
        }
        return listOfItems;
    }
}