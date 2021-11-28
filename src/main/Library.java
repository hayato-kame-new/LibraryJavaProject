package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import controller.UserManager;
import model.Book;
import util.LibraryUtil;

public class Library {

    // メソッド宣言の外側で宣言された変数(このlist1のような場所で宣言された変数)は， フィールドと呼ばれます
    // 変数shelfを，フィールドに変更して，クラス内のメソッドから参照できるようにします
     List<Book> shelf = new ArrayList<Book>();  // このshelfは、mainメソッドでrunメソッドを呼び出した時に、
     // runメソッドの中で this.addBooks(shelf);を実行してリストの中身を作っています

     /**
      * 作成したインスタンスメソッドをrun メソッドから呼び出して本の情報を操作していきます
      *
      * mainメソッドの中では この自クラスLibraryインスタンスを生成して、インスタンス.run()で呼び出す
      * thisは自分自身のインスタンスです
      * runメソッド内で， Bookの実体を作成
      */
     void run() {
         this.printWelcomeMessage(); // まず、メッセージ表示
         // Bookの実体を作成 newで値を作成することではじめてメモリ上に領域が確保されて値が代入できるようになります
//        Book book = new Book();
//        book.title = "羅生門";// book.title = new String("羅生門");
//        book.authors = "芥川龍之介";  // book.authors = new String("芥川龍之介");
//        book.publisher = "青空文庫"; // book.publisher = new String("青空文庫");
//        book.publishYear = 1997;  // book.publishYear = new Integer(1997);


         // 配列ではなく，より高度なデータ構造を使うようにしましょう
         // Bookの集合を保持する変数を用意する
         // 配列のデメリット 上限が決まってしまう． 途中のインデックスの値を削除すると，あとあと面倒．
         // 配列より高度なデータ構造が標準で用意されています List型です． 上限がなく，途中の要素を削除しても，自動的に間を詰めてくれます

         /* List型を利用するときには，List型に入れる型を指定する必要があります．
          * 例えば，ListにString型の値を順番に入れていくときは， List<String>型として宣言する必要があります．
          * また，List型の実体を作成するときにも，newを利用しますが， List<String> list = new List<String> とはできません．
          *  Listはあくまで型であり，実体を作成できるようにはなっていないためです． ArrayList<String>の実体を作成することで，
          *  List<String>型の変数に代入できるようになります． さらに，ListやArrayList を利用するときは，
          * クラス宣言よりも前に import java.util.*;という一行を書いておく必要があります．
          * Book book = this.createBook("羅生門", "芥川龍之介", "青空文庫", 1997);
          * */

         //　この変数shelfの宣言場所を、メソッド の中ではなく，クラス宣言 の中（メソッド宣言の外側）で宣言しましょう
         //  List<Book> shelf = new ArrayList<Book>();  // newしてメモリ確保する

//        shelf.add(this.createBook("羅生門", "芥川龍之介", "青空文庫", 1997));
//        Book book1 = shelf.get(0);
//        System.out.printf("%s (%s) %s, %d%n", book1.title, book1.authors, book1.publisher, book1.publishYear);

         this.addBooks(shelf);
         Book book1 = this.find("それから");
         this.remove(book1);
        // this.list();
         this.findAndPrint(null, "夏目漱石", null, 1991);

         System.out.println("");

         List<Book> orBookList = this.findOr(null, "夏目漱石", null, 1991);
         for(Book book : orBookList) {
             this.printBook(book);
         }

         System.out.println("");

         // remove2  これは間違ったメソッドです！！削除はできてません
         this.remove2("こころ", "夏目漱石", "青空文庫", 1991);
         this.list();

         // ここで、findメソッドを使って、検索
//         Book book = this.find("それから");
//         this.printBook(book);

         // ここで、findAndメソッドを使って、検索
        // List<Book> booklist = this.findAnd("こころ", null, null, 1999);  // 色々やって見て確認
//         List<Book> booklist = this.findAnd(null, null, "青空文庫", null);
//         for(Book book : booklist) {
//             this.printBook(book);
//         }


//        Book book1 = shelf.get(0);
//        this.printBook(book1);
//
//        Book book2 = shelf.get(1);
//        this.printBook(book2);
//
//        Book book3 = shelf.get(2);
//        this.printBook(book3);
//
//        Book book4 = shelf.get(3);
//        this.printBook(book4);
//
//        Book book5 = shelf.get(4);
//        this.printBook(book5);

         // リストの要素よりも多く取り出そうとすると Exception in thread "main" java.lang.IndexOutOfBoundsException: Index 5 out of bounds for length 5
//        for(Integer i = 0; i < shelf.size(); i++) {
//            Book book = shelf.get(i);
//            this.printBook(book);
//        }

     }

