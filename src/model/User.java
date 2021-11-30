package model;

public class User {

    // カプセル化のために、privateにする
    private Integer id;   // データベース化した時の主キーとなる 主キーなのでユニークです(一意制約)

    private String name;

    // 性別は Integerで管理する
    private Integer gender;  // 性別  1: 男  2: 女

    private Integer age;

    // 他のフィールドも追加する
    private String pass;  // これは、ハッシュ化した後のパスワード

    private Integer roll;  // 0:一般   1:管理者

    private String mail;

    private String tel;

    /**
     * 引数なしのコンストラクタ
     */
    public User() {
        super();
        // TODO 自動生成されたコンストラクター・スタブ
    }

    /**
     * 引数ありのコンストラクタ
     * @param id
     * @param name
     * @param gender
     * @param age
     * @param pass
     * @param roll
     * @param mail
     * @param tel
     */
    public User(Integer id, String name, Integer gender, Integer age, String pass, Integer roll, String mail, String tel) {
        super();
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.pass = pass;
        this.roll = roll;
        this.mail = mail;
        this.tel = tel;
    }

    /**
     * 性別を文字で表すインスタンスメソッド
     * @return str
     */
    public String genderStr() {
        String str = "";
        if(this.gender == 1) {
            str = "男";
        }else if (this.gender == 2) {
            str = "女";
        }
        return str;
    }

    // アクセッサ
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getPass() {
        return pass;
    }

    public Integer getRoll() {
        return roll;
    }

    public String getMail() {
        return mail;
    }

    public String getTel() {
        return tel;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setRoll(Integer roll) {
        this.roll = roll;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


}
