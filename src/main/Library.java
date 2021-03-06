package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import controller.UserManager;
import model.Book;
import model.History;
import model.User;
import util.LibraryUtil;

public class Library {
    // フィールド宣言
    // メソッド宣言の外側で宣言された変数(このlist1のような場所で宣言された変数)は， フィールドと呼ばれます
    // 変数shelfを，フィールドに変更して，クラス内のメソッドから参照できるようにします
     List<Book> shelf = new ArrayList<Book>();  // このshelfは、mainメソッドでrunメソッドを呼び出した時に、
     // runメソッドの中で this.addBooks(shelf);を実行してリストの中身を作っています

     UserManager manager = new UserManager();

     /**
      * 本のインスタンスがキー その本に関する情報としてその本の今までの貸出記録が値
      */
     Map<Book, List<History>> historyMap = new HashMap<Book, List<History>>();

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
         for(Book book : shelf) {

             this.printBook(book);
         }
         this.registerUsers();
         // 元のリストに変更を加えることなく安全に扱うために Iterator型( 列挙子型の)リストで参照する
         Iterator<User> iterator = manager.iterator();
      // 列挙子を使った繰り返し 重要
         while(iterator.hasNext()) {
             User user = iterator.next();
             manager.print(user);
         }

         System.out.println("");

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
         this.runLend();
         for (Map.Entry< Book, List<History>> entry : this.historyMap.entrySet()) {
             Book book = entry.getKey();
             System.out.print("貸出本:");
             this.printBook(book);

             List<History> histories = entry.getValue();
             for(History history : histories) {
                 System.out.println( history.getLendDate() + " ~ " + history.getReturnDate());
             }
         }
         System.out.println("");
         System.out.println("MapのキーのBookを表示します Iterator列挙子を用いて");
         this.printKeySetIte(historyMap);  // Iterator列挙子を用いたやり方

         System.out.println("");
         System.out.println("MapのキーのBookを表示します Iterator列挙子を用いてない 拡張forを用いて");
         this.printKeySet(historyMap);  // Iterator列挙子を用いないやり方 こっちの方が一般的

         System.out.println("");
         System.out.println("Mapの 値 List<History>を表示します Iterator列挙子を用いて");
         this.printMapValuesIte(historyMap);  // Iterator列挙子を用いたやり方

         System.out.println("");
         System.out.println("Mapの 値 List<History>を表示します Iterator列挙子を用いてない 拡張forを用いて");
         this.printMapValues(historyMap);  // Iterator列挙子を用いないやり方 こっちの方が一般的

         // キーとバリューのペア集合を表示
         System.out.println("");
         System.out.println("Mapの  キーとバリューのペア集合を表示します Iterator列挙子を用いて");
         this.printMapKeyAndValueIte(historyMap);

         System.out.println("");
         System.out.println("Mapの  キーとバリューのペア集合を表示します Iterator列挙子を用いてない 拡張forを用いて");
         this.printMapKeyAndValue(historyMap);

         System.out.println("");
         System.out.println("検索結果を表示します");


         Book book2 = this.shelf.get(1);

         // この検索は見つかる
         System.out.println("1回目検索");
         List<History> histories2 = this.runFindHistory(book2, null);
         for(History h : histories2) {
             this.printHistory(h);
         }

         User user1 = manager.find(1);
         User user2 = manager.find(2);

         // この検索は見つからない
         System.out.println("２回目検索");
         List<History> histories3 = this.runFindHistory(book2, user2);
         for(History h : histories3) {
             this.printHistory(h);
         }

         // この検索はすべての本の全ての履歴を表示する
         System.out.println("3回目検索");
         List<History> histories4 = this.runFindHistory(null, null);
         for(History h : histories4) {
             this.printHistory(h);
         }