     /**
      * これは間違ったメソッドです！！削除はできません！！
      * @param title
      * @param author
      * @param publisher
      * @param publishYear
      */
     void remove2(String title, String author, String publisher, Integer publishYear){
         Book book = createBook(title, author, publisher, publishYear);
         shelf.remove(book);  // メソッドの引数には参照を渡しているので、
         // つまり、メモリの番地の情報を渡しているので、shelfにもともと入れてあった要素とは違う参照のため、リストからは削除されません
         // 正しく作用するには、findメソッドで探したBookオブジェクトの参照を引数に渡すのが正しいです
         // 参照型のデータ型のオブジェクトは、参照渡しです つまり、メモリ番地を渡しているのです。
     }


     /**
      * OR検索をして、いずれかの条件と一致したなら、その本を検索してリストに格納する
      * メソッドの中でisMatchOrメソッドを呼び出す
      * @param title
      * @param authors
      * @param publisher
      * @param publishYear
      * @return
      */
     List<Book> findOr(String title, String authors, String publisher, Integer publishYear) {
         List<Book> result = new ArrayList<Book>();

         for(Book book : this.shelf) {
             if(this.isMatchOr(book, title, authors, publisher, publishYear) ) {
                 result.add(book);
             }
         }
         return result;
     }


     /**
      * OR検索をする
      * 引数のtitle authors publisher publishYearのいずれかの条件と一致すればtrueを返し、
      * どれとも一致しない場合にfalseを返す
      *
      * @param book
      * @param title
      * @param authors
      * @param publisher
      * @param publishYear
      * @return true:いずれかの条件と一致した<br /> false: いずれの条件とも一致しなかった
      */
     Boolean isMatchOr(Book book, String title, String authors, String publisher, Integer publishYear) {
         // 条件は，title, authors, publisher, publishYearの4種類．
         // null以外が指定された場合，検索条件として指定されたものとする．
         // ここに，検索条件として与えられた条件いずれか(ORなので)を満たすか否かを判定する処理を書く．
         // 検索条件を満たしていれば，trueを返し，満たしていなければfalseを返す．
         Boolean result = false;
         if(title != null) { // titleが nullじゃない時に比較する
             if(Objects.equals(title, book.getTitle())) {
            // if(Objects.equals(title, book.title)) {
                 result = true;  // 一致したら、tureにする
                 return result; // 条件でいずれか一つでも一致したら検索結果となるので、即returnして引数のresultを呼び出し元へ返す
                 // もし、一致したら、以下は行われない
             }
                 // 一致しない時は OR検索だから result は まだ確定できないので、何もしない
             // result はfalseのままになってる 次の条件に一致するのかどうか次に続ける
         }
         // titleが nullだったら、検索条件として指定されてないものとしてスルー

         if(authors != null) {  // nullじゃなかったら比較する
             if(Objects.equals(authors, book.getAuthors())) {
            //  if(Objects.equals(authors, book.authors)) {
                 result = true;  // 一致したら trueにする
                 return result; // 即このメソッド終了 引数のresultを呼び出し元へ返す
                 // ここより以下は実行されない
             }
             // 一致しない時は OR検索だから result は まだ確定できないので、何もしない
             // result はfalseのままになってる 次の条件に一致するのかどうか次に続ける
         }
        // authorsが nullだったら、検索条件として指定されてないものとしてスルー

         if(publisher != null) { // nullじゃなかったら比較する
             if(Objects.equals(publisher, book.getPublisher())) {
            //  if(Objects.equals(publisher, book.publisher)) {
                 result = true;  // 一致したら trueにする
                 return result; // 即このメソッド終了 引数のresultを呼び出し元へ返す
                 // ここより以下は実行されない
             }
             // 一致しない時は OR検索だから result は まだ確定できないので、何もしない
             // result はfalseのままになってる 次の条件に一致するのかどうか次に続ける
         }
        // publisherが nullだったら、検索条件として指定されてないものとしてスルー

         if(publishYear != null) { // nullじゃなかったら比較する
             if(Objects.equals(publishYear, book.getPublishYear())) {
            // if(Objects.equals(publishYear, book.publishYear)) {
                 result = true;  // 一致したら trueにする
                 return result; // 即このメソッド終了 引数のresultを呼び出し元へ返す
                 // ここより以下は実行されない
             }
             // OR検索で どの条件とも一致しない時は result は false
         }
        // publishYearが nullだったら、検索条件として指定されてないものとしてスルー
         return result;  // ここまで来るということはどの条件にも会わず falseが返される
     }



