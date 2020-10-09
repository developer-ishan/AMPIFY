package ampifyServer.requests;

import ampifyServer.Song;

public class SendSongObjToseverRequest extends Request {
    public Song song;

    public SendSongObjToseverRequest(Song song)
    {
        super(ResquestCode.SENDSONGNAME);
        this.song=song;
    }
}
