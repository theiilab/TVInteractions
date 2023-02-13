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
                "Jumanji",

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

                "Category Zero",
                "Category One",
                "Category Two",
                "Category Three",
                "Category Four"
        };
        String videoUrl[] = {
                "https://streamable.com/l/ksya4v/mp4.mp4",
                "https://streamable.com/l/46vm7y/mp4-high.mp4",
                "https://streamable.com/l/efmzs7/mp4-high.mp4",

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
        entry2.put("type", Arrays.asList(xRay2Type));
        entry2.put("name", Arrays.asList(xRay2Name));
        entry2.put("image", Arrays.asList(xRay2ImageUrl));
        entry2.put("description", Arrays.asList(xRay2Description));
        entry2.put("merchandise", Arrays.asList(xRay2Merchandise));
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
        entry3.put("type", Arrays.asList(xRay3Type));
        entry3.put("name", Arrays.asList(xRay3Name));
        entry3.put("image", Arrays.asList(xRay3ImageUrl));
        entry3.put("description", Arrays.asList(xRay3Description));
        entry3.put("merchandise", Arrays.asList(xRay3Merchandise));
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
                "https://lsco.scene7.com/is/image/lsco/595440006-front-pdp?fmt=avif&qlt=40&resMode=bisharp&op_usm=0.6,0.6,8&fit=crop,0&wid=450&hei=491",
                "https://bonobos-prod-s3.imgix.net/products/286106/original/JACKET_BOMBER-JACKET_BOT01535N1233G_1.jpg?1675112807=&auto=format&fit=clip&cs=srgb&w=768&q=75",
                "https://lsco.scene7.com/is/image/lsco/005500110-front-pdp?fmt=avif&qlt=40&resMode=bisharp&op_usm=0.6,0.6,8&fit=crop,0&wid=450&hei=491",
                ""
        };
        String xRay4Description[] = {
                "Tom Holland ;• Born in Kingston upon Thames, United Kingdom\n• Age: 26 (1996)\n• Height: 5′ 8″\n• Nationality: British;Thomas Stanley Holland is an English actor. His accolades include a British Academy Film Award, three Saturn Awards, a Guinness World Record and an appearance on the Forbes 30 Under 30 Europe list. Some publications have called him one of the most popular actors of his generation.",
                "Ryan Reynolds;• Born in San Diego, CA, American\n• Age: 27 (1995)\n• Height: 5′ 8″\n• Nationality: American;Sophia Taylor Ramsey Ali is an American actress. She is best known for her work in the MTV romantic comedy series Faking It, the ABC medical drama series Grey's Anatomy, and The Wilds.",
                "Mark Wahlberg;• Born in Boston, MA, American\n• Age: 51 (1971)\n• Height: 5′ 8″\n• Nationality: American;Mark Robert Michael Wahlberg, former stage name Marky Mark, is an American actor, businessman, and former rapper. His work as a leading man spans the comedy, drama, and action genres.",
                "Neck Ruffle Fit and Flare Dress;$128;• Polyester/Rayon 100%\n• Length: Regular 36, Petite 34\n\nThis one features fun ruffle details and a flattering fit and flare style. This piece perfectly pairs with heels or booties for your next event.",
                "Vegan Leather Moto Jacket;$128;• Machine wash cold, tumble dry low\n• Machine wash cold, tumble dry low\n\nWater resistant outer shell for extra protection against inclement weather.",
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
        entry4.put("type", Arrays.asList(xRay4Type));
        entry4.put("name", Arrays.asList(xRay4Name));
        entry4.put("image", Arrays.asList(xRay4ImageUrl));
        entry4.put("description", Arrays.asList(xRay4Description));
        entry4.put("merchandise", Arrays.asList(xRay4Merchandise));
        listOfItems.add(entry4);




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
            entry21.put("type", Arrays.asList(xRay21Type));
            entry21.put("name", Arrays.asList(xRay21Name));
            entry21.put("image", Arrays.asList(xRay21ImageUrl));
            entry21.put("description", Arrays.asList(xRay21Description));
            entry21.put("merchandise", Arrays.asList(xRay21Merchandise));
            listOfItems.add(entry21);
        }
        return listOfItems;
    }
}