     /**
      * AND検索で得た結果を表示するfindAndPrintメソッド
      * このfindAndPrintメソッドの中では、引数に受け取った情報をそのままfindAndに渡して
      *  そして，findAndの返り値であるList<Book>を変数に代入してからループで表示する
      * @param title
      * @param authors
      * @param publisher
      * @param publishYear
      */
     void findAndPrint(String title, String authors, String publisher, Integer publishYear) {

//         List<Book> result = new ArrayList<Book>();
//
//         for(Book book : this.shelf) {
//             if(this.isMatch(book, title, authors, publisher, publishYear) ) {
//                 result.add(book);
//             }
//         }
         List<Book> result = this.findAnd(title, authors, publisher, publishYear);
         // AND検索で得たリストを表示する
         for(Book book2 : result) {
             System.out.printf("%s (%s) %s %d%n", book2.getTitle(), book2.getAuthors(), book2.getPublisher(), book2.getPublishYear());
             // System.out.printf("%s (%s) %s %d%n", book2.title, book2.authors, book2.publisher, book2.publishYear);
         }

     }


     /**
      * 格納された本を 登録から削除する
      * @param book
      */
     void remove(Book book) {
         // Listのremoveには，要素そのもの，もしくは，インデックスの２種類の値が渡せます
         // 今回は、要素を渡して削除をします
         this.shelf.remove(book);
     }

     /**
      * this.isMatchを呼び出してAND検索をして、一致したらtrueが返されるので、検索結果のリストに格納してリストを返します
      * @param title
      * @param authors
      * @param publisher
      * @param publishYear
      * @return List<Book>
      */
     List<Book> findAnd(String title, String authors, String publisher, Integer publishYear) {
         List<Book> result = new ArrayList<Book>();

         for(Book book : this.shelf) {
             if(this.isMatch(book, title, authors, publisher, publishYear) ) {
                 result.add(book);
             }
         }
         return result;
     }

     /**
      * AND検索をするメソッド 検索条件は4つ 検索条件が nullの時には検索条件が指定されてないとみなす
      *
      * @param book
      * @param title
      * @param authors
      * @param publisher
      * @param publishYear
      * @return true:全ての条件に一致した<br /> false:全ての条件には一致しない
      */
     Boolean isMatch(Book book, String title, String authors, String publisher, Integer publishYear) {
         // 条件は，title, authors, publisher, publishYearの4種類．
         // null以外が指定された場合，検索条件として指定されたものとする．
         // ここに，検索条件として与えられた条件全てを満たすか否かを判定する処理を書く．
         // 検索条件を満たしていれば，trueを返し，満たしていなければfalseを返す．
         Boolean result = false;
         if(title != null) { // titleが nullじゃない時に比較する
             if(Objects.equals(title, book.getTitle())) {
             // if(Objects.equals(title, book.title)) {
                 result = true;  // 一致したら、tureにする
             } else {
                 // 一致しない時は result は falseのままreturnする　AND検索だから
                 return result; // ここで即メソッド終了で引数を呼び出し元へ返す
             }
         }
         // titleが nullだったら、検索条件として指定されてないものとしてスルー

         if(authors != null) {  // nullじゃなかったら比較する
             if(Objects.equals(authors, book.getAuthors())) {
            //  if(Objects.equals(authors, book.authors)) {
                 result = true;  // 一致したら trueにする
             } else {
                 // 一致しないなら、AND検索なのでここで即リターンしてfalseを呼び出し元へ返す
                 result = false;
                 return result;
             }
         }
        // authorsが nullだったら、検索条件として指定されてないものとしてスルー

         if(publisher != null) { // nullじゃなかったら比較する
             if(Objects.equals(publisher, book.getPublisher())) {
            // if(Objects.equals(publisher, book.publisher)) {
                 result = true;  // 一致したら trueにする
             } else {
                 // 一致しないなら、AND検索なのでここで即リターンしてfalseを呼び出し元へ返す
                 result = false;
                 return result;
             }
         }
        // publisherが nullだったら、検索条件として指定されてないものとしてスルー

         if(publishYear != null) { // nullじゃなかったら比較する
             if(Objects.equals(publishYear, book.getPublishYear())) {
             // if(Objects.equals(publishYear, book.publishYear)) {
                 result = true;  // 一致したら trueにする
             } else {
                 // 一致しないなら、AND検索なのでここで即リターンしてfalseを呼び出し元へ返す
                 result = false;
                 return result;
             }
         }
        // publishYearが nullだったら、検索条件として指定されてないものとしてスルー
         return result;
     }

