package mobileutility.bookmanager.network.interfaces;

import com.google.gson.JsonObject;

import java.util.List;

import mobileutility.bookmanager.models.Book;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface BookApiInterface {


  //////////////////////////Get all books
  @GET("books")
  Observable<List<Book>> getBooksRx();


  //////////////////////////////Add a book
  @POST("/books")
  Observable<Book> addBook(@Body Book book);

  /////////////////////////////update a book
  @PUT("/books/{book_id}")
  Observable<Book> updateBook(@Path("book_id") String bookId,@Body Book book);

    /////////////////////////////Checkout a book
  @PUT("/books/{book_id}")
  Observable<Book> checkout(@Path("book_id") String bookId,@Body Book book);

  /////////////////////////////Delete a book
  @DELETE("/books/{book_id}")
  Observable<Book> deleteBook(@Path("book_id") String bookId);

  @DELETE("/clean")
  Observable<Void> deleteAllBook();

  @PUT("/books/{book_id}")
  Observable<JsonObject> getUser(@Path("book_id") String bookId);
}