         System.out.println("4回目検索");
         List<History> histories5 = this.runFindHistory(null, user1);
         for(History h : histories5) {
             this.printHistory(h);
         }

     }



     List<History> runFindHistory(Book book, User user) {
         return  this.findHistory(book, user);
     }


     // このメソッドは runFindHistoryメソッドの中で使われます
     List<History> findHistory(Book book, User user) {
         // 結果として返すための resultList を newで確保
         List<History> resultList = new ArrayList<History>();
         // キーは重複を許さないから キーを元にして見つけた バリューは一意
         List<History> keyFindHistories = new ArrayList<History>();
             // キーは一位なので、見つかる値も一つのコレクションだけ もしくは null
         if(book != null) {  // キーが nullじゃなければ キーを元に検索する
             keyFindHistories = this.historyMap.get(book);  // キーを元に バリューを取得する

            //  keyFindHistories nullじゃ無いなら さらに絞り込む
            // この keyFindHistoriesの中の要素に、引数のuserインスタンスに一致するHistoryインスタンスが有るのか調べる
            if(keyFindHistories != null ) {
                for(History history: keyFindHistories) {
                    if(user != null && history.getUser().equals(user)) {
                        // 一致したなら
                        resultList.add(history);  // キーが指定されてたなら、一意になる
                        return resultList;
                    }

                    if (user == null) {
                        // userが nullなら キーとなるBookを指定して得られたバリューが返り値そのものになります
                        resultList = keyFindHistories;  // 参照を代入する 参照渡しする
                        return resultList;
                    }
                }
            }
         } else {  // bookがnullの場合は Map に格納されているすべてのバリューに対して検索を行わなければいけない
             // そして 各バリューに対して，Userで絞り込む
             // collection に 全てのバリューを取得
             Collection<List<History>> collection = this.historyMap.values();
             if(user != null) {
                 for(List<History> histories : collection) {
                     for(History history : histories) {
                         if( history.getUser().equals(user)) {
                             resultList.add(history);  //  キーが指定されて無いので、 結果には複数になることもある
                         }
                     }
                 }
                     return resultList;
             } else if (user == null) {
                 // userが nullなら キーも指定してないので
                 // 引数のBook，Userの両方が nullであった場合は，Map のバリューの内容をすべて返り値のList に追加し直す必要があります．
                 // 全てのBookを検索して その全ての履歴も検索する(全てのUser)
                 Set<Book> allKeySet = this.historyMap.keySet();  // キーのSetを取り出す キーは重複を許さないので インタフェースインタフェースSet<E>型
                 // インタフェースSet<E>  は Iterable<E> が スーパーインタフェースだから、このインタフェースを実装すると、オブジェクトを「for-eachループ」文の対象にすることができます
                 for(Book key : allKeySet) {
                     // ループで keyを全て取り出す ただし、Setは格納した順序は保証されない、Setは順番を保持しません
                     // その key に対応する バリューを全て表示する
                     List<History> allHistories = this.historyMap.get(key);
                     for(History h : allHistories) {
                         resultList.add(h);  // 結果リストに全部詰める
                     }
                 }
                 return resultList;
             }
         }
         return resultList;
 }



     // Mapからの列挙子の取得方法
     // Mapの列挙子は３種類存在します．キーの集合，バリューの集合， キーとバリューのペアの集合です．それぞれ取得する方法や返される列挙子が含む型も異なります．

     /**
      * キーの集合の列挙子
      * キーの集合を表示する Iteratorを使う
      * キーは重複を許しませんので historyMap.keySet()では Setが得られる ただし，Setは順番を保持しません
      * Map<Book, List<History>>のキー集合に対する Iteratorを使った繰り返し
      * Map型の変数に対して，keySetメソッドを呼び出し その結果，キー集合のSetが返される
      * れに対して， iteratorメソッドを呼び出すと キー集合に対する列挙子が取得できます
      * Iterableインターフェースを実装するクラスのiterator()メソッドを呼び出すと、イテレータを得られます
      * インタフェースのList  クラスのArrayListは、インタフェースIterable<T>を実装してますので、iterator()を呼び出せます
      * このインタフェースIterable<T>を実装すると、オブジェクトを「for-eachループ」文の対象にすることができます
      * ただし，Setは順番を保持しませんので， どのような順序で返されるかはユーザ側では制御できない
      * 下の拡張for文のやり方のprintKeySetメソッドでもいい
      *
      * @param historyMap
      */
     void printKeySetIte(Map<Book, List<History>> historyMap) {
         for(Iterator<Book> i = historyMap.keySet().iterator(); i.hasNext(); ) {
             Book book = i.next();
             this.printBook(book);
         }
     }

     /**
      * キーの集合の列挙子
      * キーの集合を表示する   キーは重複を許しませんので historyMap.keySet()では Setが得られる ただし，Setは順番を保持しません
      * Iterator列挙子を用いないやり方 拡張for文のやり方 こっちの方が一般的
      * @param historyMap
      */
     void printKeySet(Map<Book, List<History>> historyMap) {
         for(Book book : historyMap.keySet()) {
             this.printBook(book);
         }
     }

     /**
      * バリューの集合を表示する
      * Map<Book, List<History>>のバリューに対する Iterator型 列挙子型 を使った繰り返し バリューの集合の列挙子
      * Map型の変数に対して，valuesメソッドを呼び出し
      * その結果，バリューの集合であるCollectionが返されます Collection です Setではありません Mapの値は重複を許すからです
      * それに対して， iteratorメソッドを呼び出すと，バリュー集合に対する列挙子が取得できます．
      * バリューは重複を許しますので、Setではありません
      * List も ArrayListも インタフェースIterable<T>を実装してますので、iterator()を呼び出せます
      * このインタフェースIterable<T>を実装すると、オブジェクトを「for-eachループ」文の対象にすることができます
      * ただし，Setは順番を保持しませんので， どのような順序で返されるかはユーザ側では制御できない
      * 下 の拡張for文のやり方 printMapValuesメソッドでもいい
      * @param historyMap
      */
     void printMapValuesIte(Map<Book, List<History>> historyMap) {
         for(Iterator<List<History>> i = historyMap.values().iterator(); i.hasNext();  ) {
              List<History> histories = i.next();
              for(Iterator<History> j = histories.iterator(); j.hasNext();  ) {
                  History history = j.next();
                  this.printHistory(history);
              }
         }
     }

     /**
      * バリューの集合を表示する 列挙子Iteratorを用いないやり方
      * 拡張for文を用いて Mapの値を表示する
      * @param historyMap
      */
     void printMapValues(Map<Book, List<History>> historyMap) {
         for(List<History> histories : historyMap.values()) {
             for(History history : histories) {
                 this.printHistory(history);
             }
         }
     }

     // キーとバリューのペア集合の列挙子
     /**
      * Map<Book, List<History>>の各ペアに対して， Iteratorを使った繰り返しを行い 表示する
      * Map型の変数に対して，entrySetメソッドを呼び出します． その結果，キーとバリューのペアの集合であるSetが返されます
      * それに対して， iteratorメソッドを呼び出すと，ペア集合に対する列挙子が取得できます
      * キーとバリューのペアは Map.Entry型で表される
      * 拡張for文を用いで下のprintMapKeyAndValueメソッドのようにも書けます
      * @param historyMap
      */
     void printMapKeyAndValueIte(Map<Book, List<History>> historyMap) {
         for(Iterator<Map.Entry<Book, List<History>>> i = historyMap.entrySet().iterator(); i.hasNext();  ) {
            Map.Entry<Book, List<History>> entry = i.next();  // キーとバリューのペア集合を取得してる
            Book key = entry.getKey();
            List<History> value = entry.getValue();
            // key, value に対する処理を書けば良い
            System.out.println("[キー]");
            this.printBook(key);

            System.out.println("[バリュー]");
            for(History history : value) {
                this.printHistory(history);
            }
            System.out.println("");
         }
     }

     /**
      *
      * キーとバリューのペアは Map.Entry型で表される
      *
      * @param historyMap
      */
     void printMapKeyAndValue(Map<Book, List<History>> historyMap) {
         for(Map.Entry<Book, List<History>> entry: historyMap.entrySet()){
                Book key = entry.getKey();
                List<History> value = entry.getValue();
                // key, value に対する処理．
                System.out.println("[キー]");
                this.printBook(key);

                System.out.println("[バリュー]");
                for(History history : value) {
                    this.printHistory(history);
                }
                System.out.println("");
            }
     }

     /**
      * 貸出し情報を表示する
      * @param history
      */
     void printHistory(History history) {
         this.printBook(history.getBook());
         manager.print(history.getUser());
         System.out.println( history.getLendDate() + " ~ " + history.getReturnDate());
     }

     /**
      * run()メソッドの中で実行する
      * 貸出を実行して、Mapに登録する 中でlendメソッドを実行する
      */
     void runLend() {
         User user1 = manager.find(1);
         User user2 = manager.find(2);
         User user3 = manager.find(3);

         History history1 = null;
         History history2 = null;
         if(user1 != null) {
             history1 = this.createHistory(user1, this.shelf.get(0));
             history2 = this.createHistory(user1, this.shelf.get(1));
         }
         if(this.registerHistory(history1)) { // registerHistoryでtrueを返したら lendを実行する

             // lendメソッドを実行すると、成功するとhistoryインスタンスが返り、失敗すれば nullが返る
            history1 = this.lend(user1, this.shelf.get(0));  // 実行される
            if(history1 != null) {
                // Mapに追加する(更新)
                this.historyMap.put(this.shelf.get(0), this.historyMap.get(history1.getBook()));
            }
         }
         if(this.registerHistory(history2)) {
             history2 = this.lend(user1, this.shelf.get(1)); // 実行される
             if(history2 != null) {
                 // Mapに追加する(更新)
                 this.historyMap.put(this.shelf.get(1), this.historyMap.get(history2.getBook()));
             }
         }

         History history3 = null;  // history3 は失敗する
         History history4 = null;
         if(user2 != null) {
             history3 = this.createHistory(user2, this.shelf.get(1));  // 同じ本を借りようとしてる 失敗するはず
             history4 = this.createHistory(user2, this.shelf.get(2));
         }
         if(this.registerHistory(history3)) {  // falseが返るはず
             history3 = this.lend(user2, this.shelf.get(1));  // ここは実行されないはず
             if(history3 != null) {   // 実行されない
                 // Mapに追加する(更新)
                 this.historyMap.put(this.shelf.get(1), this.historyMap.get(history2.getBook())); // 実行されない
             }
         }
         if(this.registerHistory(history4)) {
             history4 = this.lend(user2, this.shelf.get(2));  // 実行される
             if(history4 != null) {
                 // Mapに追加する(更新)
                 this.historyMap.put(this.shelf.get(2), this.historyMap.get(history4.getBook()));
             }
         }

         History history5 = null;
         if(user3 != null) {
             history5 = this.createHistory(user3, this.shelf.get(3));
         }
         if(this.registerHistory(history5)) {
             history5 = this.lend(user3, this.shelf.get(3));  // 実行される
             if(history5 != null) {
                 // Mapに追加する(更新)
                 this.historyMap.put(this.shelf.get(3), this.historyMap.get(history5.getBook()));
             }
         }
     }

     /**
      * もし、registerHistoryメソッドがtrueを返したら、このメソッドを使用して貸出処理をする
      * 貸し出し処理を実行する このlendメソッドは、runLendメソッドの中で使われる
      * @param user
      * @param book
      * @return Historyインスタンス 貸出しない場合は nullを返す
      */
     History lend(User user, Book book) {
         // 引数に与えられたuserが本を借りた履歴を作成する
         History history = this.createHistory(user, book);
         // 作成した履歴を 貸出記録へ記録する 戻り値はBoolean型です 貸出可能ならtrueが返り貸出記録へ記録されてます
         // 貸出不可なら、falseが返ります
         if(this.registerHistory(history)) {
              // trueを返せば貸出記録に記録してるので 登録したHistoryの実体を返します
            return history;
         } else {
             // falseを返せば、貸し出せないので貸出記録には何もしてない nullを返す
             return null;
         }
     }

     /**
      * 履歴(history)を登録する
      * 履歴を登録してるだけで、貸出の処理は実行してない
      * この後、このregisterHistoryメソッドがtrueを返せば、この本を貸し出し処理を行うlendメソッドを実行して、貸出し完了する
      * @param history これから登録しようとする履歴historyのインスタンス
      * canLendメソッドを呼び出して、貸出できるかどうかを判定してる canLendがtrueを返せば貸し出せる falseを返せば貸出不可
      *
      * @return true:貸出し履歴に登録しました(履歴を登録しただけ)< br /> false:貸出履歴に登録してません(履歴を登録しない)
      */
     Boolean registerHistory(History history){
         // 自分自身のインスタンスフィールドのthis.historyMapからキー(本インスタンス) で 値(その本に関する履歴のリスト)を取得する
            List<History> histories = this.historyMap.get(history.getBook());
            if(histories == null){  // その本に関しての貸出し履歴は無いnullだと参照先が無い 指し示すのが無い つまり一度も貸し出されたことが無い
                histories = new ArrayList<History>();  // new でメモリを確保して、コンストラクタで初期化する
                historyMap.put(history.getBook(), histories);  // putで作成（追加）更新も作成（追加）と同じく，putメソッドで行います． 従来のキーに結びついていたバリューを削除し，キーとバリューを新たに結びつけます．
            }

            if(this.canLend(history, histories)){
                histories.add(history);
                return true;
            }
            return false;
        }

         /**
          * 貸し出しができるか判定する registerHistoryメソッドの中で使う
          *
          * @param history   これから貸し出そうとしている History型の実体
          * @param histories  これから貸し出そうとする本に関する これまでの貸し出し履歴であるHistory のリスト
          * @return true:貸出可能 <br /> false:貸出不可
          */
        Boolean canLend(History history, List<History> histories){
            // まず、これから貸し出そうとしている本が，この図書館システムが管理している本のリストshelf に存在するかを判定
            // containsメソッドで コレクション変数shelfの中に 引数に渡された本が含まれているかを判定する
            //  ! で 判定を逆にしてるから 含まれていなければ falseを返す
            if(!this.shelf.contains(history.getBook())){
                return false;  // これから貸し出そうとする本は、図書館システムには無い本なので 貸出できません
                // 貸出できないので、returnで即メソッドを終了させて、呼び出し元に引数の falseを返します。
                // このメソッドはreturnしたので、この行以降は実行されない
            }

            // これから貸し出そうとする本は、この図書館システムに有るので、貸出できます。(貸出中じゃなければ)
            // 次に、貸し出し中かどうか調べます
            // その本の貸出履歴が存在しない場合 つまり histories.size() == 0 は，今まで借りられたことがないため，貸し出し可能
            // その本の貸出履歴があったら histories.size() > 0 だったら、貸出可能かどうかを調べる
            if(histories.size() > 0){  // その本に関する今までの貸出記録があれば
                // その本に関する最後の貸出記録の実態を取得する
                // List<History> は、 右辺が new ArrayList<Hirstory>(); で初期化されてるため、ArrayListは、中身は配列と同じ構造なので、
                //  履歴は順に格納されるため，最後の要素が貸し出し中でなければ貸し出し可能です
                History lastHistory = histories.get(histories.size() - 1);
                if(lastHistory.isLent()){  // 最後の貸出記録を調べてる isLent()は 貸出中ならtrueを返す
                    return false;  // isLent()かtrueを返したので、貸出中だから falseを返す 貸出不可です
                }
            }
            return true;  // 貸出可能です
        }

     /**
      * 引数に受け取った値を元にHistory 型の実体を作成して返す
      * @param user
      * @param book
      * @return
      */
     History createHistory(User user, Book book) {
         History history = new History();
         history.setUser(user);
         history.setBook(book);
         history.setLendDate(new Date());

         return history;
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
     * csvファイルから読み込んでフィールドに値を設定するメソッド
     * 自分自身のフィールドのUserManagerインスタンスがもつ usersListフィールに値をセットします
     */
    void registerUsers() {
        manager.addUsers(manager.getUsersList());
    }

    /**
     * メインルーチンです
     * @param args
     */
    public static void main(String[] args) {
        Library lib = new Library();
        lib.run();
//        UserManager userManager = new UserManager();
//        userManager.run();
    }
}
