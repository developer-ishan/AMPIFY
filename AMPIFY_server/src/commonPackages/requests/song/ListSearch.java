package commonPackages.requests.song;

import commonPackages.requests.Request;
import commonPackages.requests.song.SearchFilterCode;

public class ListSearch extends Request {
    private String token;
    private String searchQuery;
    private SearchFilterCode searchCode;

    public ListSearch(String token, String searchQuery, SearchFilterCode searchCode){
        this.token=token;
        this.searchQuery = searchQuery;
        this.searchCode = searchCode;
    }

    @Override
    public String getToken() {
        return token;
    }

    public String getSearchQuery() { return  searchQuery; }

    public String getSearchCode() { return searchQuery;}
}