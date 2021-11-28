package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import model.User;


public class UserUtil {


    public List<User> readFromFile(String fileName){
        List<User> users = new ArrayList<User>();
        try(BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), "utf-8"))){
            String line;
            while((line = in.readLine()) != null){
                line = line.trim();
                if(Objects.equals(line, "") || line.startsWith("#")){
                    continue;
                }
                String[] data = line.split(",");
                // 引数がStringだからオーバーロード多重定義してる引数が全てString型のを使う
                User user = this.createUser(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
                users.add(user);
            }
        } catch(IOException e){
            e.printStackTrace();
            // ignore
        }
        return Collections.unmodifiableList(users);
    }

    // 引数が全てStringです  シグネチャが違うので、オーバーロード多重定義できる
    @SuppressWarnings("deprecation")
    public User createUser( String id, String name, String gender, String age, String pass, String roll, String mail, String tel){
        // Stringから intへ変換してIntegerへ変換が必要 nullだとエラー
        int intId = Integer.parseInt(id);
        Integer integerId = new Integer(intId);  // 推奨されないけど、使う
        // 一気に
        Integer integerGender = new Integer(gender);
        Integer integerAge = new Integer(age);
        Integer integerRoll = new Integer(roll);

        return this.createUser(integerId, name, integerGender, integerAge, pass, integerRoll, mail, tel);
    }

    // シグネチャが違うので、オーバーロード多重定義できる
    public User createUser(Integer id, String name, Integer gender, Integer age, String pass, Integer roll, String mail, String tel){
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

}
