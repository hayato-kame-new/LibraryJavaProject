package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import model.User;
import util.UserUtil;

public class UserManager {

    // メソッド宣言の外側で宣言された変数(このlist1のような場所で宣言された変数)は， フィールドと呼ばれます
    // 変数usersLisを，フィールドに変更して，クラス内のメソッドから参照できるようにします
     List<User> usersList = new ArrayList<User>();  // このshelfは、mainメソッドでrunメソッドを呼び出した時に、
     // runメソッドの中で this.addUsers(usersList);を実行してリストの中身を作っています

     /**
      * mainパッケージのLibraryクラス内の mainメソッドに
      * このクラスのインスタンスを生成して、runメソッドを実行します
      * 異なるパッケージからもこのクラスが見えるように、このクラス宣言にアクセス修飾子をpublicにして、このrunメソッドもpublicにすること
      */
    public void run() {
        // プロジェクトのルートフォルダにおいたusers.csvファイルを読み込む
        this.addUsers(this.usersList);  // 引数は、自分自身のインスタンスフィールドです。このメソッドを使って、フィールドの usersList に要素を代入します

        User myself = new User();
        myself.setId(8);
        myself.setName("竹村圭織");
        myself.setGender(2);
        myself.setAge(6);
        myself.setPass("f6e2e52bc7b6565928e2d0d18d1b074d5c21571da95d4ec250bc3168ddd37bd2");
        myself.setRoll(0);
        myself.setMail("kao@kao.com");
        myself.setTel("090-0000-1111");

        this.add(myself);

        User user9 = new User();
        user9.setId(9);
        user9.setName("竹村恵");
        user9.setGender(2);
        user9.setAge(46);
        user9.setPass("f6e2e52bc7b6565928e2d0d18d1b074d5c21571da95d4ec250bc3168ddd37bd2");
        user9.setRoll(1);
        user9.setMail("me@me.com");
        user9.setTel("090-2222-1111");

        this.add(user9);

        // OR検索を実行する 結果を表示する 検索に成功する例
        List<User> searchList = this.findOr(1, "日本花子", 1, null, null , null);
        for(User searchUser : searchList) {
            this.print(searchUser);
        }

        // OR検索を実行する 結果を表示する 検索に失敗する例 完全一致で検索するため 曖昧検索をすると失敗する
        List<User> searchList2 = this.findOr(null, "花子", null, null, null , null);
        for(User searchUser : searchList2) {
            this.print(searchUser);
        }

        // 列挙子を使ってユーザの一覧を順に表示していく 列挙子を使うと、参照専門の集合なので安全に参照ができる   書き換えられる心配が無いため
        // Iterator型 (列挙子) という集合
        Iterator<User> iterator = this.iterator();  // 自分自身のiterator()インスタンスメソッドを呼び出せばいい
        // 列挙子を使った繰り返し 重要
        while(iterator.hasNext()) {
            User user = iterator.next();
            this.print(user);
        }

    }

    /**
     * 参照しかできないUserの集合を返す   Iterator型 (列挙子) という集合を返す
     * UserManagerクラスの外でも、安全に全ユーザを参照できるようになります
     * Iterator型    参照専門の集合を表す型 日本語では， 列挙子という
     * ListからIteratorに変換するには、インスタンスメソッドのiterator()を呼び出す
     *
     * @return this.usersList.iterator()
     */
    public Iterator<User> iterator() {  // java.util.Iterator
        return this.usersList.iterator();
    }

    /**
     * Userオブジェクトを作成し，引数の値を作成したオブジェクトに代入して返すメソッド
     * @param id
     * @param name
     * @param gender
     * @param age
     * @param pass
     * @param roll
     * @param mail
     * @param tel
     * @return Userインスタンス
     */
    User create(Integer id, String name, Integer gender, Integer age, String pass, int roll, String mail, String tel) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setGender(gender);
        user.setAge(age);
        user.setPass(pass);
        user.setRoll(roll);
        user.setMail(mail);
        user.setTel(tel);

