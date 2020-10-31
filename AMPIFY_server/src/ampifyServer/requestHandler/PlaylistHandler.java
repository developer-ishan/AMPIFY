package ampifyServer.requestHandler;

import commonPackages.models.Artist;
import commonPackages.models.Playlist;
import commonPackages.models.Song;
import commonPackages.requests.playlist.CreatePlaylist;
import commonPackages.requests.playlist.ListPlaylists;
import commonPackages.responses.ResponseCode;
import commonPackages.responses.playlist.CreatePlaylistResponse;
import commonPackages.responses.playlist.ListPlaylistsResponse;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import static commonPackages.responses.ResponseCode.SUCCESS;

public class PlaylistHandler {
    public static CreatePlaylistResponse CreatePlaylist(CreatePlaylist req, Connection con) throws SQLException {
        // get the user credentials
        // and verify the user
        String userId;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if(token == null){
                return new CreatePlaylistResponse(ResponseCode.DENIED, "Login first.", null);
            }
            if (token.isValid()) {
                userId = token.getSubject();
            } else {
                return new CreatePlaylistResponse(ResponseCode.DENIED, "Login first.", null);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new CreatePlaylistResponse(ResponseCode.DENIED, "Login first.", null);
        }

        // if the user if indeed verified
        String id = UUID.randomUUID().toString();
        String name = req.getName();
        int type = req.getType();
        String query1 = "INSERT into playlist(p_id,name) values(?,?)";
        PreparedStatement ps1 = con.prepareStatement(query1);
        ps1.setString(1, id);
        ps1.setString(2, name);
        ps1.executeUpdate();

        if(type == 1){
            // role one means owner
            // role 2 means shared with me
            String query2 = "INSERT into user_playlist_mem(p_id,u_id,role) values(?,?,?)";
            PreparedStatement ps2 = con.prepareStatement(query2);
            ps2.setString(1,id);
            ps2.setString(2,userId);
            ps2.setInt(3,1);
            ps2.executeUpdate();
        } else if(type == 2){
            String query2 = "INSERT into group_playlist_mem(p_id,g_id) values(?,?)";
            PreparedStatement ps2 = con.prepareStatement(query2);
            ps2.setString(1,id);
            ps2.setString(2, req.getGroupId());
            ps2.executeUpdate();
        }
        return new CreatePlaylistResponse(ResponseCode.SUCCESS,"New Playlist Creqted successfully",new Playlist(id, req.getName()));
    }

    public static ListPlaylistsResponse listPlaylists(ListPlaylists req, Connection con) throws SQLException {
        String userId;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if (token.isValid()) {
                userId = token.getSubject();
            } else {
                return new ListPlaylistsResponse(ResponseCode.DENIED, "Login first.");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new ListPlaylistsResponse(ResponseCode.DENIED, "Login first.");
        }
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();
        String query = "SELECT user_playlist_mem.*, p.type, p.name from user_playlist_mem JOIN playlist p on p.p_id = user_playlist_mem.p_id " +
                "WHERE user_playlist_mem.u_id = ?";
        PreparedStatement preStat = con.prepareStatement(query);
        preStat.setString(1, userId);
        ResultSet rs = preStat.executeQuery();

        while (rs.next()) {
            String p_id;
            Playlist playlist = new Playlist();
            ArrayList<Song> songs = new ArrayList<>();

            playlist.setId(p_id = rs.getString("p_id"));
            playlist.setDate(rs.getString("date"));
            playlist.setName(rs.getString("name"));
            /* DATABASE
             * type can be
             * 1: not public
             * 2: public
             *
             * role can be
             * 1: made by myself
             * 2: shared with me
             * */
            playlist.setType(rs.getInt("type"));
            playlist.setRole(rs.getInt("role"));
            // Triple Join
            String getSongsQuery = "SELECT song.*,count(lm.s_id) as likes  " +
                    "from (SELECT s.* from song_mem JOIN song s on s.s_id = song_mem.s_id where p_id=?) song" +
                    " left join likes_membership lm on song.s_id = lm.s_id" +
                    " group by  song.s_id;";
            PreparedStatement ps = con.prepareStatement(getSongsQuery);
            ps.setString(1,p_id);
            ResultSet rsSongs = ps.executeQuery();
            while (rsSongs.next()){
                String songId = rsSongs.getString("s_id");
                ArrayList<Artist> artists = SongHandler.getArtists(songId, con);
                String year = rsSongs.getString("year");
                String genre = rsSongs.getString("genre");
                String name = rsSongs.getString("title");
                double duration = rsSongs.getDouble("duration");
                long likes = rsSongs.getLong("likes");
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
            playlist.setSongs(songs);
            playlists.add(playlist);
        }
        return new ListPlaylistsResponse(SUCCESS, "Here are your groups.", playlists);
    }
}