     /**
      * 検索のインスタンスメソッド 完全一致で検索
      * 引数で与えられたタイトルと,本棚の本のタイトルを比べて、同じ文字列のものが見つかったらBookオブジェクトを返す
      * タイトルから本が一意に決まります (現実世界では一意に定まらない場合もありますが，定まるものとします)
      * @param title
      * @return
      */
     Book find(String title) {
         for(Book book : shelf) {
             if(Objects.equals(title, book.getTitle())) {
             // if(Objects.equals(title, book.title)) {
                 return book;
             }
         }
         return null;  // 見つからない時には nullを返す
         // 引数で与えられた文字列と，Book 型の変数が持つtitleが一致するかを判定し， 一致した場合，その要素を返し．一方，全ての要素を調べても見つからなかった場合は， nullを返す
     }

     /**
      * 拡張for文  for(型 変数名: コンテナ変数)のように書く
      *  コンテナ変数とはList型や配列のような複数の要素を持てる変数を指します
      *  この拡張for文のコンテナ変数として扱えるかはBoolean flag = 変数 instanceof Iterable で確認できます
      */
     void list() {
         for(Book book : this.shelf) {
             this.printBook(book);
         }
     }


    /**
     * runメソッドの中でウェルカムメッセージを表示するのも良いですが
     * ウェルカムメッセージを表示するためだけのメソッドを用意する方がより良いでしょう
     * run メソッドの処理内容を見るだけで，どのようなことを行うのかが理解できます
     * インスタンスメソッド(非staticメソッド)
     */
    void printWelcomeMessage() {
        System.out.println("ようこそ図書館システムへ.");
    }


    /**
     * インスタンスメソッド(非staticメソッド)
     * 本のタイトル，著者，出版社，出版年を引数として受け取り， Bookの実体を返します
     * @param title
     * @param authors
     * @param publisher
     * @param publishYear
     * @return
     */
    Book createBook(String title, String authors, String publisher, Integer publishYear) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthors(authors);
        book.setPublisher(publisher);
        book.setPublishYear(publishYear);
//        book.title = title;
//        book.authors = authors;
//        book.publisher = publisher;
//        book.publishYear = publishYear;
        return book;
    }

    /**
     * 本の詳細表示するインスタンスメソッド(非staticメソッド)
     * @param book
     */
    void printBook(Book book) {
        // どっちでもいいけど
//        System.out.printf("%s (%s) %s, %d%n", book.title, book.authors, book.publisher, book.publishYear);
     //   System.out.printf( "%s,%s,%s,%d%n", book.title, book.authors, book.publisher, book.publishYear);
        System.out.printf( "%s,%s,%s,%d%n", book.getTitle(), book.getAuthors(), book.getPublisher(), book.getPublishYear());
    }

    /**
     * 本を本棚に入れる
     * インスタンスメソッド(非staticメソッド)
     * @param shelf
     */
    void addBooks(List<Book> shelf) {
//        Book book1 = this.createBook("羅生門", "芥川龍之介", "青空文庫", 1997);
//        shelf.add(book1);
//        // さらに本を追加する
//        Book book2 = this.createBook("銀河鉄道の夜", "宮沢賢治", "青空文庫", 1989);
//        shelf.add(book2);
//
//        Book book3 = this.createBook("こころ", "夏目漱石", "青空文庫", 1999);
//        shelf.add(book3);
//
//        Book book4 = this.createBook("PHP", "AAA", "青空文庫", 2010);
//        shelf.add(book4);
//
//        Book book5 = this.createBook("Java", "BBB", "青空文庫", 2019);
//        shelf.add(book5);

        // プロジェクトのルートフォルダにおいたbooks.csvファイルを読み込む
        LibraryUtil util = new LibraryUtil();
        List<Book> books = util.readFromFile("books.csv");
        for(Integer i = 0; i < books.size(); i++) {
            shelf.add(books.get(i));
        }


    }

    /**
     * メインルーチンです
     * @param args
     */
    public static void main(String[] args) {
        Library lib = new Library();
        lib.run();
        UserManager userManager = new UserManager();
        userManager.run();
    }
}
