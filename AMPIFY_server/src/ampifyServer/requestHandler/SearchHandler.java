package ampifyServer.requestHandler;

import commonPackages.models.Song;
import commonPackages.requests.song.ListSearch;
import commonPackages.requests.song.SearchFilterCode;
import commonPackages.responses.ResponseCode;
import commonPackages.responses.song.ListSongsResponse;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchHandler {

    public static ListSongsResponse listSearch(ListSearch req, Connection con) {
        System.out.println(req);
        String userId;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if (token == null) {
                return new ListSongsResponse(ResponseCode.DENIED, "Login first.", null);
            }
            if (token.isValid()) {
                userId = token.getSubject();
            } else {
                return new ListSongsResponse(ResponseCode.DENIED, "Login first.", null);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new ListSongsResponse(ResponseCode.DENIED, "Login first.", null);
        }

        ArrayList<Song> SearchedSongs = new ArrayList<>();
        String searchQuery = req.getSearchQuery().substring(0, 2);
        String searchString = "";

        if (req.getSearchCode().equals(SearchFilterCode.BY_SONG_NAME))
            searchString = Search_By_Name(searchQuery);

        if (req.getSearchCode().equals(SearchFilterCode.BY_SONG_ARTIST))
            searchString = Search_By_Artist(searchQuery);

        if (req.getSearchCode().equals(SearchFilterCode.BY_SONG_GENRE))
            searchString = Search_By_Genre(searchQuery);

        try {
            PreparedStatement ps = con.prepareStatement(searchString);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String songId = rs.getString("s_id");
                String year = rs.getString("year");
                String genre = rs.getString("genre");
                String name = rs.getString("title");
                double duration = rs.getDouble("duration");
                String lyrics = "lyrics from srt file";

                Song song = new Song();
                song.setGenre(genre);
                song.setDuration(duration);
                song.setId(songId);
                song.setLyrics(lyrics);
                song.setName(name);
                song.setYear(year);
                song.setArtists(SongHandler.getArtists(songId,con));
                SearchedSongs.add(song);
            }

            return new ListSongsResponse(ResponseCode.SUCCESS, "list of songs search by name", SearchedSongs);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ListSongsResponse(ResponseCode.SERVERDOWN, "server might be slow", SearchedSongs);
        }
    }

    private static String Search_By_Name(String searchString) {
        return "SELECT song.*,count(lm.s_id) as likes  " +
                "from song left join likes_membership lm on song.s_id = lm.s_id " +
                "where title REGEXP '^" + searchString + "'"+
                "group by  song.s_id ;";
    }

    private static String Search_By_Artist(String searchString) {
        return "SELECT * FROM 'song' where genre REGEXP '^" + searchString + "'";
    }

    private static String Search_By_Genre(String searchString) {
        return "SELECT song.*,count(lm.s_id) as likes  " +
                "from song left join likes_membership lm on song.s_id = lm.s_id " +
                "where genre REGEXP '^" + searchString + "'"+
                "group by  song.s_id ;";
    }
}