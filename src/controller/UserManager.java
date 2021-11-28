package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.User;
import util.UserUtil;

public class UserManager {

    // メソッド宣言の外側で宣言された変数(このlist1のような場所で宣言された変数)は， フィールドと呼ばれます
    // 変数usersLisを，フィールドに変更して，クラス内のメソッドから参照できるようにします
     List<User> usersList = new ArrayList<User>();  // このshelfは、mainメソッドでrunメソッドを呼び出した時に、
     // runメソッドの中で this.addUsers(usersList);を実行してリストの中身を作っています

    void run() {
        // プロジェクトのルートフォルダにおいたusers.csvファイルを読み込む
        this.addUsers(this.usersList);  // 引数は、自分自身のインスタンスフィールドです。このメソッドを使って、フィールドの usersList に要素を代入します
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
        System.out.printf("%d %s %d %d %s %d %s %s", user.getId(), user.getName(), user.getGender(), user.getAge(), user.getPass(), user.getRoll(), user.getMail(), user.getTel());
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


}
