package model;

public class Book {  // Libraryから参照されるクラス

    // これでBookという型ができました コンストラクタは書かなければ、引数なしのコンストラクタが自動で生成されてます

    // 同じパッケージなら publicなしでも参照できますが、違うパッケージからは参照できないので publicが必要です
    public String title;
    public String authors;
    public String publisher;
    public Integer publishYear;

}
