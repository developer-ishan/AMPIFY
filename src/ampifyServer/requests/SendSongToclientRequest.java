package ampifyServer.requests;

import ampifyServer.Song;

public class SendSongToclientRequest extends Request{
    public Song song;
    public SendSongToclientRequest(Song song)
    {
        super(ResquestCode.SENDSONGTOCLIENT);
        this.song=song;
    }
}
