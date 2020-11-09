public class Song {
    private String songTitle;
    private int averageStreamsCount;
    private String artistName;
    
    public Song() {
        this.songTitle = null;
        this.averageStreamsCount = 0;
        this.artistName = null;
    }
    
    public Song(String songTitle, int averageStreamsCount, String artistName) {
        this.songTitle = songTitle;
        this.averageStreamsCount = averageStreamsCount;
        this.artistName = artistName;
    }
    
    public String toString() {
        String str = songTitle;
        /*the following line of code, if de-commented, returns, for example,
        "Sandstorm by Darude" instead of just "Sandstorm"*/
        //str = str + " by " + artistName;
        return str;
    }
    
    public int getAverageStreamsCount() {
        return averageStreamsCount;
    }
    
    public String getArtistName() {
        return artistName;
    }
    
    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
    
    public void setAverageStreamsCount(int count) {
        this.averageStreamsCount = count;
    }
    
    public boolean equals(Song that) {
        return (this.songTitle.equals(that.toString()) &&
                this.artistName.equals(that.getArtistName()));
        //two different artists can have two different songs that have the same title
        //but we do not consider a case where one artist has two different songs with the same title
    }
    
    public int compareTo(Song that) {
        return (this.toString().compareToIgnoreCase(that.toString()));
    }
}
