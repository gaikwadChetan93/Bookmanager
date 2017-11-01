package mobileutility.bookmanager.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable
{
    private String author;

    private String title;

    private String lastCheckedOut;

    private String lastCheckedOutBy;

    private String categories;

    private String url;

    private String publisher;
    public Book(){};
    public Book(Parcel in) {
        author = in.readString();
        title = in.readString();
        lastCheckedOut = in.readString();
        lastCheckedOutBy = in.readString();
        categories = in.readString();
        url = in.readString();
        publisher = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getAuthor ()
    {
        return author;
    }

    public void setAuthor (String author)
    {
        this.author = author;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getLastCheckedOut ()
    {
        return lastCheckedOut;
    }

    public void setLastCheckedOut (String lastCheckedOut)
    {
        this.lastCheckedOut = lastCheckedOut;
    }

    public String getLastCheckedOutBy ()
    {
        return lastCheckedOutBy;
    }

    public void setLastCheckedOutBy (String lastCheckedOutBy)
    {
        this.lastCheckedOutBy = lastCheckedOutBy;
    }

    public String getCategories ()
    {
        return categories;
    }

    public void setCategories (String categories)
    {
        this.categories = categories;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getPublisher ()
    {
        return publisher;
    }

    public void setPublisher (String publisher)
    {
        this.publisher = publisher;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [author = "+author+", title = "+title+", lastCheckedOut = "+lastCheckedOut+", lastCheckedOutBy = "+lastCheckedOutBy+", categories = "+categories+", url = "+url+", publisher = "+publisher+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(lastCheckedOut);
        dest.writeString(lastCheckedOutBy);
        dest.writeString(categories);
        dest.writeString(url);
        dest.writeString(publisher);
    }
}