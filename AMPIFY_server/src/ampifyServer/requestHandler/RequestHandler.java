package ampifyServer.requestHandler;

import commonPackages.requests.Request;
import commonPackages.requests.auth.LoginRequest;
import commonPackages.requests.auth.SignupRequest;
import commonPackages.requests.group.*;
import commonPackages.requests.playlist.AddSong;
import commonPackages.requests.playlist.CreatePlaylist;
import commonPackages.requests.playlist.ListPlaylists;
import commonPackages.requests.song.ListSongs;
import commonPackages.requests.user.*;
import commonPackages.responses.Response;

import java.sql.Connection;
import java.sql.SQLException;

public class RequestHandler {
    public static Response getResponse(Request req, Connection con) throws SQLException {
        if(req instanceof SignupRequest){
            return UserRequestsHandler.signup((SignupRequest) req,con);
        }
        //Authentication requests
        else if(req instanceof LoginRequest){
            return UserRequestsHandler.login((LoginRequest) req,con);
        }
        else if(req instanceof ListInvites){
            return UserRequestsHandler.getInvites((ListInvites) req,con);
        }
        else if(req instanceof UserDetails){
            return UserRequestsHandler.userDetails((UserDetails) req,con);
        }
        //Groups requests
        else if(req instanceof AcceptInvite){
            return UserRequestsHandler.acceptInvite((AcceptInvite) req,con);
        }
        else if(req instanceof DeclineInvite){
            return UserRequestsHandler.declineInvite((DeclineInvite)req,con);
        }
        else if(req instanceof ListGroups){
            return UserRequestsHandler.getGroups((ListGroups) req,con);
        }
        else if(req instanceof ListInvites){
            return UserRequestsHandler.getInvites((ListInvites) req,con);
        }
        else if(req instanceof CreateGroup){
            return GroupRequestsHandler.create((CreateGroup) req,con);
        }
        else if (req instanceof InviteUser){
            return GroupRequestsHandler.invite((InviteUser) req,con);
        }
        else if (req instanceof LeaveGroup){
            return UserRequestsHandler.leaveGroup((LeaveGroup) req,con);
        }
        else if (req instanceof ListMembers){
            return GroupRequestsHandler.getMembers((ListMembers) req,con);
        }
        else if (req instanceof MakeAdmin){
            return GroupRequestsHandler.makeAdmin((MakeAdmin) req,con);
        }
        else if(req instanceof ListSongs){
            return SongHandler.listAll((ListSongs) req,con);
        }
        else if(req instanceof CreatePlaylist){
            return PlaylistHandler.CreatePlaylist((CreatePlaylist) req,con);
        }
        else if(req instanceof ListPlaylists){
            return PlaylistHandler.listPlaylists((ListPlaylists) req,con);
        }
        else if(req instanceof AddSong){
            return PlaylistHandler.addSong((AddSong) req,con);
        }
        else{
            return null;
        }
    }
}
