package model;

import java.util.Date;

public class History {

    private Date lendDate;

    private Date returnDate;

    private Book book;

    private User user;

    /**
     * 引数なしコンストラクタ
     */
    public History() {
        super();
        // TODO 自動生成されたコンストラクター・スタブ
    }

    /**
     * 引数4つのコンストラクタ
     * @param lendDate
     * @param returnDate
     * @param book
     * @param user
     */
    public History(Date lendDate, Date returnDate, Book book, User user) {
        super();
        this.lendDate = lendDate;
        this.returnDate = returnDate;
        this.book = book;
        this.user = user;
    }


    /**
     * 貸し出し中であるかを判定するメソッド 異なるパッケージから見えるようにするため publicにする
     * Library.java の 173行目付近で使う
     * 貸し出し中であれば，true， 貸し出し中でなければfalseを返す
     * 貸し出し中である，ということはまだ返却されていないということ
     * returnDateにまだ値が代入されていないことを表します
     * 貸し出された時に初めてHistory型のオブジェクトが生成される
     * @return true:貸出中 <br /> false:貸出中ではない
     */
    public Boolean isLent() {
        if(this.returnDate == null) {  // 貸出中である
            return true;  // 貸出中なら tureを返す
        }
        return false;
    }

    String lentStr() {
        String str = "";
        if(this.isLent()) {
            str = "貸し出し中";
        } else {
            str = "配架中";
        }
        return str;
    }

    /**
     *  履歴の情報を画面に出力する
     *  publicにする
     */
    public void print() {
        System.out.printf("%s, %s, %s, %d (%s) %s(%s; %d)",
                this.book.getTitle(), this.book.getAuthors(), this.book.getPublisher(), this.book.getPublishYear(), this.lentStr(),
                this.user.getName(), this.user.genderStr(), this.user.getAge());
       System.out.print(this.lendDate.toString());
       System.out.println("~");
       if(this.returnDate != null) {
           System.out.println(this.returnDate.toString());
       }
    }

    // アクセッサ
    public Date getLendDate() {
        return lendDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setUser(User user) {
        this.user = user;
    }




}
