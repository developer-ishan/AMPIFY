package commonPackages.requests.song;

import commonPackages.requests.Request;

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

    public SearchFilterCode getSearchCode() {
        return searchCode;
    }

    @Override
    public String toString() {
        return "ListSearch{" +
                "token='" + token + '\'' +
                ", searchQuery='" + searchQuery + '\'' +
                ", searchCode=" + searchCode +
                '}';
    }
}
