package model;

public class Book {  // Libraryから参照されるクラス

    // これでBookという型ができました コンストラクタは書かなければ、引数なしのコンストラクタが自動で生成されてます

    // 同じパッケージなら publicなしでも参照できますが、違うパッケージからは参照できないので publicが必要ですが、
    // カプセル化で、privateにする
    private String title;
    private String authors;
    private String publisher;
    private Integer publishYear;

    /**
     * コンストラクタ引数なし
     */
    public Book() {
        super();
        // TODO 自動生成されたコンストラクター・スタブ
    }

    /**
     * コンストラクタ引数４つ
     * @param title
     * @param authors
     * @param publisher
     * @param publishYear
     */
    public Book(String title, String authors, String publisher, Integer publishYear) {
        super();
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishYear = publishYear;
    }

    // アクセッサ
    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

}