        return user;
    }

    /**
     * 引数に受け取ったUser オブジェクトの情報を画面に出力するメソッド
     * @param user
     */
    void print(User user) {
        System.out.printf("%d %s %d %d %s %d %s %s%n", user.getId(), user.getName(), user.getGender(), user.getAge(), user.getPass(), user.getRoll(), user.getMail(), user.getTel());
    }

    /**
     * 返り値は，Integerとし， UserManagerが管理するUserの数を返します．
     * 引数なしです。自分自身thisのインスタンスのフィールドを使います。
     * @return usersListSize
     */
    Integer size() {
        Integer usersListSize = this.usersList.size();
        return usersListSize;
    }

    /**
     * run()メソッドの初めでusers.csvファイルを読み込んで、フィールドのusersList
     * @param usersList
     */
    void addUsers(List<User> usersList) {
    // プロジェクトのルートフォルダにおいたusers.csvファイルを読み込む
         UserUtil util = new UserUtil();
        List<User> users = util.readFromFile("users.csv");
        for(Integer i = 0; i < users.size(); i++) {
            usersList.add(users.get(i));
        }
    }

    /**
     * 受け取ったUserオブジェクトをユーザ型の集合usersListに追加します フィールドとして持ってるusersListに追加
     * @param user
     */
    void add(User user) {

        this.usersList.add(user);  // 自分自身のインスタンスのフィールのリストにaddメソッドで追加する
    }

     /**
      * OR検索をして一致したUserオブジェクトを集合にして返す
      * OR検索には インスタンスメソッドのisMatchOrを使う
      * @param id
      * @param name
      * @param gender
      * @param age
      * @param mail
      * @param tel
      * @return List<User>
      */
    List<User> findOr(Integer id, String name, Integer gender, Integer age,  String mail, String tel) {
          List<User> result = new ArrayList<User>();

          for(User user : this.usersList) {
              if(this.isMatchOr(user, id, name, gender, age, mail, tel) ) {
                  result.add(user);
              }
          }
          return result;
    }

    /**
     * OR検索をする
     * 条件は， id, name,  gender, age, mail, tel の 6種類
     *  null以外が指定された場合，検索条件として指定されたものとする
     * @param user
     * @param id
     * @param name
     * @param gender
     * @param age
     * @param mail
     * @param tel
     * @return true:いずれかの条件と一致した<br /> false: いずれの条件とも一致しなかった
     */
    Boolean isMatchOr(User user, Integer id, String name, Integer gender, Integer age,  String mail, String tel) {
        // 条件は， id, name,  gender, age, mail, tel の 6種類
        // null以外が指定された場合，検索条件として指定されたものとする．
        // ここに，検索条件として与えられた条件いずれか(ORなので)を満たすか否かを判定する処理を書く．
        // 検索条件を満たしていれば，trueを返し，満たしていなければfalseを返す．
        Boolean result = false;
        if(id != null) { // idが nullじゃない時に比較する
            if(Objects.equals(id, user.getId())) {

                result = true;  // 一致したら、tureにする
                return result; // 条件でいずれか一つでも一致したら検索結果となるので、即returnして引数のresultを呼び出し元へ返す
                // もし、一致したら、以下は行われない
            }
                // 一致しない時は OR検索だから result は まだ確定できないので、何もしない
            // result はfalseのままになってる 次の条件に一致するのかどうか次に続ける
        }
        // idが nullだったら、検索条件として指定されてないものとしてスルー

        if(name != null) {  // nullじゃなかったら比較する
            if(Objects.equals(name, user.getName())) {

                result = true;  // 一致したら trueにする
                return result; // 即このメソッド終了 引数のresultを呼び出し元へ返す
                // ここより以下は実行されない
            }
            // 一致しない時は OR検索だから result は まだ確定できないので、何もしない
            // result はfalseのままになってる 次の条件に一致するのかどうか次に続ける
        }
       // nameが nullだったら、検索条件として指定されてないものとしてスルー

        if(gender != null) { // nullじゃなかったら比較する
            if(Objects.equals(gender, user.getGender())) {

                result = true;  // 一致したら trueにする
                return result; // 即このメソッド終了 引数のresultを呼び出し元へ返す
                // ここより以下は実行されない
            }
            // 一致しない時は OR検索だから result は まだ確定できないので、何もしない
            // result はfalseのままになってる 次の条件に一致するのかどうか次に続ける
        }
       // genderが nullだったら、検索条件として指定されてないものとしてスルー

        if(age != null) { // nullじゃなかったら比較する
            if(Objects.equals(age, user.getAge())) {

                result = true;  // 一致したら trueにする
                return result; // 即このメソッド終了 引数のresultを呼び出し元へ返す
                // ここより以下は実行されない
            }
            // OR検索で どの条件とも一致しない時は result は false
        }
       // ageが nullだったら、検索条件として指定されてないものとしてスルー

        if(mail != null) { // nullじゃなかったら比較する
            if(Objects.equals(mail, user.getMail())) {

                result = true;  // 一致したら trueにする
                return result; // 即このメソッド終了 引数のresultを呼び出し元へ返す
                // ここより以下は実行されない
            }
            // OR検索で どの条件とも一致しない時は result は false
        }
       // mailが nullだったら、検索条件として指定されてないものとしてスルー

        if(tel != null) { // nullじゃなかったら比較する
            if(Objects.equals(tel, user.getTel())) {

                result = true;  // 一致したら trueにする
                return result; // 即このメソッド終了 引数のresultを呼び出し元へ返す
                // ここより以下は実行されない
            }
            // OR検索で どの条件とも一致しない時は result は false
        }
       // telが nullだったら、検索条件として指定されてないものとしてスルー

        return result;  // ここまで来るということはどの条件にもあわず falseが返される
    }

    /**
     * インスタンスフィールドのusersListに格納されたUserインスタンスを 登録から削除する
     * 引数に受け取ったUserオブジェクトを登録から削除します
     * @param user
     * @return
     */
    Boolean delete(User user) {

        // Listのremoveには，要素そのもの，もしくは，インデックスの２種類の値が渡せます
        // 今回は、要素を渡して削除をします Listのremoveは 戻り値がboolean型ですので Boolean型で受けられます
        Boolean result = this.usersList.remove(user);
        return result;
    }


}
