package ampifyServer.requestHandler;

import commonPackages.models.Artist;
import commonPackages.models.Song;
import commonPackages.requests.song.ListSongs;
import commonPackages.responses.ResponseCode;
import commonPackages.responses.song.ListSongsResponse;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SongHandler {
    public static ListSongsResponse listAll(ListSongs req, Connection con) {

        // get the user credentials
        // and verify the user
        String userId;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if(token == null){
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

        // if the user if indeed verified
        ArrayList<Song> songs = new ArrayList<>();
        try {
            String query = "SELECT song.*,count(lm.s_id) as likes  from song left join likes_membership lm on song.s_id = lm.s_id group by  song.s_id;";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String songId = rs.getString("s_id");
                ArrayList<Artist> artists = getArtists(songId, con);
                String year = rs.getString("year");
                String genre = rs.getString("genre");
                String name = rs.getString("title");
                double duration = rs.getDouble("duration");
                long likes = rs.getLong("likes");
                String lyrics = "lyrics from srt file";

                Song song = new Song();
                song.setGenre(genre);
                song.setDuration(duration);
                song.setArtists(artists);
                song.setId(songId);
                song.setLikes(likes);
                song.setLyrics(lyrics);
                song.setName(name);
                song.setYear(year);

                songs.add(song);
            }
            return new ListSongsResponse(ResponseCode.SUCCESS, "List of all the songs", songs);
        } catch (SQLException e) {
            return new ListSongsResponse(ResponseCode.SERVERDOWN, "We are facing some technical difficulties please be patient.", songs);
        }
    }
    public static ArrayList<Artist> getArtists(String songId, Connection con){
        String query = "select artist.* from artist join artist_membership on artist.a_id = artist_membership.a_id where s_id = ?;";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,songId);
            ResultSet rs = ps.executeQuery();
            ArrayList<Artist> artists = new ArrayList<>();
            while (rs.next()){
                Artist artist = new Artist(rs.getString("name"),rs.getString("a_id"),rs.getString("active_from"),rs.getString("email"));
                artists.add(artist);
            }
            artists.forEach(artist -> {
                System.out.println(artist);
            });
            return artists;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
