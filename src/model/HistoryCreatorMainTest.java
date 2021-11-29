package model;

import java.util.Date;

public class HistoryCreatorMainTest {
    // メインメソッドをもつクラス
    // テスト用です

      void run(){
          Book book = createBook("プログラミング", "達人", "プログラミング社", 1999);
          User user = createUser(20, "かおり", 2, 6, "f6e2e52bc7b6565928e2d0d18d1b074d5c21571da95d4ec250bc3168ddd37bd238", 0, "kaka@ka.com", "0299-55-4444");

          History history = this.createHistory(book, user);
          history.print();

            history.setReturnDate(new Date());
            history.print();
        }

        Book createBook(String authors, String title, String publisher, Integer publishYear){
            Book book = new Book();

            book.setTitle(title);
            book.setAuthors(authors);
            book.setPublisher(publisher);
            book.setPublishYear(publishYear);

            return book;
        }

        User createUser(Integer id, String name, Integer gender, Integer age, String pass, Integer roll, String mail, String tel){
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

        History createHistory(Book book, User user){
            Date date = new Date();
            History history = new History();
            history.setBook(book);
            history.setUser(user);

            history.setLendDate(new Date(date.getTime() - 1000*60*60*24*3));

            return history;
        }

        /**
         * メインメソッド メインルーチン
         * @param args
         */
        public static void main(String[] args){
            HistoryCreatorMainTest creator = new HistoryCreatorMainTest();
            creator.run();
        }

}
